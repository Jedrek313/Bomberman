package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

//public class MyGdxGame extends ApplicationAdapter {
public class MyGdxGame extends Game {
	//private OrthographicCamera camera;
	public SpriteBatch batch;
	public BitmapFont font;
	//Texture img;
	//Rectangle playBounds;
	//Texture bomberman;
	//Vector3 touchPoint;
	
	@Override
	public void create () {
		//camera = new OrthographicCamera();
		//camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		font = new BitmapFont();
		setScreen(new MainMenu(this));
		//bomberman = new Texture ();
	}

	@Override
	public void render () {
		super.render();
		//Gdx.gl.glClearColor(1, 0, 0, 1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//camera.update();
		/*
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) bucket.y += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) bucket.y -= 200 * Gdx.graphics.getDeltaTime();
		*/
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}
