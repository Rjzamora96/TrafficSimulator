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
			ROAD_OFFSET_LEFT = 10, ROAD_WIDTH = 50, WORLD_WIDTH = 998, WORLD_HEIGHT = 750, CELL_SIZE = 1,
			HORIZONTAL_ROADS = 5, VERTICAL_ROADS = 7, COLOR_CHOOSE = 4;
	public static ArrayList<Intersection> intersectionList = new ArrayList<Intersection>();
	public static long startTime = System.nanoTime();
	public static GreenfootImage foreground = null;
	
	public TrafficWorld() {
		super(WORLD_WIDTH, WORLD_HEIGHT, CELL_SIZE);
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
		Random rand = new Random();
		if(1 + rand.nextInt(100) > 99) {
			int side = rand.nextInt(COLOR_CHOOSE);
			if(side == 0) {
				makeCar(Direction.EAST, 0, findVerticalOffset() + (rand.nextInt(HORIZONTAL_ROADS) * (ROAD_WIDTH + findVerticalDistance())) + ROAD_OFFSET_RIGHT);
			} else if(side == 1) {
				makeCar(Direction.WEST, WORLD_WIDTH, findVerticalOffset() + (rand.nextInt(HORIZONTAL_ROADS) * (ROAD_WIDTH + findVerticalDistance())) + ROAD_OFFSET_LEFT);
			} else if(side == 2) {
				makeCar(Direction.SOUTH, findHorizontalOffset() + (rand.nextInt(VERTICAL_ROADS) * (ROAD_WIDTH + findHorizontalDistance())) + ROAD_OFFSET_LEFT, 0);
			} else if(side == 3) {
				makeCar(Direction.NORTH, findHorizontalOffset() + (rand.nextInt(VERTICAL_ROADS) * (ROAD_WIDTH + findHorizontalDistance())) + ROAD_OFFSET_RIGHT, WORLD_HEIGHT);
			}
		}
	}
	public void populateRoads() {	//Populates Roads with Cars
		Random rand = new Random();
		for(int i = 0; i < HORIZONTAL_ROADS; i++) {
			makeCar(Direction.EAST, rand.nextInt(WORLD_WIDTH), findVerticalOffset() + (i * (ROAD_WIDTH + findVerticalDistance())) + ROAD_OFFSET_RIGHT);
		} for(int i = 0; i < HORIZONTAL_ROADS; i++) {
			makeCar(Direction.WEST, rand.nextInt(WORLD_WIDTH), findVerticalOffset() + (i * (ROAD_WIDTH + findVerticalDistance())) + ROAD_OFFSET_LEFT);
		} for(int i = 0; i < VERTICAL_ROADS; i++) {
			makeCar(Direction.NORTH, findHorizontalOffset() + (i * (ROAD_WIDTH + findHorizontalDistance())) + ROAD_OFFSET_RIGHT, rand.nextInt(WORLD_HEIGHT));
		} for(int i = 0; i < VERTICAL_ROADS; i++) {
			makeCar(Direction.SOUTH, findHorizontalOffset() + (i * (ROAD_WIDTH + findHorizontalDistance())) + ROAD_OFFSET_LEFT, rand.nextInt(WORLD_HEIGHT));
		}
	}
	
	public void makeCar(Direction dir, int x, int y) {
		Random rand = new Random();
		Paint[] possibleColors = Paint.values();
		int selected = rand.nextInt(Paint.values().length);
		if(selected == 0) {
			Car car = new BlueCar(possibleColors[selected], dir);
			this.addObject(car, x, y);
		} else if(selected == 1) {
			Car car = new PurpleCar(possibleColors[selected], dir);
			this.addObject(car, x, y);
		} else if(selected == 2) {
			Car car = new RedCar(possibleColors[selected], dir);
			this.addObject(car, x, y);
		} else if(selected == 3) {
			Car car = new YellowCar(possibleColors[selected], dir);
			this.addObject(car, x, y);
		}
	}
	
	public void genIntersections() {
		for(int x = 0; x < HORIZONTAL_ROADS; x++) {
			for(int y = 0; y < VERTICAL_ROADS; y++) {
				Intersection intersection = new Intersection();
				this.addObject(intersection, findHorizontalOffset() + (y * (ROAD_WIDTH + findHorizontalDistance())) + (ROAD_WIDTH/2), findVerticalOffset() + (x * (ROAD_WIDTH + findVerticalDistance())) + (ROAD_WIDTH/2));
				intersection.placeStopLights();
				intersectionList.add(intersection);
			}
		}
	}
	public void genHorizontalRoads() {	//Generates Horizontal Roads
		for(int i = 0; i < HORIZONTAL_ROADS; i++) {
			Road road = new Road(ROAD_WIDTH, WORLD_WIDTH, Direction.EAST);
			this.addObject(road, 0, findVerticalOffset() + (i * (ROAD_WIDTH + findVerticalDistance())));
			road.draw();
		}
	}
	
	public void genVerticalRoads() {	//Generates Vertical Roads
		for(int i = 0; i < VERTICAL_ROADS; i++) {
			Road road = new Road(ROAD_WIDTH, WORLD_HEIGHT, Direction.NORTH);
			this.addObject(road, findHorizontalOffset() + (i * (ROAD_WIDTH + findHorizontalDistance())), 0);
			road.draw();
		}
	}
	
	public int findVerticalOffset() {
		int result = ((WORLD_HEIGHT  - (ROAD_WIDTH * HORIZONTAL_ROADS))/(HORIZONTAL_ROADS - 1))/2;
		return result;
	}
	
	public int findVerticalDistance() {
		int result = ((WORLD_HEIGHT - (findVerticalOffset() * 2))  - (ROAD_WIDTH * HORIZONTAL_ROADS))/(HORIZONTAL_ROADS - 1);
		return result;
	}
	
	public int findHorizontalOffset() {
		int result = ((WORLD_WIDTH  - (ROAD_WIDTH * VERTICAL_ROADS))/(VERTICAL_ROADS - 1))/2;
		return result;
	}
	
	public int findHorizontalDistance() {
		int result = ((WORLD_WIDTH - (findHorizontalOffset() * 2))  - (ROAD_WIDTH * VERTICAL_ROADS))/(VERTICAL_ROADS - 1);
		return result;
	}
}
