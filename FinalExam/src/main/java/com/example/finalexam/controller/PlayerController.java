package com.example.finalexam.controller;

import com.example.finalexam.model.Player;
import com.example.finalexam.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    //Get all players
    @GetMapping()
    public ResponseEntity<List<Player>> getAllPlayers() {
        List<Player> players = playerService.getAll();
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    //Get player by ID
    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id) {
        Player player = playerService.getEntityById(id);
        return ResponseEntity.ok(player);
    }

    //Create a new player
    @PostMapping("/create")
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        Player createdPlayer = playerService.createEntity(player);
        return new ResponseEntity<>(createdPlayer, HttpStatus.CREATED);
    }

    //Update a player
    @PutMapping("/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable Long id, @RequestBody Player updatedPlayer) {
        Player player = playerService.updateEntity(id, updatedPlayer);
        return ResponseEntity.ok(player);
    }

    //Delete a player by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerService.deleteEntity(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
