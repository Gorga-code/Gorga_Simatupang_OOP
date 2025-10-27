package com.gorga.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Main extends ApplicationAdapter {
    private ShapeRenderer shape;
    private float boxX, boxY;
    private float boxSize = 100f; // ukuran kotak
    private float speed = 300f; // px per detik
    // movement flags
    private boolean moveLeft, moveRight, moveUp, moveDown;

    // color cycle: Merah -> Kuning -> Biru -> Merah ...
    private Color[] colors = {Color.RED, Color.YELLOW, Color.BLUE};
    private int colorIndex = 0;

    @Override
    public void create() {
        shape = new ShapeRenderer();
        // center the box initially
        boxX = Gdx.graphics.getWidth() / 2f - boxSize / 2f;
        boxY = Gdx.graphics.getHeight() / 2f - boxSize / 2f;

        // input handling: keyboard + mouse click
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Input.Keys.A:
                    case Input.Keys.LEFT:
                        moveLeft = true;
                        break;
                    case Input.Keys.D:
                    case Input.Keys.RIGHT:
                        moveRight = true;
                        break;
                    case Input.Keys.W:
                    case Input.Keys.UP:
                        moveUp = true;
                        break;
                    case Input.Keys.S:
                    case Input.Keys.DOWN:
                        moveDown = true;
                        break;
                }
                return true;
            }

            @Override
            public boolean keyUp(int keycode) {
                switch (keycode) {
                    case Input.Keys.A:
                    case Input.Keys.LEFT:
                        moveLeft = false;
                        break;
                    case Input.Keys.D:
                    case Input.Keys.RIGHT:
                        moveRight = false;
                        break;
                    case Input.Keys.W:
                    case Input.Keys.UP:
                        moveUp = false;
                        break;
                    case Input.Keys.S:
                    case Input.Keys.DOWN:
                        moveDown = false;
                        break;
                }
                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                // mouse click -> change color to next in cycle
                colorIndex = (colorIndex + 1) % colors.length;
                // print color change to terminal
                Color c = colors[colorIndex];
                System.out.println("Color changed to: " + colorName(colorIndex));
                return true;
            }
        });
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        // movement applied every frame for smooth motion
        float dx = 0f, dy = 0f;
        if (moveLeft)  dx -= speed * delta;
        if (moveRight) dx += speed * delta;
        if (moveUp)    dy += speed * delta;
        if (moveDown)  dy -= speed * delta;

        boxX += dx;
        boxY += dy;

        // BONUS: clamp box so it can't go outside the screen
        clampToScreen();

        // clear background black
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // draw box with current color
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(colors[colorIndex]);
        shape.rect(boxX, boxY, boxSize, boxSize);
        shape.end();
    }

    private void clampToScreen() {
        float minX = 0f;
        float minY = 0f;
        float maxX = Gdx.graphics.getWidth() - boxSize;
        float maxY = Gdx.graphics.getHeight() - boxSize;

        if (boxX < minX) boxX = minX;
        if (boxX > maxX) boxX = maxX;
        if (boxY < minY) boxY = minY;
        if (boxY > maxY) boxY = maxY;
    }

    @Override
    public void resize(int width, int height) {
        // center again on resize (optional)
        if (width > 0 && height > 0) {
            // keep center relative to new size if you want
            // here we keep the box within screen bounds
            clampToScreen();
        }
    }

    @Override
    public void dispose() {
        shape.dispose();
    }

    // helper to print readable color name
    private String colorName(int idx) {
        switch (idx) {
            case 0: return "Red";
            case 1: return "Yellow";
            case 2: return "Blue";
            default: return "Unknown";
        }
    }
}
