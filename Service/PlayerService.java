package Service;

import Model.Player;
import Repository.PlayerRepository;
import java.util.*;

public class PlayerService{
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    
    public void createPlayer(Player player) {
        playerRepository.save(player);
    }

    
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    
    public Optional<Player> getPlayerById(UUID id) {
        return playerRepository.findById(id);
    }

    
    public void updatePlayerStats(UUID id, int coins, int distance, int highScore) {
        Optional<Player> playerOpt = playerRepository.findById(id);
        playerOpt.ifPresent(player -> {
            player.addCoins(coins);
            player.addDistance(distance);
            player.updateHighScore(highScore);
        });
    }

    
    public void deletePlayer(UUID id) {
        playerRepository.deleteById(id);
    }

    
    public List<Player> getLeaderboardByCoins() {
        return playerRepository.findAllByOrderByTotalCoinsDesc();
    }

    public List<Player> getLeaderboardByDistance() {
        return playerRepository.findAllByOrderByTotalDistanceTravelledDesc();
    }

    public List<Player> getLeaderboardByHighScore(int limit) {
        return playerRepository.findTopPlayersByHighScore(limit);
    }

}
