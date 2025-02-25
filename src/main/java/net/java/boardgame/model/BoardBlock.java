package net.java.boardgame.model;

import net.java.boardgame.enums.EnumBlock;

public class BoardBlock {
	private int col; //가로위치
	private int row; //세로위치
	private int num = 0; // (EnumColor) 색상코드
	private boolean complete = false;
	private EnumBlock stat = EnumBlock.NONE;
	
	private Rectangle inner;
	private Rectangle outer;
	
	public BoardBlock(int row, int col) {
		setCol(col);
		setRow(row);
	}
	
	public BoardBlock(int row, int col, int num) {
		setCol(col);
		setRow(row);
		setNum(num);
	}
	
	public void update(BoardBlock block) {
		setStat(block.getStat());
		setInner(block.getInner());
		setOuter(block.getOuter());
		setNum(block.getNum());
	}

	public String key() {
		return String.format("%02d:%02d", row, col);
	}
	
	public BoardBlock reset() {
		this.complete = false;
		this.stat = EnumBlock.NONE;
		this.num = 0;
		return this;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public EnumBlock getStat() {
		return stat;
	}
	public void setStat(EnumBlock stat) {
		this.stat = stat;
		if( stat == EnumBlock.COMPLETE ) {
			this.complete = true;
		}
	}
	public Rectangle getInner() {
		return inner;
	}
	public void setInner(Rectangle inner) {
		this.inner = inner;
	}
	public Rectangle getOuter() {
		return outer;
	}
	public void setOuter(Rectangle outer) {
		this.outer = outer;
	}
	public boolean isComplete() {
		return complete;
	}
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
}
