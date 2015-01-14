package edu.csc150;

import java.awt.Color;
import java.util.Random;

import enums.Direction;
import enums.Paint;
import greenfoot.World;

public class TrafficWorld extends World {
	public static final int VERTICAL_SEPARATION = 175, HORIZONTAL_SEPARATION = 159, ROAD_OFFSET_RIGHT = 40,
			ROAD_OFFSET_LEFT = 10, ROAD_WIDTH = 50, WORLD_WIDTH = 998, WORLD_HEIGHT = 750, CELL_SIZE = 1,
			HORIZONTAL_ROADS = 5, VERTICAL_ROADS = 7;
	public TrafficWorld() {
		super(WORLD_WIDTH, WORLD_HEIGHT, CELL_SIZE);
		this.getBackground().setColor(Color.green);
		this.getBackground().fill();
		genHorizontalRoads();
		genVerticalRoads();
		populateRoads();
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
		int selected = rand.nextInt(Paint.values().length - 1);
		Car car = new Car(possibleColors[selected], dir);
		this.addObject(car, x, y);
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
