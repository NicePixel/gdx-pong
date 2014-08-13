package com.pong.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pong.game.GDXPong;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "_Pong_";
		config.width = 800;
		config.height = 720;
		
		new LwjglApplication(new GDXPong(), config);
	}
}
