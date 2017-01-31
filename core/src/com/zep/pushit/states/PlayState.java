package com.zep.pushit.states;

import java.text.DecimalFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.zep.pushit.controller.Controller;
import com.zep.pushit.inputhandler.ClockHandler;
import com.zep.pushit.inputhandler.PlayTimerHandler;
import com.zep.pushit.inputhandler.ScoreHandler;
import com.zep.pushit.inputhandler.TahtaInputProcessor;
import com.zep.pushit.inputhandler.TimerHandlerImpl;
import com.zep.pushit.object.Direction;
import com.zep.pushit.sounds.MusicLoader;
import com.zep.pushit.ui.Tahta;
import com.zep.pushit.util.Constant;
import com.zep.pushit.util.SkinLoader;

/**
 * Created by secelik on 05.08.2016.
 */
public class PlayState extends State {

	private GameWorld			gm;

	public static boolean		end;
	private ShapeRenderer		sr;								// basit cizim yapmak icin
	private StateManager		sm;
	private OrthographicCamera	cam;

	private Tahta				tahta;
	private Controller			controller;
	private ScoreHandler		score;
//	private PlayTimerHandler	timer;
	private ClockHandler		clock;

	private ImageButton			btnMusic, btnEffect, btnPause;
	private Stage				stage;
	private Table				mainTable;
	private ImageTextButton		btnScoreTitle;

	public PlayState(StateManager sm) {

		super(sm);
		gm = new GameWorld();
		cam = camera;
		this.sm = sm;

		int kareWidth = (Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / (Constant.POW / 2)) / (Constant.SQUARE_SIZE + 2);

		int unitWidth = Gdx.graphics.getWidth() / Constant.POW;
		int unitHeight = Gdx.graphics.getHeight() / Constant.POH;

		sr = new ShapeRenderer();

//		timer = new PlayTimerHandler(this, Constant.SQUARE_SIZE);
//		clock = new CircleClockHandlerImpl(new PlayTimerHandler(this, Constant.SQUARE_SIZE), new Vector2(3 * unitWidth, 3 * unitHeight));
		clock = new TimerHandlerImpl(new PlayTimerHandler(this, Constant.SQUARE_SIZE), new Vector2(3 * unitWidth, 3 * unitHeight));
		score = new ScoreHandler(clock.getPlayerTimer(), Constant.MULTIPLIER, Constant.SQUARE_SIZE);

		tahta = new Tahta(this, kareWidth, Constant.SQUARE_SIZE, Constant.SQUARE_SIZE);

		controller = new Controller(tahta, kareWidth, kareWidth);

		tahta.checkSame(Direction.NOTHING);

		stage = new Stage(new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), cam));

		btnMusic = new ImageButton(SkinLoader.skin, "winLeftSound");
		btnPause = new ImageButton(SkinLoader.skin, "winCenterPause");
		btnEffect = new ImageButton(SkinLoader.skin, "winRightMusic");
		buttonListener();

		Table table = new Table(SkinLoader.skin);
		table.add().padTop(220);

		mainTable = createMainTableByButton(table, "Score: 0", btnMusic, btnPause, btnEffect);
		mainTable.setFillParent(true);

		stage.addActor(mainTable);

		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(new TahtaInputProcessor(controller));
		multiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(multiplexer);

//		Gdx.input.setInputProcessor(new TahtaInputProcessor(controller)); // input processor atandi (tus hareketleri, dokunma, surukleme vs.)
		System.out.println(tahta.getKare()[0][0]);
	}

	private void buttonListener() {
		MusicLoader.refresh();
		btnMusic.setChecked(!MusicLoader.isMusicOn());
		btnEffect.setChecked(!MusicLoader.isEffectOn());

		btnMusic.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				if (btnMusic.isChecked()) {
					MusicLoader.setMusic(true);
					MusicLoader.playMusic(true);
				} else {
					MusicLoader.setMusic(false);
					MusicLoader.stopMusic();
				}
				return false;
			}
		});

		btnEffect.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				if (btnEffect.isChecked()) {
					MusicLoader.setEffect(true);
					MusicLoader.playEffect(false);
				} else {
					MusicLoader.setEffect(false);
					MusicLoader.stopEffect();
				}
				return false;
			}
		});

	}

	public Table createMainTableByButton(Table tableButton, String title, ImageButton... button) {
		btnScoreTitle = new ImageTextButton(title, SkinLoader.skin, "headDark");

		Table table = new Table(SkinLoader.skin);
		table.setBackground(new TextureRegionDrawable(SkinLoader.skin.getRegion("container.center")));
		table.setFillParent(true);
		table.bottom();
		table.add().colspan(5).minHeight(Gdx.graphics.getHeight() / 3);
		table.row().bottom();
		table.add(new Image(SkinLoader.skin.getRegion("container.top.left.up"))).right();
		table.add(btnScoreTitle).center().colspan(3).fillX();
		table.add(new Image(SkinLoader.skin.getRegion("container.top.right.up"))).left();
		table.row();
		table.add(new Image(SkinLoader.skin.getRegion("container.top.left.down"))).right();
		table.add(new Image(SkinLoader.skin.getRegion("container.center"))).colspan(3).fill();
		table.add(new Image(SkinLoader.skin.getRegion("container.top.right.down"))).left();
		table.row();
		table.add(new Image(SkinLoader.skin.getRegion("window.center.left"))).right().fillY();
		table.add(tableButton).colspan(3).fillX();
		table.add(new Image(SkinLoader.skin.getRegion("window.center.right"))).left().fillY();

		table.row();
		table.add(button[0]).right();
		table.add(new Image(SkinLoader.skin.getRegion("window.bottom"))).right().fillX().expandX();
		table.add(button[1]);
		table.add(new Image(SkinLoader.skin.getRegion("window.bottom"))).right().fillX().expandX();
		table.add(button[2]).left();

//		table.debug();

		return table;
	}

	public void reScheduleTask() {
		// satir ya da sütun yok edildiginde cagrilir
		clock.reSchedule((tahta.getKareRowLen() + tahta.getKareColLen()) / 2);
	}

	public void cancelSchedule() {
		// Oyun bittiginde cagrilir
		clock.cancelSchedule();
	}

	public void render(SpriteBatch sb) {
		sr.setProjectionMatrix(camera.combined);
		sb.setProjectionMatrix(camera.combined);

		stage.act();
		stage.draw();
//		gm.render(sr);

		clock.render(sr);

		tahta.render(sb);

		score.render(sb);

	}

	public void moveCamtoLeft() {
		camera.position.x -= 10;
	}

	public void update(float delta) {

		gm.update(delta);

		tahta.update(delta);

		clock.update(delta);

		score.update(delta);

		updateScoreTitle();
	}

	private void updateScoreTitle() {

		DecimalFormat myFormatter = new DecimalFormat("00000");
		btnScoreTitle.setText("Score: " + myFormatter.format(getScore().getScore()));
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
	}

	public StateManager getSm() {
		return sm;
	}

	public OrthographicCamera getCam() {
		return cam;
	}

	public Tahta getTahta() {
		return tahta;
	}

	public void setTahta(Tahta tahta) {
		this.tahta = tahta;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public ScoreHandler getScore() {
		return score;
	}

	public ClockHandler getClock() {
		return clock;
	}

}
