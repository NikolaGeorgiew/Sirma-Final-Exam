package com.example.finalexam.service;

import com.example.finalexam.model.Match;
import com.example.finalexam.model.Player;
import com.example.finalexam.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchService {
    @Autowired
    private MatchRepository matchRepository;

    //Get all matches
    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }
    //Get a match by ID
    public Optional<Match> getMatchById(Long id) {
        //TODO check if match is not existing
        return matchRepository.findById(id);
    }
    //Create a match
    public Match createMatch(Match match) {
        //Check if the match already exists
        if (match.getId() != null && matchRepository.existsById(match.getId())){
            throw new IllegalArgumentException("Match already exists.");
        }
        return matchRepository.save(match);
    }
    //TODO: UPDATE MATCH
    //Delete a match by ID
    public void deleteMatch(Long id) {
        matchRepository.deleteById(id);
    }
}
