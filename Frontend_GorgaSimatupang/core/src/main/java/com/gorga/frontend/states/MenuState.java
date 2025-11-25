package com.gorga.frontend.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gorga.frontend.GameManager;

public class MenuState implements GameState {
    private GameStateManager gsm;
    private Stage stage;
    private Skin skin;
    private TextField nameField;
    private TextButton startButton;

    public MenuState(GameStateManager gsm) {
        this.gsm = gsm;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        createBasicSkin();
        buildUI();
    }

    private void createBasicSkin() {
        skin = new Skin();
        BitmapFont font = new BitmapFont();
        skin.add("default", font);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        pixmap.setColor(Color.GRAY);
        pixmap.fill();
        skin.add("gray", new Texture(pixmap));

        pixmap.setColor(Color.DARK_GRAY);
        pixmap.fill();
        skin.add("dark_gray", new Texture(pixmap));

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("default");
        labelStyle.fontColor = Color.WHITE;
        skin.add("default", labelStyle);

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = skin.getFont("default");
        textFieldStyle.fontColor = Color.WHITE;
        textFieldStyle.background = skin.newDrawable("dark_gray");
        textFieldStyle.cursor = skin.newDrawable("white");
        textFieldStyle.selection = skin.newDrawable("gray");
        skin.add("default", textFieldStyle);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = skin.getFont("default");
        textButtonStyle.up = skin.newDrawable("gray");
        textButtonStyle.down = skin.newDrawable("white");
        textButtonStyle.over = skin.newDrawable("dark_gray");
        skin.add("default", textButtonStyle);
    }

    private void buildUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label titleLabel = new Label("NETLAB JOYRIDE", skin);
        titleLabel.setFontScale(2f);

        Label nameLabel = new Label("Enter Your Name:", skin);

        nameField = new TextField("", skin);
        nameField.setMessageText("Username...");
        nameField.setAlignment(1);

        startButton = new TextButton("START GAME", skin);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String inputName = nameField.getText();
                if (inputName.isEmpty()) {
                    inputName = "Guest";
                }
                GameManager.getInstance().registerPlayer(inputName);
                gsm.set(new PlayingState(gsm));
            }
        });

        table.add(titleLabel).padBottom(20).row();
        table.add(nameLabel).padBottom(10).row();
        table.add(nameField).width(200).height(40).padBottom(20).row();
        table.add(startButton).width(200).height(50);
    }

    @Override
    public void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
