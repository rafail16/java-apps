package players;

import java.util.Random;
import javafx.util.Pair;
import models.*;

public class Opponent extends Player {
	
	public Opponent() {
		super();
		board = new Board(true);
	}

	public void move(User user) {
		int x, y;
		if (next.getFirst()) {
			Random r = new Random();
			x = r.nextInt(10);
			y = r.nextInt(10);
			while (user.board.isHit(x, y)) {
				x = r.nextInt(10);
				y = r.nextInt(10);
			}
		}
		else {
			Pair<Integer, Integer> temp = next.getMoves();
			x = temp.getKey();
			y = temp.getValue();
			while(user.board.isHit(x, y)) {
				temp = next.getMoves();
				x = temp.getKey();
				y = temp.getValue();
			}
			
		}
		boolean hit = user.board.hit(x, y);
		if(hit) {
			next.addMoves(x,y);
			int temp_type = user.board.getCell(x, y).shipType()-1;
			succesfulShots++;
			totalPoints += shipPoints[temp_type];
			if (!user.board.isAlive(x, y)) {
				totalPoints+=shinkPoints[temp_type];
				user.activeShips--;
				next.clear();
			}
			shots.addFirst(new Shot(x, y, 1, temp_type));
		}
		else shots.addFirst(new Shot(x, y, 0, -1));
		if(shots.size() == 6) shots.removeLast();
	}
}
