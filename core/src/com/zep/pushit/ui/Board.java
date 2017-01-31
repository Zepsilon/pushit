package com.zep.pushit.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.zep.pushit.screen.history.BoardHistory;

public class Board {

	private OrthographicCamera	camera;
	private ShapeRenderer		sr;				// basit cizim yapmak icin

	private BoardHistory		history;
	private Square[][]			square;

	private int					width, height;	// boyutlar
	private int					x, y;			// x - y koordinatlari
	public int					row, col;		// satir - sutun sayisi
	private final int			CN	= 5;		// renk miktari

	public Board(int width, int height, int row, int col) {
		super();
		sr = new ShapeRenderer();

		this.width = width;
		this.height = height;

		this.row = row;
		this.col = col;

		this.x = (Gdx.graphics.getWidth() - width) / 2;
		this.y = (Gdx.graphics.getHeight() - height) / 2;

		int sw = width / row;
		int sh = height / col;

		camera = new OrthographicCamera();
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		square = new Square[row][col];

		square[0][0] = new Square(this, sw, sh, x, y, (int) new Random().nextInt(CN));
		square[0][0].setActive(true);

		for (int i = 1; i < square.length - 1; i++) {
			for (int j = 1; j < square[i].length - 1; j++) {
				square[i][j] = new Square(this, sw, sh, x + i * sw, y + j * sh, (int) new Random().nextInt(CN));
			}
		}

//		Gdx.input.setInputProcessor(new BoardInput(this)); // input processor atandi (tus hareketleri, dokunma, surukleme vs.)

		prepareRowColumn(square[1][1].x(), square[1][1].y());

		history = new BoardHistory(this);
//		System.out.println(this);
		print(false);
	}

	public void render(SpriteBatch sb, float delta) {
		sr.begin(ShapeType.Line);
		sr.setProjectionMatrix(camera.combined);
		sr.setColor(Color.RED);
		sr.line(x - 10, y - 10, x + width + 10, y - 10);
		sr.line(x - 10, y - 10, x - 10, y + height + 10);
		sr.setColor(Color.YELLOW);
		sr.rect(x, y, width, height);
		sr.end();

		sb.begin();
		sb.setProjectionMatrix(camera.combined);
		for (int i = 0; i < square.length; i++) {
			for (int j = 0; j < square[i].length; j++) {
				if (square[i][j] != null) {
					square[i][j].render(sb, delta);
//					sb.draw(ImageLoader.txtrRegBtn[0], square[i][j].centerX, square[i][j].centerY, 5, 5);
//					sb.draw(ImageLoader.txtrRegBtn[1], square[i][j].x(), square[i][j].y(), 5, 5);
				}
			}
		}

		sb.end();
	}

	public void update(SpriteBatch sb, float delta) {

		for (int i = 0; i < square.length; i++) {
			for (int j = 0; j < square[i].length; j++) {
				if (square[i][j] != null)
					square[i][j].update(sb, delta);
			}
		}

	}

	public Square getSquare(int x, int y) {

		for (int i = 0; i < square.length; i++) {
			for (int j = 0; j < square[i].length; j++) {
				if (square[i][j] != null && square[i][j].x() == x && square[i][j].y() == y) {
					return square[i][j];
				}
			}
		}

		return null;
	}

//	public Square getSquareAtLastRow(int x, int y) { // en alttaki kare
//		Square sq = square[x][y];
//		for (int i = 0; i < square.length; i++) {
//			for (int j = 0; j < square[i].length; j++) {
//				if (square[i][j] != null && square[i][j].x() == sq.x() && square[i][j].y() > sq.y()) {
//					sq = square[i][j];
//				}
//			}
//		}
//
//		System.out.println(sq);
//		return sq;
//	}
//
//	public Square getSquareAtLastColumn(int x, int y) { // en sagdaki kare
//		Square sq = square[x][y];
//		for (int i = 0; i < square.length; i++) {
//			for (int j = 0; j < square[i].length; j++) {
//				if (square[i][j] != null && square[i][j].y() == sq.y() && square[i][j].x() > sq.x()) {
//					sq = square[i][j];
//				}
//			}
//		}
//
//		System.out.println(sq);
//		return sq;
//	}

	private void prepareRowColumn(int x, int y) {
		List<Square> squareColumn = getSquareOnColumn(x);
		if (squareColumn != null && squareColumn.size() > 1) {
			for (Square square : squareColumn) {
				isSameRow(getSquareOnRow(square.y()));
			}
		}

		List<Square> squareRow = getSquareOnRow(y);
		if (squareRow != null && squareRow.size() > 1) {
			for (Square square : squareRow) {
				isSameColumn(getSquareOnColumn(square.x()));
			}
		}
	}

	/**
	 * verilen x koordinatindaki (kolon) aktif olmayan tüm Square objelerini döndürür
	 */
	public List<Square> getSquareOnColumn(int x) {
		if (x <= 0) // 0 koordinati olamaz
			return null;

		List<Square> sl = new ArrayList<Square>();

		for (int i = 0; i < square.length; i++) {
			for (int j = 0; j < square[i].length; j++) {
				if (square[i][j] != null && !square[i][j].isActive() && square[i][j].x() == x)
					sl.add(square[i][j]);
			}
		}

		return sl;

	}

	/**
	 * verilen y koordinatindaki (satir) aktif olmayan tüm Square objelerini döndürür
	 */
	public List<Square> getSquareOnRow(int y) {
		if (y <= 0)
			return null;

		List<Square> sl = new ArrayList<Square>();

		for (int i = 0; i < square.length; i++) {
			for (int j = 0; j < square[i].length; j++) {
				if (square[i][j] != null && !square[i][j].isActive() && square[i][j].y() == y)
					sl.add(square[i][j]);
			}
		}

		return sl;

	}

	private void isSameRow(List<Square> allRowSquares) {
		if (allRowSquares == null || allRowSquares.size() < 2)
			return;

		int yColor = allRowSquares.get(0).color;
		for (Square square : allRowSquares) {
			if (yColor != square.color) {
				yColor = -1;
				break;
			}
		}

		if (yColor > -1) { // ayni renk satir
			System.out.println("Ayni satir");
			for (Square square : allRowSquares) {
				List<Square> squareOnRow = getSquareOnColumn(square.x());
				for (Square sq : squareOnRow) {
					if (sq.y() < square.y()) {
						System.out.println("1: " + sq);
						deleteRow(sq);
					}
				}
			}
		}
	}

	private void deleteRow(Square sq) {
		for (int i = 1; i < square.length; i++) {
			for (int j = 1; j < square[i].length; j++) {
				if (square[i][j] != null && square[i][j].x() == sq.x() && square[i][j].y() == sq.y()) {
					square[i][j].color = 0;
				}
			}
		}

	}

	private void isSameColumn(List<Square> allColSquares) {
		if (allColSquares == null || allColSquares.size() < 2)
			return;

		int xColor = allColSquares.get(0).color;
		for (Square square : allColSquares) {
			if (xColor != square.color) {
				xColor = -1;
				break;
			}
		}

		if (xColor > -1) { // ayni renk sütun
			System.out.println("Ayni sütun");
			for (Square square : allColSquares) {
				List<Square> squareOnCol = getSquareOnRow(square.y());
				for (Square sq : squareOnCol) {
					if (sq.x() < square.x()) {
						System.out.println("1: " + sq);
						deleteColumn(sq);
					}
				}
			}
		}
	}

	private void deleteColumn(Square sq) {
		for (int i = 1; i < square.length; i++) {
			for (int j = 1; j < square[i].length; j++) {
				if (square[i][j] != null && square[i][j].x() == sq.x() && square[i][j].y() == sq.y()) {
					square[i][j].color = 0;
				}
			}
		}

	}

	public void moveRight() {
		for (int i = 0; i < square.length; i++) {
			for (int j = 0; j < square[i].length; j++) {
				if (square[i][j] != null && square[i][j].isActive()) {
					square[i][j].moveRight();
					square[i][j].setActive(!switchToRight(square[i][j])); // saga gecmediyse true kalir, gectiyse false
					if (!square[i][j].isActive())
						prepareRowColumn(square[i][j].x(), square[i][j].y());
					history.historyAdd(square);
//					print();
					return;
				}
			}
		}
	}

	public void moveLeft() {
		for (int i = 0; i < square.length; i++) {
			for (int j = 0; j < square[i].length; j++) {
				if (square[i][j] != null && square[i][j].isActive()) {
					square[i][j].moveLeft();
					square[i][j].setActive(!switchToLeft(square[i][j]));
					if (!square[i][j].isActive())
						prepareRowColumn(square[i][j].x(), square[i][j].y());
					history.historyAdd(square);
					return;
				}
			}
		}
	}

	public void moveDown() {
		for (int i = 0; i < square.length; i++) {
			for (int j = 0; j < square[i].length; j++) {
				if (square[i][j] != null && square[i][j].isActive()) {
					square[i][j].moveUp();
					square[i][j].setActive(!switchToUp(square[i][j]));
					if (!square[i][j].isActive())
						prepareRowColumn(square[i][j].x(), square[i][j].y());
					history.historyAdd(square);
					return;
				}
			}
		}
	}

	public void moveUp() {
		for (int i = 0; i < square.length; i++) {
			for (int j = 0; j < square[i].length; j++) {
				if (square[i][j] != null && square[i][j].isActive()) {
					square[i][j].moveDown();
					square[i][j].setActive(!switchToDown(square[i][j]));
					if (!square[i][j].isActive())
						prepareRowColumn(square[i][j].x(), square[i][j].y());
					history.historyAdd(square);
					return;
				}
			}
		}
	}

	public void nextHistory() {
//		square = history.next(square);
//		print(false);
	}

	public void prevHistory() {
//		square = history.previous(square);
//		print(false);
	}

	public void clicked() {
		for (int i = 0; i < square.length; i++) {
			for (int j = 0; j < square[i].length; j++) {
				if (square[i][j] != null && 
						Gdx.input.getX() >= square[i][j].x() && Gdx.input.getX() <= square[i][j].x() + square[i][j].width()
						&& Gdx.input.getY() >= square[i][j].y() && Gdx.input.getY() <= square[i][j].y() + square[i][j].height())
					System.out.println(square[i][j]);
			}
		}
	}

	public boolean switchToRight(Square sq) {
		int x = -1, y = -1;

		for (int i = 0; i < square.length; i++) {
			for (int j = 0; j < square[i].length; j++) {
				if (square[i][j] != null && square[i][j].y() == sq.y()) {
					if (square[i][j].x() > sq.x()) {
						x = i;
						y = j;
						sq = square[i][j];
					}
				}
			}
		}

		if (x > -1 && y > -1) {
			square[x][y].setActive(true);
			return true;
		}

		return false;
	}

	public boolean switchToLeft(Square sq) {
		int x = -1, y = -1;
//		System.out.println("x:"+sq.x() + ", y:"+sq.y());

		for (int i = 0; i < square.length; i++) {
			for (int j = 0; j < square[i].length; j++) {
				if (square[i][j] != null && square[i][j].y() == sq.y()) {
					if (square[i][j].x() < sq.x()) {
						x = i;
						y = j;
						sq = square[i][j];
					}
				}
			}
		}

		if (x > -1 && y > -1) {
			square[x][y].setActive(true);
			return true;
		}

		return false;
	}

	public boolean switchToUp(Square sq) {
		int x = -1, y = -1;

		for (int i = 0; i < square.length; i++) {
			for (int j = 0; j < square[i].length; j++) {
				if (square[i][j] != null && square[i][j].x() == sq.x()) {
					if (square[i][j].y() > sq.y()) {
						x = i;
						y = j;
						sq = square[i][j];
					}
				}
			}
		}

		if (x > -1 && y > -1) {
			square[x][y].setActive(true);
			return true;
		}

		return false;
	}

	public boolean switchToDown(Square sq) {
		int x = -1, y = -1;

		for (int i = 0; i < square.length; i++) {
			for (int j = 0; j < square[i].length; j++) {
				if (square[i][j] != null && square[i][j].x() == sq.x()) {
					if (square[i][j].y() < sq.y()) {
						x = i;
						y = j;
						sq = square[i][j];
					}
				}
			}
		}

		if (x > -1 && y > -1) {
			square[x][y].setActive(true);
			return true;
		}

		return false;
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

	public int height() {
		return height;
	}

	public Square[][] getSquare() {
		return square;
	}

	public void setSquare(Square[][] square) {
		this.square = square;
	}

	public void print(boolean printAll) {

		for (int i = 0; i < square.length; i++) {
			for (int j = 0; j < square[i].length; j++) {
				if (square[i][j] != null && (printAll || square[i][j].isActive()))
					System.out.print(printAll ? square[i][j].color + " " : square[i][j]);
				else if (printAll || (square[i][j] != null && square[i][j].isActive()))
					System.out.print("x ");
			}
			if (printAll)
				System.out.println();
		}
		if (printAll)
			System.out.println();

	}

	@Override
	public String toString() {
		return "Board [width=" + width + ", height=" + height + ", x=" + x + ", y=" + y + ", row=" + row + ", col=" + col + "]";
	}

}
