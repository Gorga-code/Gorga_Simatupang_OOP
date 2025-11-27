package com.gorga.frontend.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.gorga.frontend.strategies.DifficultyStrategy;

public class DifficultyTransitionState implements GameState {

    private final GameStateManager gsm;
    private final PlayingState playingState;
    private final DifficultyStrategy newStrategy;
    private final BitmapFont font;
    private final OrthographicCamera camera;
    private float timer = 2.0f;

    public DifficultyTransitionState(GameStateManager gsm, PlayingState playingState, DifficultyStrategy newStrategy) {
        this.gsm = gsm;
        this.playingState = playingState;
        this.newStrategy = newStrategy;
        this.font = new BitmapFont();
        this.font.getData().setScale(2.5f);
        this.font.setColor(Color.YELLOW);

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
        playingState.render(batch);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        String difficultyName = newStrategy.getClass().getSimpleName().replace("DifficultyStrategy", "").toUpperCase();
        font.draw(batch, "DIFFICULTY INCREASED!", Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 1.5f, 0,
                Align.center, false);
        font.draw(batch, difficultyName + " MODE", Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0,
                Align.center, false);

        batch.end();
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
