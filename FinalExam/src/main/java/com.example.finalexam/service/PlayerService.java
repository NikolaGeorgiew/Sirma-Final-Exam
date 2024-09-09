package com.example.finalexam.service;

import com.example.finalexam.exceptions.EntityNotFoundException;
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
        List<Player> players = playerRepository.findAll();
        //Check if the list is empty
        if (players.isEmpty()) {
            throw new EntityNotFoundException("No players found");
        }
        return players;
    }
    // Get a player by ID
    public Player getPlayerById(Long id) {
        return playerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Player with ID " + id + " not found"));
    }
    //Create a new player
    public Player createPlayer(Player player) {
        //Check if the player already exists
        boolean playerExists = playerRepository.existsByTeamNumberAndPositionAndFullName(
                player.getTeamNumber(), player.getPosition(), player.getFullName());
        if (playerExists) {
            throw new EntityNotFoundException("Player already exists");
        }
        return playerRepository.save(player);
    }
    //Update a Player
    public Player updatePlayer(Long id, Player updatedPlayer) {
        Player existingPlayer = playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Player with ID " + id + " not found"));
        //Update the player's details
        existingPlayer.setTeamNumber(updatedPlayer.getTeamNumber());
        existingPlayer.setPosition(updatedPlayer.getPosition());
        existingPlayer.setFullName(updatedPlayer.getFullName());
        existingPlayer.setTeam(updatedPlayer.getTeam());

        return playerRepository.save(existingPlayer);
    }
    //Delete a player by ID
    public void deletePlayer(Long id) {
        //Check if the player exists
        Player player = playerRepository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("Player with ID " + id + " not found"));
        playerRepository.deleteById(id);
    }
}
