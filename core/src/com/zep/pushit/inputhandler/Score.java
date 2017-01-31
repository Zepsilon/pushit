package com.zep.pushit.inputhandler;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Score implements Comparable<Score>{
	private int score;
//	private String name; // hep ayni
	private Date date;
	private String dateStr;
	private String dateSeperator;
	
	public Score(String scores) {
		dateSeperator = ":";
		String[] split = scores.split(dateSeperator);
		this.score = Integer.valueOf(split[0]);
		this.date = new Date(Long.valueOf(split[1]));
		dateStr = new SimpleDateFormat("dd/MM/yyyy hh:mm").format(this.date);
	}
	
	public Score(int score, Date date) {
		this.score = score;
		this.date = date;
	}
	
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	@Override
	public int compareTo(Score scr) {
		return scr.score - this.score;
	}

	@Override
	public String toString() {
		return score + ":" + date.getTime();
	}

}
