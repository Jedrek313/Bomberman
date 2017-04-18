package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by jedre on 27.01.2017.
 */
public class Flame {
    public static final float SIZE = 1f;
    Vector2  position = new Vector2();
    Rectangle bounds = new Rectangle();
    int x;
    int y;
    int counter;

    public Flame(Vector2 pos) {
        this.position = pos;
        this.bounds.width = SIZE;
        this.bounds.height = SIZE;
        this.counter = 2;
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean disapear(){
        if(counter < 1){
            return true;
        }
        else {
            counter--;
            return false;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
