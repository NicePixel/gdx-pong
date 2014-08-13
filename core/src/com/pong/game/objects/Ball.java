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

	private Rectangle bounds;

	// Constructor
	public Ball() {

		// Set the visuals
		sprite = new Sprite(new Texture(Gdx.files.internal("ball.png")));
		sprite.setSize(16, 16);

		pos = new Vector2(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 - sprite.getHeight() / 2);

		bounds = new Rectangle();
	}

	// Render method
	public void render(SpriteBatch batch) {
		sprite.setPosition(pos.x, pos.y);
		sprite.draw(batch);
	}

	// Update method
	public void update(Player p1, Player p2) {

		pos.x += speedX;
		pos.y += speedY;

		// Collision with upper&&bottom bounds of window
		// Up
		if (pos.y + speedY + sprite.getHeight() > Gdx.graphics.getHeight()) {
			while (pos.y + Math.signum(speedY) + sprite.getHeight() < Gdx.graphics
					.getHeight()) {
				pos.y += Math.signum(speedY);
			}
			speedY = -speedY;

		}
		// Down
		else if (pos.y + speedY + sprite.getHeight() < 0) {
			while (pos.y - Math.signum(speedY) - sprite.getHeight() < 0) {
				pos.y -= Math.signum(speedY);
			}

			speedY = -speedY;
		}

		// Collision with walls left&&right
		// Right
		if (pos.x + speedX + sprite.getWidth() > Gdx.graphics.getWidth()) {
			while (pos.x + Math.signum(speedX) + sprite.getWidth() < Gdx.graphics
					.getWidth()) {
				pos.x += Math.signum(speedX);
			}

			GDXPong.setScore(1);
			resetBall(2);
		}
		// Left
		else if (pos.x + speedX + sprite.getWidth() < 0) {
			while (pos.x - Math.signum(speedX) - sprite.getWidth() < 0) {
				pos.x -= Math.signum(speedX);
			}

			GDXPong.setScore(2);
			resetBall(1);
		}

		bounds.set(pos.x, pos.y, sprite.getWidth(), sprite.getHeight());

		if (bounds.overlaps(p1.getBounds())) {
			if (Math.abs(speedX) != speedX) {
				speedX = -speedX;
			}
			System.out.println("--> hit p1");
		} else if (bounds.overlaps(p2.getBounds())) {
			if (Math.abs(speedX) == speedX) {
				speedX = -speedX;
			}
			System.out.println("--> hit p2");
		}
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
