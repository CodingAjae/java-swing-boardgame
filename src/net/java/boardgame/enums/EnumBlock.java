package net.java.boardgame.enums;

import java.awt.Color;

public enum EnumBlock {
	NONE(), MOVE(), COMPLETE();
	
	public Color getStatusColor() {
		if( this == MOVE ) {
			return Color.GREEN;
		} 
		if( this == COMPLETE ) {
			return Color.GRAY;
		} 
		
		return Color.DARK_GRAY;
	}
	
	@Override
	public String toString() {
		if( this == COMPLETE ) 
			return "COMPLETE";
		if( this == MOVE ) 
			return "MOVE";
		
		return "NONE";
	}
	
}
