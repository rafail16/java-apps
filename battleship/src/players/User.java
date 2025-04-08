package players;

import models.*;

public class User extends Player {

	public User() {
		super();
		board = new Board(false);
	}

	public void move(Opponent opp, int x, int y) {
		boolean hit = opp.board.hit(x, y);
		if(hit) {
			int temp_type = opp.board.getCell(x, y).shipType()-1;
			opp.ships[temp_type] = 1;
			succesfulShots++;
			totalPoints += shipPoints[temp_type];
			if (!opp.board.isAlive(x, y)) {
				totalPoints += shinkPoints[temp_type];
				opp.activeShips--;
				opp.ships[temp_type] = 2;
			}
			shots.addFirst(new Shot(x, y, 1, temp_type));
		}
		else shots.addFirst(new Shot(x, y, 0, -1));
		if(shots.size() == 6) shots.removeLast();
	}
}
