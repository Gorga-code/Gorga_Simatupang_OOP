package com.gorga.frontend.commands;

import com.gorga.frontend.GameManager;
import com.gorga.frontend.Player;

/**
 * Concrete command for restarting the game
 */
public class RestartCommand implements Command {
    private Player player;
    private GameManager gameManager;

    public RestartCommand(Player player, GameManager gameManager) {
        this.player = player;
        this.gameManager = gameManager;
    }

    @Override
    public void execute() {
        player.reset();
        gameManager.setScore(0);
    }
}
