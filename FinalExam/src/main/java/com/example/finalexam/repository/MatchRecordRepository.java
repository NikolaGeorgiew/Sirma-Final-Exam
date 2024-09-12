package com.example.finalexam.repository;

import com.example.finalexam.model.MatchRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRecordRepository extends JpaRepository<MatchRecord, Long> {
    boolean existsByPlayerId(Long playerId);

    boolean existsByMatchId(Long matchId);
}
