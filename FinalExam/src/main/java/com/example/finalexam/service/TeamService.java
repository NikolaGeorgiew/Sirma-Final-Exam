package com.example.finalexam.service;

import com.example.finalexam.constants.ErrorMessages;
import com.example.finalexam.exceptions.EntityAlreadyExistsException;
import com.example.finalexam.exceptions.NoChangesMadeException;
import com.example.finalexam.model.Team;
import com.example.finalexam.repository.PlayerRepository;
import com.example.finalexam.repository.TeamRepository;
import com.example.finalexam.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService implements CrudService<Team> {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayerRepository playerRepository;

    //Get all teams
    @Override
    public List<Team> getAll() {
        List<Team> teams = teamRepository.findAll();
        //Check if the list is empty
        if (teams.isEmpty()) {
            throw new EntityNotFoundException(ErrorMessages.TEAMS_NOT_FOUND_MESSAGE);
        }
        return teams;
    }

    //Get a team by ID
    @Override
    public Team getEntityById(Long id) {
        //Check if team is missing and returning entity or exception message
        return teamRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format(ErrorMessages.TEAM_NOT_FOUND_MESSAGE, id)));
    }

    //Create a team
    @Override
    public Team createEntity(Team team) {
        //Check if the team already exists
        boolean teamExists = teamRepository.existsByNameAndManagerFullNameAndTeamGroup(
                team.getName(), team.getManagerFullName(), team.getTeamGroup());
        if (teamExists) {
            throw new EntityAlreadyExistsException(ErrorMessages.TEAM_ALREADY_EXISTS_MESSAGE);
        }
        return teamRepository.save(team);
    }

    //Update a team
    @Override
    public Team updateEntity(Long id, Team updatedTeam) {
        //Fetch the existing team from the repository or return exception
        Team existingTeam = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ErrorMessages.TEAM_NOT_FOUND_MESSAGE, id)));

        //Check if the fields are not updated(no changes)
        if (hasNoChanges(updatedTeam, existingTeam)) {
            throw new NoChangesMadeException(ErrorMessages.NO_CHANGES_MADE_MESSAGE);
        }
        //Update the team's details
        existingTeam.setName(updatedTeam.getName());
        existingTeam.setManagerFullName(updatedTeam.getManagerFullName());
        existingTeam.setTeamGroup(updatedTeam.getTeamGroup());

        return teamRepository.save(existingTeam);
    }

    //Delete a team by ID
    @Override
    public void deleteEntity(Long id) {
        //Check if the team exists
        teamRepository
                .findById(id).orElseThrow(() -> new EntityNotFoundException(String.format(ErrorMessages.TEAM_NOT_FOUND_MESSAGE, id)));
        //Check if there are players in the team
        if (playerRepository.existsByTeamId(id)) {
            throw new IllegalStateException(ErrorMessages.DELETING_WITH_RELATIONS_MESSAGE);
        }
        //If exists delete it
        teamRepository.deleteById(id);
    }

    //Validation method
    private static boolean hasNoChanges(Team updatedTeam, Team existingTeam) {
        return existingTeam.getName().equals(updatedTeam.getName()) &&
                existingTeam.getManagerFullName().equals(updatedTeam.getManagerFullName()) &&
                existingTeam.getTeamGroup().equals(updatedTeam.getTeamGroup());
    }
}
