package edu.csc150;

import java.util.Random;

import enums.Direction;
import enums.Paint;

public class PurpleCar extends Car{
	private final int PROBABILITY_ROLL = 4;
	public PurpleCar(Paint color, Direction dir) {
		super(color, dir);
	}
	
	@Override
	public void decideTurn() {
		Random rand = new Random();
		int roll = rand.nextInt(PROBABILITY_ROLL);
		int nextRotation = this.getRotation();
		if(roll == 0) {
			nextRotation = (nextRotation == BASE_ROTATION)? MAX_ROTATION : nextRotation - ROTATION_VAL;
			turningRight = false;
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
			getWorld().removeObject(this);
		} else if(this.getX() <= 0) {
			getWorld().removeObject(this);
		} else if(this.getY() >= getWorld().getHeight()-1) {
			getWorld().removeObject(this);
		} else if(this.getY() <= 0) {
			getWorld().removeObject(this);
		}
	}
}
