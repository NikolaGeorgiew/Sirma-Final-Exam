package com.example.finalexam.model;

import jakarta.persistence.*;

@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer teamNumber;
    private String position;
    private String fullName;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
}
