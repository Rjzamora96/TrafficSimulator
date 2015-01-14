package edu.csc150;

import enums.Direction;
import greenfoot.Actor;

public class StopLight extends Actor {
	private State state;
	private Direction dir;
	
	private enum State {
		GREEN, YELLOW, RED;
	}
	
	public void changeGreen() {
		state = State.GREEN;
	}
	
	public void changeYellow() {
		state = State.YELLOW;
	}
	
	public void changeRed() {
		state = State.RED;
	}

	public State getState() {
		return state;
	}

}
