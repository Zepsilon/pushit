package com.zep.pushit.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.zep.pushit.images.ImageLoader;
import com.zep.pushit.sounds.MusicLoader;
import com.zep.pushit.util.Constant;
import com.zep.pushit.util.SkinLoader;
import com.zep.pushit.util.TableUtils;
import com.zep.pushit.util.Util.Settings;

/**
 * Dil, ses ve müzik ayalarinin olacagi class
 * 
 * @author secelik
 *
 */
public class SettingsState extends State {

	private StateManager	sm;
	private boolean			isEffectOn, isMusicOn, isEnglish;
	private CheckBox		cbEnglish, cbTurkish;
	private CheckBox		cbMusic, cbEffect;
	private Slider			sldVolume;
	private Stage			stage;
	private Skin			skin;

	public SettingsState(StateManager sm) {
		super(sm);

		this.sm = sm;
		stage = new Stage(new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), camera));

		skin = SkinLoader.skin;

		Settings.refresh();
		isEffectOn = MusicLoader.isEffectOn();
		isMusicOn = MusicLoader.isMusicOn();
		
//		String language = Settings.getLanguage();
//		System.out.println(language);
		isEnglish = !Constant.LOCAL_TR.equals(Settings.getLanguage());

		TextureRegionDrawable txtrBckgr = new TextureRegionDrawable(ImageLoader.txtrContainerTop[4]);

		sldVolume = new Slider(0, 1, (float) 0.1, false, skin, "default-horizontal");
		sldVolume.setAnimateDuration((float) 0.05);
		sldVolume.setValue(MusicLoader.getVolume());

		Label lblVolume = new Label("VOLUME", skin, "Medium-14", "textActive");
		Label lblSound = new Label("SOUND", skin, "Medium-14", "textActive");
		Label lblLanguage = new Label("LANGUAGE", skin, "Medium-14", "textActive");

		cbMusic = new CheckBox("MUSIC", skin);
		cbMusic.setChecked(isMusicOn);

		cbEffect = new CheckBox("EFFECT", skin);
		cbEffect.setChecked(isEffectOn);

		cbEnglish = new CheckBox("ENGLISH", skin, "radiobox");
		cbEnglish.setChecked(isEnglish);
		cbTurkish = new CheckBox("TURKISH", skin, "radiobox");
		cbTurkish.setChecked(!isEnglish);

		ImageButton btnOk = new ImageButton(SkinLoader.skin, "windowOk");
		ImageButton btnCancel = new ImageButton(SkinLoader.skin, "windowCancel");

		Table tableButton = new Table(skin);
		tableButton.setBackground(txtrBckgr);
		tableButton.row();
		tableButton.add(lblVolume).left().colspan(2);
		tableButton.row().padBottom(0);
		tableButton.add(sldVolume).fillX().colspan(2).center();
		tableButton.row().padTop(25);
		tableButton.add(lblSound).left().colspan(2);
		tableButton.row().padBottom(0);
		tableButton.add(cbMusic).left();
		tableButton.add(cbEffect).left();
		tableButton.row().padTop(25);
		tableButton.add(lblLanguage).left().colspan(2);
		tableButton.row().padBottom(0);
		tableButton.add(cbEnglish).left();
		tableButton.add(cbTurkish).left();
		tableButton.row();
		tableButton.add().colspan(2).height(15);
//		tableButton.debug();

		Table table = new TableUtils().createMainTableByButton(tableButton, "SETTINGS", btnOk, btnCancel);
		
		setListeners(btnOk, btnCancel);

		/**/
		stage.addActor(table);

		Gdx.input.setInputProcessor(stage);
		/**/
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(camera.combined);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub

	}
	
	private void setListeners(ImageButton btnOk, ImageButton btnCancel) {

		setVolumeListener();
		setLanguageListener();
		setButtonListener(btnOk, btnCancel);
	}

	private void setButtonListener(ImageButton btnOk, ImageButton btnCancel) {

		btnOk.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				System.out.println("Volume: "+sldVolume.getValue());
				MusicLoader.setVolume(sldVolume.getValue());
				MusicLoader.setMusic(isMusicOn);
				MusicLoader.setEffect(isEffectOn);
				Settings.setLanguage(isEnglish ? Constant.LOCAL_EN : Constant.LOCAL_TR);

				Gdx.app.log("DEBUG", "Bilgiler kaydedildi");

				getSm().pushState(new MenuState(getSm()));
				
				return false;
			}
		});
		
		btnCancel.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				
				System.out.println("iptal edildi");
				
				Settings.refresh();

				getSm().pushState(new MenuState(getSm()));
				
				return false;
			}
		});

	}

	private void setLanguageListener() {
		cbTurkish.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (!cbTurkish.isChecked() && !cbEnglish.isChecked()) {
					// radiobox icin bu secenek olamaz, yani aktif olan secenek tekrar tiklanarak pasif yapilamaz
//					Util.Prefs.putValue(Constant.PREF_LANG, Constant.LOCAL_TR);
					event.cancel();
				} else {
					cbEnglish.setChecked(false);
					isEnglish = false;

				}

			}
		});
		
		cbEnglish.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (!cbEnglish.isChecked() && !cbTurkish.isChecked()) {
					// radiobox icin bu secenek olamaz, yani aktif olan secenek tekrar tiklanarak pasif yapilamaz
//					Util.Prefs.putValue(Constant.PREF_LANG, Constant.LOCAL_EN);
					event.cancel();
				} else {
					cbTurkish.setChecked(false);
					isEnglish = true;

				}

			}
		});

	}

	private void setVolumeListener() {
		if (isMusicOn) {
			MusicLoader.playMusic(sldVolume.getValue(), true);
		}

		sldVolume.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (isMusicOn)
					MusicLoader.playMusic(sldVolume.getValue(), true);

				if (sldVolume.getValue() == 0) {
					setMusicPref(true);
					cbMusic.setChecked(false);

					setEffectPref(true);
					cbEffect.setChecked(false);
				}

			}
		});

		cbMusic.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				setMusicPref(isMusicOn);

				return false;
			}
		});

		cbEffect.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				setEffectPref(isEffectOn);

				return false;
			}
		});
	}

	private void setMusicPref(boolean isMusicOn) {
		if (isMusicOn) {
			MusicLoader.stopMusic();
		} else {
			MusicLoader.playMusic(sldVolume.getValue(), true);
		}

		this.isMusicOn = !isMusicOn;
	}

	private void setEffectPref(boolean isEffectOn) {
		if (isEffectOn) {
			MusicLoader.stopEffect();
		} else {
			MusicLoader.playEffect(sldVolume.getValue(), false);
		}

		this.isEffectOn = !isEffectOn;
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub

	}

	public StateManager getSm() {
		return sm;
	}

	public void dispose() {
		stage.dispose();
	}

}
