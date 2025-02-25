package net.java.boardgame.model;

import java.util.HashMap;
import java.util.Map;

public class BaseShape extends HashMap<String, Integer> {
	private static final long serialVersionUID = -7895724930260674911L;

	int posx;
	int posy;
	int cols=-1;
	int rows=-1;
	
	public void set(int ... arr) {
		rows += 1;
		int col = -1;
		for( int data : arr) {
			put(String.format("%02d:%02d", rows, ++col), data);
		}
		
		cols = col;
	}
	
	protected void update(int row, int col, int data) {
		remove(String.format("%02d:%02d", row, col));
		put(String.format("%02d:%02d", row, col), data);
	}
	
	public BaseShape clone() {
		BaseShape shape = new BaseShape();
		shape.putAll(this);
		shape.setPosx(posx);
		shape.setPosy(posy);
		shape.setCols(cols);
		shape.setRows(rows);
		return shape;
	}
	
	public void rotate() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int rows = getRows();
		int cols = getCols();
		
		for(int row = 0; row <= rows; row++ ) {
			for(int col = 0; col <= cols; col++ ) {
				int data = get(row, col);
				int nrow = col;
				int ncol = rows-row;
				
				map.put(String.format("%02d:%02d", nrow, ncol), data);
			}
		}
		
		clear();
		putAll(map);
		setRows(cols);
		setCols(rows);
	}
	
	public void setPosition(int posx, int posy) {
		setPosx(posx);
		setPosy(posy);
	}	
	public int get(int row, int col) {
		return get(String.format("%02d:%02d", row, col));
	}
	public int getPosx() {
		return posx;
	}
	public void setPosx(int posx) {
		this.posx = posx;
	}
	public int getPosy() {
		return posy;
	}
	public void setPosy(int posy) {
		this.posy = posy;
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
	public int getLeft() {
		return posx;
	}
	public int getTop() {
		return posy;
	}
	public int getRight() {
		return posx+cols;
	}
	public int getBottom() {
		return posy+rows;
	}
}
