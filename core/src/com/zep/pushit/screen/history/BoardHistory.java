package com.zep.pushit.screen.history;

import java.util.Stack;

import com.zep.pushit.ui.Board;
import com.zep.pushit.ui.Square;

public class BoardHistory {

	private Board board;
	private Stack<Square[][]>	next;
	private Stack<Square[][]>	previous;

	public BoardHistory(Board board) {
		this.board = board;

		previous = new Stack<Square[][]>();
		next = new Stack<Square[][]>();
	}
	
	public void historyAdd(Square[][] sq) {
//		System.out.println("Added:");
//
//		previous.push(sq.clone()); // yeni eklenenler gecmise aittir
//		next.clear(); // gecmise eklenen her sey gelecegi degistirir!
	}

	public Square[][] previous(Square[][] sq) {
		if (previous.isEmpty()) {
			return sq;
		}
		System.out.println("Prev");
		next.push(previous.pop()); // gecmisten al, gelecege koy

		return next.peek();
	}

	public Square[][] next(Square[][] sq) {
		if (next.isEmpty()) {
			return sq;
		}
		System.out.println("Next");
		previous.push(next.pop()); // gelecekten al, gecmise koy
		
		return previous.peek();
	}
}
