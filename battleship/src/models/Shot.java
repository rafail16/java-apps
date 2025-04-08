package models;

public class Shot {
	private int x, y, madeShot, typeOfShip;

	public Shot(int x, int y, int madeShot, int typeOfShip) {
		super();
		this.x = x;
		this.y = y;
		this.madeShot = madeShot;
		this.typeOfShip = typeOfShip;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getMade() {
		return madeShot;
	}

	public void setMade(int made) {
		this.madeShot = made;
	}

	public int getTypeOfShip() {
		return typeOfShip;
	}

	public void setTypeOfShip(int typeOfShip) {
		this.typeOfShip = typeOfShip;
	}
	
}
