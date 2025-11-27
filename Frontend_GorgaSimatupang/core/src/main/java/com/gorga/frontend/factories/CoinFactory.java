package com.gorga.frontend.factories;

import com.badlogic.gdx.utils.Array;
import com.gorga.frontend.Coin;

import com.gorga.frontend.pools.CoinPool;

public class CoinFactory {
    public final CoinPool coinPool;
    private Array<Coin> active_coins;

    public CoinFactory() {
        coinPool = new CoinPool();
        active_coins = new Array<>();
    }

    public Array<Coin> getActiveCoins() {
        return active_coins;
    }

    public void addActiveCoins(java.util.List<Coin> coins) {
        for (Coin coin : coins) {
            active_coins.add(coin);
        }
    }

    public void releaseCoin(Coin coin) {
        active_coins.removeValue(coin, true);
        coinPool.free(coin);
    }

    public void releaseAll() {
        for (Coin c : active_coins)
            coinPool.free(c);
        active_coins.clear();
    }
}
