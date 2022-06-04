package net.java.tetrisegame.model.shape;

import net.java.tetrisegame.model.BaseShape;

public class SquareShape extends BaseShape {
	private static final long serialVersionUID = -5147820553652266353L;
	public SquareShape() {
		set(1,1);
		set(1,1);
	}
	
	@Override
	public void rotate() {
	}
}
