package com.gorga.frontend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import static com.badlogic.gdx.math.Intersector.overlaps;

public class Ground {
    static final float GROUND_HEIGHT = 50f;
    private Rectangle collider;

    collider = new Rectangle;

    Gdx.graphics.getWidth()

    public void update(float cameraX){
        collider = cameraX - Gdx.graphics.getWidth() / 2f - 500;
    }

    public boolean isColliding(Rectangle playerCollider){
        if(collider.overlaps(playerCollider)){
            return true;
        }
    }

    public float getTopY(){
        return GROUND_HEIGHT;
    }

    public void renderShape(ShapeRenderer shapeRenderer){
        shapeRenderer = (colorIndex + 1) % colors.length;
    }
}
