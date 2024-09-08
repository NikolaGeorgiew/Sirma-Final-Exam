package com.example.finalexam.service;

import com.example.finalexam.model.Player;
import com.example.finalexam.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;
    // Get all players
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }
    // Get a player by ID
    public Optional<Player> getPlayerById(Long id) {
        return playerRepository.findById(id);
    }
    //Create a new player
    public Player createPlayer(Player player) {
        //Check if the player already exists
        if (player.getId() != null && playerRepository.existsById(player.getId())){
            throw new IllegalArgumentException("Player already exists.");
        }
        return playerRepository.save(player);
    }
    //TODO: UPDATE method
    //Delete a player by ID
    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }
}
