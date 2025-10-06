package com.gorga.backend.service;

import com.gorga.backend.model.Score;
import com.gorga.backend.model.Player;
import com.gorga.backend.repository.ScoreRepository;
import com.gorga.backend.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerService playerService;

    @Transactional
    public Score createScore(Score score) {
        UUID playerId = score.getPlayer().getId();

        // Cek apakah player ada
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player dengan ID " + playerId + " tidak ditemukan"));

        // Simpan score baru
        Score savedScore = scoreRepository.save(score);

        // Update statistik player
        playerService.updatePlayerStats(player, savedScore);

        return savedScore;
    }

    public Optional<Score> getScoreById(UUID scoreId) {
        return scoreRepository.findById(scoreId);
    }

    public List<Score> getAllScores() {
        return scoreRepository.findAll();
    }

    public List<Score> getScoresByPlayerId(UUID playerId) {
        return scoreRepository.findByPlayerId(playerId);
    }

    public List<Score> getScoresByPlayerIdOrderByValue(UUID playerId) {
        return scoreRepository.findByPlayerIdOrderByValueDesc(playerId);
    }

    public List<Score> getLeaderboard(int limit) {
        return scoreRepository.findTopScores(limit);
    }

    public Optional<Score>getHighestScoreByPlayerId(UUID playerId){
        List<Score> scores =  scoreRepository.findHighestScoreByPlayerId(playerId);
        if(scores.isEmpty()){return Optional.empty();}
        else{return Optional.of((scores.get(0)));}
    }

    public List<Score>  getScoresAboveVale(Integer minValue){
        return scoreRepository.findByValueGreaterThan();
    }

    public List<Score> getRecentScores{
        return scoreRepository.findAllByOrderByCreatedAtDesc();
    }

    public Integer getTotalCoinsByPlayerId( UUID playerId){
        Integer total = scoreRepository.getTotalCoinsByPlayerId(playerId);
        if(total == null){return 0;}
        else{return total;}
    }

    public Integer getTotalDistanceByPlayerId(UUID playerId){
        Integer total = scoreRepository.getTotalDistanceByPlayerId(playerId);
        if(total == null){return 0;}
        else{return total;}
    }

    public Score updateScore(UUID scoreId,  Score updatedScore) {
        scoreRepository.findById(scoreId).orElseThrow(() -> new RuntimeException(...));
        Score existingScore = null;
        if(updatedScore.getValue() != null){
            existingScore = updatedScore;
        }
        assert existingScore != null;
        return scoreRepository.save(existingScore);
    }

    public void deleteScore( UUID scoreId){
        if(scoreRepository.existsById()){
            scoreRepository.deleteById(scoreId);
        }
        else{
            new RuntimeException("scoreRepository tidak ada, tidak bisa di delete");
        }
    }

    public void deleteScoresByPlayerId(UUID playerId){
        scoreRepository.findByPlayerId(playerId);
        scoreRepository.deleteAll();
    }
}

