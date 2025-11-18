package com.gorga.frontend.strategies;

import java.util.HashMap;
import java.util.Map;

public class MediumDifficultyStrategy implements DifficultyStrategy {
    @Override
    public float getSpawnInterval() {
        return 1.3f;
    }

    @Override
    public float getDensity() {
        return 1.0f;
    }

    @Override
    public float getMinGap() {
        return 130f;
    }

    @Override
    public Map<String, Integer> getObstacleWeights() {
        Map<String, Integer> map = new HashMap<>();
        map.put("VerticalLaser", 2);
        map.put("HorizontalLaser", 2);
        map.put("HomingMissile", 1);
        return map;
    }
}
