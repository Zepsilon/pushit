package com.zep.pushit.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class GameWorld {

	private Rectangle	timeCoord;
	private Rectangle	scoreCoord;
	private Rectangle	gameCoord;
	private Rectangle	test;

	public GameWorld() {

		float x = Gdx.graphics.getWidth() / 10;
		float y = Gdx.graphics.getHeight() / 15;

		timeCoord = new Rectangle(x, y, 4 * x, 4 * y);
		scoreCoord = new Rectangle(5 * x, y, 4 * x, 4 * y);
		gameCoord = new Rectangle(x, scoreCoord.y + scoreCoord.height + y, 8 * x, 8 * y);
		test = new Rectangle(x, y, x, y);
	}

	public void render(ShapeRenderer sr) {
		sr.begin(ShapeType.Line);

		sr.setColor(Color.RED);
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 10; j++) {
				sr.rect(test.x * j, test.y * i, test.width, test.height);
			}
		}

		sr.setColor(Color.GOLD);
		sr.rect(timeCoord.x, timeCoord.y, timeCoord.width, timeCoord.height);

		sr.setColor(Color.GOLD);
		sr.rect(scoreCoord.x, scoreCoord.y, scoreCoord.width, scoreCoord.height);

		sr.setColor(Color.YELLOW);
		sr.rect(gameCoord.x, gameCoord.y, gameCoord.width, gameCoord.height);

		sr.end();
	}

	public void update(float delta) {
	}
}
