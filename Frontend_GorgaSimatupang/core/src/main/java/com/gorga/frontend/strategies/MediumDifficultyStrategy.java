package com.gorga.frontend.strategies;

import java.util.HashMap;
import java.util.Map;

public class MediumDifficultyStrategy implements DifficultyStrategy {
    @Override
    public float getSpawnInterval() {
        return 2.5f;
    }

    @Override
    public int getDensity() {
        return 1;
    }

    @Override
    public float getMinGap() {
        return 200f;
    }

    @Override
    public Map<String, Integer> getObstacleWeights() {
        Map<String, Integer> weights = new HashMap<>();
        // Vertical: 40%, Horizontal: 40%, Homing Missile: 20%
        weights.put("VerticalLaser", 2);
        weights.put("HorizontalLaser", 2);
        weights.put("HomingMissile", 1);
        return weights;
    }

    @Override
    public float getCoinSpawnInterval() {
        return 1.5f;
    }
}
