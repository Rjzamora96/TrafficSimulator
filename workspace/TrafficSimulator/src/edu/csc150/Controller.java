package edu.csc150;

import greenfoot.Actor;
import greenfoot.Greenfoot;

public class Controller extends Actor {
	boolean keyDown = false;
	public Controller() {
		this.getImage().setTransparency(0);
	}
	
	public void act() {
		if(!Greenfoot.isKeyDown("s")) {
			keyDown = false;
		} else if(Greenfoot.isKeyDown("s") && !keyDown) {
			keyDown = true;
			StatisticManager.printReport();
		}
	}
}
