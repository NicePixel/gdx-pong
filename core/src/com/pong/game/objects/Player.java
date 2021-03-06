package com.pong.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pong.game.GDXPong;

public class Player {
	// FIELDS
	private final float speed = 14f;
	private Vector2 pos; // The position
	private Sprite sprite; // The sprite
	
	private final int k_up;
	private final int k_down;
	
	private int side;
	
	private Rectangle bounds;
	
	private final float offsetX = (float) Math.pow(2, 6);

	// Constructor
	/** side: 1: left :: 2: right */
	public Player(int side) {
		this.side = side;
		
		// Set the visuals
		sprite = new Sprite(new Texture(Gdx.files.internal("paddle.png")));
		sprite.setSize(sprite.getTexture().getWidth(), sprite.getTexture().getHeight());
		sprite.setColor(GDXPong.color_theme);
		
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
		
		pos = new Vector2((side==1?offsetX:Gdx.graphics.getWidth()-sprite.getWidth() - offsetX), Gdx.graphics.getHeight()/2 - sprite.getHeight()/2);
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
		
		boolean doneMovement = false;
		for(int i = 0; i < 2; i++){
			if(doneMovement == false){
				float touchY = Math.abs(Gdx.input.getY(i) - Gdx.graphics.getHeight());
				float touchX = Gdx.input.getX(i);
				if (Gdx.input.isKeyPressed(k_up) || (Gdx.input.isTouched(i) && touchY-sprite.getHeight()/2 > pos.y+sprite.getHeight()/2 && (side==1?touchX<=Gdx.graphics.getWidth()/2:touchX>Gdx.graphics.getWidth()/2))) {
					move = speed;
					doneMovement = true;
				} else if (Gdx.input.isKeyPressed(k_down) || (Gdx.input.isTouched(i) && touchY+sprite.getHeight()/2 < pos.y+sprite.getHeight()/2 && (side==1?touchX<=Gdx.graphics.getWidth()/2:touchX>Gdx.graphics.getWidth()/2))) {
					move = -speed;
					doneMovement = true;
				} else {
					move = 0f;
				}
			}
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
