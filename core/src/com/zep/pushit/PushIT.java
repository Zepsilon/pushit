package com.zep.pushit;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zep.pushit.images.ImageLoader;
import com.zep.pushit.inputhandler.ScoreHandler;
import com.zep.pushit.sounds.MusicLoader;
import com.zep.pushit.states.MenuState;
import com.zep.pushit.states.ProgressBarTest;
import com.zep.pushit.states.StateManager;
import com.zep.pushit.util.Constant;
import com.zep.pushit.util.SkinLoader;
import com.zep.pushit.util.Util;

public class PushIT extends Game {
	
	public static final String TITLE = "Square";
	public static final int WIDTH = 480*4/5;
	public static final int HEIGHT = 800*4/5;

    private SpriteBatch sb;
//    private State state;

    private static StateManager sm; // oyun icinde tum bolumler buradan yonetiliyor


    @Override
    public void create() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.
//        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);

		ImageLoader.load(5);
		Util.load();
		MusicLoader.load(); // sistemsel olarak yukle, her seferinde sormasın
		

        sb = new SpriteBatch();
        sm = new StateManager();

        sm.pushState(new MenuState(sm)); //(new PlayState(sm));
        System.out.println("Yaratildi..");
        System.out.println("Language: "+java.util.Locale.getDefault().toString());
        System.out.println(Util.Bundle.getText("button.newGame"));
    }

    @Override
    public void render() {

    	Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        sb.begin();
        sb.draw(ImageLoader.txtrRegBkg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        sb.draw(ImageLoader.txrgGround, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sb.end();
        
        sm.render(sb);
        sm.update(Gdx.graphics.getDeltaTime()); //bir iki frame arasinda gecen sureyi verir
    }


    public void dispose() {
        sb.dispose();
        ImageLoader.dispose();
        MusicLoader.dispose();
        ScoreHandler.dispose();
        SkinLoader.dispose();
        System.out.println("Yok edildi...");

    }
}
