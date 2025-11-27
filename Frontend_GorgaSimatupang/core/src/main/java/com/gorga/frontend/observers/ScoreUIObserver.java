package com.gorga.frontend.observers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Concrete observer that displays the score on screen
 */
public class ScoreUIObserver implements Observer {
    private BitmapFont font;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    public ScoreUIObserver() {
        this.font = new BitmapFont();
        this.font.getData().setScale(1.5f);
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void update(int score) {
        Gdx.app.log("ScoreUI", "Score updated: " + score);
    }

    public void render(int score, int coins, float distanceToObstacle) {
        float screenHeight = Gdx.graphics.getHeight();
        float centerY = screenHeight / 2f;
        float leftX = 30;

        Color scoreColor = calculateDangerColor(distanceToObstacle);

        batch.begin();

        font.setColor(0f, 1f, 0f, 1f);
        font.draw(batch, "Coins: " + coins, leftX, centerY + 30);

        font.setColor(scoreColor);
        font.draw(batch, "Score: " + score, leftX, centerY);

        batch.end();
    }

    private Color calculateDangerColor(float distance) { //fungsi tambahan dari saya supaya indikator score bervariasi warnanya tergantung bahaya di depanm
        float safeDistance = 250f;
        float dangerDistance = 100f;

        if (distance > safeDistance) {
            return Color.WHITE;
        } else if (distance < dangerDistance) {
            return Color.RED;
        } else {
            float ratio = (distance - dangerDistance) / (safeDistance - dangerDistance);
            return new Color(1f, ratio, ratio, 1f);
        }
    }

    public void dispose() {
        font.dispose();
        batch.dispose();
        shapeRenderer.dispose();
    }
}
