package com.example.finalexam.service;

import com.example.finalexam.exceptions.EntityNotFoundException;
import com.example.finalexam.model.Match;
import com.example.finalexam.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {
    @Autowired
    private MatchRepository matchRepository;

    //Get all matches
    public List<Match> getAllMatches() {
        List<Match> matches = matchRepository.findAll();
        //Check if the list is empty
        if (matches.isEmpty()) {
            throw new EntityNotFoundException("No matches found");
        }
        return matches;
    }
    //Get a match by ID
    public Match getMatchById(Long id) {
        return matchRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Match with ID " + id + " not found"));
    }
    //Create a match
    public Match createMatch(Match match) {
//        //Check if the match already exists by ID
//        if (match.getId() != null && matchRepository.existsById(match.getId())){
//            throw new IllegalArgumentException("Match already exists.");
//        }
        //Check if a match with the same aTeamID, bTeamID, date and score already exists
        boolean matchExists = matchRepository.existsByATeamIDAndBTeamIDAndDateAndScore(
                match.getaTeamID(), match.getbTeamID(), match.getDate(), match.getScore());
        if (matchExists) {
            throw new EntityNotFoundException("Match already exists");
        }
        return matchRepository.save(match);

    }
    //TODO: UPDATE MATCH
    //Delete a match by ID
    public void deleteMatch(Long id) {
        //Check if the match exist
        Match match = matchRepository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("Match with ID " + id + " not found"));
        //If exists delete it
        matchRepository.deleteById(id);
    }
}
