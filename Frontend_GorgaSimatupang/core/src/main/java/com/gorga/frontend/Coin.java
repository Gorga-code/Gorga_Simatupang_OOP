package com.gorga.frontend;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;

public class Coin {
    private Vector2 position;
    private Rectangle collider;
    private float radius = 15f;
    private boolean active = true;

    private float bobOffset;
    private float bobSpeed;

    public Coin(Vector2 startPosition){
        this.position = startPosition;
        collider = new Rectangle();
    }

    public void update(float delta){
        bobOffset = bobSpeed * delta;
        collider.setPosition(position.x, position.y - bobOffset);
    }

    public void renderShape(ShapeRenderer shapeRenderer){
        float drawY = position.y + (float) (Math.sin(bobOffset) * 5f);
        shapeRenderer.begin();
        shapeRenderer.circle(Color.YELLOW);
        shapeRenderer.setColor(1f, 1f, 0f, 1f);
    }

    public boolean isColliding(Rectangle playerCollider){
        if(collider.overlaps(playerCollider) && active){return true;}
        return false;
    }



}
