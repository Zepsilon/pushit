package com.zep.pushit.inputhandler;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.zep.pushit.util.SkinLoader;

public class TimerHandlerImpl implements ClockHandler {

	private float				value;
	private Timer				timer;
	private Task				task;
	private PlayTimerHandler	playerTimer;

	private Stage				stage;
	private ProgressBar			bar;
	private ImageTextButton		button;

	public TimerHandlerImpl(PlayTimerHandler playerTimer, Vector2 coordStart) {
		this.playerTimer = playerTimer;

		timer = new Timer();

		//---------------------------

		stage = new Stage(new ScreenViewport());

		bar = new ProgressBar(0, 100, 1f, false, SkinLoader.skin, "timerBlue");
//		bar.setSize(100, 250);
		bar.setAnimateDuration((float) 1);

		//for the knobBefore
		bar.getStyle().knobBefore.setRightWidth(0);
		//for the background
		bar.getStyle().background.setLeftWidth(0);
		bar.getStyle().background.setRightWidth(0);

		button = new ImageTextButton("", SkinLoader.skin, "timer");
		Table table = new Table(SkinLoader.skin);
		table.setFillParent(true);
		table.padTop(20);
		table.add(button).padLeft(10).expandY().top();
		table.add(bar).padRight(10).fillX().expand().top();

//		table.debug();

		stage.addActor(table);

		schedule();

//		Gdx.input.setInputProcessor(stage);

	}

	private void schedule() {

		task = new Task() {
			@Override
			public void run() {
				updateTask();
			}
		};

		reSchedule();
	}

	private void reSchedule() {
		timer.clear();
		bar.setValue(0);

		if (task == null)
			schedule();

		timer.scheduleTask(task, 1, 1);
	}

	public void reSchedule(float delay) {
		playerTimer.scheduleTask(delay);
		reSchedule();
	}

	public void cancelSchedule() {
		timer.clear();
		playerTimer.cancelSchedule();
	}

	private void updateTask() {

	}

	public void render(ShapeRenderer sr) {
		if (playerTimer.isRescheduled()) {
			playerTimer.setRescheduled(false);
			reSchedule();
		}

		stage.act();
		stage.draw();
	}

	public void update(float delta) {
		value = (playerTimer.elapsedTime() + 1) * 100 / (playerTimer.delay());
		button.setText("0" + ((playerTimer.delay() - playerTimer.elapsedTime()) < 60 ? "0:" + MathUtils.ceil(playerTimer.delay() - playerTimer.elapsedTime())
				: ":" + MathUtils.ceil(playerTimer.delay() - playerTimer.elapsedTime())));
		bar.setValue(value);
	}

	public PlayTimerHandler getPlayerTimer() {
		return playerTimer;
	}

	public void setPlayerTimer(PlayTimerHandler playerTimer) {
		this.playerTimer = playerTimer;
	}

}
