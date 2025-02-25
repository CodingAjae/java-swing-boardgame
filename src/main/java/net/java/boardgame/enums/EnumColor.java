package net.java.boardgame.enums;

import java.awt.Color;

public enum EnumColor {
	DARKGRAY(Color.DARK_GRAY, 0),
	GRAY(Color.GRAY, 1),
	CYAN(new Color(0,255,255), 2),
	YELLOW(new Color(255,255,0), 3),
	PURPLE(new Color(128,0,128), 4),
	GREEN(new Color(0,255,0), 5),
	RED(new Color(255,0,0), 6),
	BLUE(new Color(0,0,255), 7),
	ORANGE(new Color(255,127,0), 8),
	PINK(new Color(191,112,185), 9);
	
	private int num;
	private Color color;
	EnumColor(Color color, int num) {
		this.color = color;
		this.num = num;
	}
	
	public int getNum() {
		return num;
	}
	public Color getColor() {
		return color;
	}
	
	public static EnumColor search(int num) {
		for( EnumColor item : EnumColor.values() ) {
			if( item.getNum() == num ) {
				return item;
			}
		}
		
		return null;
	}
}
 