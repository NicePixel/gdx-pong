package com.pong.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
	// FIELDS
	private final float speed = 9;
	private Vector2 pos; // The position
	private Sprite sprite; // The sprite
	
	private final int k_up;
	private final int k_down;
	
	private Rectangle bounds;
	
	private final float offsetX = (float) Math.pow(2, 6);

	// Constructor
	/** side: 1: left :: 2: right */
	public Player(int side) {
		
		// Set the keys
		switch(side){
		case 1:
			k_up = Keys.W;
			k_down = Keys.S;
			break;
		case 2:
			k_up = Keys.UP;
			k_down = Keys.DOWN;
			break;
		default:
			System.out.println("Uknown side, using player 1!");
			k_up = Keys.W;
			k_down = Keys.S;
			break;
		}

		// Set the visuals
		sprite = new Sprite(new Texture(Gdx.files.internal("paddle.png")));
		sprite.setSize(16, 64);
		
		pos = new Vector2((side==1?offsetX:Gdx.graphics.getWidth()-sprite.getWidth() - offsetX), 0);
		bounds = new Rectangle();
	}

	// Render method
	public void render(SpriteBatch batch) {
		sprite.setPosition(pos.x, pos.y);
		sprite.draw(batch);
	}

	// Update method
	public void update() {
		// Handle movement
		float move = 0f;

		if (Gdx.input.isKeyPressed(k_up)) {
			move = speed;
		} else if (Gdx.input.isKeyPressed(k_down)) {
			move = -speed;
		} else {
			move = 0f;
		}

		if (move + pos.y + sprite.getHeight() > Gdx.graphics.getHeight()) {
			while (pos.y + Math.signum(move) + sprite.getHeight() < Gdx.graphics
					.getHeight())
				pos.y += Math.signum(move);
			move = 0;
		} else if (move + pos.y < 0) {
			while (Math.signum(move) - pos.y > 0)
				pos.y -= Math.signum(move);
			move = 0;
		}

		pos.y += move;
		
		bounds.set(pos.x, pos.y, sprite.getWidth(), sprite.getHeight());

	}
	
	public Vector2 getPos(){
		return pos;
	}
	public Rectangle getBounds(){
		return bounds;
	}
}
