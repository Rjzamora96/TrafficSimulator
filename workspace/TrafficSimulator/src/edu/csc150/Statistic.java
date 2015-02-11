package edu.csc150;

public class Statistic {
	private int redCars = 0;
	private int blueCars = 0;
	private int yellowCars = 0;
	private int purpleCars = 0;
	
	private Intersection owner = null;
	
	public Statistic(Intersection owner) {
		this.owner = owner;
	}

	public int getRedCars() {
		return redCars;
	}

	public int getBlueCars() {
		return blueCars;
	}

	public int getYellowCars() {
		return yellowCars;
	}

	public int getPurpleCars() {
		return purpleCars;
	}

	public Intersection getOwner() {
		return owner;
	}

	public void setRedCars(int redCars) {
		this.redCars = redCars;
	}

	public void setBlueCars(int blueCars) {
		this.blueCars = blueCars;
	}

	public void setYellowCars(int yellowCars) {
		this.yellowCars = yellowCars;
	}

	public void setPurpleCars(int purpleCars) {
		this.purpleCars = purpleCars;
	}

	public void setOwner(Intersection owner) {
		this.owner = owner;
	}
}
