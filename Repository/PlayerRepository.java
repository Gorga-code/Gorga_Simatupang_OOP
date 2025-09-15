package Repository;

import Model.Player;
import java.util.*;
import java.util.stream.Collectors;

public class PlayerRepository extends BaseRepository<Player, UUID> {

    
    public Player findByUsername(String username) {
        return entities.stream()
                .filter(p -> p.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
    }

    
    public List<Player> findTopPlayersByHighScore(int limit) {
        return entities.stream()
                .sorted((p1, p2) -> Integer.compare(p2.getHighScore(), p1.getHighScore()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    
    public List<Player> findByHighscoreGreaterThan(int minScore) {
        return entities.stream()
                .filter(p -> p.getHighScore() > minScore)
                .collect(Collectors.toList());
    }

    
    public List<Player> findAllByOrderByTotalCoinsDesc() {
        return entities.stream()
                .sorted((p1, p2) -> Integer.compare(p2.getTotalCoins(), p1.getTotalCoins()))
                .collect(Collectors.toList());
    }

    
    public List<Player> findAllByOrderByTotalDistanceTravelledDesc() {
        return entities.stream()
                .sorted((p1, p2) -> Integer.compare(p2.getTotalDistance(), p1.getTotalDistance()))
                .collect(Collectors.toList());
    }

    
    @Override
    public void save(Player player) {
        UUID id = getId(player);
        storage.put(id, player);

        
        if (!entities.contains(player)) {
            entities.add(player);
        }
    }

    
    @Override
    public UUID getId(Player entity) {
        return entity.getPlayerId();
    }
}
