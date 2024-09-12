package com.example.finalexam.controller;

import com.example.finalexam.model.Team;
import com.example.finalexam.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {
    @Autowired
    private TeamService teamService;

    //Get all teams
    @GetMapping()
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teams = teamService.getAll();
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    //Get a team by ID
    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        Team team = teamService.getEntityById(id);
        return ResponseEntity.ok(team);
    }

    //Create a team
    @PostMapping("/create")
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        Team createdTeam = teamService.createEntity(team);
        return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
    }

    //Update a team
    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @RequestBody Team updatedTeam) {
        Team team = teamService.updateEntity(id, updatedTeam);
        return ResponseEntity.ok(team);
    }

    //Delete a team
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteEntity(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
