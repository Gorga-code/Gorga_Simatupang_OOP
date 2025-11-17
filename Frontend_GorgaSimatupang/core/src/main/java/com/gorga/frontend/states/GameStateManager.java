package com.gorga.frontend.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameStateManager {
    private final Stack<GameState> states;

    public  GameStateManager() {
        states = new Stack<GameState>();
    }

    public void push(GameState state){
        states.push(state);
    }

    public void pop(){
        states.pop();
    }

    public void set(GameState state){
        states.pop();
        states.push(state);
    }

    public void update(float delta){
        states.peek().update(delta);
    }

    public void render(SpriteBatch batch){
        render(batch);
    }
}
