package com.gorga.frontend;

import com.gorga.frontend.observers.Observer;
import com.gorga.frontend.observers.ScoreManager;

public class GameManager {
    private static GameManager instance;
    private Observer observer;
    private ScoreManager scoreManager;
    private boolean gameActive;

    private GameManager() {
        scoreManager = new ScoreManager();
        gameActive = false;
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
        System.out.println("Game Started!");
    }

    public void setScore(int newScore) {
        if (gameActive) {
            scoreManager.setScore(newScore);
        }
    }

    // Getters
    public int getScore() { return scoreManager.getScore(); }

    public void addObserver(Observer observer){
        scoreManager.addObserver(observer);
    }

    public void removeObserver(Observer observer){
        scoreManager.removeObserver(observer);
    }
}
