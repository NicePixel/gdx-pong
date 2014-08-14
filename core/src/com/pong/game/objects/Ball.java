package com.pong.game.objects;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pong.game.GDXPong;

public class Ball {
	// FIELDS
	private Random rand;
	private float speedX_init; // Ball's initial X speed
	private float speedY_init; // Ball's initial Y speed
	private float speedX;
	private float speedY;
	
	private final float ballCooldown = 3f; // How much time ball _waits_ to be launched
	private float currentBallCooldown = 0f; // How much time ball _waited_ to be launched
	
	private Vector2 pos; // The position
	private Sprite sprite; // The sprite

	private float rotation;
	private float rotationSpeed;

	private Rectangle bounds;

	// Constructor
	public Ball() {
		rand = new Random();
		
		// Set the visuals
		sprite = new Sprite(new Texture(Gdx.files.internal("ball.png")));
		sprite.setSize(16, 16);
//		sprite.setOriginCenter();
		
		pos = new Vector2(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 - sprite.getHeight() / 2);

		bounds = new Rectangle();
		
		rotation = 0f;
		rotationSpeed = 2f;
		
		resetBall();
	}

	// Render method
	public void render(SpriteBatch batch) {
		sprite.setRotation(rotation);
		sprite.setPosition(pos.x, pos.y);
		sprite.draw(batch);
	}

	// Update method
	public void update(Player p1, Player p2) {
		if(currentBallCooldown < ballCooldown){
			currentBallCooldown += Gdx.graphics.getDeltaTime();
		}else{
			
			pos.x += speedX;
			pos.y += speedY;
			
			// Handle collision
			// Up
			if (pos.y + speedY + sprite.getHeight() > Gdx.graphics.getHeight()) {
				if(Math.abs(speedY) == speedY)
					speedY = -speedY;
			}
			// Down
			else if (pos.y - speedY - sprite.getHeight()/2 < 0) {
				if(Math.abs(speedY) != speedY)
					speedY = -speedY;
			}
			
			// Right
			if (pos.x + speedX + sprite.getWidth()/2 > Gdx.graphics.getWidth()) {
				GDXPong.setScore(1);
				resetBall();
			}
			// Left
			else if (pos.x + speedX + sprite.getWidth()/2 < 0) {
				GDXPong.setScore(2);
				resetBall();
			}
			
			bounds.set(pos.x, pos.y, sprite.getWidth(), sprite.getHeight());
			
			// Handle collision with players
			if (bounds.overlaps(p1.getBounds())) {
				if (Math.abs(speedX) != speedX) {
					speedUp(p1.getBounds(), bounds);
				}
			} else if (bounds.overlaps(p2.getBounds())) {
				if (Math.abs(speedX) == speedX) {
					speedUp(p2.getBounds(), bounds);
				}
			}
			rotation += rotationSpeed;
			
		}
	}
	
//	private void speedUp(){
//		speedX += (speedX > 0?1:-1) * 0.2f;
//		speedX = -speedX;
//		
//		System.out.println("--> hit");
//		rotationSpeed = -rotationSpeed;
//	}
	
	private void speedUp(Rectangle colliderBounds, Rectangle bounds){
		speedX += (speedX > 0?1:-1) * 0.2f;
		speedX = -speedX;
		speedY += (colliderBounds.y + colliderBounds.getHeight()/2 < pos.y ? Math.signum(speedY):0f );
		
		System.out.println("--> hit");
		rotationSpeed = -rotationSpeed;
	}
	
	private void resetBall() {

		speedX_init = rand.nextFloat()*4 + 2.5f;
		speedY_init = rand.nextFloat()*4 + 1.5f;
		speedX = speedX_init;
		speedY = speedY_init;
		
		rotation = 0;
		currentBallCooldown = 0f;
		pos = new Vector2(Gdx.graphics.getWidth() / 2 - sprite.getWidth()/2,
				Gdx.graphics.getHeight() / 2 - sprite.getHeight()/2);

	}
	
	public boolean isOnCooldown(){
		return (currentBallCooldown < ballCooldown);
	}
}
