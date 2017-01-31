package com.zep.pushit.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.zep.pushit.asset.AssetLoader;
import com.zep.pushit.util.SkinLoader;
import com.zep.pushit.util.TableUtils;

public class ProgressBarTest extends State {

	Stage		stage;
	ProgressBar	bar;

	public ProgressBarTest(StateManager sm) {
		super(sm);

		stage = new Stage(new ScreenViewport());

		Skin skin = SkinLoader.skin;

		bar = new ProgressBar(0, 1, .001f, false, skin, "timerBlue");
//		bar.setSize(100, 250);
		bar.setAnimateDuration((float) 0.1);
		//for the knobBefore
		bar.getStyle().knobBefore.setRightWidth(0);

		//for the background
		bar.getStyle().background.setLeftWidth(0);
		bar.getStyle().background.setRightWidth(0);
		
		ImageButton[] button = new ImageButton[3];
		button[0] = new ImageButton(skin, "winLeftSound");
		button[1] = new ImageButton(skin, "winCenterPause");
		button[2] = new ImageButton(skin, "winRightMusic");
		

		Table table = new Table(SkinLoader.skin);
		table.add(button);
		table.add(bar).fillX();
		
//		table.debug();
		
		Table mainTable = new TableUtils().createMainTableByButton(table, "Skore: 12321", button);

		stage.addActor(mainTable);

		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(SpriteBatch sb) {
		stage.act();
		stage.draw();
	}

	@Override
	public void update(float delta) {
		if (AssetLoader.assets.update()) {
			bar.setValue(AssetLoader.assets.getProgress());
			return;
		}
		bar.setValue(AssetLoader.assets.getProgress());
		System.out.println(AssetLoader.assets.getProgress());
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub

	}

}
