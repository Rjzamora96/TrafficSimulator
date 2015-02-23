package edu.csc150;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Configuration implements Serializable{
	private int heightConfig = 750, widthConfig = 998, verticalRoads = 7, horizontalRoads = 5,
			intervalConfig = 5, lengthConfig = 30;
	private boolean allowRed = false, allowYellow = false, allowBlue = false, allowPurple = false;

	public int getHeightConfig() {
		return heightConfig;
	}

	public int getVerticalRoads() {
		return verticalRoads;
	}

	public int getHorizontalRoads() {
		return horizontalRoads;
	}

	public void setHeightConfig(int heightConfig) {
		this.heightConfig = heightConfig;
	}

	public void setVerticalRoads(int verticalRoads) {
		this.verticalRoads = verticalRoads;
	}

	public void setHorizontalRoads(int horizontalRoads) {
		this.horizontalRoads = horizontalRoads;
	}

	public int getWidthConfig() {
		return widthConfig;
	}

	public void setWidthConfig(int widthConfig) {
		this.widthConfig = widthConfig;
	}

	public int getIntervalConfig() {
		return intervalConfig;
	}

	public void setIntervalConfig(int intervalConfig) {
		this.intervalConfig = intervalConfig;
	}

	public boolean isAllowRed() {
		return allowRed;
	}

	public void setAllowRed(boolean allowRed) {
		this.allowRed = allowRed;
	}

	public boolean isAllowYellow() {
		return allowYellow;
	}

	public void setAllowYellow(boolean allowYellow) {
		this.allowYellow = allowYellow;
	}

	public boolean isAllowBlue() {
		return allowBlue;
	}

	public void setAllowBlue(boolean allowBlue) {
		this.allowBlue = allowBlue;
	}

	public boolean isAllowPurple() {
		return allowPurple;
	}

	public void setAllowPurple(boolean allowPurple) {
		this.allowPurple = allowPurple;
	}

	public int getLengthConfig() {
		return lengthConfig;
	}

	public void setLengthConfig(int lengthConfig) {
		this.lengthConfig = lengthConfig;
	}
}
