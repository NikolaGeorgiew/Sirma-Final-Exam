package com.example.finalexam.service;

import com.example.finalexam.constants.DateFormatConstants;
import com.example.finalexam.constants.ErrorMessages;
import com.example.finalexam.exceptions.EntityAlreadyExistsException;
import com.example.finalexam.exceptions.NoChangesMadeException;
import com.example.finalexam.model.dto.MatchDTO;
import com.example.finalexam.exceptions.EntityNotFoundException;
import com.example.finalexam.model.Match;
import com.example.finalexam.repository.MatchRepository;
import com.example.finalexam.repository.MatchRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class MatchService implements CrudService<Match> {

    private final MatchRepository matchRepository;

    private final MatchRecordRepository recordRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository, MatchRecordRepository recordRepository) {
        this.matchRepository = matchRepository;
        this.recordRepository = recordRepository;
    }

    //Get all matches
    @Override
    public List<Match> getAll() {
        List<Match> matches = matchRepository.findAll();
        //Check if the list is empty
        if (matches.isEmpty()) {
            throw new EntityNotFoundException(ErrorMessages.MATCHES_NOT_FOUND_MESSAGE);
        }
        return matches;
    }

    //Get a match by ID
    @Override
    public Match getEntityById(Long id) {
        //Check if match is missing and returning entity or exception message
        return matchRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ErrorMessages.MATCH_NOT_FOUND_MESSAGE, id)));
    }

    //Create a match
    @Override
    public Match createEntity(Match match) {
        //Fetch the existing match from the repository or return exception
        boolean matchExists = matchRepository.existsByATeamIDAndBTeamIDAndDateAndScore(
                match.getaTeamID(), match.getbTeamID(), match.getDate(), match.getScore());
        if (matchExists) {
            throw new EntityAlreadyExistsException(ErrorMessages.MATCH_ALREADY_EXISTS_MESSAGE);
        }
        return matchRepository.save(match);
    }

    //Update a match
    @Override
    public Match updateEntity(Long id, Match updatedMatch) {
        //Fetch the existing match from the repository or return exception
        Match existingMatch = getEntityById(id);
        ///Check if the fields are not updated
        if (hasNoChanges(existingMatch, updatedMatch)) {
            throw new NoChangesMadeException(ErrorMessages.NO_CHANGES_MADE_MESSAGE);
        }

        //Updating the fields
        existingMatch.setaTeamID(updatedMatch.getaTeamID());
        existingMatch.setbTeamID(updatedMatch.getbTeamID());
        existingMatch.setDate(updatedMatch.getDate());
        existingMatch.setScore(updatedMatch.getScore());

        return matchRepository.save(existingMatch);
    }

    //Delete a match by ID
    @Override
    public void deleteEntity(Long id) {
        //Check if the match exist
        getEntityById(id);
        //Check if any match records are associated with this match
        if (recordRepository.existsByMatchId(id)) {
            throw new IllegalStateException(ErrorMessages.DELETING_WITH_RELATIONS_MESSAGE);
        }
        //If exists delete it
        matchRepository.deleteById(id);
    }

    //Helper method
    public Match createMatch(MatchDTO matchDTO) {
        Match match = mapDtoToMatch(matchDTO);
        return createEntity(match);

    }

    //Helper method
    public Match updateMatch(Long id, MatchDTO matchDTO) {
        Match updatedMatch = mapDtoToMatch(matchDTO);
        return updateEntity(id, updatedMatch);
    }

    private static boolean hasNoChanges(Match existingMatch, Match updatedMatch) {
        return existingMatch.getaTeamID().equals(updatedMatch.getaTeamID()) &&
                existingMatch.getbTeamID().equals(updatedMatch.getbTeamID()) &&
                existingMatch.getDate().equals(updatedMatch.getDate()) &&
                existingMatch.getScore().equals(updatedMatch.getScore());
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
        for (DateTimeFormatter formatter : DateFormatConstants.DATE_FORMATTERS) {
            try {
                return LocalDate.parse(date, formatter);
            } catch (DateTimeParseException e) {
                //Ignore
            }
        }
        throw new IllegalArgumentException(String.format(ErrorMessages.DATE_FORMAT_MESSAGE, date));
    }
}
