package com.gorga.frontend;

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
    private float height = 48f;

    private float baseSpeed = 300f;
    private float distanceTraveled = 0f;

    public startPosition(){
        position = new Vector2();
        velocity = new Vector2();
        collider = new Rectangle();
    }

    public update(float delta, boolean isFlying){
        private updateDistanceAndSpeed(float delta){
            //
        }
        private updatePosition(float delta){
            position.x = position.x * delta;
            position.y = position.y * delta;
        }
        private applyGravity(float delta){
            velocity.x = baseSpeed;
            velocity.y = velocity.y - gravity * delta;
            if(velocity.y > maxVerticalSpeed){velocity.y = maxVerticalSpeed;}
            if(velocity.y < -maxVerticalSpeed){velocity.y = -maxVerticalSpeed;}
            if(Player.isFlying){return fly(delta);}
        }
        private fly(float delta){

        }
    }
}
