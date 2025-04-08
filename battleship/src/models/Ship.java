package models;

import javafx.scene.Parent;


public class Ship extends Parent {
	private int type;
	private int health;
	
	public Ship(int type, int health) {
        this.type = type;
        this.health = health;
    }
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	
	public void hit() {
		this.health--;
	}
	
	public boolean alive() {
		return this.health > 0;
	}
}
