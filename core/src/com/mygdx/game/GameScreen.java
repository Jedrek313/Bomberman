package com.mygdx.game;

import java.util.Iterator;
import java.util.Random;
import java.util.Timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.Block.Breakable;

public class GameScreen extends ScreenAdapter implements InputProcessor {

    /*
    static final int GAME_READY = 0;
    static final int GAME_RUNNING = 1;
    static final int GAME_PAUSED = 2;
    static final int GAME_LEVEL_END = 3;
    static final int GAME_OVER = 4;
    */
    static{
        System.out.println(Gdx.files.getLocalStoragePath());
    }
    MyGdxGame game;

    int state;
    OrthographicCamera guiCam;
    Vector3 touchPoint;
    Rectangle pauseBounds;
    Rectangle resumeBounds;
    Rectangle quitBounds;
    int lastScore;
    String scoreString;
    Timer timer;
    private World world;
    private WorldRenderer renderer;
    private WorldController	controller;
    private int width, height;
    private boolean debug = false;
    private int time;
    int randomNumber;
    Random random;
    boolean up, down, right, left;
    int xr, yr, xu, yu, xd, yd, xl, yl;


    GlyphLayout glyphLayout = new GlyphLayout();


    public GameScreen(final MyGdxGame game){
        this.game = game;
    }

    @Override
    public void show() {
        world = new World();
        renderer = new WorldRenderer(world, debug);
        controller = new WorldController(world);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(time%20 == 0){
            moveEnemies();
            bombExplosions();
            flameDisapear();
        }
        controller.update(delta);
        renderer.render();
        time++;
    }

    @Override
    public void resize(int width, int height) {
        renderer.setSize(width, height);
        this.width = width;
        this.height = height;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);

    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.LEFT)
            controller.leftPressed();
        if (keycode == Keys.RIGHT)
            controller.rightPressed();
        if (keycode == Keys.UP)
            controller.upPressed();
        if (keycode == Keys.DOWN)
            controller.downPressed();
        if (keycode == Keys.X)
            controller.firePressed();
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Keys.LEFT)
            controller.leftReleased();
        if (keycode == Keys.RIGHT)
            controller.rightReleased();
        if (keycode == Keys.UP)
            controller.upReleased();
        if (keycode == Keys.DOWN)
            controller.downReleased();
        if (keycode == Keys.X)
            controller.fireReleased();
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchUp(int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void bombExplosions(){
        for(Bomb bomb : world.getBombs()){
            int x = (int)bomb.position.x;
            int y = (int)bomb.position.y;
            if (bomb.explode() == true){
                world.createFlame(x,y);
                up = true;
                right = true;
                down = true;
                left = true;
                xr = x;
                xl = x;
                xu = x;
                xd = x;
                yu = y;
                yd = y;
                yl = y;
                yr = y;
                for(int power = 0; power<bomb.getPower(); power++){
                    if(right){
                        switch (world.blockTable[xr+1][yr]){
                            case 0:
                                world.createFlame(xr+1, yr);
                                break;
                            case 1:
                                right = false;
                                break;
                            case 2:
                                world.createFlame(xr+1, yr);
                                world.blockTable[1][1]=2;
                                world.bob.moveToStart();
                                break;
                            case 3:
                                world.createFlame(xr+1, yr);
                                for (Enemy enemy : world.getEnemies()){
                                    if(enemy.position.x ==xr+1 && enemy.position.y == yr){
                                        world.deleteEnemy(enemy);
                                    }
                                }
                                break;
                            case 4:
                                /*
                                for (Bomb bomb2 : world.getBombs()){
                                    if(bomb2.position.x ==xr+1 && bomb2.position.y == y){
                                        bomb2.zeroCounter();
                                    }
                                }
                                */
                                break;
                            case 5:
                                //do nothing
                                break;
                            case 6:
                                world.createFlame(xr+1, y);
                                for (Breakable breakable : world.getBreaks()){
                                    if(breakable.position.x ==xr+1 && breakable.position.y == yr){
                                        world.deleteBreakable(breakable);
                                    }
                                }
                                right = false;
                                break;
                        }
                    }
                    if(left){
                        switch (world.blockTable[xl-1][yl]){
                            case 0:
                                world.createFlame(xl-1, yl);
                                break;
                            case 1:
                                left = false;
                                break;
                            case 2:
                                world.createFlame(xl-1, yl);
                                world.blockTable[1][1]=2;
                                world.bob.moveToStart();
                                break;
                            case 3:
                                world.createFlame(xl-1, yl);
                                for (Enemy enemy : world.getEnemies()){
                                    if(enemy.position.x ==xl-1 && enemy.position.y == yl){
                                        world.deleteEnemy(enemy);
                                    }
                                }
                                break;
                            case 4:
                                /*
                                for (Bomb bomb2 : world.getBombs()){
                                    if(bomb2.position.x ==xl-1 && bomb2.position.y == y){
                                        bomb2.zeroCounter();
                                    }
                                }
                                */
                                break;
                            case 5:
                                //do nothing
                                break;
                            case 6:
                                world.createFlame(xl-1, yl);
                                for (Breakable breakable : world.getBreaks()){
                                    if(breakable.position.x ==xl-1 && breakable.position.y == yl){
                                        world.deleteBreakable(breakable);
                                    }
                                }
                                left = false;
                                break;
                        }
                    }
                    if(up){
                        switch (world.blockTable[x][yu+1]){
                            case 0:
                                world.createFlame(x, yu+1);
                                break;
                            case 1:
                                up = false;
                                break;
                            case 2:
                                world.createFlame(x, yu+1);
                                world.blockTable[1][1]=2;
                                world.bob.moveToStart();
                                break;
                            case 3:
                                world.createFlame(x, yu+1);
                                for (Enemy enemy : world.getEnemies()){
                                    if(enemy.position.x ==x && enemy.position.y == yu+1){
                                        world.deleteEnemy(enemy);
                                    }
                                }
                                break;
                            case 4:
                                /*
                                for (Bomb bomb2 : world.getBombs()){
                                    if(bomb2.position.x ==x && bomb2.position.y == yu+1){
                                        bomb2.zeroCounter();
                                    }
                                }
                                */
                                break;
                            case 5:
                                //do nothing
                                break;
                            case 6:
                                world.createFlame(x, yu+1);
                                for (Breakable breakable : world.getBreaks()){
                                    if(breakable.position.x ==x && breakable.position.y == yu+1){
                                        world.deleteBreakable(breakable);
                                    }
                                }
                                up = false;
                                break;
                        }
                    }
                    if(down){
                        switch (world.blockTable[x][yd-1]){
                            case 0:
                                world.createFlame(x, yd-1);
                                break;
                            case 1:
                                down = false;
                                break;
                            case 2:
                                world.createFlame(x, yd-1);
                                world.blockTable[1][1]=2;
                                world.bob.moveToStart();
                                break;
                            case 3:
                                world.createFlame(x, yd-1);
                                for (Enemy enemy : world.getEnemies()){
                                    if(enemy.position.x ==x && enemy.position.y == yd-1){
                                        world.deleteEnemy(enemy);
                                    }
                                }
                                break;
                            case 4:
                                /*
                                for (Bomb bomb2 : world.getBombs()){
                                    if(bomb2.position.x ==x && bomb2.position.y == yd-1){
                                        bomb2.zeroCounter();
                                    }
                                }
                                */
                                break;
                            case 5:
                                //do nothing
                                break;
                            case 6:
                                world.createFlame(x, yd-1);
                                for (Breakable breakable : world.getBreaks()){
                                    if(breakable.position.x ==x && breakable.position.y == yd-1){
                                        world.deleteBreakable(breakable);
                                    }
                                }
                                down = false;
                                break;
                        }
                    }
                    yu++;
                    yd--;
                    xr++;
                    xl--;
                }
                world.deleteBomb(bomb);
            }
        }
    }

    public void flameDisapear(){
        for(Flame flame : world.getFlames()){
            if(flame.disapear()) {
                world.deleteFlame(flame);
            }
        }
    }

    public void moveEnemies(){
        random = new Random();
        for (Enemy enemy : world.getEnemies()) {
            randomNumber = random.nextInt(4);
            switch (randomNumber) {
                case 0:
                    //floor
                    if(world.blockTable[(int)enemy.position.x-1][(int)enemy.position.y]==0) {
                        world.blockTable[(int) enemy.position.x][(int) enemy.position.y] = 0;
                        world.blockTable[(int) enemy.position.x - 1][(int) enemy.position.y] = 3;
                        enemy.move(-1, 0);
                    }
                    //player
                    else{
                        if(world.blockTable[(int)enemy.position.x-1][(int)enemy.position.y]==2) {
                            world.blockTable[(int) enemy.position.x][(int) enemy.position.y] = 0;
                            world.blockTable[(int) enemy.position.x - 1][(int) enemy.position.y] = 3;
                            world.bob.moveToStart();
                            enemy.move(-1, 0);
                        }
                        else {
                            if(world.blockTable[(int)enemy.position.x-1][(int)enemy.position.y]==5){
                                world.blockTable[(int) enemy.position.x][(int) enemy.position.y] = 0;
                                world.deleteEnemy(enemy);
                            }
                        }
                    }
                    break;

                case 1:
                    if(world.blockTable[(int)enemy.position.x+1][(int)enemy.position.y]==0) {
                        world.blockTable[(int) enemy.position.x][(int) enemy.position.y] = 0;
                        world.blockTable[(int) enemy.position.x + 1][(int) enemy.position.y] = 3;
                        enemy.move(1, 0);
                    }
                    else{
                        if(world.blockTable[(int)enemy.position.x+1][(int)enemy.position.y]==2) {
                            world.blockTable[(int) enemy.position.x][(int) enemy.position.y] = 0;
                            world.blockTable[(int) enemy.position.x + 1][(int) enemy.position.y] = 3;
                            world.bob.moveToStart();
                            enemy.move(1, 0);
                        }
                        else {
                            if(world.blockTable[(int)enemy.position.x+1][(int)enemy.position.y]==5){
                                world.blockTable[(int) enemy.position.x][(int) enemy.position.y] = 0;
                                world.deleteEnemy(enemy);
                            }
                        }
                    }
                    break;

                case 2:
                    if(world.blockTable[(int)enemy.position.x][(int)enemy.position.y+1]==0) {
                        world.blockTable[(int) enemy.position.x][(int) enemy.position.y] = 0;
                        world.blockTable[(int) enemy.position.x][(int) enemy.position.y+1] = 3;
                        enemy.move(0, 1);
                    }
                    else{
                        if(world.blockTable[(int)enemy.position.x][(int)enemy.position.y+1]==2) {
                            world.blockTable[(int) enemy.position.x][(int) enemy.position.y] = 0;
                            world.blockTable[(int) enemy.position.x][(int) enemy.position.y+1] = 3;
                            world.bob.moveToStart();
                            enemy.move(0, 1);
                        }
                        else {
                            if(world.blockTable[(int)enemy.position.x][(int)enemy.position.y+1]==5){
                                world.blockTable[(int) enemy.position.x][(int) enemy.position.y] = 0;
                                world.deleteEnemy(enemy);
                            }
                        }
                    }
                    break;

                case 3:
                    if(world.blockTable[(int)enemy.position.x][(int)enemy.position.y-1]==0) {
                        world.blockTable[(int) enemy.position.x][(int) enemy.position.y] = 0;
                        world.blockTable[(int) enemy.position.x][(int) enemy.position.y-1] = 3;
                        enemy.move(0, -1);
                    }
                    else{
                        if(world.blockTable[(int)enemy.position.x][(int)enemy.position.y-1]==2) {
                            world.blockTable[(int) enemy.position.x][(int) enemy.position.y] = 0;
                            world.blockTable[(int) enemy.position.x][(int) enemy.position.y-1] = 3;
                            world.bob.moveToStart();
                            enemy.move(0, -1);
                        }
                        else {
                            if(world.blockTable[(int)enemy.position.x][(int)enemy.position.y-1]==5){
                                world.blockTable[(int) enemy.position.x][(int) enemy.position.y] = 0;
                                world.deleteEnemy(enemy);
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
