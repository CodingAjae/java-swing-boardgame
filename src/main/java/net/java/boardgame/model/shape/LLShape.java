package net.java.boardgame.model.shape;

import net.java.boardgame.enums.EnumColor;
import net.java.boardgame.model.BaseShape;

public class LLShape extends BaseShape {
	private static final long serialVersionUID = -8370678439899775146L;
	public LLShape() {
		int n = EnumColor.YELLOW.getNum();
		set(0,0,n);
		set(n,n,n);
	}
}
