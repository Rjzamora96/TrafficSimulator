package edu.csc150;

import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.World;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

public class StatisticManager {
	private static HashMap<Intersection, Statistic> statistics = new HashMap<Intersection, Statistic>();
	private static boolean screenShowing = false;
	private static ArrayList<Components> screenComponents = new ArrayList<Components>();
	private static final int FONTSIZE = 12, SPACING = 100;
	
	public static void addStatistic(Statistic statistic) {
		if(statistics.containsKey(statistic.getOwner())) {
			throw new Error("Statistic is already initialized!");
		} statistics.put(statistic.getOwner(), statistic);
	}
	
	public static void removeStatistic(Intersection intersection) {
		statistics.remove(intersection);
	}
	
	public static Statistic findStatistic(Intersection intersection) {
		return statistics.get(intersection);
	}
	
	public static void printReport() {
		if(!screenShowing) {
			screenShowing = true;
			int totalRedCars = 0;
			int totalBlueCars = 0;
			int totalPurpleCars = 0;
			int totalYellowCars = 0;
			World targetWorld = null;
			int numOfIntersections = TrafficWorld.intersectionList.size();
			for(Intersection intersection : TrafficWorld.intersectionList) {
				targetWorld = intersection.getWorld();
				Statistic statistic = statistics.get(intersection);
				totalRedCars += statistic.getRedCars();
				totalBlueCars += statistic.getBlueCars();
				totalPurpleCars += statistic.getPurpleCars();
				totalYellowCars += statistic.getYellowCars();
				int carsGone = statistic.getBlueCars() + statistic.getPurpleCars() + statistic.getRedCars() + statistic.getYellowCars();
				double carsPerSecond = (double) carsGone/TrafficWorld.getRuntime();
				double interArrivalTime = (carsPerSecond != 0) ? 1/(carsPerSecond) : TrafficWorld.getRuntime();
				Components component = new Components();
				targetWorld.addObject(component, intersection.getX(), intersection.getY());
				component.setImage(new GreenfootImage("Cars gone by: " + carsGone
						+ "\nInter-Arrival Time: " + (int)interArrivalTime + "s", FONTSIZE, Color.white, Color.black));
				screenComponents.add(component);
			} int averageRedBlocksTraveled = (RedCar.instancesCreated != 0) ? totalRedCars/RedCar.instancesCreated : totalRedCars;
			int averageBlueBlocksTraveled = (BlueCar.instancesCreated != 0) ? totalBlueCars/BlueCar.instancesCreated : totalBlueCars;
			int averagePurpleBlocksTraveled = (PurpleCar.instancesCreated != 0) ? totalPurpleCars/PurpleCar.instancesCreated : totalPurpleCars;
			int averageYellowBlocksTraveled = (YellowCar.instancesCreated != 0) ? totalYellowCars/YellowCar.instancesCreated : totalYellowCars;
			Components redStats = new Components();
			redStats.setImage(new GreenfootImage("Red Car Life: " + averageRedBlocksTraveled, FONTSIZE, Color.white, Color.black));
			screenComponents.add(redStats);
			Components blueStats = new Components();
			blueStats.setImage(new GreenfootImage("Blue Car Life: " + averageBlueBlocksTraveled, FONTSIZE, Color.white, Color.black));
			screenComponents.add(blueStats);
			Components purpleStats = new Components();
			purpleStats.setImage(new GreenfootImage("Purple Car Life: " + averagePurpleBlocksTraveled, FONTSIZE, Color.white, Color.black));
			screenComponents.add(purpleStats);
			Components yellowStats = new Components();
			yellowStats.setImage(new GreenfootImage("Yellow Car Life: " + averageYellowBlocksTraveled, FONTSIZE, Color.white, Color.black));
			screenComponents.add(yellowStats);
			for(int i = 0; i < screenComponents.size() - numOfIntersections; i++) {
				Components component = screenComponents.get(i + numOfIntersections);
				targetWorld.addObject(component, SPACING + (i * SPACING), FONTSIZE);
			} Greenfoot.stop();
		} else {
			screenShowing = false;
			for(Components components : screenComponents) {
				components.getWorld().removeObject(components);
			} screenComponents.removeAll(screenComponents);
		}
	}
}
