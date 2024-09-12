package com.example.finalexam.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FilePaths {
    @Value("${file.players}")
    private String playersFilePath;

    @Value("${file.teams}")
    private String teamsFilePath;

    @Value("${file.matches}")
    private String matchesFilePath;

    @Value("${file.records}")
    private String recordsFilePath;

    // Getters
    public String getPlayersFilePath() {
        return playersFilePath;
    }

    public String getTeamsFilePath() {
        return teamsFilePath;
    }

    public String getMatchesFilePath() {
        return matchesFilePath;
    }

    public String getRecordsFilePath() {
        return recordsFilePath;
    }
}
