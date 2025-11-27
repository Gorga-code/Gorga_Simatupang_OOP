package com.gorga.frontend.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gorga.frontend.*;
import com.gorga.frontend.strategies.*;
import com.gorga.frontend.*;
import com.gorga.frontend.commands.Command;
import com.gorga.frontend.commands.JetpackCommand;
import com.gorga.frontend.factories.CoinFactory;
import com.gorga.frontend.factories.ObstacleFactory;
import com.gorga.frontend.observers.ScoreUIObserver;
import com.gorga.frontend.obstacles.BaseObstacle;
import com.gorga.frontend.obstacles.HomingMissile;
import com.gorga.frontend.strategies.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayingState implements GameState {
    private final GameStateManager gsm;
    private final ShapeRenderer shapeRenderer;

    private final Player player;
    private final Ground ground;
    private final Background background;
    private final Command jetpackCommand;
    private final ScoreUIObserver scoreUIObserver;
    private final ObstacleFactory obstacleFactory;

    private final CoinFactory coinFactory;
    private float coinSpawnTimer = 0f;
    private final List<CoinPattern> coinPatterns;
    private final Random random;

    private float obstacleSpawnTimer;
    private float lastObstacleSpawnX = 0f;
    private static final float SPAWN_AHEAD_DISTANCE = 300f;
    private static final float OBSTACLE_CLUSTER_SPACING = 250f;

    private final OrthographicCamera camera;
    private final float cameraOffset = 0.2f;

    private final int screenWidth;
    private final int screenHeight;

    private DifficultyStrategy difficultyStrategy;
    private boolean isFlying = false;

    public PlayingState(GameStateManager gsm) {
        this.gsm = gsm;
        this.shapeRenderer = new ShapeRenderer();
        this.screenWidth = Gdx.graphics.getWidth();
        this.screenHeight = Gdx.graphics.getHeight();
        this.random = new Random();

        this.coinFactory = new CoinFactory();
        this.coinPatterns = new ArrayList<>();
        this.coinPatterns.add(new LinePattern());
        this.coinPatterns.add(new RectanglePattern());

        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);

        player = new Player(new Vector2(100, screenHeight / 2f));
        ground = new Ground();
        background = new Background();

        jetpackCommand = new JetpackCommand(player);

        scoreUIObserver = new ScoreUIObserver();
        GameManager.getInstance().addObserver(scoreUIObserver);

        obstacleFactory = new ObstacleFactory();
        setDifficulty(new EasyDifficultyStrategy());

        obstacleSpawnTimer = 0f;

        GameManager.getInstance().startGame();
    }

    public void setDifficulty(DifficultyStrategy newStrategy) {
        this.difficultyStrategy = newStrategy;
        this.obstacleFactory.setWeights(newStrategy.getObstacleWeights());
    }

    @Override
    public void update(float delta) {
        isFlying = Gdx.input.isKeyPressed(Input.Keys.SPACE);
        if (isFlying) {
            jetpackCommand.execute();
        }

        player.update(delta, isFlying);
        updateCamera(delta);
        background.update(camera.position.x);
        ground.update(camera.position.x);
        player.checkBoundaries(ground, screenHeight);

        updateCoins(delta);
        checkCoinCollisions();
        updateObstacles(delta);
        checkCollisions();

        if (player.isDead()) {
            GameManager.getInstance().endGame();
            gsm.set(new GameOverState(gsm));
            return;
        }

        int currentScoreMeters = (int) player.getDistanceTraveled();
        GameManager.getInstance().setScore(currentScoreMeters);
        updateDifficulty(currentScoreMeters);
    }

    private void updateDifficulty(int score) {
        if (score > 1000 && !(difficultyStrategy instanceof HardDifficultyStrategy)) {
            if (score > 2000) {
                gsm.push(new DifficultyTransitionState(gsm, this, new HardDifficultyStrategy()));
            } else if (!(difficultyStrategy instanceof MediumDifficultyStrategy)) {
                gsm.push(new DifficultyTransitionState(gsm, this, new MediumDifficultyStrategy()));
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        background.render(batch);

        // Render player sprite
        player.render(batch, isFlying);

        // Render HomingMissile sprites
        for (BaseObstacle obstacle : obstacleFactory.getAllInUseObstacles()) {
            obstacle.render(batch);
        }

        batch.end();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(1f, 0f, 0f, 1f);
        for (BaseObstacle obstacle : obstacleFactory.getAllInUseObstacles()) {
            obstacle.render(shapeRenderer);
        }

        for (Coin coin : coinFactory.getActiveCoins()) {
            coin.renderShape(shapeRenderer);
        }
        shapeRenderer.end();

        int currentScore = GameManager.getInstance().getScore();
        int currentCoins = GameManager.getInstance().getCoins();
        float distanceToObstacle = calculateDistanceToNearestObstacle();

        scoreUIObserver.render(currentScore, currentCoins, distanceToObstacle);
    }

    private float calculateDistanceToNearestObstacle() {
        float minDistance = Float.MAX_VALUE;
        float playerX = player.getPosition().x;
        float playerY = player.getPosition().y;

        for (BaseObstacle obstacle : obstacleFactory.getAllInUseObstacles()) {
            float obstacleX = obstacle.getPosition().x;
            float obstacleY = obstacle.getPosition().y;

            if (obstacleX > playerX) {
                float dx = obstacleX - playerX;
                float dy = obstacleY - playerY;
                float distance = (float) Math.sqrt(dx * dx + dy * dy);

                if (distance < minDistance) {
                    minDistance = distance;
                }
            }
        }

        return minDistance;
    }

    private void updateCamera(float delta) {
        float cameraFocus = player.getPosition().x + screenWidth * cameraOffset;
        camera.position.x = cameraFocus;
        camera.update();
    }

    private void updateObstacles(float delta) {
        obstacleSpawnTimer += delta;

        if (obstacleSpawnTimer >= difficultyStrategy.getSpawnInterval()) {
            spawnObstacle();
            obstacleSpawnTimer = 0f;
        }

        float cameraLeftEdge = camera.position.x - screenWidth / 2f;

        for (BaseObstacle obstacle : obstacleFactory.getAllInUseObstacles()) {
            if (obstacle instanceof HomingMissile) {
                ((HomingMissile) obstacle).setTarget(player);
                ((HomingMissile) obstacle).update(delta);
            }

            if (obstacle.isOffScreenCamera(cameraLeftEdge)) {
                obstacleFactory.releaseObstacle(obstacle);
            }
        }
    }

    private void spawnObstacle() {
        float cameraRightEdge = camera.position.x + screenWidth / 2f;
        float spawnAheadOfCamera = cameraRightEdge + SPAWN_AHEAD_DISTANCE;
        float spawnAfterLastObstacle = lastObstacleSpawnX + difficultyStrategy.getMinGap();
        float baseSpawnX = Math.max(spawnAheadOfCamera, spawnAfterLastObstacle);

        for (int i = 0; i < difficultyStrategy.getDensity(); i++) {
            float spawnX = baseSpawnX + (i * OBSTACLE_CLUSTER_SPACING);
            obstacleFactory.createRandomObstacle(ground.getTopY(), spawnX, player.getHeight());
            lastObstacleSpawnX = spawnX;
        }
    }

    private void checkCollisions() {
        Rectangle playerCollider = player.getCollider();
        for (BaseObstacle obstacle : obstacleFactory.getAllInUseObstacles()) {
            if (obstacle.isColliding(playerCollider)) {
                player.die();
                return;
            }
        }
    }

    private void updateCoins(float delta) {
        coinSpawnTimer += delta;
        if (coinSpawnTimer > difficultyStrategy.getCoinSpawnInterval()) {
            spawnCoins();
            coinSpawnTimer = 0;
        }

        float cameraLeft = camera.position.x - (Gdx.graphics.getWidth() / 2);

        for (Coin coin : coinFactory.getActiveCoins()) {
            coin.update(delta);
            if (coin.getPosition().x < cameraLeft - 50) {
                coinFactory.releaseCoin(coin);
            }
        }
    }

    private void spawnCoins() {
        float spawnX = camera.position.x + (screenWidth / 2) + 50;
        CoinPattern pattern = coinPatterns.get(random.nextInt(coinPatterns.size()));
        java.util.List<Coin> spawnedCoins = pattern.spawn(coinFactory, ground.getTopY(), spawnX, screenHeight);
        coinFactory.addActiveCoins(spawnedCoins);
    }

    private void checkCoinCollisions() {
        Rectangle playerCollider = player.getCollider();
        for (Coin coin : coinFactory.getActiveCoins()) {
            if (coin.isColliding(playerCollider)) {
                coin.setActive(false);
                GameManager.getInstance().addCoin();
            }
        }
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        obstacleFactory.releaseAllObstacles();
        scoreUIObserver.dispose();
        background.dispose();
        coinFactory.releaseAll();
    }
}
