package com.gorga.frontend.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.gorga.frontend.obstacles.VerticalLaser;
import com.gorga.frontend.obstacles.BaseObstacle;
import com.gorga.frontend.pools.VerticalLaserPool;
import java.util.List;
import java.util.Random;

public class VerticalLaserCreator implements ObstacleFactory.ObstacleCreator {
    private final VerticalLaserPool pool = new VerticalLaserPool();
    private static final float MIN_HEIGHT = 100f;
    private static final float MAX_HEIGHT = 300f;

    @Override
    public BaseObstacle create(float groundTopY,float spawnX,float playerHeight,Random rng){
        float height = MIN_HEIGHT + rng.nextFloat() * (MAX_HEIGHT - MIN_HEIGHT);
        float y = groundTopY;
        return pool.obtain(new Vector2(spawnX,y),(int)height);
    }

    @Override
    public void release(BaseObstacle obstacle){
        if(obstacle instanceof VerticalLaser) pool.release((VerticalLaser)obstacle);
    }

    @Override
    public void releaseAll(){
        pool.releaseAll();
    }

    @Override
    public List<VerticalLaser> getInUse(){
        return pool.getInUse();
    }

    @Override
    public boolean supports(BaseObstacle obstacle){
        return obstacle instanceof VerticalLaser;
    }

    @Override
    public String getName(){
        return "VerticalLaser";
    }
}
