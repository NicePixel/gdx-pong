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
	private final float speed = 9f;
	private Vector2 pos; // The position
	private Sprite sprite; // The sprite
	
	private final int k_up;
	private final int k_down;
	
	private Rectangle bounds;
	
	private final float offsetX = (float) Math.pow(2, 6);

	// Constructor
	/** side: 1: left :: 2: right */
	public Player(int side) {
		
		// Set the visuals
		sprite = new Sprite(new Texture(Gdx.files.internal("paddle.png")));
		sprite.setSize(sprite.getTexture().getWidth(), sprite.getTexture().getHeight());
		
		// Set stuff that effects which player we are working with
		switch(side){
		case 1:
			k_up = Keys.W;
			k_down = Keys.S;
			break;
		case 2:
			k_up = Keys.UP;
			k_down = Keys.DOWN;
			sprite.setScale(-1, 1);
			break;
		default:
			System.out.println("Uknown side, using player 1!");
			k_up = Keys.W;
			k_down = Keys.S;
			break;
		}
		
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
			while (pos.y + 1 + sprite.getHeight() < Gdx.graphics.getHeight()){
				pos.y += 1;
			}
			move = 0f;
		} else if (move + pos.y < 0) {
			while (-1 + pos.y > 0){
				pos.y -= 1;
			}
			move = 0f;
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
