package edu.csc150;

import enums.Direction;
import enums.Lights;
import greenfoot.Actor;

public class StopLight extends Actor {
	private Lights light;
	private Direction dir;
	
	public StopLight(Direction dir, boolean on) {
		this.dir = dir;
		this.setRotation(dir.getRotation() + 90);
		if(on) {
			this.changeGreen();
		} else {
			this.changeRed();
		}
	}
	
	public void changeGreen() {
		light = Lights.GREEN;
		this.setImage("Images/trafficLightGreen.png");
	}
	
	public void changeYellow() {
		light = Lights.YELLOW;
		this.setImage("Images/trafficLightYellow.png");
	}
	
	public void changeRed() {
		light = Lights.RED;
		this.setImage("Images/trafficLightRed.png");
	}

	public Lights getLight() {
		return light;
	}

	public Direction getDirection() {
		return dir;
	}
}
