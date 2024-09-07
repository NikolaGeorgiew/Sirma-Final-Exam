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

}
