package Model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Score implements ShowDetail {
    private UUID scoreId;
    private UUID playerId;
    private int value;
    private int coinsCollected;
    private int distance;
    private LocalDateTime createdAt;

    public Score(UUID playerId, int value, int coinsCollected, int distance) {
        this.scoreId = UUID.randomUUID(); // tambahkan ID unik untuk Score
        this.playerId = playerId;
        this.value = value;
        this.coinsCollected = coinsCollected;
        this.distance = distance;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getScoreId() {
        return scoreId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public int getValue() {
        return value;
    }

    public int getCoinsCollected() {
        return coinsCollected;
    }

    public int getDistance() {
        return distance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void showDetail() {
        System.out.println("Score ID : " + scoreId);
        System.out.println("Player ID : " + playerId);
        System.out.println("Score Value : " + value);
        System.out.println("Coins Collected : " + coinsCollected);
        System.out.println("Distance : " + distance);
        System.out.println("Created At : " + createdAt);
    }
}
