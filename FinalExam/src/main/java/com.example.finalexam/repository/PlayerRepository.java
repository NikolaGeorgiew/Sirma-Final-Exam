package com.example.finalexam.repository;

import com.example.finalexam.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    boolean existsByTeamNumberAndPositionAndFullName(Integer teamNumber, String position, String fullName);
}
