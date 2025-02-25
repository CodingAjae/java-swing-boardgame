package net.java.boardgame.model.shape;

import net.java.boardgame.enums.EnumColor;
import net.java.boardgame.model.BaseShape;

public class SquareShape extends BaseShape {
	private static final long serialVersionUID = -5147820553652266353L;
	public SquareShape() {
		int n = EnumColor.PINK.getNum();
		set(n,n);
		set(n,n);
	}
	
	@Override
	public void rotate() {
	}
}
