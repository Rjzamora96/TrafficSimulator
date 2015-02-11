package edu.csc150;

import enums.*;
import greenfoot.Actor;

public abstract class Car extends Actor implements IntersectionListener{
	protected State state = Car.State.FAR;
	private int speed = 1, turnCountdown = 0;
	private Direction direction;
	protected Direction turnDirection;
	protected Intersection watching = null;
	protected static final int RIGHT_TIMER = 17, LEFT_TIMER = 32, MAX_ROTATION = 270, ROTATION_VAL = 90, BASE_ROTATION = 0;
	private boolean isGoing = true, stopped = false, turning = false;
	protected boolean turningRight = false;
	
	public Car(Paint color, Direction dir) {
		this.direction = dir;
		this.setImage(color.getImage());
		this.setRotation(dir.getRotation());
	}
	
	public abstract void decideTurn();
	
	public void bump() throws Exception {
		if(isTouching(Car.class)) {
			throw new Exception("Cars collided, two dead!");
		}
	}
	
	public abstract void incrementStatistic();
	
	public abstract void checkMapEdge();
	
	public void act() {
		checkStopLight();
		move(speed);
		if(turning && !stopped) {
			turnCountdown--;
			if(turnCountdown == 0) {
				executeTurn();
			}
		} this.checkMapEdge();
		if(this.getWorld() != null) {
			try {
				bump();
			} catch(Exception e) {
				System.out.println(e.getMessage());
				Explosion exp = new Explosion();
				getWorld().addObject(exp, getX(), getY());
				removeTouching(Car.class);
				getWorld().removeObject(this);
			}
		}
	}
	
	private void executeTurn() {
		this.turning = false;
		this.direction = this.turnDirection;
		this.turnDirection = null;
		this.setRotation(direction.getRotation());
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
					} else if(state == Car.State.COMING && (light.getLight() == Lights.RED ||light.getLight() == Lights.YELLOW)) {
						isGoing = false;
						stopped = false;
						speed = 1;
					} else if(state == Car.State.IN && light.getLight() == Lights.GREEN) {
						speed = 2;
						isGoing = true;
						stopped = false;
						if(turnDirection != null && !turning) {
							beginTurnCountdown();
						}
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

	private void beginTurnCountdown() {
		this.turnCountdown = (turningRight)? RIGHT_TIMER : LEFT_TIMER;
		turning = true;
	}

	@Override
	public void inside() {
		if(state == Car.State.COMING) {
			state = Car.State.IN;
			this.incrementStatistic();
		}
	}

	@Override
	public void gone() {
		if(state == Car.State.GOING) {
			state = Car.State.FAR;
			watching = null;
			isGoing = false;
			decideTurn();
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

