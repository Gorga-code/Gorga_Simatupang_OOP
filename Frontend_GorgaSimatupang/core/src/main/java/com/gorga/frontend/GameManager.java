package com.gorga.frontend;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
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

    public void setScore(int newScore) {
        if (gameActive) {
            scoreManager.setScore(newScore);
        }
    }

    public void endGame() {
        if(currentPlayerId == null){
            Gdx.app.error("Cannot submit score... ", null);

        }
    }

    // Getters
    public int getScore() { return scoreManager.getScore(); }

    // Delegate observer methods to ScoreManager
    public void addObserver(com.gorga.frontend.observers.Observer observer) {
        scoreManager.addObserver(observer);
    }

    public void removeObserver(com.gorga.frontend.observers.Observer observer) {
        scoreManager.removeObserver(observer);
    }

    public void registerPlayer(String username){
        backendService.createPlayer(username, new BackendService.RequestCallback() {
            @Override
            public void onSuccess(String response) {
                try{
                    new JsonReader().parse(response);
                }catch(Exception e){
                    String playerId = currentPlayerId;
                    Gdx.app.log("Player", playerId);
                }
            }

            @Override
            public void onError(String error) {
                Gdx.app.error("error", error);
            }
        });

    }
}
