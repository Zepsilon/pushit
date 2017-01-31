package com.zep.pushit.states;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;

public class SplashScreenState extends State {

	private StateManager		sm;
	private Button				buttonNewGame, buttonMusic, buttonSound;
	private SelectBox<String>	buttonLanguage;
	private boolean				isSoundOn, isMusicOn;

	private static BitmapFont	fontButton;

	private float				buttonX;
	private float				buttonY;

	public SplashScreenState(StateManager sm) {
		super(sm);
		
		this.sm = sm;
	}

	@Override
	public void render(SpriteBatch sb) {

	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void handleInput() {

	}

}
