package com.gorga.frontend.observers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScoreUIObserver implements Observer {
    private BitmapFont font;
    private SpriteBatch batch;

    public ScoreUIObserver() {
        this.font = new BitmapFont();
        font.setColor(Color.RED);
        this.batch = new SpriteBatch();
    }

    public void update(int score){
        System.out.println("Score saat ini : " + score);
    }

    public void render(int score) {
        batch.begin();
        font.draw(batch,"Score : " + score,10,Gdx.graphics.getHeight()-10);
        batch.end();
    }

    public void dispose() {
        font.dispose();
        batch.dispose();
    }
}
