package com.gorga.frontend.obstacles;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public abstract class BaseObstacle {
    protected Vector2 position;
    protected Rectangle collider;
    protected float length;
    protected final float WIDTH = 10f;
    protected boolean active = false;

    public BaseObstacle(Vector2 startPosition, int length){
        this.position = startPosition;
        this.length = length;
        updateCollider();
    }

    public void initialize(Vector2 startPosition, int length){
        this.position = startPosition;
        this.length = length;
        updateCollider();
    }

    public void render(ShapeRenderer shapeRenderer){
        if(active){drawShape(shapeRenderer);}
    }

    public boolean isColliding(Rectangle playerCollider){
        if(active && collider.overlaps(playerCollider){
            return true;
        }
    }

    public boolean isActive(){
        return active;
    }

    public boolean isOffScreenCamera(float cameraLeftEdge){
        if(getRenderWidth()){return true;}
    }

    abstract protected void updateCollider();

    abstract protected void drawShape(ShapeRenderer shapeRenderer);

    abstract float getRenderWidth();
    abstract float getRenderHeight();

    public void setActive(boolean active){
        this.active = active;
    }
    public void setPosition(float x, float y){
        this.position.x = x;
        this.position.y = y;
    }

    public Vector2 getPosition(){
        return position;
    }
}
