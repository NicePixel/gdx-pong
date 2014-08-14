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
	private float speedX = 6;
	private float speedY = 6;

	private Vector2 pos; // The position
	private Sprite sprite; // The sprite
	
	private float rotation = 0f;
	private float rotationSpeed = 2f;

	private Rectangle bounds;

	// Constructor
	public Ball() {

		// Set the visuals
		sprite = new Sprite(new Texture(Gdx.files.internal("ball.png")));
		sprite.setSize(sprite.getTexture().getWidth(), sprite.getTexture().getHeight());
		sprite.setOriginCenter();

		pos = new Vector2(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 - sprite.getHeight() / 2);

		bounds = new Rectangle();
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
		
		// reset the bounds
		bounds.set(pos.x, pos.y, sprite.getWidth(), sprite.getHeight());

		// Handle collision with players
		// p1
		if (bounds.overlaps(p1.getBounds())) {
			
			if (Math.abs(speedX) != speedX) {
				speedX -= 0.2f;
				speedX = -speedX;
				
				if(pos.y > p1.getPos().y + p1.getBounds().getHeight()/2){
					if(speedY != Math.abs(speedY)){
						speedY += 0.05f;
						speedY = - speedY;
					}
				}
				
				rotationSpeed = -rotationSpeed;
				System.out.println("--> hit p1");
			}
			
		}
		// p2
		else if (bounds.overlaps(p2.getBounds())) {
			
			if (Math.abs(speedX) == speedX) {
				speedX += 0.2f;
				speedX = -speedX;
				
				if(pos.y > p2.getPos().y + p2.getBounds().getHeight()/2){
					if(speedY != Math.abs(speedY)){
						speedY = - speedY;
					}
				}
				
				rotationSpeed = -rotationSpeed;
				System.out.println("--> hit p2 ");
			}
		}
		
		rotation += rotationSpeed;
		
	}

	private void resetBall(int i) {
		if(i == 1){
			if(speedX == Math.abs(speedX)){
				speedX = -speedX;
			}
		}else if(i == 2){
			if(speedX != Math.abs(speedX)){
				speedX = -speedX;
			}
		}
		pos.set(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 - sprite.getHeight() / 2);

	}
}
