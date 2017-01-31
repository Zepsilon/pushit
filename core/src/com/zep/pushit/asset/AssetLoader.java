package com.zep.pushit.asset;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.zep.pushit.util.Constant;

public class AssetLoader {

	public static AssetManager assets = new AssetManager();

	static {
		
		// Texture
		assets.load("skin/uiskin/logoSmall.png", Texture.class);
		assets.load("backround/borders.png", Texture.class);
		assets.load("backround/groundAndPattern4.png", Texture.class);
		assets.load("skin/uiskin/uiskinBackground1.png", Texture.class);
		assets.load("buttons/Square.png", Texture.class);
		// BitmapFont
		assets.load("skin/"+Constant.SKIN_DEFAULT+"/Medium-18.fnt", BitmapFont.class);
		// Music
		assets.load("music/SpinCycle.mp3", Music.class);
		assets.load("music/remove.mp3", Music.class);
		// Skin
//		assets.load("skin/" + Constant.SKIN_DEFAULT + "/" + Constant.SKIN_DEFAULT + ".atlas", TextureAtlas.class);
////		assets.load("skin/" + Constant.SKIN_DEFAULT + "/" + Constant.SKIN_DEFAULT + ".json", Skin.class);
		
		assets.load("skin/"+Constant.SKIN_DEFAULT+"/"+Constant.SKIN_DEFAULT+".atlas", TextureAtlas.class);
		assets.load("skin/"+Constant.SKIN_DEFAULT+"/"+Constant.SKIN_DEFAULT+".json", Skin.class, new SkinLoader.SkinParameter("skin/"+Constant.SKIN_DEFAULT+"/"+Constant.SKIN_DEFAULT+".atlas"));
		
////		assets.load("images_packed/gameatlas.atlas", TextureAtlas.class);
//		assets.load("skins/skin.json", Skin.class, new SkinLoader.SkinParameter("skin/" + Constant.SKIN_DEFAULT + "/" + Constant.SKIN_DEFAULT + ".atlas"));
		
//		assets.finishLoading();
	}
	
}
