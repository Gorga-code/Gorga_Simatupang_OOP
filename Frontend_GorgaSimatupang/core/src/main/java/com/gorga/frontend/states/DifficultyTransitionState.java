package com.gorga.frontend.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.gorga.frontend.strategies.DifficultyStrategy;

public class DifficultyTransitionState implements GameState {

    private final GameStateManager gsm;
    private final PlayingState playingState;
    private final DifficultyStrategy newStrategy;
    private final BitmapFont font;
    private float timer = 2.0f; // Show screen for 2 seconds

    public DifficultyTransitionState(GameStateManager gsm, PlayingState playingState, DifficultyStrategy newStrategy) {
        this.gsm = gsm;
        this.playingState = playingState;
        this.newStrategy = newStrategy;
        this.font = new BitmapFont();
    }

    @Override
    public void update(float delta) {
        timer -= delta;
        if (timer <= 0) {
            playingState.setDifficulty(newStrategy);
            gsm.pop();
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        // Render the paused playing state in the background
        playingState.render(batch);

        // Render the transition message over it
        batch.begin();
        font.getData().setScale(2); // Make font bigger
        font.draw(batch, "DIFFICULTY INCREASED!", Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 1.5f, 0, Align.center, false);
        font.draw(batch, newStrategy.getClass().getSimpleName(), Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0, Align.center, false);
        batch.end();
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
