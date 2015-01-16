package edu.csc150;

import enums.*;
import greenfoot.Actor;

public class Car extends Actor implements IntersectionListener{
	private State state = Car.State.FAR;
	private int speed = 1;
	private Direction direction;
	private Intersection watching = null;
	private boolean isGoing = true;
	private boolean stopped = false;
	
	public Car(Paint color, Direction dir) {
		this.direction = dir;
		this.setImage(color.getImage());
		this.setRotation(dir.getRotation());
	}
	
	public void act() {
		checkStopLight();
		move(speed);
		if(this.getX() >= getWorld().getWidth()-1) {
			setLocation(0, this.getY());
		} else if(this.getX() <= 0) {
			setLocation(getWorld().getWidth(), this.getY());
		} else if(this.getY() >= getWorld().getHeight()-1) {
			setLocation(this.getX(), 0);
		} else if(this.getY() <= 0) {
			setLocation(this.getX(), getWorld().getHeight());
		}
	}
	
	public enum State {
		IN, COMING, GOING, FAR;
	}
	
	public State getState() {
		return state;
	}
	
	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public void checkStopLight() {
		if(watching != null) {
			for(StopLight light : watching.getLights()) {
				if(light.getDirection() == this.getDirection()) {
					if(state == Car.State.IN && (light.getLight() == Lights.RED || light.getLight() == Lights.YELLOW) && !isGoing) {
						speed = 0;
						stopped = true;
					} else if(state == Car.State.COMING && light.getLight() == Lights.YELLOW) {
						isGoing = false;
						stopped = false;
						speed = 1;
					} else if(state == Car.State.IN && light.getLight() == Lights.GREEN) {
						speed = 2;
						isGoing = true;
						stopped = false;
					} else if(state == Car.State.FAR){
						speed = 2;
						isGoing = false;
						stopped = false;
					} if(stopped == true) {
						speed = 0;
					}
				}
			}
		} else {
			speed = 2;
			isGoing = false;
			stopped = false;
		}
	}

	@Override
	public void inside() {
		if(state == Car.State.COMING) {
			state = Car.State.IN;
		}
	}

	@Override
	public void gone() {
		if(state == Car.State.GOING) {
			state = Car.State.FAR;
			watching = null;
			isGoing = false;
		}
	}
	
	@Override
	public void near() {
		if(state == Car.State.FAR) {
			state = Car.State.COMING;
		} else if(state == Car.State.IN) {
			state = Car.State.GOING;
		}
	}
	
	@Override
	public void watch(Intersection intersection) {
		watching = intersection;
	}
}

