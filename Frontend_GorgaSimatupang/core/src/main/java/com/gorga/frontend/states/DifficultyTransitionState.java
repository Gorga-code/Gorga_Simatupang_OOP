package com.gorga.frontend.states;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gorga.frontend.strategies.DifficultyStrategy;

public class DifficultyTransitionState implements GameState {
    private final GameStateManager gsm;
    private final PlayingState playingState;
    private DifficultyStrategy newStrategy;
    private final BitmapFont font;
    private float timer = 3.67f;

    public DifficultyTransitionState(GameStateManager gsm, PlayingState playingState, DifficultyStrategy newStrategy){
        this.gsm = gsm;
        this.playingState = playingState;
        this.newStrategy = newStrategy;
        this.font = new BitmapFont();
    }

    public void update(float delta){
        timer -= delta;
        if(timer <= 0) {
            playingState.setDifficulty(newStrategy);
            gsm.set(playingState);
            return;
        }
    }
    public void render(SpriteBatch batch){
        float x = Gdx.graphics.getWidth() / 2f;
        float y = Gdx.graphics.getHeight() / 2f;

        batch.begin();
        font.draw(batch,"DIFFICULTY INCREASED! Difficulty menjadi: " + newStrategy.getClass().getSimpleName(),x-90,y+10);
        batch.end();
    }
    public void dispose() {
        font.dispose();
    }
}
