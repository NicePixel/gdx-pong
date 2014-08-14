package com.pong.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pong.game.GDXPong;

public class Ball {
	// FIELDS
	private final float speedX_init = 6;
	private final float speedY_init = 6;
	private float speedX = speedX_init;
	private float speedY = speedY_init;

	private Vector2 pos; // The position
	private Sprite sprite; // The sprite

	private float rotation;
	private float rotationSpeed;

	private Rectangle bounds;

	// Constructor
	public Ball() {

		// Set the visuals
		sprite = new Sprite(new Texture(Gdx.files.internal("ball.png")));
		sprite.setSize(16, 16);
		sprite.setOriginCenter();

		pos = new Vector2(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 - sprite.getHeight() / 2);

		bounds = new Rectangle();
		
		rotation = 0f;
		rotationSpeed = 2f;
	}

	// Render method
	public void render(SpriteBatch batch) {
		sprite.setRotation(rotation);
		sprite.setPosition(pos.x, pos.y);
		sprite.draw(batch);
	}

	// Update method
	public void update(Player p1, Player p2) {

		pos.x += speedX;
		pos.y += speedY;

		// Handle collision
		// Up
		if (pos.y + speedY + sprite.getHeight() > Gdx.graphics.getHeight()) {
			if(Math.abs(speedY) == speedY)
				speedY = -speedY;
		}
		// Down
		else if (pos.y - speedY - sprite.getHeight() < 0) {
			if(Math.abs(speedY) != speedY)
				speedY = -speedY;
		}
		
		// Right
		if (pos.x + speedX + sprite.getWidth() > Gdx.graphics.getWidth()) {
			GDXPong.setScore(1);
			resetBall(2);
		}
		// Left
		else if (pos.x + speedX + sprite.getWidth() < 0) {
			GDXPong.setScore(2);
			resetBall(1);
		}

		bounds.set(pos.x, pos.y, sprite.getWidth(), sprite.getHeight());
		
		// Handle collision with players
		if (bounds.overlaps(p1.getBounds())) {
			if (Math.abs(speedX) != speedX) {
				
				speedX += (speedX > 0?1:-1) * 0.2f;
				speedX = -speedX;
				
				System.out.println("--> hit p1");
				rotationSpeed = -rotationSpeed;
				
			}
		} else if (bounds.overlaps(p2.getBounds())) {
			if (Math.abs(speedX) == speedX) {
				
				speedX += (speedX > 0?1:-1) * 0.2f;
				speedX = -speedX;
				
				System.out.println("--> hit p2");
				rotationSpeed = -rotationSpeed;
				
			}
		}
		rotation += rotationSpeed;
		
	}

	private void resetBall(int winPlayer) {
		
//		if(winPlayer == 2){
//			if(speedX == Math.abs(speedX)){
//				speedX = -speedX;
//			}
//		}else if(winPlayer == 1){
//			if(speedX != Math.abs(speedX)){
//				speedX = -speedX;
//			}
//		}
		
		pos.set(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 - sprite.getHeight() / 2);

	}
}
