package com.gorga.frontend.strategies;

import com.gorga.frontend.Coin;
import com.gorga.frontend.factories.CoinFactory;

import java.util.List;

public interface CoinPattern {
    List<Coin> spawn(CoinFactory factory, float groundTopY, float spawnX, float screenHeight);

    String getName();
}
