package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Block.Block;

/**
 * Created by jedre on 17.01.2017.
 */
public class Player {

    public enum State {
        IDLE, WALKING_UP, WALKING_DOWN, WALKING_LEFT, WALKING_RIGHT, DYING
    }

    static final float SIZE = 1.0f;

    Vector2 	position = new Vector2();
    Vector2 	acceleration = new Vector2();
    Vector2 	velocity = new Vector2();
    Rectangle 	bounds = new Rectangle();
    State		state = State.IDLE;
    boolean		facingLeft = true;
    float		stateTime = 0;
    Vector2     velocityFrame = new Vector2();
    int x;
    int y;
    static int start = 1;
    int deathCount = 0;
    int power = 2;

    public Player(Vector2 position, int power) {
        this.position = position;
        this.bounds.height = SIZE;
        this.bounds.width = SIZE;
        x = (int) position.x;
        y = (int) position.y;
        this.power = power;
    }

    int getX(){
        return x;
    }
    int getY(){
        return y;
    }

    void moveToStart(){
        deathCount++;
        position.x = start;
        position.y = start;
    }
    public boolean isFacingLeft() {
        return facingLeft;
    }

    public void setFacingLeft(boolean facingLeft) {
        this.facingLeft = facingLeft;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public State getState() {
        return state;
    }

    public void setState(State newState) {
        this.state = newState;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void move(float x, float y){
        position.add(x, y);
    }

    public void update(float delta) {
        stateTime += delta;
        position.add(velocityFrame);
    }

    public void setPower(int power){
        this.power = power;
    }

}
