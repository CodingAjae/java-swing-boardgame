package net.java.boardgame.model.shape;

import net.java.boardgame.enums.EnumColor;
import net.java.boardgame.model.BaseShape;

public class TShape extends BaseShape {
	private static final long serialVersionUID = -2415006745912649036L;
	public TShape() {
		int n = EnumColor.BLUE.getNum();
		set(n,n,n);
		set(0,n,0);
	}
}
