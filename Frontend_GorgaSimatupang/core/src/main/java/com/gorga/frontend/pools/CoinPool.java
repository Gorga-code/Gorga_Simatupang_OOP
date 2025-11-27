package com.gorga.frontend.pools;

import com.badlogic.gdx.math.Vector2;
import com.gorga.frontend.Coin;
import com.badlogic.gdx.utils.Pool;

public class CoinPool extends Pool<Coin> {

    public Coin obtain(float x, float y){
        Coin coin = super.obtain();
        coin.getPosition().set(x,y);
        coin.setActive(true);
        return coin;
    }

    @Override
    protected Coin newObject() {
        return new Coin(new Vector2(0,0));
    }

    @Override
    protected void reset(Coin object) {
        object.setActive(false);

    }
}
