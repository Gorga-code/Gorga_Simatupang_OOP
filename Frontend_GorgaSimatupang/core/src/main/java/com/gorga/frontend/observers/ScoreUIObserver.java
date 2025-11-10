package com.gorga.frontend.observers;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScoreUIObserver implements Observer{
    private BitmapFont font;
    private SpriteBatch batch;

    public ScoreUIObserver(SpriteBatch batch){
        font = new BitmapFont();
        font.setColor(255,255,255,255);
        batch = new SpriteBatch();
    }

    public void update(int score){
        System.out.println("score telah diperbarui");
    }

    public void render(int score){
        batch.begin();
        batch.draw();
        System.out.println("Score : " + score);
        batch.end();
    }

    public void dispose(){
        batch.dispose();
        font.dispose();
    }
}
