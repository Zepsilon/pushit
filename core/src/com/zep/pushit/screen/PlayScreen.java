package com.zep.pushit.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zep.pushit.states.PlayState;
import com.zep.pushit.states.StateManager;
import com.zep.pushit.ui.Tahta;

public class PlayScreen implements Screen {

//	private Board		board;
	private Tahta		tahta;
	private SpriteBatch	sb;
	private float timer;

	public PlayScreen(SpriteBatch sb) {
		this.sb = sb;

//		board = new Board(50 * 4, 50 * 4, 6, 6); // width, height, x, y
		tahta = new Tahta(new PlayState(new StateManager()), 10, /*50 * 4, 50 * 4,*/ 6, 6);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//		update(delta);

//		board.render(sb, delta);
		tahta.render(sb);
		
	}

	public void update(float delta) {
//		board.update(sb, delta);
		timer += delta;
		if (timer >= .05f) {
			timer = 0;
//			tahta.update(sb, delta);
		}
	}

	@Override
	public void resize(int width, int height) {
//		System.out.println("Resizing");
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		System.out.println("Kapandi");
	}

}
