package com.example.finalexam.model.dto;

public class MatchDTO {
    private Long aTeamID;
    private Long bTeamID;
    private String date; //String to allow different formats
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
