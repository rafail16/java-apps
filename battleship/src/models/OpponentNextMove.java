package models;

import java.util.*;

import javafx.util.Pair;

public class OpponentNextMove {
	private Deque<Pair<Integer,Integer>> verticalMoves, horizontalMoves;
	private boolean vertical, first;
	
	public OpponentNextMove() {
		verticalMoves = new LinkedList<Pair<Integer,Integer>>();
		horizontalMoves = new LinkedList<Pair<Integer,Integer>>();
		vertical = true;
		first = true;
	}
	
	public boolean getFirst() {
		return verticalMoves.isEmpty() && horizontalMoves.isEmpty();
	}
	public Pair<Integer, Integer> getMoves() {
		Pair<Integer, Integer> temp;
		if (verticalMoves.isEmpty()) {
			vertical = false;
			temp = horizontalMoves.getFirst();
			horizontalMoves.removeFirst();
			return temp;
		}
		else {
			temp = verticalMoves.getFirst();
			verticalMoves.removeFirst();
			return temp;
		}
	}
	
	public void addMoves(int x, int y) {
		if (first) {
			first = false;
			if (y-1 >= 0) verticalMoves.add(new Pair<Integer,Integer>(x,y-1));
			if (y+1 < 10) verticalMoves.add(new Pair<Integer,Integer>(x,y+1));
			if (x-1 >= 0) horizontalMoves.add(new Pair<Integer,Integer>(x-1,y));
			if (x+1 < 10) horizontalMoves.add(new Pair<Integer,Integer>(x+1,y));
		}
		else if (vertical) {
			if (y-1 >= 0) verticalMoves.add(new Pair<Integer,Integer>(x,y-1));
			if (y+1 < 10) verticalMoves.add(new Pair<Integer,Integer>(x,y+1));
		}
		else {
			if (x-1 >= 0) horizontalMoves.add(new Pair<Integer,Integer>(x-1,y));
			if (x+1 < 10) horizontalMoves.add(new Pair<Integer,Integer>(x+1,y));
		}
	}
	
	public void clear() {
		verticalMoves = new LinkedList<Pair<Integer,Integer>>();
		horizontalMoves = new LinkedList<Pair<Integer,Integer>>();
		vertical = true;
		first = true;
	}
}

