package com.zep.pushit.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zep.pushit.images.ImageLoader;

public class Square {

	private Board	board;
	private Tahta	tahta;
	private int		width, height;		// boyutlar
	private int		x, y;				// x - y koordinatlari
	public int		color;				// renk no
	private boolean	active;				// kontrol edilebilen
	public int		centerX, centerY;	// merkez koordinatlari 
	public boolean	ignore;

	public Square(Board board, int width, int height, int x, int y, int color) {
		this.board = board;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.color = color;

		updateCenter();
	}

	public Square(Tahta tahta, int width, int height, int x, int y, int color, boolean ignore) {
		this.tahta = tahta;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.color = color;
		this.ignore = ignore;

		updateCenter();
	}

	public void render(SpriteBatch sb, float delta) {
		sb.draw(ImageLoader.txtrRegBtn[color], x, y, width, height);
	}

	public void update(SpriteBatch sb, float delta) {

	}

	public boolean moveRight() {
		if (x + width < board.x() + board.row * width) {
			Square s = board.getSquare(x + width, y);
			if (s != null) {
				if (s.moveRight()) {
					movePlusX();
					return true;
				}

				return false;
			}

			movePlusX();
			return true;

		}

		return false;
	}

	private int movePlusX() {
		x += width;
		updateCenter();
		return x;
	}

	public boolean moveLeft() {
		if (x > board.x()) {
			Square s = board.getSquare(x - width, y);
			if (s != null) {
				if (s.moveLeft()) {
					moveMinusX();
					return true;
				}

				return false;
			}
			moveMinusX();
			return true;
		}

		return false;
	}

	private int moveMinusX() {
		x -= width;
		updateCenter();
		return x;
	}

	public boolean moveUp() {
		if (y + height < board.y() + board.col * height) {
			Square s = board.getSquare(x, y + height);
			if (s != null) {
				if (s.moveUp()) {
					movePlusY();
					return true;
				}
				return false;
			}
			movePlusY();
			return true;
		}
		return false;
	}

	private int movePlusY() {
		y += height;
		updateCenter();
		return y;
	}

	public boolean moveDown() {
		if (y > board.y()) {
			Square s = board.getSquare(x, y - height);
			if (s != null) {
				if (s.moveDown()) {
					moveMinusY();
					return true;
				}
				return false;
			}
			moveMinusY();
			return true;
		}
		return false;
	}

	private int moveMinusY() {
		y -= height;
		updateCenter();
		return y;
	}

	public int x() {
		return this.x;
	}

	public int y() {
		return this.y;
	}

	public int width() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
		updateCenter();
	}

	public int height() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
		updateCenter();
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void updateCenter() {
		centerX = x + width / 2;
		centerY = y + height / 2;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean equalsCoordinate(Square square) {
		return square != null && square.x() == this.x && square.y() == this.y;
	}

	@Override
	public String toString() {
		return "Square [x=" + x + ", y=" + y + ", centerX=" + centerX + ", centerY=" + centerY + ", color=" + color + ", active=" + active + ", width=" + width
				+ ", height=" + height + "]";
	}

}
