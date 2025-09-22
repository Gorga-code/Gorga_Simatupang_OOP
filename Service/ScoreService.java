package Service;

import Model.Player;
import java.util.*;
import java.util.stream.Collectors;

public abstract class ScoreService{
     private final scoreRepository ScoreRepository;
     private final playerRepository PlayerRepository;
     private final playerService PlayerService;

     public ScoreService(ScoreRepository scoreRepository, PlayerRepository playerRepository, PlayerService playerService) {
        this.scoreRepository = scoreRepository;
        this.playerRepository = playerRepository;
        this.playerService = playerService;
    }

    public List<Score> createScore(Score score){

    }

    public Optional<Score> getScoreById(UUID scoreId){

    }

    public void getAllScores(){

    }

    public void getScoresByPlayerId(UUID playerId){

    }
    
    public List<Score> getScoresByPlayerIdOrderByValue(UUID playerId){

    }

    public List<Score> getLeaderboard(int limit){

    }

    public Optional<Score> getHighestScoreByPlayerId(UUID playerId){

    }

    public List<Score> getScoresAboveValue(int minValue){

    }

    public void  getRecentScores(){

    }

    public int getTotalCoinsByPlayerId(UUID playerId){

    }

    public int getTotalDistanceByPlayerId(UUID playerId){

    }

    public void updateScore(UUID scoreId, Score updatedScore){

    }

    public void deleteScore(UUID scoreId){

    }

    public void deleteScoresByPlayerId(UUID playerId){

    }
}