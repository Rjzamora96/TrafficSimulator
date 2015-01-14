package edu.csc150;

import java.awt.Color;

import enums.Direction;
import greenfoot.*;

public class Road extends Actor {
	private int width, length;
	private Direction direction;
	public Road(int width, int length, Direction direction) {
		this.getImage().setTransparency(0);
		this.width = width;
		this.length = length;
		this.direction = direction;
	}
	
	public void draw() {
		if(direction.equals(Direction.NORTH) || direction.equals(Direction.SOUTH)) {
			getWorld().getBackground().setColor(Color.gray);
			getWorld().getBackground().fillRect(this.getX(), 0, width, length);
		} else if(direction.equals(Direction.EAST) || direction.equals(Direction.WEST)) {
			getWorld().getBackground().setColor(Color.gray);
			getWorld().getBackground().fillRect(0, this.getY(), length, width);
		}
	}
}
