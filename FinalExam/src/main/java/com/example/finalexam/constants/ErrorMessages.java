package com.example.finalexam.constants;

public class ErrorMessages {
    public static final String TEAM_NOT_FOUND_MESSAGE = "Team with ID: %d not found";
    public static final String TEAMS_NOT_FOUND_MESSAGE = "Teams not found";
    public static final String MATCHES_NOT_FOUND_MESSAGE = "Matches not found";
    public static final String PLAYER_NOT_FOUND_MESSAGE = "Player with ID: %d not found";
    public static final String PLAYERS_NOT_FOUND_MESSAGE = "Players not found";
    public static final String MATCH_NOT_FOUND_MESSAGE = "Match with ID: %d not found";
    public static final String TEAM_ALREADY_EXISTS_MESSAGE = "Team already exists";
    public static final String PLAYER_ALREADY_EXISTS_MESSAGE = "Player already exists";
    public static final String MATCH_ALREADY_EXISTS_MESSAGE = "Match already exists";
    public static final String NO_CHANGES_MADE_MESSAGE = "No changes were made";
    public static final String DATE_FORMAT_MESSAGE = "Date format not supported: %s";
    public static final String MINUTES_VALIDATION_MESSAGE = "To minutes must be greater than or equal to from minutes";
    public static final String TEAMS_LOADING_MESSAGE = "Teams loaded successfully";
    public static final String PLAYERS_LOADING_MESSAGE = "Players loaded successfully";
    public static final String MATCHES_LOADING_MESSAGE = "Matches loaded successfully";
    public static final String RECORDS_LOADING_MESSAGE = "Records loaded successfully";
    public static final String LOADING_ERROR_MESSAGE = "Error occurred while populating the database";
    public static final String DELETING_WITH_RELATIONS_MESSAGE = "Cannot delete entity with relations";

    private ErrorMessages() {
        // hide implicit public one
    }
}
