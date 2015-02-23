package edu.csc150;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import enums.Direction;
import enums.Paint;
import greenfoot.GreenfootImage;
import greenfoot.World;

public class TrafficWorld extends World {
	public static final int VERTICAL_SEPARATION = 175, HORIZONTAL_SEPARATION = 159, ROAD_OFFSET_RIGHT = 40,
			ROAD_OFFSET_LEFT = 10, ROAD_WIDTH = 50, CELL_SIZE = 1, COLOR_CHOOSE = 4;
	public static ArrayList<Intersection> intersectionList = new ArrayList<Intersection>();
	public static long startTime = System.nanoTime();
	public static int intervalMultiplier = 1;
	public static GreenfootImage foreground = null;
	private static Configuration settings = ConfigurationPanel.grabConfiguration();
	public static Intersection[][] layout = new Intersection[settings.getVerticalRoads()][settings.getHorizontalRoads()];
	private static boolean firstAct = true;
	
	public TrafficWorld() {
		super(settings.getWidthConfig(), settings.getHeightConfig(), CELL_SIZE);
		this.getBackground().setColor(Color.green);
		this.getBackground().fill();
		genHorizontalRoads();
		genVerticalRoads();
		genIntersections();
		this.addObject(new Controller(), 0, 0);
		//populateRoads();
	}
	
	public static int getRuntime() { //Returns the runtime of the world in seconds.
		long nowTime = System.nanoTime();
		long delta = (nowTime - startTime)/1000000000;
		return (int) delta;
	}
	public void act() {
		if(firstAct) {
			firstAct = false;
			startTime = System.nanoTime();
		} if(getRuntime() >= settings.getLengthConfig()) {
			StatisticManager.printReport();
		} Random rand = new Random();
		if(getRuntime() >= settings.getIntervalConfig()*intervalMultiplier) {
			intervalMultiplier++;
			int side = rand.nextInt(COLOR_CHOOSE);
			if(side == 0) {
				makeCar(Direction.EAST, 0, findVerticalOffset() + (rand.nextInt(settings.getHorizontalRoads()) * (ROAD_WIDTH + findVerticalDistance())) + ROAD_OFFSET_RIGHT);
			} else if(side == 1) {
				makeCar(Direction.WEST, settings.getWidthConfig(), findVerticalOffset() + (rand.nextInt(settings.getHorizontalRoads()) * (ROAD_WIDTH + findVerticalDistance())) + ROAD_OFFSET_LEFT);
			} else if(side == 2) {
				makeCar(Direction.SOUTH, findHorizontalOffset() + (rand.nextInt(settings.getVerticalRoads()) * (ROAD_WIDTH + findHorizontalDistance())) + ROAD_OFFSET_LEFT, 0);
			} else if(side == 3) {
				makeCar(Direction.NORTH, findHorizontalOffset() + (rand.nextInt(settings.getVerticalRoads()) * (ROAD_WIDTH + findHorizontalDistance())) + ROAD_OFFSET_RIGHT, settings.getHeightConfig());
			}
		}
	}
	public void populateRoads() {	//Populates Roads with Cars
		Random rand = new Random();
		for(int i = 0; i < settings.getHorizontalRoads(); i++) {
			makeCar(Direction.EAST, rand.nextInt(settings.getWidthConfig()), findVerticalOffset() + (i * (ROAD_WIDTH + findVerticalDistance())) + ROAD_OFFSET_RIGHT);
		} for(int i = 0; i < settings.getHorizontalRoads(); i++) {
			makeCar(Direction.WEST, rand.nextInt(settings.getWidthConfig()), findVerticalOffset() + (i * (ROAD_WIDTH + findVerticalDistance())) + ROAD_OFFSET_LEFT);
		} for(int i = 0; i < settings.getVerticalRoads(); i++) {
			makeCar(Direction.NORTH, findHorizontalOffset() + (i * (ROAD_WIDTH + findHorizontalDistance())) + ROAD_OFFSET_RIGHT, rand.nextInt(settings.getHeightConfig()));
		} for(int i = 0; i < settings.getVerticalRoads(); i++) {
			makeCar(Direction.SOUTH, findHorizontalOffset() + (i * (ROAD_WIDTH + findHorizontalDistance())) + ROAD_OFFSET_LEFT, rand.nextInt(settings.getHeightConfig()));
		}
	}
	
	public void makeCar(Direction dir, int x, int y) {
		Random rand = new Random();
		ArrayList<Paint> possibleColors = new ArrayList<Paint>();
		if(settings.isAllowBlue()) {
			possibleColors.add(Paint.BLUE);
		} if(settings.isAllowPurple()) {
			possibleColors.add(Paint.PURPLE);
		} if(settings.isAllowYellow()) {
			possibleColors.add(Paint.YELLOW);
		} if(settings.isAllowRed()) {
			possibleColors.add(Paint.RED);
		} int selected = rand.nextInt(possibleColors.size());
		Car car = null;
		switch(possibleColors.get(selected)) {
			case BLUE:
				car = new BlueCar(Paint.BLUE, dir);
				break;
			case PURPLE:
				car = new PurpleCar(Paint.PURPLE, dir);
				break;
			case RED:
				car = new RedCar(Paint.RED, dir);
				break;
			case YELLOW:
				car = new YellowCar(Paint.YELLOW, dir);
				break;
			default:
				break;
		
		} this.addObject(car, x, y);
	}
	
	public void genIntersections() {
		for(int x = 0; x < settings.getHorizontalRoads(); x++) {
			for(int y = 0; y < settings.getVerticalRoads(); y++) {
				Intersection intersection = new Intersection();
				this.addObject(intersection, findHorizontalOffset() + (y * (ROAD_WIDTH + findHorizontalDistance())) + (ROAD_WIDTH/2), findVerticalOffset() + (x * (ROAD_WIDTH + findVerticalDistance())) + (ROAD_WIDTH/2));
				intersection.placeStopLights();
				intersection.arrayX = y;
				intersection.arrayY = x;
				layout[y][x] = intersection;
				intersectionList.add(intersection);
			}
		} /*intersectionList.remove(layout[2][3]);
		this.removeObject(layout[2][3]);
		layout[2][3] = null;
		intersectionList.remove(layout[1][1]);
		this.removeObject(layout[1][1]);
		layout[1][1] = null;
		intersectionList.remove(layout[1][2]);
		this.removeObject(layout[1][2]);
		layout[1][2] = null;
		intersectionList.remove(layout[1][3]);
		this.removeObject(layout[1][3]);
		layout[1][1] = null;*/
	}
	public void genHorizontalRoads() {	//Generates Horizontal Roads
		for(int i = 0; i < settings.getHorizontalRoads(); i++) {
			Road road = new Road(ROAD_WIDTH, settings.getWidthConfig(), Direction.EAST);
			this.addObject(road, 0, findVerticalOffset() + (i * (ROAD_WIDTH + findVerticalDistance())));
			road.draw();
		}
	}
	
	public void genVerticalRoads() {	//Generates Vertical Roads
		for(int i = 0; i < settings.getVerticalRoads(); i++) {
			Road road = new Road(ROAD_WIDTH, settings.getHeightConfig(), Direction.NORTH);
			this.addObject(road, findHorizontalOffset() + (i * (ROAD_WIDTH + findHorizontalDistance())), 0);
			road.draw();
		}
	}
	
	public int findVerticalOffset() {
		int result = ((settings.getHeightConfig()  - (ROAD_WIDTH * settings.getHorizontalRoads()))/(settings.getHorizontalRoads() - 1))/2;
		return result;
	}
	
	public int findVerticalDistance() {
		int result = ((settings.getHeightConfig() - (findVerticalOffset() * 2))  - (ROAD_WIDTH * settings.getHorizontalRoads()))/(settings.getHorizontalRoads() - 1);
		return result;
	}
	
	public int findHorizontalOffset() {
		int result = ((settings.getWidthConfig()  - (ROAD_WIDTH * settings.getVerticalRoads()))/(settings.getVerticalRoads() - 1))/2;
		return result;
	}
	
	public int findHorizontalDistance() {
		int result = ((settings.getWidthConfig() - (findHorizontalOffset() * 2))  - (ROAD_WIDTH * settings.getVerticalRoads()))/(settings.getVerticalRoads() - 1);
		return result;
	}

	public static Configuration getSettings() {
		return settings;
	}

	public static void setSettings(Configuration settings) {
		TrafficWorld.settings = settings;
	}
}
