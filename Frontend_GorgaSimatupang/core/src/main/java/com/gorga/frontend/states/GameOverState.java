package com.gorga.frontend.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOverState implements GameState {
    private final GameStateManager gsm;
    private final BitmapFont font;

    public GameOverState(GameStateManager gsm){
        this.gsm = gsm;
        this.font = new BitmapFont();
    }

    public void update(float delta){
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            gsm.set(new PlayingState(gsm));
            return;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        float x = Gdx.graphics.getWidth()/2;
        float y = Gdx.graphics.getHeight()/2;
        batch.begin();
        font.draw(batch,"GAME OVER",x - 40,y + 20);

        font.draw(batch,"Press SPACE to restart",x - 60,y - 20);
        batch.end();

    }

    @Override
    public void dispose() {

        font.dispose();
    }
}
