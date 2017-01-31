package com.zep.pushit.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.zep.pushit.images.ImageLoader;
import com.zep.pushit.inputhandler.Score;
import com.zep.pushit.inputhandler.ScoreManager;
import com.zep.pushit.util.SkinLoader;
import com.zep.pushit.util.TableUtils;

public class ScoreState extends State {

	private StateManager	sm;
	private Stage			stage;
	private Skin			skin;

	public ScoreState(StateManager sm) {
		super(sm);

		this.sm = sm;
		stage = new Stage(new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), camera));

		skin = SkinLoader.skin;

		TextureRegionDrawable txtrBckgr = new TextureRegionDrawable(ImageLoader.txtrContainerTop[4]);

		Label lblScore = new Label("Scores", skin, "Medium-18", "textPassive");
//		Label lblName = new Label("İsim", skin, "Medium-14", "textActive");
		Label lblDate = new Label("Date", skin, "Medium-18", "textPassive");

		ImageButton btnClose = new ImageButton(SkinLoader.skin, "windowCenterOk");

		Table tableButton = new Table(skin);
		tableButton.setBackground(txtrBckgr);
		tableButton.row();
		tableButton.add(lblScore).colspan(2).left().expandX();
		tableButton.add().left();
		tableButton.add(lblDate).right().expandX();
		tableButton.row().padBottom(5);
		int i = 0;
		for (Score score : ScoreManager.getScores()) {
			tableButton.add(new Label((++i)+".", skin, "Medium-12", "textActive")).left();
			tableButton.add(new Label(""+score.getScore(), skin, "Medium-12", "textActive")).left().expandX();
			tableButton.add();
			tableButton.add(new Label(String.valueOf(score.getDateStr()), skin, "Medium-12", "textActive")).right();
			tableButton.row().padBottom(5);
		}
		tableButton.add().colspan(4).height(15);

//		tableButton.debug();

		Table table = new TableUtils().createMainTableByButton(tableButton, "SCORES", btnClose);

		setButtonListener(btnClose);

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

	private void setButtonListener(ImageButton btnCancel) {

		btnCancel.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				System.out.println("iptal edildi");

				getSm().pushState(new MenuState(getSm()));

				return false;
			}
		});

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
