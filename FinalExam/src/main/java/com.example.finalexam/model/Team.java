package com.example.finalexam.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

@Entity
@Table(name = "teams")
public class Team extends BaseEntity {
    @NotBlank(message = "Team name cannot be blank")
    private String name;
    @NotBlank(message = "Manager full name cannot be blank")
    private String managerFullName;
    @NotBlank(message = "Team group cannot be blank")
    private String teamGroup;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManagerFullName() {
        return managerFullName;
    }

    public void setManagerFullName(String managerFullName) {
        this.managerFullName = managerFullName;
    }

    public String getTeamGroup() {
        return teamGroup;
    }

    public void setTeamGroup(String teamGroup) {
        this.teamGroup = teamGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false; //Compare id first
        Team team = (Team) o;
        return Objects.equals(name, team.name) &&
                Objects.equals(managerFullName, team.managerFullName) &&
                Objects.equals(teamGroup, team.teamGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, managerFullName, teamGroup);
    }
}
