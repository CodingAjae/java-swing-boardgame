package net.java.boardgame.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class HexaBlockChecker extends HashMap<String, BoardBlock> {
	private static final long serialVersionUID = -7638658524195327616L;
	
	public void putClearBlocks(List<BoardBlock> blocks) {
		for( BoardBlock data : blocks) {
			String key = String.format("%02d:%02d@clears", data.getRow(), data.getCol());
			if( !containsKey(key) ) {
				put(key, data);
			}
		}
	}
	 
	public void putCheckShape(BaseShape shape) {
		int posx = shape.getPosx(), posy = shape.getPosy();
		for( int row = 0; row <= shape.getRows(); row++ ) {
			for( int col = 0; col <= shape.getCols(); col++ ) {
				int num = shape.get(row, col);
				if( num  > 0 ) {
					putCheckBlock(new BoardBlock(row+posy, col+posx, num));
				}
			}
		}
	}

	public void putCheckBlock(BoardBlock data) {
		String key = String.format("%02d:%02d@checks", data.getRow(), data.getCol());
		if( !containsKey(key) ) {
			put(key, data);
		}
	}
	
	
	public List<BoardBlock> extractClears() {
		List<BoardBlock> rslt = new ArrayList<BoardBlock>();
		for( Entry<String, BoardBlock> entry : entrySet() ) {
			if(entry.getKey().endsWith("@clears") ) {
				rslt.add(entry.getValue());
			}
		}
		return rslt;
	}
	
	public List<BoardBlock> extractChecks() {
		List<String> keys = new ArrayList<String>();
		for( Entry<String, BoardBlock> entry : entrySet() ) {
			if(entry.getKey().endsWith("@checks") ) {
				keys.add(entry.getKey());
			}
		}
		
		Collections.sort(keys);
		Collections.reverse(keys);
		
		List<BoardBlock> rslt = new ArrayList<BoardBlock>();
		for( String key : keys) {
			rslt.add(get(key));
		}
		
		return rslt;
	}

	public boolean hasClears() {
		return extractClears().size()>0;
	}
	public boolean hasChecker() {
		return extractChecks().size()>0;
	}
	
}
 