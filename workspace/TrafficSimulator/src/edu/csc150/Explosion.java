package edu.csc150;

import greenfoot.Actor;

public class Explosion extends Actor {
	public int imageState = 1;
	public Explosion() {
		setImage("Images/explosion1.png");
	}
	
	public void act() {
		imageState++;
		if(imageState == 10) {
			setImage("Images/explosion2.png");
		} else if(imageState == 20) {
			setImage("Images/explosion3.png");
		} else if(imageState == 30) {
			getWorld().removeObject(this);
		}
	}
}
