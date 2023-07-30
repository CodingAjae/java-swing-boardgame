package net.java.boardgame.model.shape;

import net.java.boardgame.enums.EnumColor;
import net.java.boardgame.model.BaseShape;

public class ZShape extends BaseShape {
	private static final long serialVersionUID = 4232023242215738860L;
	public ZShape() {
		int n = EnumColor.PURPLE.getNum();
		set(n,n,0);
		set(0,n,n);
	}	
}
