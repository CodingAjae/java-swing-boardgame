package net.java.tetrisegame.model;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.java.tetrisegame.enums.EnumBlock;

public class BoardTable extends HashMap<String, BoardBlock> {
	private static final long serialVersionUID = 2922098190496293599L;
	
	private int cols;
	private int rows;
	private int width;
	private int height;
	
	public BoardTable(int cols, int rows) {
		setCols(cols);
		setRows(rows);
		
		for( int col = 0; col < cols; col++) { 
			for( int row = 0; row < rows; row++) {
				update(new BoardBlock(row, col));
			}
		}
	}
	
	public void setSize(Dimension size) {
		width = (int)size.getWidth();
		height = (int)size.getHeight();
		
		int wmod = width%cols; 
		int hmod = height%rows; 
		
		int uwidth = (width-wmod)/cols;
		int uheight = (height-hmod)/rows; 
		
		int startx = (wmod-wmod%2)/2;
		int starty = (hmod-hmod%2)/2;
		
		int gap = 1;
		
		for(Entry<String, BoardBlock> entry: entrySet() ) {
			BoardBlock block = entry.getValue();
			
			Rectangle outer = new Rectangle(uwidth*block.getCol()+startx, uheight*block.getRow()+starty, uwidth, uheight);
			Rectangle inner = new Rectangle(uwidth*block.getCol()+startx+gap, uheight*block.getRow()+starty+gap, uwidth-(gap*2), uheight-(gap*2));
			block.setOuter(outer);
			block.setInner(inner);
			update(block);
		} 
		
	}
	
	public List<BoardBlock> extract() {
		List<BoardBlock> list = new ArrayList<BoardBlock>();
		for(Entry<String, BoardBlock> entry: entrySet() ) {
			list.add(entry.getValue());
		}
		
		return list;
	}
	
	public boolean contains(int row, int col) {
		String key = String.format("%02d:%02d", row, col);
		return containsKey(key);
	}
	
	public EnumBlock getStatus(int row, int col) {
		String key = String.format("%02d:%02d", row, col);
		if( containsKey(key) )  {
			return get(key).getStat();
		} else {
			return EnumBlock.NONE;
		}
	}
	
	public boolean checkCompleteBlock(int row, int col) {
		String key = String.format("%02d:%02d", row, col);
		return containsKey(key)&&get(key).isComplete();
	}
	
	public void updateStatus(int row, int col, EnumBlock stat) {
		String key = String.format("%02d:%02d", row, col);
		if( containsKey(key) ) {
			BoardBlock block = get(key);
			block.setStat(stat);
			update(block);
		}
	}
	public void resetStatus(int row, int col){
		String key = String.format("%02d:%02d", row, col);
		if( containsKey(key) ) {
			get(key).reset();
		}
	}
	
	public void update(BoardBlock block) {
		String key = String.format("%02d:%02d", block.getRow(), block.getCol());
		if( containsKey(key) ) {
			get(key).update(block);
		} else {
			block.setStat(EnumBlock.NONE);
			put(key, block);
		}
	}

	public int getCols() {
		return cols;
	}
	public void setCols(int cols) {
		this.cols = cols;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
}
