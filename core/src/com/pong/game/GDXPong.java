package com.pong.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.pong.game.objects.Ball;
import com.pong.game.objects.Player;

public class GDXPong extends ApplicationAdapter {
	// FIELDS
	private final float midlane_width = 4;
	private final Color midlane_color = new Color(1, 1, 1, 0.1f);
	
	private SpriteBatch batch;
	private ShapeRenderer sr;
	private BitmapFont fnt_score;
	
	private Player p1, p2;
	private static int p1score;
	private static int p2score;
	private Ball ball;

	@Override
	public void create() {
		batch = new SpriteBatch();
		sr = new ShapeRenderer();
		
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
		
		sr.begin(ShapeType.Filled);
		batch.begin();
		
		update();
		rnder();
		
		batch.end();
		sr.end();
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
		
		
		// Midlane
		sr.setColor(midlane_color);
		sr.rect(Gdx.graphics.getWidth()/2 - midlane_width/2, 0, midlane_width, Gdx.graphics.getHeight());

		ball.render(batch);
		
		renderScore();
	}

	private void renderScore() {
		String txt1 = ""+p1score;
		String txt2 = ""+p2score;
		
		fnt_score.draw(batch, txt1, Gdx.graphics.getWidth()/2 - fnt_score.getBounds(txt1).width*5 - fnt_score.getBounds(txt1).width, Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/32);
		fnt_score.draw(batch, txt2, Gdx.graphics.getWidth()/2 + fnt_score.getBounds(txt2).width*5, Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/32);
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
