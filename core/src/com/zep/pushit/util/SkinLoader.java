package com.zep.pushit.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SkinLoader {

	public static Skin skin;

	static {
		setSkin(Constant.SKIN_DEFAULT);
	}

	public static void setSkin(String name) {
		skin = new Skin(Gdx.files.internal("skin/" + name + "/" + name + ".json"));
	}

	public static void dispose() {
		skin.dispose();
	}
}
