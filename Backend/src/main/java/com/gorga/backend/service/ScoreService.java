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

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerService playerService;

    /**
     * Membuat skor baru dan update statistik player terkait
     */
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

    /**
     * Ambil skor berdasarkan ID
     */
    public Optional<Score> getScoreById(UUID scoreId) {
        return scoreRepository.findById(scoreId);
    }

    /**
     * Ambil semua skor
     */
    public List<Score> getAllScores() {
        return scoreRepository.findAll();
    }

    /**
     * Ambil semua skor berdasarkan ID player
     */
    public List<Score> getScoresByPlayerId(UUID playerId) {
        return scoreRepository.findByPlayerId(playerId);
    }

    /**
     * Ambil semua skor player dan urutkan dari terbesar ke terkecil
     */
    public List<Score> getScoresByPlayerIdOrderByValue(UUID playerId) {
        return scoreRepository.findByPlayerIdOrderByValueDesc(playerId);
    }

    /**
     * Ambil daftar skor tertinggi (leaderboard)
     */
    public List<Score> getLeaderboard(int limit) {
        return scoreRepository.findTopScores(limit);
    }
}
