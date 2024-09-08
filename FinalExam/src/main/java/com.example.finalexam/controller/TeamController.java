package com.example.finalexam.controller;

import com.example.finalexam.utils.CsvParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TeamController {
    @Autowired
    private CsvParser csvParser;

    @GetMapping("/load-teams")
    public String loadTeams() {
        try {
            csvParser.loadTeams();
            return "Teams have been loaded and saved to the database";
        } catch (IOException e) {
            return "Error occurred while loading teams!";
        }
    }
}
