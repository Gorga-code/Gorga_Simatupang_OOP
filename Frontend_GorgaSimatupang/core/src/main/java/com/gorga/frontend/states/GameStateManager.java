package com.gorga.frontend.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameStateManager implements GameState {
    private final Stack<GameState> states;
    public GameStateManager() {
        this.states = new Stack<>();
    }

    public void push(GameState state){
        states.push(state);
    }

    public void pop() {
        states.pop();
        dispose();
    }

    public void set(GameState state){
        states.pop();
        states.push(state);
    }

    public void update(float delta) {
        if (!states.isEmpty()) {
            states.peek().update(delta);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!states.isEmpty()) {
            states.peek().render(batch);
        }
    }

    @Override
    public void dispose() {}
}
