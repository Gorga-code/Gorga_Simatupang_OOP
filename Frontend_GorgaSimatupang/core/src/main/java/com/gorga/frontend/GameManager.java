package com.gorga.frontend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.gorga.frontend.observers.Observer;
import com.gorga.frontend.observers.ScoreManager;
import com.gorga.frontend.services.BackendService;

public class GameManager {
    private static GameManager instance;

    private ScoreManager scoreManager;
    private boolean gameActive;
    private BackendService backendService;
    private String currentPlayerId = null;
    private int coinsCollected = 0;

    private GameManager() {
        scoreManager = new ScoreManager();
        gameActive = false;
        backendService = new BackendService();
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void startGame() {
        scoreManager.setScore(0);
        gameActive = true;
        coinsCollected = 0;
        System.out.println("Game Started!");
    }

    public void setScore(int distance) {
        if (gameActive) {
            scoreManager.setScore(distance);
        }
    }

    public int getScore() {
        return scoreManager.getScore();
    }

    public void addObserver(Observer observer) {
        scoreManager.addObserver(observer);
    }

    public void registerPlayer(String username, final Runnable onComplete) {
        backendService.createPlayer(username, new BackendService.RequestCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JsonValue json = new JsonReader().parse(response);
                    currentPlayerId = json.getString("playerId");
                    Gdx.app.log("GameManager", "Player Registered ID: " + currentPlayerId);
                    if (onComplete != null) {
                        onComplete.run();
                    }
                } catch (Exception e) {
                    Gdx.app.error("GameManager", "Parse Error", e);
                    if (onComplete != null) {
                        onComplete.run();
                    }
                }
            }

            @Override
            public void onError(String error) {
                Gdx.app.error("GameManager", "Register Error: " + error);
                if (onComplete != null) {
                    onComplete.run();
                }
            }
        });
    }

    public void addCoin() {
        coinsCollected++;
        Gdx.app.log("GameManager", "COIN COLLECTED! Total: " + coinsCollected);
    }

    public int getCoins() {
        return coinsCollected;
    }

    public void endGame() {
        if (currentPlayerId == null) {
            Gdx.app.error("GameManager", "Cannot submit score. Player ID null.");
            return;
        }
        int distance = scoreManager.getScore();
        int finalScore = distance + (coinsCollected * 10);

        backendService.submitScore(currentPlayerId, finalScore, coinsCollected, distance,
                new BackendService.RequestCallback() {
                    @Override
                    public void onSuccess(String response) {
                        Gdx.app.log("GameManager", "Score Submitted: " + response);
                    }

                    @Override
                    public void onError(String error) {
                        Gdx.app.error("GameManager", "Submit Failed: " + error);
                    }
                });
    }
}
