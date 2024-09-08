package com.example.finalexam.repository;

import com.example.finalexam.model.MatchRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<MatchRecord, Long> {
}
