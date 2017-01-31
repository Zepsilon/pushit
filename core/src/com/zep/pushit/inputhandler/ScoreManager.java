package com.zep.pushit.inputhandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.zep.pushit.util.Util.Settings;

public class ScoreManager {
	private static List<Score>	scores;
	public static final String	SEPERATOR;

	static {
		SEPERATOR = "#";
		prepareScores();
	}

	private static void prepareScores() {
		String strScore = Settings.getScores();
		scores = new ArrayList<Score>();
		if (strScore != null && strScore.length() > 0) {
			String[] scoreArray = strScore.split(SEPERATOR);
			for (String scr : scoreArray) {
				scores.add(new Score(scr));
			}

			Collections.sort(scores);
		}
	}

	public static List<Score> getScores() {
		if (scores == null || scores.isEmpty()) {
			scores = new ArrayList<Score>();
			scores.add(new Score(0, new Date()));
		}
		return scores;
	}

	public static void addScore(int score, Date date) {
		while (scores.size() > 9) {
			scores.remove(9);
		}
		
		scores.add(new Score(score, date));

		saveScore();
	}

	public static void saveScore() {
		if (scores == null || scores.size() == 0) {
			return;
		}

		String allScore = "";
		for (Score score : scores) {
			allScore += score + SEPERATOR;
		}
		
		Settings.setScores(allScore);
		
		prepareScores();
	}
}
