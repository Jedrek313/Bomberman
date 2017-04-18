package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Timer;
import java.util.TimerTask;


public class Bomb {

    Vector2  position = new Vector2();
    Rectangle  bounds = new Rectangle();
    public static final float SIZE = 1f;
    private int bombCounter;
    private int power;

    public Bomb(Vector2 pos, int power) {
        this.position = pos;
        this.bounds.width = SIZE;
        this.bounds.height = SIZE;
        this.bombCounter = 3;
        this.power = power;
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getPower(){
        return power;
    }

    public void zeroCounter(){
        this.bombCounter = 0;
    }

    public boolean explode(){
        this.bombCounter--;
        if(bombCounter<1){
            return true;
        }
        return false;
    }


}