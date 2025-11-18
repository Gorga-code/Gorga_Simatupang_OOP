package com.gorga.frontend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Background {
    private Texture backgroundTexture;
    private TextureRegion backgroundRegion;
    private float width,height;
    private float currentCameraX = 0f;

    public Background () {
        this.backgroundTexture = new com.badlogic.gdx.graphics.Texture(Gdx.files.internal("background.png"));
        this.backgroundRegion = new TextureRegion(backgroundTexture);
        this.width = 2688f;
        this.height = 1536f;
    }

    public void update(float cameraX){
        currentCameraX = cameraX;
    }

    public void render(SpriteBatch batch) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        float scale = screenHeight / height;
        float scaledWidth = width * scale;
        float scaledHeight = height * scale;

        float cameraLeft = currentCameraX - screenWidth / 2f;
        float startX = (float) Math.floor(cameraLeft / scaledWidth) * scaledWidth;
        float endX = cameraLeft + screenWidth + scaledWidth;

        for (float x = startX; x < endX; x += scaledWidth) {
            batch.begin();
            batch.draw(backgroundRegion, x, 0, scaledWidth, scaledHeight);
            batch.end();
        }
    }

    public void dispose() {
        if(backgroundTexture!=null){
            backgroundTexture.dispose();
        }
    }
}
