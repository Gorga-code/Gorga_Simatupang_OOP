package com.gorga.frontend.strategies;

import com.gorga.frontend.Coin;
import com.gorga.frontend.factories.CoinFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LinePattern implements CoinPattern {
    private static final float SPACING = 40f;
    private final Random random;

    public LinePattern() {
        this.random = new Random();
    }

    @Override
    public List<Coin> spawn(CoinFactory factory, float groundTopY, float spawnX, float screenHeight) {
        List<Coin> coins = new ArrayList<>();

        int coinCount = 3 + random.nextInt(3);

        float minY = groundTopY + 50;
        float maxY = screenHeight - 100;
        float startY = minY + random.nextFloat() * (maxY - minY);

        for (int i = 0; i < coinCount; i++) {
            float x = spawnX + (i * SPACING);
            Coin coin = factory.coinPool.obtain(x, startY);
            coins.add(coin);
        }

        return coins;
    }

    @Override
    public String getName() {
        return "Line";
    }
}
