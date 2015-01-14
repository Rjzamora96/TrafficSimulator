package enums;

public enum Direction {
	NORTH(-90),
	EAST(0),
	SOUTH(90),
	WEST(180);
	
	private int rotation;
	
	private Direction(int rotate) {
		this.rotation = rotate;
	}
	public int getRotation() {
		return rotation;
	}
}
