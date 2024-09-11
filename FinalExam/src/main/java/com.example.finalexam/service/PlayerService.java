package com.example.finalexam.service;

import com.example.finalexam.constants.ErrorMessages;
import com.example.finalexam.exceptions.EntityAlreadyExistsException;
import com.example.finalexam.exceptions.EntityNotFoundException;
import com.example.finalexam.exceptions.NoChangesMadeException;
import com.example.finalexam.model.Player;
import com.example.finalexam.repository.PlayerRepository;
import com.example.finalexam.repository.MatchRecordRepository;
import com.example.finalexam.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService implements CrudService<Player> {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MatchRecordRepository recordRepository;

    // Get all players
    @Override
    public List<Player> getAll() {
        List<Player> players = playerRepository.findAll();
        //Check if the list is empty
        if (players.isEmpty()) {
            throw new EntityNotFoundException(ErrorMessages.PLAYERS_NOT_FOUND_MESSAGE);
        }
        return players;
    }

    // Get a player by ID
    @Override
    public Player getEntityById(Long id) {
        //Check if player is missing and returning entity or exception message
        return playerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format(ErrorMessages.PLAYER_NOT_FOUND_MESSAGE, id)));
    }

    //Create a new player
    @Override
    public Player createEntity(Player player) {
        //Check if the team exists
        if (!teamRepository.existsById(player.getTeam().getId())) {
            throw new EntityNotFoundException(String.format(ErrorMessages.TEAM_NOT_FOUND_MESSAGE, player.getTeam().getId()));
        }
        //Check if the player already exists
        boolean playerExists = playerRepository.existsByTeamNumberAndPositionAndFullName(
                player.getTeamNumber(), player.getPosition(), player.getFullName());
        if (playerExists) {
            throw new EntityAlreadyExistsException(ErrorMessages.PLAYER_ALREADY_EXISTS_MESSAGE);
        }
        return playerRepository.save(player);
    }

    //Update a Player
    @Override
    public Player updateEntity(Long id, Player updatedPlayer) {
        //Check if the team exists
        if (!teamRepository.existsById(updatedPlayer.getTeam().getId())) {
            throw new EntityNotFoundException(String.format(ErrorMessages.TEAM_NOT_FOUND_MESSAGE, updatedPlayer.getTeam().getId()));
        }
        //Fetch the existing player from the repository or return exception
        Player existingPlayer = playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ErrorMessages.PLAYER_NOT_FOUND_MESSAGE, id)));
        //Check if the fields are not updated(no changes)
        if (hasNoChanges(updatedPlayer, existingPlayer)) {
            throw new NoChangesMadeException(ErrorMessages.NO_CHANGES_MADE_MESSAGE);
        }
        //Update the player's details
        existingPlayer.setTeamNumber(updatedPlayer.getTeamNumber());
        existingPlayer.setPosition(updatedPlayer.getPosition());
        existingPlayer.setFullName(updatedPlayer.getFullName());
        existingPlayer.setTeam(updatedPlayer.getTeam());

        return playerRepository.save(existingPlayer);
    }

    //Delete a player by ID
    @Override
    public void deleteEntity(Long id) {
        //Check if the player exists
        playerRepository
                .findById(id).orElseThrow(() -> new EntityNotFoundException(String.format(ErrorMessages.PLAYER_NOT_FOUND_MESSAGE, id)));
        //Check if any match records are associated with this player
        if (recordRepository.existsByPlayerId(id)) {
            throw new IllegalStateException(ErrorMessages.DELETING_WITH_RELATIONS_MESSAGE);
        }
        playerRepository.deleteById(id);
    }

    //Validation method
    private static boolean hasNoChanges(Player updatedPlayer, Player existingPlayer) {
        return existingPlayer.getTeamNumber() == updatedPlayer.getTeamNumber() &&
                existingPlayer.getPosition().equals(updatedPlayer.getPosition()) &&
                existingPlayer.getFullName().equals(updatedPlayer.getFullName()) &&
                existingPlayer.getTeam().equals(updatedPlayer.getTeam());
    }
}
