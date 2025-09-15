package Repository;

import Model.Score;
import java.util.*;
import java.util.stream.Collectors;

public class ScoreRepository extends BaseRepository<Score, UUID> {

    public List<Score> findByPlayerId(UUID playerId) {
        return entities.stream()
                .filter(s -> s.getPlayerId().equals(playerId))
                .collect(Collectors.toList());
    }

    public List<Score> findByPlayerIdOrderByValueDesc(UUID playerId) {
        return entities.stream()
                .filter(s -> s.getPlayerId().equals(playerId))
                .sorted((s1, s2) -> Integer.compare(s2.getValue(), s1.getValue()))
                .collect(Collectors.toList());
    }

    public List<Score> findTopScores(int limit) {
        return entities.stream()
                .sorted((s1, s2) -> Integer.compare(s2.getValue(), s1.getValue()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    public Score findHighestScoreByPlayerId(UUID playerId) {
        return entities.stream()
                .filter(s -> s.getPlayerId().equals(playerId))
                .max(Comparator.comparingInt(Score::getValue))
                .orElse(null);
    }

    public List<Score> findByValueGreaterThan(Integer minValue) {
        return entities.stream()
                .filter(s -> s.getValue() > minValue)
                .collect(Collectors.toList());
    }

    public List<Score> findAllByOrderByCreatedAtDesc() {
        return entities.stream()
                .sorted((s1, s2) -> s2.getCreatedAt().compareTo(s1.getCreatedAt()))
                .collect(Collectors.toList());
    }

    public int getTotalCoinsByPlayerId(UUID playerId) {
        return entities.stream()
                .filter(s -> s.getPlayerId().equals(playerId))
                .mapToInt(Score::getCoins)
                .sum();
    }

    public int getTotalDistanceByPlayerId(UUID playerId) {
        return entities.stream()
                .filter(s -> s.getPlayerId().equals(playerId))
                .mapToInt(Score::getDistance)
                .sum();
    }

    @Override
    public void save(Score score) {
        UUID id = getId(score);
        storage.put(id, score);
        if (!entities.contains(score)) {
            entities.add(score);
        }
    }

    @Override
    public UUID getId(Score entity) {
        return entity.getScoreId();
    }
}
