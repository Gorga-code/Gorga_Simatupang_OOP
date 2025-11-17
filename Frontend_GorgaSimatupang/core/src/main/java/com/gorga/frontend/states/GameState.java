package com.gorga.frontend.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface GameState {
    public abstract void update(float delta);
    public abstract void render(SpriteBatch batch);
    public abstract void dispose();

}
