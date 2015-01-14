package edu.csc150;

import enums.*;
import greenfoot.Actor;

public class Car extends Actor implements IntersectionListener{
	private State state = State.FAR;
	public Car(Paint color, Direction dir) {
		this.setImage(color.getImage());
		this.setRotation(dir.getRotation());
	}
	
	public void act() {
		move(1);
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
		IN, NEAR, FAR;
	}

	@Override
	public void approachingIntersection() {
		state = Car.State.NEAR;
	}

	@Override
	public void enteringIntersection() {
		state = Car.State.IN;
	}

	@Override
	public void leavingIntersection() {
		state = Car.State.NEAR;
	}

	@Override
	public void gone() {
		state = Car.State.FAR;
	}
}
