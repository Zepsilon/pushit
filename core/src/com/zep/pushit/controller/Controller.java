package com.zep.pushit.controller;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.zep.pushit.object.Direction;
import com.zep.pushit.ui.Kare;
import com.zep.pushit.ui.Pattern;
import com.zep.pushit.ui.Tahta;
import com.zep.pushit.util.Constant;

public class Controller {

	private Tahta	tahta;
	private int		width, height;	// karenin boyutlari. boyut kucultmek icin

	public Controller(Tahta tahta, int width, int height) {
		super();
		this.tahta = tahta;
		this.width = width;
		this.height = height;
	}

	public void moveRight(boolean checkSame) {
		tahta.getState().getScore().addMoveCount();
		for (int i = 0; i < tahta.getKareRowLen(); i++) {
			for (int j = 0; j < tahta.getKareColLen(); j++) {
				if (getKare(i, j) != null && getKare(i, j).isActive()) {
					
					getKare(i, j).moveRight(i, j, getKare(i, j).color(), false);
					
					skipCorners(false, i, j, tahta.getKareRowLen() - 2, tahta.getKareColLen() - 1);
					
					if (checkSame)
						tahta.checkSame(Direction.RIGHT);
					
					return;
				}
			}
		}

	}

	public void moveLeft(boolean checkSame) {
		tahta.getState().getScore().addMoveCount();
		for (int i = 0; i < tahta.getKareRowLen(); i++) {
			for (int j = 0; j < tahta.getKareColLen(); j++) {
				if (getKare(i, j) != null && getKare(i, j).isActive()) {
					
					getKare(i, j).moveLeft(i, j, getKare(i, j).color(), false);
					
					skipCorners(false, i, j, 1, tahta.getKareColLen() - 1);
					
					if (checkSame)
						tahta.checkSame(Direction.LEFT);
					
					return;
				}
			}
		}
	}

	public void moveDown(boolean checkSame) {
		tahta.getState().getScore().addMoveCount();
		for (int i = 0; i < tahta.getKareRowLen(); i++) {
			for (int j = 0; j < tahta.getKareColLen(); j++) {
				if (getKare(i, j) != null && getKare(i, j).isActive()) {
					
					getKare(i, j).moveDown(i, j, getKare(i, j).color(), false);
					
					skipCorners(true, i, j, tahta.getKareRowLen() - 1, tahta.getKareColLen() - 2);
					
					if (checkSame)
						tahta.checkSame(Direction.DOWN);
					
					return;
				}
			}
		}
	}

	public void moveUp(boolean checkSame) {
		tahta.getState().getScore().addMoveCount();
		for (int i = 0; i < tahta.getKareRowLen(); i++) {
			for (int j = 0; j < tahta.getKareColLen(); j++) {
				if (getKare(i, j) != null && getKare(i, j).isActive()) {

					getKare(i, j).moveUp(i, j, getKare(i, j).color(), false);

					skipCorners(true, i, j, tahta.getKareRowLen() - 1, 1);

					if (checkSame)
						tahta.checkSame(Direction.UP);

					return;
				}
			}
		}
	}

	/** aktif karenin koselere gelmesini engeller, uygun yerlere ilerletir */
	private void skipCorners(boolean isVertical, int i, int j, int iMax, int jMax) {
		if (i>0 || j>0) // iptal et
			return;
			
		if (isVertical) {
			if (i == 0 && j == jMax)
				moveRight(false);
			else if (i == iMax && j == jMax)
				moveLeft(false);
		} else {
			if (j == 0 && i == iMax)
				moveDown(false);
			else if (j == jMax && i == iMax)
				moveUp(false);
		}
	}

	public void addRow() {
		Kare[][] kClone = tahta.getKare().clone();
		setKare(new Kare[tahta.getKareRowLen()][tahta.getKareColLen() + 1]);

		copyArray(kClone);

		Vector2 activeIdx = new Vector2(-1, -1);

		for (int i = 0; i < tahta.getKareRowLen(); i++) {
			for (int j = tahta.getKareColLen() - 2; j < tahta.getKareColLen(); j++) {
				prepareKare(activeIdx, i, j);
			}
		}

		tahta.getFx().animType(false);

		shiftActiveKareToDown((int) activeIdx.x, (int) activeIdx.y);
		
		changePatternSize(true, true);

		System.out.println(kClone[0].length + " - " + tahta.getKareColLen());
	}

	public void addColumn() {
		Kare[][] kClone = tahta.getKare().clone();
		setKare(new Kare[tahta.getKareRowLen() + 1][tahta.getKareColLen()]);

		copyArray(kClone);

		Vector2 activeIdx = new Vector2(-1, -1);

		for (int i = tahta.getKareRowLen() - 2; i < tahta.getKareRowLen(); i++) {
			for (int j = 0; j < tahta.getKareColLen(); j++) {
				prepareKare(activeIdx, i, j);
			}
		}

		tahta.getFx().animType(false);

		shiftActiveKareToRight((int) activeIdx.x, (int) activeIdx.y);
		
		changePatternSize(false, true);

	}

	private void prepareKare(Vector2 activeIdx, int i, int j) {
		if (getKare(i, j) != null && getKare(i, j).isActive()) {
			activeIdx.x = i;
			activeIdx.y = j;
		} else {
			boolean visible = !(i == 0 || i == tahta.getKareRowLen() - 1 || j == 0 || j == tahta.getKareColLen() - 1); // ilk ve son kareler görünmez olacak
			int color = (visible) ? (int) new Random().nextInt(Tahta.CN) : 0;

			setNewKare(color, visible, i, j);
		}
	}

	private void setNewKare(int color, boolean visible, int i, int j) {
		int kh = height;
		int kw = width;
		if (visible) {
			kh = 0;
			kw = 0;
		}
		tahta.getKare()[i][j] = new Kare(tahta, kw, kh, color, visible, visible); // visible => markForAnim = true
	}

	public void deleteRow(int jD) {
		Kare[][] args = tahta.getKare().clone();
		if (args != null && args.length > 0) {
			
			Kare active = null;
			int x = 0,y = 0;
			
			setKare(new Kare[args.length][args[0].length - 1]);
			for (int i = 0; i < args.length; i++) {
				
				int newColId = 0;
				for (int j = 0; j < args[i].length; j++) {
					
					if (j != jD) {
						tahta.getKare()[i][newColId] = args[i][j];
						newColId++;
					} else if (args[i][j] != null && args[i][j].isActive()) {
						active = args[i][j];
						x = i;
						y = j;
					}
				}
			}
			
			if (active != null)
				tahta.getKare()[x][y] = active;

			deletePatternRow();
		}

	}

	
	private void deletePatternRow() {
		changePatternSize(true, false);
	}

	public void deleteColumn(int iD) {
		Kare[][] args = tahta.getKare().clone();
		if (args != null && args.length > 0) {
			
			Kare active = null;
			int x = 0,y = 0;
			
			setKare(new Kare[args.length - 1][args[0].length]);
			int newRowId = 0;
			for (int i = 0; i < args.length; i++) {
				
				if (i != iD) {
					
					for (int j = 0; j < args[i].length; j++) {
						tahta.getKare()[newRowId][j] = args[i][j];
					}
					
					newRowId++;
				} else if (args[i][0] != null && args[i][0].isActive()) {
					active = args[i][0];
					x = i;
					y = 0;
				} else if (args[i][args[i].length - 1] != null && args[i][args[i].length - 1].isActive()) {
					active = args[i][args[i].length - 1];
					x = i;
					y = args[i].length - 1;
				}
			}
			
			if (active != null)
				tahta.getKare()[x][y] = active;
			
			deletePatternCol();
		}

	}

	private void deletePatternCol() {
		changePatternSize(false, false);
	}

	private void changePatternSize(boolean isRow, boolean add) {
		int row = 0;
		int col = 0;
		if (isRow) {
			row = (add) ? 1 : -1;
		} else {
			col = (add) ? 1 : -1;
		}
		
		tahta.setPattern(new Pattern[tahta.getPattern().length + col][tahta.getPattern()[0].length + row]);

		for (int i = 0; i < tahta.getPattern().length; i++) {
			for (int j = 0; j < tahta.getPattern()[i].length; j++) {
				tahta.getPattern()[i][j] = new Pattern(width + Constant.SHAPE_GAP, height + Constant.SHAPE_GAP, (i + j) % 2);
			}
		}

	}

	public void markRow(int jD) {
		for (int i = 0; i < tahta.getKareRowLen(); i++) {
			tahta.getKare()[i][jD].setMarkForAnim(true);
		}
	}

	public void markColumn(int iD) {
		for (int j = 0; j < tahta.getKare()[iD].length; j++) {
			tahta.getKare()[iD][j].setMarkForAnim(true);
		}
	}

	public void moveByDirection(Direction direction, boolean decrease) {
		switch (direction) {
			case LEFT:
			case RIGHT:
				if (decrease)
					moveUp(false);
				else
					moveDown(false);
				break;

			case UP:
			case DOWN:
				if (decrease)
					moveLeft(false);
				else
					moveRight(false);
			default:
				// hareket etme
		}
	}

	private void copyArray(Kare[][] kClone) {
		for (int i = 0; i < kClone.length; i++) {
			for (int j = 0; j < kClone[i].length; j++) {
				if (kClone[i][j] != null) {
					tahta.getKare()[i][j] = kClone[i][j];
				}
			}
		}
	}

	private void shiftActiveKareToRight(int i, int j) {
		if (i > -1 && j > -1) { // aktif kare sondaysa (eklenen sütunda)
			getKare(i + 1, j).setColor(getKare(i, j).color());
			getKare(i + 1, j).setVisible(true);
			getKare(i + 1, j).setActive(true);
			getKare(i + 1, j).setMarkForAnim(false);

			getKare(i, j).setColor(new Random().nextInt(Tahta.CN));
			getKare(i, j).setVisible((j > 0 && j < tahta.getKareColLen() - 1));
			getKare(i, j).setActive(false);

		}
	}

	private void shiftActiveKareToDown(int i, int j) {
		if (i > -1 && j > -1) { // aktif kare sondaysa (eklenen satirda)
			getKare(i, j + 1).setColor(getKare(i, j).color());
			getKare(i, j + 1).setVisible(true);
			getKare(i, j + 1).setActive(true);
			getKare(i, j + 1).setMarkForAnim(false);

			getKare(i, j).setColor(new Random().nextInt(Tahta.CN));
			getKare(i, j).setVisible((i > 0 && i < tahta.getKareRowLen() - 1));
			getKare(i, j).setActive(false);

		}
	}

	public void drag(Direction direction) {
//		System.out.println("Direction: " + direction);
		switch (direction) {
			case LEFT:
				moveLeft(true);
				break;
			case RIGHT:
				moveRight(true);
				break;
			case UP:
				moveUp(true);
				break;
			case DOWN:
				moveDown(true);
				break;
			default:
				// hareket etme
		}
	}

	public void nextHistory() {
//		kare() = history.next(kare());
//		print(false);
	}

	public void prevHistory() {
//		kare() = history.previous(kare());
//		print(false);
	}

	private Kare getKare(int i, int j) {
		return tahta.getKare()[i][j];
	}

	private void setKare(Kare[][] kare) {
		tahta.setKare(kare);
	}

	public Tahta tahta() {
		return tahta;
	}

	public int width() {
		return width;
	}

	public int height() {
		return height;
	}

}
