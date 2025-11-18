package com.gorga.frontend.strategies;

import java.util.HashMap;
import java.util.Map;

public class EasyDifficultyStrategy implements DifficultyStrategy {
    @Override
    public float getSpawnInterval() {
        return 2.0f;
    }

    @Override
    public float getDensity() {
        return 0.7f;
    }

    @Override
    public float getMinGap() {
        return 180f;
    }

    @Override
    public Map<String, Integer> getObstacleWeights() {
        Map<String, Integer> map = new HashMap<>();
        map.put("VerticalLaser", 1);
        map.put("HorizontalLaser", 1);
        return map;
    }
}
