package com.zep.pushit.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.zep.pushit.PushIT;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = PushIT.TITLE;
		config.width = PushIT.WIDTH;
		config.height = PushIT.HEIGHT;
		
		new LwjglApplication(new PushIT(), config);
	}
}
