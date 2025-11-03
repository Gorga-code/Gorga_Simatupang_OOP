package com.gorga.frontend.obstacles;

import com.badlogic.gdx.math.Vector2;

public class HomingMissile extends BaseObstacle {
    private Player target;
    private Vector2 velocity;
    private float speed = 200f;
    private float width = 40f;
    private float height = 20f;

    @Override
    public HomingMissile(Vector2 startPosition, int length) {
        velocity = new Vector2();

    }


}
