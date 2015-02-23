package edu.csc150;

import enums.Direction;
import enums.Paint;

public class BlueCar extends Car{
	private int turnCounter = 0;
	private final int EVEN = 2;
	public static int instancesCreated = 0;
	public BlueCar(Paint color, Direction dir) {
		super(color, dir);
		instancesCreated++;
	}
	
	/*@Override
	public void decideTurn() {
		turnCounter++;
		int nextRotation = this.getRotation();
		if(turnCounter % EVEN == 0) {
			nextRotation = (nextRotation == MAX_ROTATION)? BASE_ROTATION : nextRotation + ROTATION_VAL;
			turningRight = true;
		} else if(turnCounter % EVEN != 0) {
			nextRotation = (nextRotation == BASE_ROTATION)? MAX_ROTATION : nextRotation - ROTATION_VAL;
			turningRight = false;
		} if(nextRotation != this.getRotation()) {
			for(Direction dir : Direction.values()) {
				if(dir.getRotation() == nextRotation) {
					turnDirection = dir;
				}
			}
		}
	}*/
	
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
	
	public void incrementStatistic() {
		Statistic statistic = StatisticManager.findStatistic(this.watching);
		statistic.setBlueCars(statistic.getBlueCars()+1);
	}
}
