package net.java.tetrisegame.enums;

import java.awt.Color;

public enum EnumBlock {
	NONE(), MOVE(), COMPLETE();
	
	public Color getColor() {
		if( this == MOVE ) {
			return Color.GREEN;
		} 
		if( this == COMPLETE ) {
			return Color.GRAY;
		} 
		
		return Color.DARK_GRAY;
	}
}
