package com.gorga.frontend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private Vector2 position;
    private Vector2 velocity;
    private float gravity = 2000f;
    private float force = 4500f;
    private float maxVerticalSpeed = 700f;
    private Rectangle collider;
    private float width = 64f;
    private float height = 64f;

    private float baseSpeed = 300f;
    private float distanceTraveled = 0f;

    private boolean isDead = false;
    private Vector2 startPosition;

    private Animation<TextureRegion> runAnimation;
    private TextureRegion flyTexture;
    private float stateTime = 0f;

    public Player(Vector2 startPosition) {
        this.startPosition = new Vector2(startPosition);
        position = new Vector2(startPosition);

        collider = new Rectangle(
                position.x,
                position.y,
                width,
                height);
        velocity = new Vector2(baseSpeed, 0);

        loadTextures();
    }

    private void loadTextures() {
        Texture runSheet = new Texture(Gdx.files.internal("run.png"));
        TextureRegion[][] tmp = TextureRegion.split(runSheet, 36, 36);
        TextureRegion[] runFrames = new TextureRegion[4];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp[i].length && index < 4; j++) {
                runFrames[index++] = tmp[i][j];
            }
        }
        runAnimation = new Animation<>(0.1f, runFrames);
        runAnimation.setPlayMode(Animation.PlayMode.LOOP);

        flyTexture = new TextureRegion(new Texture(Gdx.files.internal("fly.png")));
    }

    public void update(float delta, boolean isFlying) {
        if (!isDead) {
            updateDistanceAndSpeed(delta);
            applyGravity(delta);
            if (isFlying) {
                fly(delta);
            }
            updatePosition(delta);
            stateTime += delta;
        }
        updateCollider();
    }

    private void updateDistanceAndSpeed(float delta) {
        distanceTraveled += velocity.x * delta;
    }

    private void updatePosition(float delta) {
        position.x += velocity.x * delta;
        position.y += velocity.y * delta;
    }

    private void applyGravity(float delta) {
        velocity.y -= gravity * delta;
        velocity.x = baseSpeed;

        if (velocity.y < -maxVerticalSpeed) {
            velocity.y = -maxVerticalSpeed;
        } else if (velocity.y > maxVerticalSpeed) {
            velocity.y = maxVerticalSpeed;
        }
    }

    private void fly(float delta) {
        if (!isDead) {
            velocity.y += force * delta;
        }
    }

    private void updateCollider() {
        collider.setPosition(position.x, position.y);
    }

    public void checkBoundaries(Ground ground, float ceilingY) {
        if (ground.isColliding(collider)) {
            position.y = ground.getTopY();
            velocity.y = 0;
        }

        if (position.y + height > ceilingY) {
            position.y = ceilingY - height;
            velocity.y = 0;
        }
    }

    public void render(SpriteBatch batch, boolean isFlying) {
        TextureRegion currentFrame;
        if (isFlying) {
            currentFrame = flyTexture;
        } else {
            currentFrame = runAnimation.getKeyFrame(stateTime);
        }
        batch.draw(currentFrame, position.x, position.y, width, height);
    }

    public void renderShape(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(0f, 1f, 0f, 1f);
        shapeRenderer.rect(position.x, position.y, width, height);
    }

    public void fly() {
        if (!isDead) {
            velocity.y += force * 1 / 60f;
        }
    }

    public void die() {
        isDead = true;
        velocity.x = 0;
        velocity.y = 0;
    }

    public void reset() {
        isDead = false;
        position.set(startPosition);
        velocity.set(baseSpeed, 0);
        distanceTraveled = 0f;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Rectangle getCollider() {
        return collider;
    }

    public float getDistanceTraveled() {
        return distanceTraveled / 10f;
    }

    public boolean isDead() {
        return isDead;
    }
}
