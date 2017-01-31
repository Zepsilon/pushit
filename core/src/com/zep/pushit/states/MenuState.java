package com.zep.pushit.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.zep.pushit.util.SkinLoader;
import com.zep.pushit.util.Util.Settings;

public class MenuState extends State {

	private StateManager	sm;
	private Button			btnNewGame, btnSettings, btnHiScore;
	private Stage			stage;

	public MenuState(StateManager sm) {
		super(sm);

		this.sm = sm;
		stage = new Stage(new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), camera));
		
		Settings.refresh();

		btnNewGame = new Button(SkinLoader.skin, "Play");
		setNewGameListener();

		btnHiScore = new Button(SkinLoader.skin, "Cup");
		setHiScoreListener();

		btnSettings = new Button(SkinLoader.skin, "Settings");
		setSettingsListener();

		ImageButton logo = new ImageButton(SkinLoader.skin, "logo");
		logo.setScale((float) 0.90, (float) 0.80);

		Table table = new Table();
		table.setFillParent(true);
		table.add(logo).colspan(3).top().center().padBottom(200);
		table.row();
		table.add(btnHiScore).right().expandX();
		table.add(btnNewGame).pad(0, 0, 50, 0).space(0);
		table.add(btnSettings).left().expandX();
//		table.debug();

		/**/
		stage.addActor(table);

		Gdx.input.setInputProcessor(stage);
		/**/
	}

	@Override
	public void render(SpriteBatch sb) {

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

//		buttonMusic.render(sb);
//		buttonSound.render(sb);
//		buttonVolumeOn.render(sb);
	}

	@Override
	public void update(float delta) {

//		buttonNewGame.update(delta);
//		buttonLanguage.update(delta);
//		buttonVolumeOn.update(delta);

	}

	private void setHiScoreListener() {
		btnHiScore.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("HiScoreState listener");
				getSm().pushState(new ScoreState(getSm()));
				return false;
			}
		});
	}

	private void setSettingsListener() {
		btnSettings.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("SettingsState listener");
				getSm().pushState(new SettingsState(getSm()));
				return false;
			}
		});
	}

	private void setNewGameListener() {
		btnNewGame.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				getSm().pushState(new PlayState(getSm()));
				return false;
			}
		});
	}

//	private void setLanguageListener() {
//		buttonLanguage.addListener(new ChangeListener() {
//			public void changed(ChangeEvent event, Actor actor) {
////	            dialog.show(stage);
//				System.out.println("Selected: " + buttonLanguage.getSelectedIndex() + " - " + buttonLanguage.getSelected());
//				if (buttonLanguage.getSelectedIndex() == 0) { // ilk secenek English
//					Util.Prefs.putValue(Constant.PREF_LANG, Constant.LOCAL_EN);
//				} else {
//					Util.Prefs.putValue(Constant.PREF_LANG, Constant.LOCAL_TR);
//				}
//
//				stage.clear();
//				Util.load();
//				getSm().pushState(new MenuState(getSm()));
//			}
//		});
//	}

	@Override
	public void handleInput() {

	}

	public StateManager getSm() {
		return sm;
	}

	public void dispose() {
		stage.dispose();
	}

}
