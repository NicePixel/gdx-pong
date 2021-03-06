package com.pong.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.pong.game.objects.Ball;
import com.pong.game.objects.Player;

public class GDXPong extends ApplicationAdapter {
	// FIELDS
	public static final Color color_theme = new Color(1, 1, 1, 1);
	
	private final float midlane_thickness = 4;
	private final float edgeLine_thickness = 2;
	
	private float bg_R, bg_G, bg_B;
	private float bg_phase = 0.005f; // bg_max holds max value of R/G/B
	public float bg_max = 0.3f; 
	private int bg_state = 0;
	
	private SpriteBatch batch;
	private ShapeRenderer sr;
	private BitmapFont fnt_score;
	
	private Player p1, p2;
	private static int p1score;
	private static int p2score;
	private Ball ball;
	
	private OrthographicCamera cam;

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
		fnt_score.setColor(color_theme);
	
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(cam.position.x + Gdx.graphics.getWidth()/2, cam.position.y + Gdx.graphics.getHeight()/2, 0);
		cam.update();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(bg_R, bg_G, bg_B, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		updateBackground();

		// Draw shapes here:
		sr.begin(ShapeType.Filled);
		
		// Mid-line
		sr.setColor(color_theme);
		sr.rect(Gdx.graphics.getWidth()/2 - midlane_thickness/2, 0, midlane_thickness, Gdx.graphics.getHeight());
		sr.rect(0, 0, Gdx.graphics.getWidth(), 2);
		sr.rect(0, Gdx.graphics.getHeight()-edgeLine_thickness, Gdx.graphics.getWidth(), edgeLine_thickness);
		
		sr.end();
		
		// Draw other stuff here:
		batch.begin();
		batch.setProjectionMatrix(cam.combined);
		
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
		
		renderScore();
		ball.render(batch);
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
	
	public void updateBackground(){
		
		if(ball.isOnCooldown()){
			if(bg_max != 0.3f){
				bg_max = 0.3f;
				bg_phase /= 2;
			}
		}else{
			if(bg_max != 0.6f){
				bg_max = 0.6f;
				bg_phase *= 2;
			}
		}
		
		switch(bg_state){
		case 0:
			bg_R += bg_phase ;
			if(bg_R >= bg_max) bg_state = 1;
			break;
		case 1:
			bg_B += bg_phase;
			if(bg_B >= bg_max) bg_state = 2;
			break;
		case 2:
			bg_R -= bg_phase;
			if(bg_R <= 0) bg_state = 3;
			break;
		case 3:
			bg_G += bg_phase;
			if(bg_G >= bg_max) bg_state = 4;
			break;
		case 4:
			bg_B -= bg_phase;
			if(bg_B <= 0) bg_state = 5;
			break;
		case 5:
			bg_R += bg_phase;
			if(bg_R >= bg_max) bg_state = 6;
			break;
		case 6:
			bg_G -= bg_phase;
			if(bg_G <= 0) bg_state = 0;
			break;
		}
		
	}
	
}
