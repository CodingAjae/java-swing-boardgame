package net.java.boardgame.model.shape;

import net.java.boardgame.enums.EnumColor;
import net.java.boardgame.model.BaseShape;

public class LineShape extends BaseShape {
	private static final long serialVersionUID = 3724661476865721565L;
	public LineShape() {
		int n = EnumColor.RED.getNum();
		set(n,n,n,n);
	}
}
