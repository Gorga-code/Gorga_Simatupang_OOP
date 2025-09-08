import Model.Player;
import Model.Score;

public class Main{
    public static void main(String[] args){
        Player player1 = new Player("Andi");
        Player player2 = new Player("Jokow");

        Score score1 = new Score(player1.getPlayerId(), 1500,250,5000);
        Score score2 = new Score(player2.getPlayerId(), 3200, 7500, 12000);
        Score score3 = new Score(player1.getPlayerId(), 1800, 300, 6000);

        player1.updateHighScore(score1.getValue());
        player1.addCoins(score1.getCoinsCollected());
        player1.addDistance(score1.getDistance());
       
        player1.updateHighScore(score3.getValue());
        player1.addCoins(score3.getCoinsCollected());
        player1.addDistance(score3.getDistance());

        player2.updateHighScore(score2.getValue());
        player2.addCoins(score2.getCoinsCollected());
        player2.addDistance(score2.getDistance());

        System.out.println("=== PLAYER 1 DETAIL === ");
        player1.showDetail();

        System.out.println("=== PLAYER 2 DETAIL === ");
        player2.showDetail();

    }
}