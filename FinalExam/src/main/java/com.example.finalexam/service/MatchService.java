package com.example.finalexam.service;

import com.example.finalexam.exceptions.EntityNotFoundException;
import com.example.finalexam.model.Match;
import com.example.finalexam.model.dto.MatchDTO;
import com.example.finalexam.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

@Service
public class MatchService {
    @Autowired
    private MatchRepository matchRepository;
    //Defining multiple date formats
    private static final List<DateTimeFormatter> dateFormatters = Arrays.asList(
            DateTimeFormatter.ofPattern("M/d/yyyy"), //US short
            DateTimeFormatter.ofPattern("yyyy-MM-dd"), // ISO
            DateTimeFormatter.ofPattern("dd/MM/yyyy"), // European short
            DateTimeFormatter.ofPattern("d-MMM-yyyy"), //European verbose
            DateTimeFormatter.ofPattern("MM-dd-yyyy"), //Another US format
            DateTimeFormatter.ofPattern("yyyyMMdd") // No separator
    );

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
        //Check if match is missing and returning entity or exception message
        return matchRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Match with ID " + id + " not found"));
    }

    //Create a match
    public Match createMatch(MatchDTO matchDTO) {
        Match match = mapDtoToMatch(matchDTO);
        boolean matchExists = matchRepository.existsByATeamIDAndBTeamIDAndDateAndScore(
                match.getaTeamID(), match.getbTeamID(), match.getDate(), match.getScore());
        if (matchExists) {
            throw new EntityNotFoundException("Match already exists");
        }
        return matchRepository.save(match);
    }

    //Method for converting DTO to Entity
    private Match mapDtoToMatch(MatchDTO matchDTO) {
        Match match = new Match();
        match.setaTeamID(matchDTO.getaTeamID());
        match.setbTeamID(matchDTO.getbTeamID());
        match.setDate(parseDate(matchDTO.getDate()));
        match.setScore(matchDTO.getScore());
        return match;
    }

    //Helper method for parsing dates
    private LocalDate parseDate(String date) {
        for (DateTimeFormatter formatter : dateFormatters) {
            try {
                return LocalDate.parse(date, formatter);
            } catch (DateTimeParseException e) {
                //Ignore
            }
        }
        throw new IllegalArgumentException("Date format not supported: " + date);
    }

    //Update a match
    public Match updateMatch(Long id, MatchDTO matchDTO) {
        Match existingMatch = matchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Match with ID " + id + " not found"));
        Match updatedMatch = mapDtoToMatch(matchDTO);
        existingMatch.setaTeamID(updatedMatch.getaTeamID());
        existingMatch.setbTeamID(updatedMatch.getbTeamID());
        existingMatch.setDate(updatedMatch.getDate());
        existingMatch.setScore(updatedMatch.getScore());

        return matchRepository.save(existingMatch);
    }

    //Delete a match by ID
    public void deleteMatch(Long id) {
        //Check if the match exist
        Match match = matchRepository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("Match with ID " + id + " not found"));
        //If exists delete it
        matchRepository.deleteById(id);
    }
}
