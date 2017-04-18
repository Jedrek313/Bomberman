package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Block.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class WorldController {

    /*
    private WorldRenderer 	renderer;
    enum Keys {
        LEFT, RIGHT, UP, DOWN
    }
*/
    enum Keys {
        LEFT, RIGHT, DOWN, UP, FIRE
    }

    int timeLeft;
    private World  world;
    private Player  bob;
    int flag = 0;
    Vector2 position;
    Vector2 position2;
    int flag2;

    static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();
    static {
        keys.put(Keys.LEFT, false);
        keys.put(Keys.RIGHT, false);
        keys.put(Keys.DOWN, false);
        keys.put(Keys.UP, false);
        keys.put(Keys.FIRE, false);
    };

    public WorldController(World world) {
        this.world = world;
        this.bob = world.getBob();
    }

    // ** Key presses and touches **************** //

    public void leftPressed() {
            keys.get(keys.put(Keys.LEFT, true));
    }

    public void rightPressed() {
            keys.get(keys.put(Keys.RIGHT, true));
    }

    public void upPressed() {
            keys.get(keys.put(Keys.UP, true));
    }

    public void downPressed() {
            keys.get(keys.put(Keys.DOWN, true));
    }

    public void firePressed() {
        keys.get(keys.put(Keys.FIRE, true));
    }

    public void leftReleased() {
        keys.get(keys.put(Keys.LEFT, false));
        flag=0;
    }

    public void rightReleased() {
        keys.get(keys.put(Keys.RIGHT, false));
        flag=0;
    }

    public void downReleased() {
        keys.get(keys.put(Keys.DOWN, false));
        flag=0;
    }

    public void upReleased() {
        keys.get(keys.put(Keys.UP, false));
        flag=0;
    }

    public void fireReleased() {
        keys.get(keys.put(Keys.FIRE, false));
    }

    /** The main update method **/
    public void update(float delta) {
        processInput();
        bob.update(delta);
    }

    void setFlag(int a){
        flag = a;
    }

    /** Change Bob's state and parameters based on input controls **/
    private void processInput() {
        if (keys.get(Keys.FIRE)){
            if(world.blockTable[(int)bob.position.x][(int)bob.position.y]==2){
                world.createBomb((int)bob.position.x, (int)bob.position.y, bob.power);
            }
        }
        if (keys.get(Keys.LEFT)) {
            // left is pressed
            if(flag==0) {
                if(world.blockTable[(int)bob.position.x-1][(int)bob.position.y]==0) {
                    if(world.blockTable[(int)bob.position.x][(int)bob.position.y]!=4) {
                        world.blockTable[(int) bob.position.x][(int) bob.position.y] = 0;
                    }
                    world.blockTable[(int)bob.position.x-1][(int)bob.position.y]=2;
                    bob.setState(Player.State.WALKING_LEFT);
                    bob.move(-1, 0);
                    flag++;
                    //bob.getVelocity().x = -Player.SPEED;
                }
                else{
                    if(world.blockTable[(int)bob.position.x-1][(int)bob.position.y]==3 || world.blockTable[(int)bob.position.x-1][(int)bob.position.y]==5) {
                        //if(world.blockTable[(int)bob.position.x][(int)bob.position.y]==bombnumber)
                        world.blockTable[(int) bob.position.x][(int) bob.position.y] = 0;
                        //world.blockTable[(int)bob.position.x-1][(int)bob.position.y]=2;
                        //bob.setState(Player.State.WALKING_LEFT);
                        world.blockTable[1][1] = 0;
                        bob.moveToStart();
                        flag++;
                        //bob.getVelocity().x = -Player.SPEED;
                    }
                }
            }
        }
        if (keys.get(Keys.RIGHT)) {
            // left is pressed
            if(flag==0) {
                if(world.blockTable[(int)bob.position.x+1][(int)bob.position.y]==0) {
                    if(world.blockTable[(int)bob.position.x][(int)bob.position.y]!=4) {
                        world.blockTable[(int) bob.position.x][(int) bob.position.y] = 0;
                    }
                    world.blockTable[(int) bob.position.x + 1][(int) bob.position.y] = 2;
                    bob.setState(Player.State.WALKING_RIGHT);
                    bob.move(1, 0);
                    flag++;
                    //bob.getVelocity().x = Player.SPEED;
                }
                else{
                    if(world.blockTable[(int)bob.position.x+1][(int)bob.position.y]==3 || world.blockTable[(int)bob.position.x+1][(int)bob.position.y]==5) {
                        //if(world.blockTable[(int)bob.position.x][(int)bob.position.y]==bombnumber)
                        world.blockTable[(int) bob.position.x][(int) bob.position.y] = 0;
                        world.blockTable[1][1] = 0;
                        bob.moveToStart();
                        flag++;
                        //bob.getVelocity().x = Player.SPEED;
                    }
                }
            }
        }
        if (keys.get(Keys.UP)) {
            // left is pressed
            if(flag==0) {
                if(world.blockTable[(int)bob.position.x][(int)bob.position.y+1]==0) {
                    if (world.blockTable[(int) bob.position.x][(int) bob.position.y] != 4) {
                        world.blockTable[(int) bob.position.x][(int) bob.position.y] = 0;
                    }
                    world.blockTable[(int) bob.position.x ][(int) bob.position.y + 1] = 2;
                    bob.setState(Player.State.WALKING_LEFT);
                    bob.move(0, (float) 1);
                    //bob.getVelocity().y = Player.SPEED;
                    flag++;
                }
                else{
                    if(world.blockTable[(int)bob.position.x][(int)bob.position.y+1]==3||world.blockTable[(int)bob.position.x][(int)bob.position.y+1]==5 ) {
                        //if(world.blockTable[(int)bob.position.x][(int)bob.position.y]==bombnumber)
                        world.blockTable[(int) bob.position.x][(int) bob.position.y] = 0;
                        world.blockTable[1][1] = 0;
                        bob.moveToStart();
                        //bob.getVelocity().y = Player.SPEED;
                        flag++;
                    }
                }
            }
        }
        if (keys.get(Keys.DOWN)) {
            // left is pressed
            if(flag==0) {
                if(world.blockTable[(int)bob.position.x][(int)bob.position.y-1]==0) {
                    if(world.blockTable[(int)bob.position.x][(int)bob.position.y]!=4) {
                        world.blockTable[(int) bob.position.x][(int) bob.position.y] = 0;
                    }
                    world.blockTable[(int) bob.position.x][(int) bob.position.y - 1] = 2;
                    bob.setState(Player.State.WALKING_DOWN);
                    bob.move(0, (float) -1);
                    //bob.getVelocity().y = -Player.SPEED;
                    flag++;
                }
                else{
                    if(world.blockTable[(int)bob.position.x][(int)bob.position.y-1]==3 || world.blockTable[(int)bob.position.x][(int)bob.position.y-1]==5) {
                        //if(world.blockTable[(int)bob.position.x][(int)bob.position.y]==bombnumber)
                        world.blockTable[(int) bob.position.x][(int) bob.position.y] = 0;
                        world.blockTable[1][1] = 0;
                        bob.moveToStart();
                        flag++;
                    }
                }
            }
        }
        // need to check if both or none direction are pressed, then Bob is idle
        //flag = 0;
        /*
        if (keys.get(Keys.LEFT)) {
            flag++;
        }
        if (keys.get(Keys.RIGHT)) {
            flag++;
        }
        if (keys.get(Keys.UP)) {
            flag++;
        }
        if (keys.get(Keys.DOWN)) {
            flag++;
        }
        */
        /*
        if(flag!=1){
            bob.setState(Player.State.IDLE);

            bob.getAcceleration().x = 0;

            bob.getVelocity().x = 0;
        }
    }

    void movePlayer(float x, float y){

        //flag2=0;

        //if(world.getStop()==false ) {
            //float dt = Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f);
            bob.move(x , y);
        //}
        /*
        for(Block block : world.getBlocks()){
            position = block.getPosition();
            if(position.x == bob.a+1 || position.y==bob.b){
                flag2++;
            }
        }
        if (flag2==0) {
        }
        */
    }

}