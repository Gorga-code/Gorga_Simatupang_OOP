package com.gorga.frontend.factories;

import java.util.*;
import com.gorga.frontend.obstacles.*;
import com.gorga.frontend.pools.*;
import com.gorga.frontend.obstacles.BaseObstacle;
import com.badlogic.gdx.math.Vector2;

public class ObstacleFactory {
    public interface ObstacleCreator {
        BaseObstacle create(float groundTopY, float spawnX, float playerHeight, Random random);
        void release(BaseObstacle obstacle);
        void releaseAll();
        List<? extends BaseObstacle> getInUse();
        boolean supports(BaseObstacle obstacle);
        String getName();
    }

    private static class WeightedCreator {
        ObstacleCreator creator;
        int weight;
        WeightedCreator(ObstacleCreator creator, int weight){
            this.creator = creator;
            this.weight = weight;
        }
    }

    private final List<WeightedCreator> weightedCreators = new ArrayList<>();
    private final Random random = new Random();
    private int totalWeight = 0;

    public ObstacleFactory(){
        register(new VerticalLaserCreator(),2);
        register(new HorizontalLaserCreator(),2);
        register(new HomingMissileCreator(),1);
    }

    public void register(ObstacleCreator creator,int weight){
        weightedCreators.add(new WeightedCreator(creator,weight));
        totalWeight += weight;
    }

    public BaseObstacle createRandomObstacle(float groundTopY,float spawnX,float playerHeight){
        if(weightedCreators.isEmpty()) throw new IllegalStateException("No obstacle creators registered");
        ObstacleCreator creator = selectWeightedCreator();
        return creator.create(groundTopY,spawnX,playerHeight,random);
    }

    private ObstacleCreator selectWeightedCreator(){
        int randomValue = random.nextInt(totalWeight);
        int currentWeight = 0;
        for(WeightedCreator wc : weightedCreators){
            currentWeight += wc.weight;
            if(randomValue < currentWeight) return wc.creator;
        }
        return weightedCreators.get(0).creator;
    }

    public void releaseObstacle(BaseObstacle obstacle){
        for(WeightedCreator wc : weightedCreators){
            if(wc.creator.supports(obstacle)){
                wc.creator.release(obstacle);
                return;
            }
        }
    }

    public void releaseAllObstacles(){
        for(WeightedCreator wc : weightedCreators){
            wc.creator.releaseAll();
        }
    }

    public List<BaseObstacle> getAllInUseObstacles(){
        List<BaseObstacle> list = new ArrayList<>();
        for(WeightedCreator wc : weightedCreators){
            list.addAll(wc.creator.getInUse());
        }
        return list;
    }

    public List<String> getRegisteredCreatorNames(){
        List<String> names = new ArrayList<>();
        for(WeightedCreator wc : weightedCreators){
            names.add(wc.creator.getName());
        }
        return names;
    }

    private static class VerticalLaserCreator implements ObstacleCreator{
        private final VerticalLaserPool pool = new VerticalLaserPool();
        public BaseObstacle create(float groundTopY,float spawnX,float playerHeight,Random random){
            float height = 100 + random.nextFloat()*100;
            Vector2 pos = new Vector2(spawnX,groundTopY);
            return pool.obtain(pos,(int)height);
        }
        public void release(BaseObstacle obstacle){pool.release((VerticalLaser)obstacle);}
        public void releaseAll(){pool.releaseAll();}
        public List<? extends BaseObstacle> getInUse(){return pool.getInUse();}
        public boolean supports(BaseObstacle obstacle){return obstacle instanceof VerticalLaser;}
        public String getName(){return "VerticalLaser";}
    }

    private static class HorizontalLaserCreator implements ObstacleCreator{
        private final HorizontalLaserPool pool = new HorizontalLaserPool();
        public BaseObstacle create(float groundTopY,float spawnX,float playerHeight,Random random){
            float y = groundTopY + playerHeight + random.nextFloat()*150;
            Vector2 pos = new Vector2(spawnX,y);
            return pool.obtain(pos,100);
        }
        public void release(BaseObstacle obstacle){pool.release((HorizontalLaser)obstacle);}
        public void releaseAll(){pool.releaseAll();}
        public List<? extends BaseObstacle> getInUse(){return pool.getInUse();}
        public boolean supports(BaseObstacle obstacle){return obstacle instanceof HorizontalLaser;}
        public String getName(){return "HorizontalLaser";}
    }

    private static class HomingMissileCreator implements ObstacleCreator{
        private final HomingMissilePool pool = new HomingMissilePool();
        public BaseObstacle create(float groundTopY,float spawnX,float playerHeight,Random random){
            float y = groundTopY + playerHeight + random.nextFloat()*200;
            Vector2 pos = new Vector2(spawnX,y);
            return pool.obtain(pos);
        }
        public void release(BaseObstacle obstacle){pool.release((HomingMissile)obstacle);}
        public void releaseAll(){pool.releaseAll();}
        public List<? extends BaseObstacle> getInUse(){return pool.getInUse();}
        public boolean supports(BaseObstacle obstacle){return obstacle instanceof HomingMissile;}
        public String getName(){return "HomingMissile";}
    }
}
