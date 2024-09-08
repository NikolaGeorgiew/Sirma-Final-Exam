package com.example.finalexam.controller;

import com.example.finalexam.model.Player;
import com.example.finalexam.service.PlayerService;
import com.example.finalexam.utils.CsvParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class PlayerController {
    @Autowired
    private CsvParser csvParser;
    @Autowired
    private PlayerService playerService;

    @GetMapping("/load-players")
    public String loadPlayers() {
        try {
            csvParser.loadPlayers();
            return "Players have been loaded and saved to the database";
        } catch (IOException e) {
            return "Error occurred while loading players!";
        }
    }

    @GetMapping("/players")
    public ResponseEntity<List<Player>> getAllPlayers() {
        List<Player> players = playerService.getAllPlayers();
        return new ResponseEntity<>(players, HttpStatus.OK);
    }
    @GetMapping("/players/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id) {
        Optional<Player> player = playerService.getPlayerById(id);
        return player.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    //Create a new player
    @PostMapping("/players/create")
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        try {
            Player createdPlayer = playerService.createPlayer(player);
            return new ResponseEntity<>(createdPlayer, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    //TODO: UPDATE method
    //Delete a player by ID
    @DeleteMapping("/players/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        if (playerService.getPlayerById(id).isPresent()) {
            playerService.deletePlayer(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
