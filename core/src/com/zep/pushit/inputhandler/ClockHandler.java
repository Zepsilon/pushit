package com.zep.pushit.inputhandler;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface ClockHandler {
	
	public void reSchedule(float delay);
	
	public void cancelSchedule();
	
	public void render(ShapeRenderer sr);
	
	public void update(float delta);
	
	public PlayTimerHandler getPlayerTimer();
}
