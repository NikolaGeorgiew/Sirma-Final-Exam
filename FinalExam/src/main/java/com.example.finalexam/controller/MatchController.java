package com.example.finalexam.controller;

import com.example.finalexam.model.Match;
import com.example.finalexam.model.dto.MatchDTO;
import com.example.finalexam.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matches")
public class MatchController {
    @Autowired
    private MatchService matchService;

    //Get all matches
    @GetMapping()
    public ResponseEntity<List<Match>> getAllMatches() {
        List<Match> matches = matchService.getAll();
        return new ResponseEntity<>(matches, HttpStatus.OK);
    }

    //Get a match by ID
    @GetMapping("/{id}")
    public ResponseEntity<Match> getMatchById(@PathVariable Long id) {
        Match match = matchService.getEntityById(id);
        return ResponseEntity.ok(match);
    }

    //Create a match
    @PostMapping("/create")
    public ResponseEntity<Match> createMatch(@RequestBody MatchDTO matchDTO) {
        Match createdMatch = matchService.createMatch(matchDTO);
        return new ResponseEntity<>(createdMatch, HttpStatus.CREATED);
    }

    //Update a match
    @PutMapping("/{id}")
    public ResponseEntity<Match> updateMatch(@PathVariable Long id, @RequestBody MatchDTO matchDTO) {
        Match updatedMatch = matchService.updateMatch(id, matchDTO);
        return new ResponseEntity<>(updatedMatch, HttpStatus.OK);
    }

    //Delete a match by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        matchService.deleteEntity(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
