package net.java.boardgame.model.shape;

import net.java.boardgame.enums.EnumColor;
import net.java.boardgame.model.BaseShape;

public class SShape extends BaseShape {
	private static final long serialVersionUID = 7944981660226756612L;
	public SShape() {
		int n = EnumColor.GREEN.getNum();
		set(0,n,n);
		set(n,n,0);
	}
}
