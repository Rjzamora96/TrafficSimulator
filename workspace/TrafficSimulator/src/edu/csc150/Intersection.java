package edu.csc150;

import enums.Direction;
import enums.Lights;
import greenfoot.Actor;

import java.util.ArrayList;

public class Intersection extends Actor {
	private ArrayList<IntersectionListener> carsInside = new ArrayList<IntersectionListener>(), carsNear = new ArrayList<IntersectionListener>(),
			carsGone = new ArrayList<IntersectionListener>();
	private StopLight[] lights = {
			new StopLight(Direction.NORTH, true), new StopLight(Direction.EAST, false),
			new StopLight(Direction.SOUTH, true), new StopLight(Direction.WEST, false)
	};
	private int timer = 0;
	private static final int SWAP_TIMER = 300, WARNING_TIMER = 150;
	public Intersection() {
		this.getImage().setTransparency(0);
	}
	
	public void placeStopLights() {
		for(StopLight light : lights) {
			if(light.getDirection().equals(Direction.NORTH)) {
				getWorld().addObject(light, getX(), getY() - (TrafficWorld.ROAD_WIDTH/2));
			} else if(light.getDirection().equals(Direction.EAST)) {
				getWorld().addObject(light, getX() + (TrafficWorld.ROAD_WIDTH/2), getY());
			} else if(light.getDirection().equals(Direction.SOUTH)) {
				getWorld().addObject(light, getX(), getY() + (TrafficWorld.ROAD_WIDTH/2));
			} else if(light.getDirection().equals(Direction.WEST)) {
				getWorld().addObject(light, getX() - (TrafficWorld.ROAD_WIDTH/2), getY());
			}
		}
	}
	public void updateStopLights() {
		timer++;
		if(timer % SWAP_TIMER == 0) {
			for(StopLight light : lights) {
				if(light.getLight().equals(Lights.YELLOW)) {
					light.changeRed();
				} else if(light.getLight().equals(Lights.RED)) {
					light.changeGreen();
				}
			} timer = 0;
		} else if(timer % WARNING_TIMER == 0) {
			for(StopLight light : lights) {
				if(light.getLight().equals(Lights.GREEN)) {
					light.changeYellow();
				}
			}
		}
	}
	
	@Override
	public void act() {
		updateStopLights();
		for(Object obj : getObjectsInRange(TrafficWorld.ROAD_WIDTH * 2, IntersectionListener.class)) {
			carsGone.add((IntersectionListener) obj);
		} for(Object obj : getObjectsInRange(TrafficWorld.ROAD_WIDTH + (TrafficWorld.ROAD_WIDTH/2), IntersectionListener.class)) {
			carsNear.add((IntersectionListener) obj);
		} for(Object obj : getObjectsInRange(TrafficWorld.ROAD_WIDTH, IntersectionListener.class)) {
			carsInside.add((IntersectionListener) obj);
		} carsGone.removeAll(carsNear);
		carsNear.removeAll(carsInside);
		for(IntersectionListener listener : carsNear) {
			listener.watch(this);
			listener.near();
		} for(IntersectionListener listener : carsInside) {
			listener.watch(this);
			listener.inside();
		} for(IntersectionListener listener : carsGone) {
			listener.gone();
		} carsNear.clear();
		carsInside.clear();
		carsGone.clear();
	}

	public StopLight[] getLights() {
		return lights;
	}

	public void setLights(StopLight[] lights) {
		this.lights = lights;
	}
}
