package com.example.finalexam.service;

import com.example.finalexam.model.Player;
import com.example.finalexam.model.Team;
import com.example.finalexam.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    //Get all teams
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }
    //Get a team by ID
    //TODO validation if there is not such a team
    public Optional<Team> getTeamById(Long id){
        return teamRepository.findById(id);
    }
    //Create a team
    public Team createTeam(Team team) {
        //Check if the team already exists
        if (team.getId() != null && teamRepository.existsById(team.getId())){
            throw new IllegalArgumentException("Team already exists.");
        }
        return teamRepository.save(team);
    }
    //TODO: UPDATE A TEAM
    //Delete a team by ID
    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }
}
