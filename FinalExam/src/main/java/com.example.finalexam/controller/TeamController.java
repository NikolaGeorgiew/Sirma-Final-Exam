package com.example.finalexam.controller;

import com.example.finalexam.model.Player;
import com.example.finalexam.model.Team;
import com.example.finalexam.service.TeamService;
import com.example.finalexam.utils.CsvParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class TeamController {
    @Autowired
    private CsvParser csvParser;
    @Autowired
    private TeamService teamService;

    @GetMapping("/load-teams")
    public String loadTeams() {
        try {
            csvParser.loadTeams();
            return "Teams have been loaded and saved to the database";
        } catch (IOException e) {
            return "Error occurred while loading teams!";
        }
    }

    //Get all teams
    @GetMapping("/teams")
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teams = teamService.getAllTeams();
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }
    //Get a team by ID
    @GetMapping("/teams/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        Team team = teamService.getTeamById(id);
        return ResponseEntity.ok(team);
    }
    //Create a team
    @PostMapping("/teams/create")
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        Team createdTeam = teamService.createTeam(team);
        return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
    }
    //TODO: UPDATE A TEAM
    @DeleteMapping("/teams/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
