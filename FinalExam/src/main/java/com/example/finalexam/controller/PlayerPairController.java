package com.example.finalexam.controller;

import com.example.finalexam.model.dto.PlayerPairDTO;
import com.example.finalexam.service.PlayerPairingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlayerPairController {

    private final PlayerPairingService playerPairingService;

    @Autowired
    public PlayerPairController(PlayerPairingService playerPairingService) {
        this.playerPairingService = playerPairingService;
    }

    @GetMapping("/player-pairs")
    public ResponseEntity<List<PlayerPairDTO>> getPlayerPairsWithMaxPlaytime(@RequestParam(defaultValue = "10") int limit) {
        List<PlayerPairDTO> playerPairsWithPlayTime = playerPairingService.findPlayerPairsWithMaxPlaytime(limit);
        return new ResponseEntity<>(playerPairsWithPlayTime, HttpStatus.OK);
    }
}
