package com.example.finalexam.repository;

import com.example.finalexam.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    boolean existsByATeamIDAndBTeamIDAndDateAndScore(Long aTeamID, Long bTeamID, LocalDate date, String score);
}
