package net.java.tetrisegame.model;

import net.java.tetrisegame.enums.EnumBlock;

public class BoardBlock {
	private int col;
	private int row;
	private boolean complete = false;
	private EnumBlock stat = EnumBlock.NONE;
	
	private Rectangle inner;
	private Rectangle outer;
	
	public BoardBlock(int row, int col) {
		setCol(col);
		setRow(row);
	}
	
	public void update(BoardBlock block) {
		setStat(block.getStat());
		setInner(block.getInner());
		setOuter(block.getOuter());
	}

	public BoardBlock reset() {
		this.complete = false;
		this.stat = EnumBlock.NONE;
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
