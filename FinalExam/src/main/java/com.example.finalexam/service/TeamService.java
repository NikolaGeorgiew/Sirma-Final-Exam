package com.example.finalexam.service;

import com.example.finalexam.exceptions.EntityNotFoundException;
import com.example.finalexam.model.Team;
import com.example.finalexam.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    //Get all teams
    public List<Team> getAllTeams() {
        List<Team> teams = teamRepository.findAll();
        //Check if the list is empty
        if (teams.isEmpty()) {
            throw new EntityNotFoundException("No teams found");
        }
        return teams;
    }

    //Get a team by ID
    public Team getTeamById(Long id) {
        //Check if team is missing and returning entity or exception message
        return teamRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Team with ID " + id + " not found"));
    }

    //Create a team
    public Team createTeam(Team team) {
        //Check if the team already exists
        boolean teamExists = teamRepository.existsByNameAndManagerFullNameAndTeamGroup(
                team.getName(), team.getManagerFullName(), team.getTeamGroup());
        if (teamExists) {
            throw new EntityNotFoundException("Team already exists");
        }
        return teamRepository.save(team);
    }

    //Update a team
    public Team updateTeam(Long id, Team updatedTeam) {
        Team existingTeam = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Team with ID " + id + " not found"));
        //Update the team's details
        existingTeam.setName(updatedTeam.getName());
        existingTeam.setManagerFullName(updatedTeam.getManagerFullName());
        existingTeam.setTeamGroup(updatedTeam.getTeamGroup());

        return teamRepository.save(existingTeam);
    }

    //Delete a team by ID
    public void deleteTeam(Long id) {
        //Check if the team exists
        Team team = teamRepository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("Team with ID " + "id" + " not found"));
        //If exists delete it
        teamRepository.deleteById(id);
    }
}
