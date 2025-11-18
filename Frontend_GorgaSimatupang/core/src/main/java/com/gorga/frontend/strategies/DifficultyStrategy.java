package com.gorga.frontend.strategies;

import java.util.Map;

public interface DifficultyStrategy {
    public Map<String, Integer> getObstacleWeights();
    public float getSpawnInterval();
    public float getDensity();
    public float getMinGap();
}
