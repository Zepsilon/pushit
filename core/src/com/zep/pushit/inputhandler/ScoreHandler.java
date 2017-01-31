package com.zep.pushit.inputhandler;

import java.util.Date;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/*
 * Score Mantığı: score = timer.delay - gecenZaman + (max kare sayisi - yok edilen kare sayisi) * multiplier multiplier degeri zorluk arttikca artacak
 */
public class ScoreHandler {

	private int					score;
	private int					multiplier;
	private int					highScore;
	private PlayTimerHandler	timer;
	private static BitmapFont	font;
	private int					maxSquare;
//	private Vector2				coordinate;	// score'un yazilacagi koordinatlar
	private int					moveCount;	// hamle sayisi

	public ScoreHandler(PlayTimerHandler timer, int multiplier, int maxSqSize) {
		moveCount = 0;
		this.timer = timer;
		this.multiplier = multiplier;
		this.maxSquare = maxSqSize;

		score = 0;
//		highScore = (Integer) Util.Prefs.getValue(Constant.PREF_HIGH_SCORE, score);
		highScore = ScoreManager.getScores().get(0).getScore();
//
//		font = new BitmapFont(true);
//		font.setColor(Color.WHITE);

//		this.coordinate = new Vector2(6 * Gdx.graphics.getWidth() / 10, 3 * Gdx.graphics.getHeight() / 15 - 10);
	}

	public void render(SpriteBatch sb) {
//		sb.begin();
//
//		font.draw(sb, "Score: " + getScore(), coordinate.x, coordinate.y);
//		font.draw(sb, "Time: " + (timer.delay() - timer.elapsedTime()), coordinate.x, coordinate.y + 20);
//		font.draw(sb, "Move: " + (getMoveCount()), coordinate.x, coordinate.y + 40);
//
//		sb.end();
	}

	public void update(float delta) {
	}

	public int getScore(int destroyed) {
		score += (int) ((timer.delay() - timer.elapsedTime()) + (maxSquare - destroyed + 1) * multiplier);
		return getScore();
	}

	public int getScore() {
		return score;
	}

	public int getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(int multiplier) {
		this.multiplier = multiplier;
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		if (this.highScore < highScore){
			this.highScore = highScore;
		}
		
		ScoreManager.addScore(highScore, new Date());
	}

	public int getMoveCount() {
		return moveCount;
	}

	public void addMoveCount() {
		this.moveCount++;
	}

	public static void dispose() {
		if (font != null)
			font.dispose();
	}

}
