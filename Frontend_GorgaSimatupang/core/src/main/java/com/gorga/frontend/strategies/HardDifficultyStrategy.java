package com.gorga.frontend.strategies;

import java.util.HashMap;
import java.util.Map;

public class HardDifficultyStrategy implements DifficultyStrategy{
    @Override
    public float getSpawnInterval(){
        return 0.8f;
    }

    @Override
    public float getDensity(){
        return 1.5f;
    }

    @Override
    public float getMinGap(){
        return 90f;
    }

    @Override
    public Map<String, Integer> getObstacleWeights(){
        Map<String, Integer> map = new HashMap<>();
        map.put("VerticalLaser", 3);
        map.put("HorizontalLaser", 3);
        map.put("HomingMissile", 4);
        return map;
    }
}
