package com.gorga.frontend;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Coin {
    private Vector2 position;
    private Rectangle collider;
    private float radius = 15f;
    private boolean active;
    private float bobOffset;
    private float bobSpeed;

    public Coin(Vector2 startPosition) {
        this.position = startPosition;
        this.collider = new Rectangle();
        this.bobSpeed = 3f;
    }

    public void update(float delta) {
        this.bobOffset += this.bobSpeed * delta;
        float drawY = position.y + (float) Math.sin(bobOffset) * 5f;
        collider.set(position.x - radius, drawY - radius, radius * 2, radius * 2);
    }

    public void renderShape(ShapeRenderer shapeRenderer) {
        if (!active)
            return;
        float drawY = position.y + (float) (Math.sin(bobOffset) * 5f);
        shapeRenderer.setColor(1f, 1f, 0f, 1f);
        shapeRenderer.circle(position.x, drawY, radius);
    }

    public boolean isColliding(Rectangle playerCollider) {
        return this.active && playerCollider.overlaps(this.collider);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setBobSpeed(float speed) {
        this.bobSpeed = speed;
    }
}
