package com.example.finalexam.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
@Table(name = "players")
public class Player extends BaseEntity {
    @NotNull(message = "Team number cannot be null")
    private Integer teamNumber;
    @NotBlank(message = "Position cannot be blank")
    private String position;
    @NotBlank(message = "Full name cannot be blank")
    private String fullName;

    @ManyToOne
    @JoinColumn(name = "team_id")
    @NotNull(message = "Team cannot be null")
    private Team team;

    public Integer getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(Integer teamNumber) {
        this.teamNumber = teamNumber;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Player player = (Player) o;
        return Objects.equals(teamNumber, player.teamNumber) && Objects.equals(position, player.position) && Objects.equals(fullName, player.fullName) && Objects.equals(team, player.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), teamNumber, position, fullName, team);
    }
}
