package com.example.finalexam.controller;

import com.example.finalexam.model.Match;
import com.example.finalexam.service.MatchService;
import com.example.finalexam.utils.CsvParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class MatchController {
    @Autowired
    private CsvParser csvParser;
    @Autowired
    private MatchService matchService;

    @GetMapping("/load-matches")
    public String loadMatches() {
        try {
            csvParser.loadMatches();
            return "Matches have been loaded and saved to the database";
        } catch (IOException e) {
            return "Error occurred while loading matches!";
        }
    }
    //Get all matches
    @GetMapping("/matches")
    public ResponseEntity<List<Match>> getAllMatches() {
            List<Match> matches = matchService.getAllMatches();
            return new ResponseEntity<>(matches, HttpStatus.OK);
    }
    //Get a match by ID
    @GetMapping("/matches/{id}")
    public ResponseEntity<Match> getMatchById(@PathVariable Long id) {
            Match match = matchService.getMatchById(id);
            return ResponseEntity.ok(match);
    }
    //Create a new match
    @PostMapping("/matches/create")
    public ResponseEntity<Match> createMatch(@RequestBody Match match) {
            Match createdMatch = matchService.createMatch(match);
            return new ResponseEntity<>(createdMatch, HttpStatus.CREATED);
    }
    //TODO: Update match
    //Delete a match by ID
    @DeleteMapping("/matches/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
            matchService.deleteMatch(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
