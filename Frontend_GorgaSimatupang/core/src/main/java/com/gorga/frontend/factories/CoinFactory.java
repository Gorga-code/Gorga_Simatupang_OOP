package com.gorga.frontend.factories;

import com.badlogic.gdx.utils.Array;
import com.gorga.frontend.Coin;
import com.gorga.frontend.pools.CoinPool;
import java.util.Random;

public class CoinFactory {

    private final CoinPool coinPool;
    private final Array<Coin> activeCoins;
    private final Random random;

    public CoinFactory() {
        coinPool = new CoinPool();
        activeCoins = new Array<>();
        random = new Random();
    }

    public void createCoinPattern(float spawnX, float groundTopY) {
        if (random.nextFloat() < 0.3f) {
            float startY = groundTopY + 50 + random.nextFloat() * 100;
            for (int i = 0; i < 3; i++) {
                Coin coin = coinPool.obtain(spawnX + (i * 40), startY);
                activeCoins.add(coin);
            }
        }
    }

    public Array<Coin> getActiveCoins() {
        return activeCoins;
    }

    public void releaseCoin(Coin coin) {
        activeCoins.removeValue(coin, true);
        coinPool.release(coin);
    }

    public void releaseAll() {
        for (Coin coin : activeCoins) {

            coinPool.release(coin);
        }
        activeCoins.clear();
    }
}
