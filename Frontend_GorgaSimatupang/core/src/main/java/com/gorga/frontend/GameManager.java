package com.gorga.frontend;

import com.gorga.frontend.observers.Observer;

public class GameManager {
    private static GameManager instance;
    private Observer observer;
    private int scoreManager;
    private boolean gameActive;

    private GameManager() {
        scoreManager = 0;
        gameActive = false;
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void startGame() {
        scoreManager = 0;
        gameActive = true;
        System.out.println("Game Started!");
    }

    public void setScore(int newScore) {
        if (gameActive) {
            scoreManager = newScore;
        }
    }

    // Getters
    public int getScore() { return scoreManager; }

    public void addObserver(Observer observer){
        scoreManager.addObserver(observer);
    }

    public void removeObserver(Observer observer){
        scoreManager.removeObserver(observer);
    }
}
