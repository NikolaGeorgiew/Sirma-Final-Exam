package com.example.finalexam.controller;

import com.example.finalexam.service.PlayerPairingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    public List<String> getPlayerPairsWithMaxPlaytime() {
        return playerPairingService.findPlayerPairsWithMaxPlaytime();
    }
}
