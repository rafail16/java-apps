package models;

import exceptions.*;
import javafx.geometry.*;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;

public class Board extends Parent {
	private GridPane board = new GridPane();
	private int[] ships;
	
	private boolean enemy = false;
	
	public Board(boolean enemy) {
		ships = new int[5];
		this.enemy = enemy;
		for (int x = 1; x < 11; x++) {
			Text t = new Text(String.valueOf(x-1));
			t.setFont(new Font(20));
			GridPane.setValignment(t, VPos.CENTER);
			GridPane.setHalignment(t, HPos.CENTER); 
			board.add(t, x, 0, 1, 1);
		}
		for (int y = 1; y < 11; y++) {
			Text t = new Text(String.valueOf(y-1));
			t.setFont(new Font(20));
			GridPane.setValignment(t, VPos.CENTER);
			GridPane.setHalignment(t, HPos.CENTER);
			board.add(t, 0, y, 1, 1);
		}
		
		for (int y = 1; y < 11; y++) {
			for (int x = 1; x < 11; x++) {
				Cell c = new Cell(x, y, this);
				board.add(c, x, y);
			}
		}
		getChildren().add(board);
	}
	public Cell getCell(int x, int y) {
        return (Cell)(board.getChildren().get((y+2)*10+x));
    }
	
	public boolean isHit(int x, int y) {
		return getCell(x, y).isHit();
	}
	
	public boolean hit(int x, int y) {
		Cell c = getCell(x, y);
		Ship s = c.ship;
		c.setHit(true);
		
		if(s == null) {
			c.setFill(Color.WHITE);
			return false;
		}
		c.setFill(Color.RED);
		s.hit();
		return true;
	}
	
	public boolean isAlive(int x, int y) {
		Ship s = getCell(x,y).ship;
		return s == null ? true : s.alive() ? true : false;
	}
	public void placeShips (int type, int x, int y, int orientation, int size) throws OversizeException, OverlapTilesException, AdjacentTilesException, InvalidCountExeception, InvalidInputException{
		int health = type == 1 ? 5: type == 2 ? 4 : type == 3 ? 3 : type == 4 ? 3 : 2; 
		Ship ship = new Ship(type, health);
		if (ships[type-1] != 0) throw new InvalidCountExeception ();
  		else if (x < 0 || x >= 10 || y < 0 || y >= 10) throw new InvalidInputException ("Coordinate");
  		else if (type > 5 || type <= 0) throw new InvalidInputException ("Type");
  		else if (orientation < 1 || orientation > 2) throw new InvalidInputException ("Orientation");
  		else if (orientation == 2) {
  			if (y+size > 10) throw new OversizeException ();
  			if (y-1 >= 0 && getCell(x, y-1).ship != null) throw new AdjacentTilesException ();
  			if (y+size+1 < 10 && getCell(x, y+size+1).ship != null) throw new AdjacentTilesException ();			
  			else {
  				for (int i = 0; i < size; i++) {
  					if (getCell(x, y+i).ship != null){
  						throw new OverlapTilesException ();
  					}
  					if ((x-1 >= 0 && getCell(x-1, y+i).ship != null) || (x+1 < 10 && getCell(x+1, y+i).ship != null)) {
  						throw new AdjacentTilesException ();
  					}
  					if (!enemy) {
                        getCell(x,y+i).setFill(Color.DARKGREY);
                        getCell(x,y+i).setStroke(Color.WHITE);
                    }
  					getCell(x, y+i).ship = ship;
  				}
  				ships[type-1] = 1;
  			}
  		}
  		else {
  			if (x+size > 10) throw new OversizeException ();
  			if (x-1 >= 0 && getCell(x-1, y).ship != null) throw new AdjacentTilesException ();
  			if (x+size+1 < 10 && getCell(x+size+1, y).ship != null) throw new AdjacentTilesException ();			
  			else {
  				for (int i = 0; i < size; i++) {
  					if (getCell(x+i, y).ship != null){
  						throw new OverlapTilesException ();
  					}
  					if ((y-1 >= 0 && getCell(x+i, y-1).ship != null) || (y+1 < 10 && getCell(x+i, y+1).ship != null)) {
  						throw new AdjacentTilesException ();
  					}
  					if (!enemy) {
  						getCell(x+i,y).setFill(Color.DARKGREY);
  						getCell(x+i,y).setStroke(Color.WHITE);
                    }
  					getCell(x+i, y).ship = ship;
  				}
  				ships[type-1] = 1;
  			}
  		}      		
	}
		
	
	public class Cell extends Rectangle {
        public int x, y;
        public Ship ship = null;
        private boolean isHit;
        private Board board;

        public Cell(int x, int y, Board board) {
            super(30, 30);
            this.x = x;
            this.y = y;
            this.board = board;
            this.isHit = false;
            setFill(Color.DODGERBLUE);
            setStroke(Color.BLACK);
        }

		public boolean isHit() {
			return isHit;
		}

		public void setHit(boolean isHit) {
			this.isHit = isHit;
		}
        
		public int shipType() {
			return ship.getType();
		}
    }
	        
}
