package com.mygdx.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by jedre on 27.01.2017.
 */
public class Enemy {
    Vector2 position = new Vector2();
    Rectangle bounds = new Rectangle();
    public static final float SIZE = 1f;
    public boolean alive;
    int randomNumber;
    public Random random;

    public Enemy(Vector2 pos) {
        this.position = pos;
        this.bounds.width = SIZE;
        this.bounds.height = SIZE;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void move(float x, float y){

        position.add(x, y);
    }

}
