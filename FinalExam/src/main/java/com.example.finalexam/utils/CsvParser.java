package com.example.finalexam.utils;

import com.example.finalexam.constants.DateFormatConstants;
import com.example.finalexam.constants.ErrorMessages;
import com.example.finalexam.model.Match;
import com.example.finalexam.model.MatchRecord;
import com.example.finalexam.model.Player;
import com.example.finalexam.model.Team;
import com.example.finalexam.repository.MatchRepository;
import com.example.finalexam.repository.PlayerRepository;
import com.example.finalexam.repository.MatchRecordRepository;
import com.example.finalexam.repository.TeamRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static com.example.finalexam.constants.FilePaths.*;

@Component
public class CsvParser {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private MatchRecordRepository recordRepository;


    private List<String[]> readCsv(String filePath) throws IOException {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstRow = true;
            while ((line = br.readLine()) != null) {
                if (isFirstRow) {
                    isFirstRow = false; //Skip header
                    continue;
                }
                String[] values = line.split(",");
                rows.add(values); // Add the values array to the list
            }
        }
        return rows;
    }

    public void loadPlayers() throws IOException {
        List<String[]> rows = readCsv(PLAYERS_FILE_PATH);
        List<Player> players = new ArrayList<>();

        for (String[] values : rows) {

            //Fetch the corresponding team based on the TeamID from the CSV
            Long teamId = Long.parseLong(values[4].trim());
            Team team = teamRepository.findById(teamId).orElse(null);

            // Create a new Player object and set its fields
            Player player = new Player();
            player.setId(Long.parseLong(values[0].trim()));
            player.setTeamNumber(Integer.parseInt(values[1].trim()));
            player.setPosition(values[2].trim());
            player.setFullName(values[3].trim());
            player.setTeam(team); //Assign the team to the player

            // Add the player to the list
            players.add(player);
        }

        // Save all players to the database
        playerRepository.saveAll(players);
    }

    public void loadMatches() throws IOException {
        List<String[]> rows = readCsv(MATCHES_FILE_PATH);
        List<Match> matches = new ArrayList<>();

        for (String[] values : rows) {
            //Create a new Match object and set its fields
            Match match = new Match();
            match.setId(Long.parseLong(values[0].trim()));
            match.setaTeamID(Long.parseLong(values[1].trim()));
            match.setbTeamID(Long.parseLong(values[2].trim()));
            //Parse the date from the CSV
            LocalDate matchDate = parseDate(values[3].trim());
            match.setDate(matchDate);
            match.setScore(values[4].trim());

            //Add the match to the list
            matches.add(match);
        }

        //Save all teams to the database
        matchRepository.saveAll(matches);
    }

    public void loadTeams() throws IOException {
        List<String[]> rows = readCsv(TEAMS_FILE_PATH);
        List<Team> teams = new ArrayList<>();

        for (String[] values : rows) {
            //Create a new Team object and set its fields
            Team team = new Team();
            team.setId(Long.parseLong(values[0].trim()));
            team.setName(values[1].trim());
            team.setManagerFullName(values[2].trim());
            team.setTeamGroup(values[3].trim());

            teams.add(team);
        }
        //Save all teams to the database
        teamRepository.saveAll(teams);
    }

    public void loadRecords() throws IOException {
        List<String[]> rows = readCsv(RECORDS_FILE_PATH);
        List<MatchRecord> records = new ArrayList<>();

        for (String[] values : rows) {

            //Fetch the corresponding player and match based on the player id and match id
            Long playerId = Long.parseLong(values[1].trim());
            Long matchId = Long.parseLong(values[2].trim());

            Player player = playerRepository.findById(playerId).orElse(null);
            Match match = matchRepository.findById(matchId).orElse(null);

            //Handle if the player ot match doesn't exist
            if (player == null || match == null) {
                continue; //Skip if player or match is not found
            }
            //Create a new Record object and set its fields
            MatchRecord matchRecord = new MatchRecord();
            matchRecord.setId(Long.parseLong(values[0].trim()));
            matchRecord.setPlayer(player);
            matchRecord.setMatch(match);
            matchRecord.setFromMinutes(Integer.parseInt(values[3].trim()));
            //Handle null in toMinutes (assuming 90 minutes if NULL)
            if (values[4].trim().equalsIgnoreCase("NULL")) {
                matchRecord.setToMinutes(90);
            } else {
                matchRecord.setToMinutes(Integer.parseInt(values[4].trim()));
            }
            //Add the record to the list
            records.add(matchRecord);
        }
        //Save all record to the database
        recordRepository.saveAll(records);
    }

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

    //Populating the database when starting the application
    @PostConstruct
    public void populateDatabase() {
        try {
            //Load teams first
            loadTeams();
            System.out.println(ErrorMessages.TEAMS_LOADING_MESSAGE);
            //Load players after teams
            loadPlayers();
            System.out.println(ErrorMessages.PLAYERS_LOADING_MESSAGE);
            //Load matches after players
            loadMatches();
            System.out.println(ErrorMessages.MATCHES_LOADING_MESSAGE);
            //Load records last
            loadRecords();
            System.out.println(ErrorMessages.RECORDS_LOADING_MESSAGE);
        } catch (IOException e) {
            System.out.println(ErrorMessages.LOADING_ERROR_MESSAGE);
        }
    }
}