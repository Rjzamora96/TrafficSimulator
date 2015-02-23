package edu.csc150;

import java.util.ArrayList;
import java.util.List;

import enums.*;
import greenfoot.Actor;

public abstract class Car extends Actor implements IntersectionListener{
	protected State state = Car.State.FAR;
	private int speed = 1, turnCountdown = 0;
	private Direction direction;
	protected Direction turnDirection;
	protected Intersection watching = null;
	protected static final int RIGHT_TIMER = 17, LEFT_TIMER = 32, MAX_ROTATION = 270, ROTATION_VAL = 90, BASE_ROTATION = 0;
	private boolean isGoing = true, stopped = false, turning = false;
	protected boolean turningRight = false;
	private boolean hasPath = false;
	private List<Intersection> closedPaths = new ArrayList<Intersection>();
	private List<Intersection> route = new ArrayList<Intersection>();
	private boolean hasDecidedTurn = false;
	
	public Car(Paint color, Direction dir) {
		this.direction = dir;
		this.setImage(color.getImage());
		this.setRotation(dir.getRotation());
	}
	
	/*private List<Intersection> findPath(Intersection current, Intersection destination) {
		List<Intersection> path = new ArrayList<Intersection>();
		closedPaths.add(current);
		if(current != destination) {
			Intersection best = null;
			double shortest = 0;
			boolean OVERRIDE = false;
			for(Intersection neighbor : current.neighbors) {
				if(!OVERRIDE) {
					if(!closedPaths.contains(neighbor)) {
						int dx = destination.getX() - neighbor.getX();
						int dy = destination.getY() - neighbor.getY();
						double dist = Math.sqrt((dx*dx) + (dy*dy));
						closedPaths.add(current);
						if(neighbor.arrayX != destination.arrayX && neighbor.arrayY != destination.arrayY) {
							if(neighbor.arrayY < current.arrayY) {	//North of Current
								if(this.getDirection() == Direction.NORTH) {
									best = neighbor;
								}
							} else if(neighbor.arrayY > current.arrayY) {	//South of Current
								if(this.getDirection() == Direction.SOUTH) {
									best = neighbor;
								}
							} else if(neighbor.arrayX > current.arrayX) {	//East of Current
								if(this.getDirection() == Direction.EAST) {
									best = neighbor;
								}
							} else if(neighbor.arrayX < current.arrayX) {	//West of Current
								if(this.getDirection() == Direction.WEST) {
									best = neighbor;
								}
							}
						} else {
							if(neighbor.arrayX == destination.arrayX) {
								if(current.arrayX > neighbor.arrayX && neighbor.arrayX > destination.arrayX) {
									best = neighbor;
								} else if(current.arrayX < neighbor.arrayX && neighbor.arrayX < destination.arrayX) {
									best = neighbor;
								}
							} else if(neighbor.arrayY == destination.arrayY) {
								if(current.arrayY > neighbor.arrayY && neighbor.arrayY > destination.arrayY) {
									best = neighbor;
								} else if(current.arrayY < neighbor.arrayY && neighbor.arrayY < destination.arrayY) {
									best = neighbor;
								}
							}
						} if(neighbor == destination) {
							best = neighbor;
							OVERRIDE = true;
						}
					}
				}
			}
			if(best!= null) {
				path = findPath(best, destination);
				path.add(best);
			}
		}
		return path;
	}*/
	//Euclidian Path
	private List<Intersection> findPath(Intersection current, Intersection destination) {
		List<Intersection> path = new ArrayList<Intersection>();
		closedPaths.add(current);
		if(current != destination) {
			Intersection best = null;
			double shortest = 0;
			for(Intersection neighbor : current.neighbors) {
				if(!closedPaths.contains(neighbor)) {
					int dx = destination.getX() - neighbor.getX();
					int dy = destination.getY() - neighbor.getY();
					double dist = Math.sqrt((dx*dx) + (dy*dy));
					closedPaths.add(neighbor);
					if(best == null || dist <= shortest) {
						best = neighbor;
						shortest = dist;
					}
				}
			} path = findPath(best, destination);
			path.add(best); 
		}
		return path;
	}
	
	//AVOID DESTINATION AT ALL COSTS
	/*private List<Intersection> findPath(Intersection current, Intersection destination) {
		List<Intersection> path = new ArrayList<Intersection>();
		closedPaths.add(current);
		if(current != destination) {
			List<Intersection> best = null;
			for(Intersection neighbor : current.neighbors) {
				if(!closedPaths.contains(neighbor)) {
					path = findPath(neighbor, destination);
					path.add(neighbor);
					if(best == null || path.size() > best.size()) {
						best = path;
					}
				}
			} if(best != null) {
				path = best;
			}
		}
		return path;
	}*/
	
	//Rube-Goldburg Style
	/*private List<Intersection> findPath(Intersection current, Intersection destination) {
		List<Intersection> path = new ArrayList<Intersection>();
		closedPaths.add(current);
		if(current != destination) {
			List<Intersection> best = null;
			for(Intersection neighbor : current.neighbors) {
				if(!closedPaths.contains(neighbor)) {
					path = findPath(neighbor, destination);
					path.add(neighbor);
					closedPaths.add(neighbor);
					if(best == null || path.size() <= best.size()) {
						best = path;
					}
				}
			} if(best != null) {
				path = best;
			}
		}
		return path;
	}*/
	
	public void decideTurn() {
		if(!route.isEmpty()) {
			hasDecidedTurn = true;
			Intersection next = route.get(route.size()-1);
			int nextRotation = this.getRotation();
			System.out.println("Next: " + next.arrayX + ", " + next.arrayY + " Current: " + watching.arrayX + ", " + watching.arrayY);
			if(next.arrayY < watching.arrayY) {
				System.out.println("North");
				switch(this.getDirection()) {
				case EAST:
					nextRotation = (nextRotation == BASE_ROTATION)? MAX_ROTATION : nextRotation - ROTATION_VAL;
					turningRight = false;
					beginTurnCountdown();
					break;
				case NORTH:
					break;
				case SOUTH:
					break;
				case WEST:
					nextRotation = (nextRotation == MAX_ROTATION)? BASE_ROTATION : nextRotation + ROTATION_VAL;
					turningRight = true;
					beginTurnCountdown();
					break;
				default:
					break;
				}
			} else if(next.arrayY > watching.arrayY) {
				System.out.println("South");
				switch(this.getDirection()) {
				case EAST:
					nextRotation = (nextRotation == MAX_ROTATION)? BASE_ROTATION : nextRotation + ROTATION_VAL;
					turningRight = true;
					beginTurnCountdown();
					break;
				case NORTH:
					break;
				case SOUTH:
					break;
				case WEST:
					nextRotation = (nextRotation == BASE_ROTATION)? MAX_ROTATION : nextRotation - ROTATION_VAL;
					turningRight = false;
					beginTurnCountdown();
					break;
				default:
					break;
				}
			} else if(next.arrayX > watching.arrayX) {
				System.out.println("East");
				switch(this.getDirection()) {
				case EAST:
					break;
				case NORTH:
					nextRotation = (nextRotation == MAX_ROTATION)? BASE_ROTATION : nextRotation + ROTATION_VAL;
					turningRight = true;
					beginTurnCountdown();
					break;
				case SOUTH:
					nextRotation = (nextRotation == BASE_ROTATION)? MAX_ROTATION : nextRotation - ROTATION_VAL;
					turningRight = false;
					beginTurnCountdown();
					break;
				case WEST:
					break;
				default:
					break;
				}
			} else if(next.arrayX < watching.arrayX) {
				System.out.println("West");
				switch(this.getDirection()) {
				case EAST:
					break;
				case NORTH:
					nextRotation = (nextRotation == BASE_ROTATION)? MAX_ROTATION : nextRotation - ROTATION_VAL;
					turningRight = false;
					beginTurnCountdown();
					break;
				case SOUTH:
					nextRotation = (nextRotation == MAX_ROTATION)? BASE_ROTATION : nextRotation + ROTATION_VAL;
					turningRight = true;
					beginTurnCountdown();
					break;
				case WEST:
					break;
				default:
					break;
				}
			} if(nextRotation != this.getRotation()) {
				for(Direction dir : Direction.values()) {
					if(dir.getRotation() == nextRotation) {
						turnDirection = dir;
					}
				}
			}
			route.remove(next);
		}
	}
	
	public void bump() throws Exception {
		if(isTouching(Car.class)) {
			throw new Exception("Cars collided, two dead!");
		}
	}
	
	public abstract void incrementStatistic();
	
	public abstract void checkMapEdge();
	
	public void act() {
		checkStopLight();
		move(speed);
		if(turning && !stopped) {
			turnCountdown--;
			if(turnCountdown == 0) {
				executeTurn();
			}
		} this.checkMapEdge();
		if(this.getWorld() != null) {
			try {
				bump();
			} catch(Exception e) {
				System.out.println(e.getMessage());
				Explosion exp = new Explosion();
				getWorld().addObject(exp, getX(), getY());
				removeTouching(Car.class);
				getWorld().removeObject(this);
			}
		}
	}
	
	private void executeTurn() {
		this.turning = false;
		this.direction = this.turnDirection;
		this.turnDirection = null;
		this.setRotation(direction.getRotation());
	}

	public enum State {
		IN, COMING, GOING, FAR;
	}
	
	public State getState() {
		return state;
	}
	
	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public void checkStopLight() {
		if(watching != null) {
			for(StopLight light : watching.getLights()) {
				if(light.getDirection() == this.getDirection()) {
					if(state == Car.State.IN && (light.getLight() == Lights.RED || light.getLight() == Lights.YELLOW) && !isGoing) {
						speed = 0;
						stopped = true;
					} else if(state == Car.State.COMING && (light.getLight() == Lights.RED ||light.getLight() == Lights.YELLOW)) {
						isGoing = false;
						stopped = false;
						speed = 1;
					} else if(state == Car.State.IN && light.getLight() == Lights.GREEN) {
						speed = 2;
						isGoing = true;
						stopped = false;
						if(turnDirection != null && !turning) {
							beginTurnCountdown();
						}
					} else if(state == Car.State.FAR){
						speed = 2;
						isGoing = false;
						stopped = false;
					} if(stopped == true) {
						speed = 0;
					}
				}
			}
		} else {
			speed = 2;
			isGoing = false;
			stopped = false;
		}
	}

	private void beginTurnCountdown() {
		this.turnCountdown = (turningRight)? RIGHT_TIMER : LEFT_TIMER;
		turning = true;
	}

	@Override
	public void inside() {
		if(state == Car.State.COMING) {
			state = Car.State.IN;
			this.incrementStatistic();
			if(!hasDecidedTurn) {
				decideTurn();
			};
		}
	}

	@Override
	public void gone() {
		if(state == Car.State.GOING) {
			state = Car.State.FAR;
			watching = null;
			hasDecidedTurn = false;
			isGoing = false;
		}
	}
	
	@Override
	public void near() {
		if(state == Car.State.FAR) {
			state = Car.State.COMING;
		} else if(state == Car.State.IN) {
			state = Car.State.GOING;
		}
	}
	
	@Override
	public void watch(Intersection intersection) {
		watching = intersection;
		if(!hasPath) {
			hasPath = true;
			route = (ArrayList<Intersection>) findPath(watching, TrafficWorld.layout[6][2]);
		}
	}
}

