package com.zep.pushit.inputhandler;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.zep.pushit.ui.Board;

/**
 * Created by secelik on 05.08.2016.
 */
public class BoardInput implements InputProcessor {

	private Board	board;
	int				x0, y0;
	boolean			touched;

//	public BoardInput(Board board) {
//		this.board = board;
//
//	}

	public BoardInput(Board tahta) {
		this.board = tahta;
	}

	public boolean keyDown(int keycode) {

		switch (keycode) {
			case Input.Keys.LEFT:
				board.moveLeft();
				break;
			case Input.Keys.RIGHT:
				board.moveRight();
				break;
			case Input.Keys.UP:
				board.moveUp();
				break;
			case Input.Keys.DOWN:
				board.moveDown();
				break;
//			case Input.Keys.NUM_1:
//				board.addRow();
//				break;
//			case Input.Keys.NUM_2:
//				board.addColumn();
//				break;
			case Input.Keys.PLUS:
				board.nextHistory();
				break;
			case Input.Keys.MINUS:
				board.prevHistory();
				break;
		}

		return false;
	}

//	public void moveRight(boolean checkSame) {
//		for (int i = 0; i < tahta.getKare().length; i++) {
//			for (int j = 0; j < tahta.getKare()[i].length; j++) {
//				if (tahta.getKare()[i][j] != null && tahta.getKare()[i][j].isActive()) {
//					tahta.getKare()[i][j].moveRight(i, j, tahta.getKare()[i][j].color, false);
//					if (checkSame)
//						checkSame(Direction.RIGHT);
//					print();
//					return;
//				}
//			}
//		}
//
//	}

	@Override
	public boolean keyUp(int keycode) {

		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		resetCoordinats(screenX, screenY);
		// TODO ilk tiklandiginda aktif karenin ustunde ya da aynı satir/susutunda olma sarti aranmali?
		touched = true;

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		touched = false;

		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	private void resetCoordinats(int screenX, int screenY) {
		x0 = screenX;
		y0 = screenY;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
