package com.pong.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pong.game.objects.Ball;
import com.pong.game.objects.Player;

public class GDXPong extends ApplicationAdapter {
	// FIELDS
	private SpriteBatch batch;
	private BitmapFont fnt_score;
	
	private Player p1, p2;
	private static int p1score;
	private static int p2score;
	private Ball ball;

	@Override
	public void create() {
		batch = new SpriteBatch();
		
		p1 = new Player(1);
		p2 = new Player(2);
		ball = new Ball();
		
		p1score = 0; 
		p2score = 0;
		
		fnt_score = new BitmapFont(Gdx.files.internal("score.fnt"));
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0f, 0f, 0.15f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		update();
		rnder();
		batch.end();
	}

	// Update sequence
	public void update() {
		p1.update();
		p2.update();
		
		ball.update(p1, p2);
	}

	// Render sequence
	public void rnder() {
		p1.render(batch);
		p2.render(batch);
		
		ball.render(batch);
		
		renderScore();
	}

	private void renderScore() {
		fnt_score.draw(batch, ""+p1score, Gdx.graphics.getWidth()/2 - "0000".length()*32, Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/32);
		fnt_score.draw(batch, ""+p2score, Gdx.graphics.getWidth()/2 + "0000".length()*32, Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/32);
	}
	
	public static void setScore(int toPlayer){
		switch(toPlayer){
		case 1:
			System.out.printf("P1:%d, P2:%d\n", ++p1score, p2score);
			break;
		case 2:
			System.out.printf("P1:%d, P2:%d\n", p1score, ++p2score);
			break;
		default:
			System.out.println("Uknown player!");
			break;
		}
	}
}
