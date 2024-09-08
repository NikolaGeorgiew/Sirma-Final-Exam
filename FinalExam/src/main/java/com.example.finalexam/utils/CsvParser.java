package com.example.finalexam.utils;

import com.example.finalexam.model.Match;
import com.example.finalexam.model.MatchRecord;
import com.example.finalexam.model.Player;
import com.example.finalexam.model.Team;
import com.example.finalexam.repository.MatchRepository;
import com.example.finalexam.repository.PlayerRepository;
import com.example.finalexam.repository.RecordRepository;
import com.example.finalexam.repository.TeamRepository;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvParser {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private RecordRepository recordRepository;

    private final String playersFilePath = "C:\\Users\\User\\IdeaProjects\\FinalExam\\data\\players.csv";
    private final String teamsFilePath = "C:\\Users\\User\\IdeaProjects\\FinalExam\\data\\teams.csv";
    private final String matchesFilePath = "C:\\Users\\User\\IdeaProjects\\FinalExam\\data\\matches.csv";
    private final String recordFilePath = "C:\\Users\\User\\IdeaProjects\\FinalExam\\data\\records.csv";

    public List<Player> loadPlayers() throws IOException {
        List<Player> players = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(playersFilePath))) {
            String line;
            boolean isFirstRow = true; //Flag to skip the first row(header)
            //Read lines from the CSV file
            while ((line = br.readLine()) != null) {
                //Skip the header row
                if (isFirstRow) {
                    isFirstRow = false;
                    continue;
                }

                // Split the line by commas
                String[] values = line.split(",");

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
        }
        // Save all players to the database
        playerRepository.saveAll(players);
        return players;
    }
    public List<Match> loadMatches() throws IOException {
        List<Match> matches = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");

        try (BufferedReader br = new BufferedReader(new FileReader(matchesFilePath))) {
            String line;
            boolean isFirstRow = true;
            while ((line = br.readLine()) != null) {
                if (isFirstRow) {
                    isFirstRow = false;
                    continue;
                }
                String[] values = line.split(",");
                //Create a new Match object and set its fields
                Match match = new Match();
                match.setId(Long.parseLong(values[0].trim()));
                match.setaTeamID(Long.parseLong(values[1].trim()));
                match.setbTeamID(Long.parseLong(values[2].trim()));

                //Parse the date from the CSV
                LocalDate matchDate = LocalDate.parse(values[3].trim(), formatter);
                match.setDate(matchDate);
                match.setScore(values[4].trim());

                //Add the match to the list
                matches.add(match);

            }
        }
        //Save all teams to the database
        matchRepository.saveAll(matches);
        return matches;
    }
    public List<Team> loadTeams() throws IOException {
        List<Team> teams = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(teamsFilePath))) {
            String line;
            boolean isFirstRow = true;
            while ((line = br.readLine()) != null) {
                if (isFirstRow) {
                    isFirstRow = false;
                    continue;
                }
                String[] values = line.split(",");
                //Create a new Team object and set its fields
                Team team = new Team();
                team.setId(Long.parseLong(values[0].trim()));
                team.setName(values[1].trim());
                team.setManagerFullName(values[2].trim());
                team.setTeamGroup(values[3].trim());

                teams.add(team);
            }
        }
        //Save all teams to the database
        teamRepository.saveAll(teams);
        return teams;
    }
    public List<MatchRecord> loadRecords() throws IOException {
        List<MatchRecord> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(recordFilePath))) {
            String line;
            boolean isFirstRow = true;
            while ((line = br.readLine()) != null) {
                if (isFirstRow) {
                    isFirstRow = false;
                    continue;
                }

                String[] values = line.split(",");
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
        }
        //Save all record to the database
        recordRepository.saveAll(records);
        return records;
    }
}
