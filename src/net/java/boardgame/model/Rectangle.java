package net.java.boardgame.model;

public class Rectangle implements Cloneable {
	int x;
	int y;
	int width;
	int height;
	
	public Rectangle( int x, int y, int width, int height ) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
}
 