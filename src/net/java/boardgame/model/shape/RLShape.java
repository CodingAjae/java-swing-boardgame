package net.java.boardgame.model.shape;

import net.java.boardgame.enums.EnumColor;
import net.java.boardgame.model.BaseShape;

public class RLShape extends BaseShape {
	private static final long serialVersionUID = 2227430530357617079L;
	public RLShape() {
		int n = EnumColor.ORANGE.getNum();
		set(n,0,0);
		set(n,n,n);
	}
}
