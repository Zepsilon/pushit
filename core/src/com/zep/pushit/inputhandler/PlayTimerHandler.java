package com.zep.pushit.inputhandler;

import java.util.Date;

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.zep.pushit.states.PlayState;
import com.zep.pushit.util.Util;

public class PlayTimerHandler {

	private PlayState	playState;
	private long		startTime;	// gecen sureyi olcmek icin
	private Timer		timer;
	private Task		task;
	private float		delay;
	private boolean rescheduled;

	public PlayTimerHandler(PlayState playState, float delay) {

		this.playState = playState;
		this.delay = delay;

		timer = new Timer();

		initTimerTask();

		scheduleTask(delay);
	}

	private void initTimerTask() {
		task = new Task() {

			@Override
			public void run() {
				System.out.println("Time is Up!");
				// TODO satir ya da sutun'dan kucuk olan eklenecek.
				// satir ve sutun max degere ulasmissa oyun biter
				if (playState.getTahta().getKareRowLen() > playState.getTahta().getKareColLen()) {
					playState.getController().addRow();
				} else {
					playState.getController().addColumn();
				}
				rescheduled = true;
				scheduleTask((playState.getTahta().getKareRowLen() + playState.getTahta().getKareColLen() + 1) / 2);
			}
		};
	}

	public void scheduleTask(float sn) {
		// satir ya da sütun yok edildiginde cagrilir
		if (task == null) {
			initTimerTask();
		}

		this.delay = Util.MathUtil.fibonacci((int) sn);
		System.out.println("Task scheduled: " + delay);
		timer.clear(); // önce timer iptal edilir, ardindan asagidaki satir ile tekrar set edilir.
		startTime = new Date().getTime();
		timer.scheduleTask(task, delay);
	}

	public void cancelSchedule() {
		timer.clear();
	}

	/** timer schedule edildikten sonra bu ana kadar gecen süreyi verir */
	public long elapsedTime() {
		return (new Date().getTime() - startTime) / 1000;
	}

	public float delay() {
		return delay;
	}

	public boolean isRescheduled() {
		return rescheduled;
	}

	public void setRescheduled(boolean rescheduled) {
		this.rescheduled = rescheduled;
	}
}
