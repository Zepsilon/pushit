package com.zep.pushit.inputhandler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class StraightClockHandlerImpl implements ClockHandler {

	private Vector2				coordStart;
	private Vector2				coordinate;
	private Timer				timer;
	private Task				task;
	private PlayTimerHandler	playerTimer;

	public StraightClockHandlerImpl(PlayTimerHandler playerTimer, Vector2 coordStart) {
		this.coordStart = coordStart;
		this.playerTimer = playerTimer;

		coordinate = new Vector2(coordStart);

		timer = new Timer();
		schedule();
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
		coordinate = new Vector2(coordStart);

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
		sr.begin(ShapeType.Line);

		sr.setColor(Color.YELLOW);
		sr.line(coordStart, new Vector2(coordStart.x + 100, coordStart.y));

		sr.setColor(Color.RED);
		sr.line(coordStart, coordinate);

		sr.end();
	}

	public void update(float delta) {
		coordinate.x = coordStart.x + playerTimer.elapsedTime() * 100 / playerTimer.delay();
	}

	public PlayTimerHandler getPlayerTimer() {
		return playerTimer;
	}

	public void setPlayerTimer(PlayTimerHandler playerTimer) {
		this.playerTimer = playerTimer;
	}

}
