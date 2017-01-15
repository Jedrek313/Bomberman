package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by jedre on 15.01.2017.
 */
public class MainMenu implements Screen{

    final MyGdxGame game;

    Texture img;
    Rectangle playBounds;
    Texture bomberman;
    Vector3 touchPoint;

    OrthographicCamera camera;

    public MainMenu(final MyGdxGame game){
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);


        img = new Texture("main.jpg");

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        game.batch.begin();
        game.batch.draw(img, 0, 0);
        game.batch.end();

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Tap anywhere to begin!", 350, 100);
        game.batch.end();
/*
        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
  */
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        img.dispose();
    }
}
