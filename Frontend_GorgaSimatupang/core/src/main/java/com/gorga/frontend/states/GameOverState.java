package com.gorga.frontend.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOverState implements GameState{
    private final GameStateManager gsm;
    private final BitmapFont font;

    public GameOverState(GameStateManager gsm) {
        this.gsm = gsm;
        font = new BitmapFont();
    }

    public void update(float delta){
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            gsm.set(new PlayingState(gsm));
        }
    }

    public void render(SpriteBatch batch){
        System.out.println("GAME OVER");
        System.out.println("Press SPACE to restart");
        font.draw(Gdx.graphics.getWidth(), getHeight());
    }

    public void dispose(){
        font.dispose();
    }
}
