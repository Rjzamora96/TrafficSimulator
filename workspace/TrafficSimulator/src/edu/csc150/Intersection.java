package edu.csc150;

import greenfoot.Actor;

import java.util.ArrayList;

public class Intersection extends Actor {
	private static ArrayList<IntersectionListener> carsInside = new ArrayList<IntersectionListener>(), carsNear = new ArrayList<IntersectionListener>();
	
	public Intersection() {
		this.getImage().setTransparency(0);
	}
	
	@Override
	public void act() {
		for(Object obj : getObjectsInRange(TrafficWorld.ROAD_WIDTH, IntersectionListener.class)) {
			if(carsNear.contains(obj)) {
				IntersectionListener listener = (IntersectionListener) obj;
				carsNear.remove(listener);
				carsInside.add(listener);
				listener.enteringIntersection();
			}
		}
	}
}
