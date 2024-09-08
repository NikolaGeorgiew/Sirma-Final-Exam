package com.example.finalexam.controller;

import com.example.finalexam.model.Match;
import com.example.finalexam.model.Player;
import com.example.finalexam.service.MatchService;
import com.example.finalexam.utils.CsvParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
        Optional<Match> match = matchService.getMatchById(id);
        return match.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    //Create a new match
    @PostMapping("/matches/create")
    public ResponseEntity<Match> createMatch(@RequestBody Match match) {
        try {
            Match createdMatch = matchService.createMatch(match);
            return new ResponseEntity<>(createdMatch, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    //TODO: Update match
    //Delete a match by ID
    @DeleteMapping("/matches/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        if (matchService.getMatchById(id).isPresent()) {
            matchService.deleteMatch(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
