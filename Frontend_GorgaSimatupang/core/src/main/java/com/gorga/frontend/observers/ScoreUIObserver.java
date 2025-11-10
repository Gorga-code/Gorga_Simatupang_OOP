package com.gorga.frontend.observers;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScoreUIObserver implements Observer{
    private BitmapFont font;

    public ScoreUIObserver() {
        font = new BitmapFont();
        font.setColor(1, 1, 1, 1); // White
    }

    @Override
    public void update(int score){
        System.out.println("Score diperbarui: " + score);
    }

    public void render(SpriteBatch batch, int score){
        batch.begin();
        font.draw(batch, "Score: " + score, 20, 460);
        batch.end();
    }

    public void dispose() {
        font.dispose();
    }
}
