package com.gorga.frontend.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

public class GameOverState implements GameState {

    private final GameStateManager gsm;
    private final BitmapFont font;
    private final OrthographicCamera camera;

    public GameOverState(GameStateManager gsm) {
        this.gsm = gsm;
        this.font = new BitmapFont();
        this.font.getData().setScale(2f);
        this.font.setColor(Color.WHITE);

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            gsm.set(new PlayingState(gsm));
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "GAME OVER", Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 1.5f, 0, Align.center,
                false);
        font.draw(batch, "Press SPACE to restart", Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0,
                Align.center, false);
        batch.end();
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
