package com.gorga.backend.service;

import com.gorga.backend.model.Score;
import com.gorga.backend.model.Player;
import com.gorga.backend.repository.ScoreRepository;
import com.gorga.backend.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerService playerService;

    @Transactional
    public Score createScore (Score score) {
        UUID playerId = score.getPlayerId();

        if (!playerRepository.existsById(playerId)) {
            throw new RuntimeException("Player dengan ID " + playerId + " tidak ditemukan!");
        }
        Score savedScore = scoreRepository.save(score);

        playerService.updatePlayerStats(playerId, score.getValue(), score.getCoinsCollected(), score.getDistanceTravelled());
        return savedScore;
    }

    public Optional<Score> getScoreById(UUID scoreId){
        return scoreRepository.findById(scoreId);
    }

    public List<Score> getAllScores(){
        return scoreRepository.findAll();
    }

    public List<Score> getScoresByPlayerId(UUID playerId){
        return scoreRepository.findByPlayerId(playerId);
    }

    public List<Score> getScoresByPlayerIdOrderByValue(UUID playerId){
        return scoreRepository.findByPlayerIdOrderByValueDesc(playerId);
    }

    public List<Score> getLeaderboard(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return scoreRepository.findTopScores(pageable);
    }

    public Optional<Score> getHighestScoreByPlayerId(UUID playerId){
        List<Score> scores = scoreRepository.findHighestScoreByPlayerId(playerId);
        if (scores.isEmpty()){return Optional.empty();}
        return Optional.of(scores.get(0));
    }

    public List<Score> getScoresAboveValue(Integer minValue){
        return scoreRepository.findByValueGreaterThan(minValue);
    }

    public List<Score> getRecentScores(){
        return scoreRepository.findAllByOrderByCreatedAtDesc();
    }

    public Integer getTotalCoinsByPlayerId(UUID playerId){
        Integer total = scoreRepository.getTotalCoinsByPlayerId(playerId);
        if (total != null){return total;}
        else{return 0;}
    }

    public Integer getTotalDistanceByPlayerId(UUID playerId){
        Integer total = scoreRepository.getTotalDistanceByPlayerId(playerId);
        if (total != null){return total;}
        else{return 0;}
    }

    public Score updateScore(UUID scoreId, Score updatedScore){
        Score existingScore = scoreRepository.findById(scoreId).orElseThrow(() -> new RuntimeException("Score tidak ditemukan"));
        if (updatedScore.getValue() != null){
            existingScore.setValue(updatedScore.getValue());
        }
        if (updatedScore.getCoinsCollected() != null){
            existingScore.setCoinsCollected(updatedScore.getCoinsCollected());
        }
        if (updatedScore.getDistanceTravelled() != null){
            existingScore.setDistanceTravelled(updatedScore.getDistanceTravelled());
        }
        return scoreRepository.save(existingScore);
    }

    public void deleteScore(UUID scoreId){
        if (scoreRepository.existsById(scoreId)){
            scoreRepository.deleteById(scoreId);
        }
        else{throw new RuntimeException("Score tidak ditemukan");}
    }

    public void deleteScoresByPlayerId(UUID playerId) {
        List<Score> scores = scoreRepository.findByPlayerId(playerId);
        scoreRepository.deleteAll(scores);
    }
}
