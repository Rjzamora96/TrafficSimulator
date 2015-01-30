package edu.csc150;

import java.util.Random;

import enums.Direction;
import enums.Paint;

public class YellowCar extends Car{
	private final int PROBABILITY_ROLL = 4;
	public YellowCar(Paint color, Direction dir) {
		super(color, dir);
	}
	
	@Override
	public void decideTurn() {
		Random rand = new Random();
		int roll = rand.nextInt(PROBABILITY_ROLL);
		int nextRotation = this.getRotation();
		if(roll == 0) {
			nextRotation = (nextRotation == MAX_ROTATION)? BASE_ROTATION : nextRotation + ROTATION_VAL;
			turningRight = true;
		} if(nextRotation != this.getRotation()) {
			for(Direction dir : Direction.values()) {
				if(dir.getRotation() == nextRotation) {
					turnDirection = dir;
				}
			}
		}
	}
	
	@Override
	public void checkMapEdge() {
		if(this.getX() >= getWorld().getWidth()-1) {
			this.setLocation(0, this.getY());
		} else if(this.getX() <= 0) {
			this.setLocation(getWorld().getWidth()-1, this.getY());
		} else if(this.getY() >= getWorld().getHeight()-1) {
			this.setLocation(this.getX(), 0);
		} else if(this.getY() <= 0) {
			this.setLocation(this.getX(), getWorld().getHeight()-1);
		}
	}
}
