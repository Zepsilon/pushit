package com.zep.pushit.inputhandler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class CircleClockHandlerImpl implements ClockHandler {

	private Vector2				coordStart;
	private Vector2				coordinate;
	private float				radius;
	private float				degrees;
	private Timer				timer;
	private Task				task;
	private PlayTimerHandler	playerTimer;
	private static final int	FIX_DEGREE	= 270;	// ust

	public CircleClockHandlerImpl(PlayTimerHandler playerTimer, Vector2 coordStart) {
		this.coordStart = coordStart;
		this.radius = 50;
		this.playerTimer = playerTimer;

		this.degrees = 0;

		coordinate = new Vector2(coordStart.x, coordStart.y - radius);

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
		this.degrees = 0;

		if (task == null)
			schedule();

		timer.scheduleTask(task, 1, 1);
	}

	private void updateTask() {
		degrees += 360 / playerTimer.delay(); // zamani duzelt
		degrees = degrees % 360;
//		System.out.println(degrees + FIX_DEGREE);
	}

	public void reSchedule(float delay) {
		playerTimer.scheduleTask(delay);
		reSchedule();
	}

	public void cancelSchedule() {
		timer.clear();
		degrees = 0;
		playerTimer.cancelSchedule();
	}

	public void render(ShapeRenderer sr) {
		if (playerTimer.isRescheduled()){
			playerTimer.setRescheduled(false);
			reSchedule();
		}
		sr.begin(ShapeType.Line);

		sr.setColor(Color.RED);
		sr.circle(coordStart.x, coordStart.y, radius);

		sr.setColor(Color.YELLOW);
		sr.line(coordStart, coordinate);

		sr.end();
	}

	public void update(float delta) {

		coordinate.x = coordStart.x + radius * MathUtils.cosDeg(degrees + FIX_DEGREE);
		coordinate.y = coordStart.y + radius * MathUtils.sinDeg(degrees + FIX_DEGREE);
	}

	public PlayTimerHandler getPlayerTimer() {
		return playerTimer;
	}

	public void setPlayerTimer(PlayTimerHandler playerTimer) {
		this.playerTimer = playerTimer;
	}

}
