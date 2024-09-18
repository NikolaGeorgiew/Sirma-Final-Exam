package com.example.finalexam.model;

import com.example.finalexam.annotations.ValidTimeRange;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
@Table(name = "records")
@ValidTimeRange
public class MatchRecord extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "player_id")
    @NotNull(message = "Player cannot be null")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "match_id")
    @NotNull(message = "Match cannot be null")
    private Match match;
    @Min(value = 0, message = "From minutes must be greater than or equal to 0")
    private Integer fromMinutes;
    @Min(value = 0, message = "To minutes must be greater than or equal to 0")
    @Max(value = 120, message = "To minutes must be less than or equal to 120") //Sometimes there is extra added time
    private Integer toMinutes;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Integer getFromMinutes() {
        return fromMinutes;
    }

    public void setFromMinutes(Integer fromMinutes) {
        this.fromMinutes = fromMinutes;
    }

    public Integer getToMinutes() {
        return toMinutes;
    }

    public void setToMinutes(Integer toMinutes) {
        this.toMinutes = toMinutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MatchRecord that = (MatchRecord) o;
        return Objects.equals(player, that.player) && Objects.equals(match, that.match) && Objects.equals(fromMinutes, that.fromMinutes) && Objects.equals(toMinutes, that.toMinutes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), player, match, fromMinutes, toMinutes);
    }
}
