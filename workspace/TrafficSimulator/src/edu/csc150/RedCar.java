package edu.csc150;

import enums.Direction;
import enums.Paint;

public class RedCar extends Car {
	public static int instancesCreated = 0;
	public RedCar(Paint color, Direction dir) {
		super(color, dir);
		instancesCreated++;
	}
	
	@Override
	public void decideTurn() {
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

	@Override
	public void incrementStatistic() {
		Statistic statistic = StatisticManager.findStatistic(this.watching);
		statistic.setRedCars(statistic.getRedCars()+1);
	}
	
}
