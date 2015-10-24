package com.hacklm.assist;

public class Marker {
	private double x, y;
	private String example;
	private int count;

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "x=" + x + ",y=" + y + ",example=" + example + ",count=" + count;
	}
}
