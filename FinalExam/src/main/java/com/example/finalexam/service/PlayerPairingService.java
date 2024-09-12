package com.example.finalexam.service;

import com.example.finalexam.model.MatchRecord;
import com.example.finalexam.repository.MatchRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PlayerPairingService {

    private final MatchRecordRepository recordRepository;

    @Autowired
    public PlayerPairingService(MatchRecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    //Helper method to calculate overlap in minutes for two players in a match
    private int calculateOverlap(MatchRecord record1, MatchRecord record2) {
        int start1 = record1.getFromMinutes();
        int end1 = record1.getToMinutes();
        int start2 = record2.getFromMinutes();
        int end2 = record2.getToMinutes();

        //Calculating overlap
        int start = Math.max(start1, start2);
        int end = Math.min(end1, end2);
        return Math.max(0, end - start); // Ensure no negative overlap
    }

    public List<String> findPlayerPairsWithMaxPlaytime() {
        //Fetch all records from the database
        List<MatchRecord> records = recordRepository.findAll();

        //Map to store the total playtime of each pair of players
        Map<String, Integer> playerPairPlaytime = new HashMap<>();

        //Group records by match
        Map<Long, List<MatchRecord>> recordsByMatch =
                records.stream().collect(Collectors.groupingBy(mr -> mr.getMatch().getId()));

        //Iterate through each match's records
        for (Map.Entry<Long, List<MatchRecord>> matchEntry : recordsByMatch.entrySet()) {
            List<MatchRecord> matchRecords = matchEntry.getValue();

            //Iterate through each pair of players in the same match
            for (int i = 0; i < matchRecords.size(); i++) {
                for (int j = i + 1; j < matchRecords.size(); j++) {
                    MatchRecord record1 = matchRecords.get(i);
                    MatchRecord record2 = matchRecords.get(j);

                    //Calculate the overlap of their playing time
                    int overlap = calculateOverlap(record1, record2);

                    if (overlap > 0) {
                        //Create a unique key for the pair of players (sorted by player IDs)
                        Long player1Id = record1.getPlayer().getId();
                        Long player2Id = record2.getPlayer().getId();
                        String pairKey = player1Id < player2Id ? player1Id + "," + player2Id : player2Id + "," + player1Id;

                        //Update the total playtime for this pair
                        playerPairPlaytime.put(pairKey, playerPairPlaytime.getOrDefault(pairKey, 0) + overlap);
                    }
                }
            }
        }
        //Sort the pairs by playtime in descending order
        return playerPairPlaytime.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .map(entry -> entry.getKey() + "," + entry.getValue())
                .toList();
    }
}
