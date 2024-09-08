package com.example.finalexam.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long aTeamID;
    private Long bTeamID;
    private LocalDate date;
    private String score;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
