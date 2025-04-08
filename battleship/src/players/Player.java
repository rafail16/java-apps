package players;

import java.util.*;

import models.*;

abstract public class Player {
	protected Board board;
	protected OpponentNextMove next = new OpponentNextMove();
	protected int activeShips = 5, totalPoints = 0, succesfulShots = 0;
	protected int[] ships = new int[5],  sizeShip = {5,4,3,3,2}, shipPoints = {350,250,100,100,50}, shinkPoints = {1000,500,250,0,0};
	Deque<Shot> shots = new LinkedList<Shot>();
	
	public void reset() {
		ships = new int[5];
		next = new OpponentNextMove();
		shots = new LinkedList<Shot>();
		activeShips = 5;
		totalPoints = 0;
		succesfulShots = 0;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Board getBoard() {
		return board;
	}
	public int getActiveShips() {
		return activeShips;
	}
	public int getTotalPoints() {
		return totalPoints;
	}
	public int getSuccesfulShots() {
		return succesfulShots;
	}
	public int[] getShips() {
		return ships;
	}
	public int[] getSizeShip() {
		return sizeShip;
	}
	public int[] getShipPoints() {
		return shipPoints;
	}
	public int[] getShinkPoints() {
		return shinkPoints;
	}
	public Deque<Shot> getShots() {
		return shots;
	}
	
	

}
