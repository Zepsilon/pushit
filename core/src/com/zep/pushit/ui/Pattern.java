package com.zep.pushit.ui;

public class Pattern {

	private float	width, height;	// boyutlar
	private int		color;			// renk no

	public Pattern(float width, float height, int color) {
		super();
		this.width = width;
		this.height = height;
		this.color = color;
	}

	public float width() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float height() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

}
