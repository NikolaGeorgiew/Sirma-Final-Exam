package com.example.finalexam.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

@Entity
@Table(name = "matches")
public class Match extends BaseEntity {
    @NotNull(message = "Team A ID cannot be null")
    private Long aTeamID;
    @NotNull(message = "Team B ID cannot be null")
    private Long bTeamID;
    @NotNull(message = "Date cannot be null")
    @PastOrPresent(message = "Match date cannot be in the future")
    private LocalDate date;
    @NotBlank(message = "Score cannot be blank")
    private String score;

    public Long getaTeamID() {
        return aTeamID;
    }

    public void setaTeamID(Long aTeamID) {
        this.aTeamID = aTeamID;
    }

    public Long getbTeamID() {
        return bTeamID;
    }

    public void setbTeamID(Long bTeamID) {
        this.bTeamID = bTeamID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}