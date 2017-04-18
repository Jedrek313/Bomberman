package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Block.Block;
import com.mygdx.game.Block.Breakable;

import java.util.List;
import java.util.Random;

public class World {

    /** The blocks making up the world **/
    Array<Block> blocks = new Array<Block>();
    Array<Enemy> enemies = new Array<Enemy>();
    Array<Breakable> breaks = new Array<Breakable>();
    Array<Bomb> bombs = new Array<Bomb>();
    Array<Flame> flames = new Array<Flame>();
    /** Our player controlled hero **/
    private Random random;
    Player bob;
    int randomNumber;
    private int power = 2;
    int[][]blockTable  = new int[15][15];
    //0 - floor
    //1 - block
    //2 - player
    //3 - enemy
    //4 - bomb
    //5 - flame
    //6 - breakableblock

    // Getters -----------
    public Array<Block> getBlocks() {
        return blocks;
    }

    public Array<Breakable> getBreaks() {
        return breaks;
    }

    public Array<Enemy> getEnemies() {
        return enemies;
    }

    public Array<Bomb> getBombs() {
        return bombs;
    }

    public Array<Flame> getFlames() {
        return flames;
    }

    public Player getBob() {
        return bob;
    }
    // --------------------

    public World() {
        for(int i=0; i< blockTable.length; i++)
            for(int j=0; j< blockTable[i].length; j++)
                blockTable[i][j] = 0;
        createWorld();
    }

    public void createBomb(int x, int y, int power){
        bombs.add(new Bomb(new Vector2(x,y), power));
        blockTable[x][y] = 4;
    }

    public void createFlame(int x, int y){
        flames.add(new Flame(new Vector2(x,y)));
        blockTable[x][y] = 5;
    }

    public void deleteBreakable(Breakable breakable){
        breaks.removeValue(breakable, true);
    }

    public void deleteFlame(Flame flame){
        blockTable[(int)flame.position.x][(int)flame.position.y] = 0;
        flames.removeValue(flame, true);
    }

    public void deleteBomb(Bomb bomb){
        //blockTable[(int)bomb.position.x][(int)bomb.position.y] = 0;
        bombs.removeValue(bomb, true);
    }
    public void deleteEnemy(Enemy enemy){
        enemies.removeValue(enemy, true);
    }

    private void createWorld() {
        random = new Random();
        bob = new Player(new Vector2(1, 1), power);
        blockTable[1][1] = 2;
        for(int j=5; j<13; j=j+2){
            randomNumber = random.nextInt(5);
            if(randomNumber==1||randomNumber==2 ) {
                enemies.add(new Enemy(new Vector2(1, j)));
                blockTable[1][j] = 3;
            }
            if(randomNumber==3||randomNumber==4 ) {
                enemies.add(new Enemy(new Vector2(13, j)));
                blockTable[13][j] = 3;
            }
        }
        enemies.add(new Enemy(new Vector2(13, 13) ));
        blockTable[13][13]=3;
        enemies.add(new Enemy(new Vector2(1, 13) ));
        blockTable[1][13]=3;
        enemies.add(new Enemy(new Vector2(13, 1) ));
        blockTable[13][1]=3;


        for(int i = 3; i<12; i++){
            for(int j=1; j < 14; j= j+2){
                randomNumber = random.nextInt(5);
                //randomNumber = 1;
                if(randomNumber==1||randomNumber==2 || randomNumber ==3) {
                    breaks.add(new Breakable(new Vector2(i, j)));
                    blockTable[i][j] = 6;
                }
            }
        }

        for(int i = 1; i<15; i=i+2){
            for(int j=4; j < 14; j=j+2){
                randomNumber = random.nextInt(4);
                randomNumber = 1;
                if(randomNumber==1||randomNumber==2 || randomNumber ==3) {
                    breaks.add(new Breakable(new Vector2(i, j)));
                    blockTable[i][j] = 6;
                }

            }
        }

        for (int i = 0; i < 15; i++) {
            blocks.add(new Block(new Vector2(i, 0)));
            blockTable[i][0] = 1;
            blocks.add(new Block(new Vector2(i, 14)));
            blockTable[i][14] = 1;
        }
        for (int j = 1; j < 14; j++) {
            blocks.add(new Block(new Vector2(0, j)));
            blockTable[0][j] = 1;
            blocks.add(new Block(new Vector2(14, j)));
            blockTable[14][j] = 1;
        }
        for (int i = 2; i < 14; i=i+2) {
            for (int j=2; j<14; j=j+2){
                blocks.add(new Block(new Vector2(i, j)));
                blockTable[i][j] = 1;
            }
        }
    }



}