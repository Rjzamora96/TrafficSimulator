package edu.csc150;

public class StopLight {
	private State state = State.RED;
	
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
}
