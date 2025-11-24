package com.gorga.frontend.pools;

import com.badlogic.gdx.math.Vector2;
import com.gorga.frontend.Coin;
import com.gorga.frontend.obstacles.BaseObstacle;

public class CoinPool extends ObjectPool<Coin>{

    @Override
    protected Coin createObject() {
        return new Coin(new Vector2(0,0));
    }

    @Override
    protected void resetObject(Coin object) {
        coin.setActive(false);
    }

    public obtain(float x, float y){
        super.obtain();
        return coin;
    }



}
