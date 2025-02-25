package net.java.boardgame.control;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import net.java.boardgame.enums.EnumAction;
import net.java.boardgame.enums.EnumBlock;
import net.java.boardgame.enums.EnumBroadcast;
import net.java.boardgame.listener.BroadcastListener;
import net.java.boardgame.model.BaseShape;
import net.java.boardgame.model.BoardBlock;
import net.java.boardgame.model.BoardTable;

public abstract class BaseBoardControl {
	protected BoardTable boardTable;
	protected List<BroadcastListener> listeners = new ArrayList<BroadcastListener>();
	protected Random rand = new Random();
	
	private Queue<BaseShape> queue = new LinkedList<BaseShape>();
	
	private String _title = "GAME";
	private long _count = 0;
	private long _score = 0;
	
	public abstract void doAction(EnumAction action); 
	
	public void setTitle(String title) {
		_title = title;
	}
	public String getTitle() {
		return _title;
	}
	
	public BoardTable getBoardTable() {
		return boardTable;
	}
	
	public void addListener(BroadcastListener listener) {
		listeners.add(listener);
	}
	
	public void setupBoardShape(BaseShape shape) {
		int posy = boardTable.getRows() - shape.getRows() -1;
		shape.setPosition(0, posy);
		
		for( int row = 0; row <= shape.getRows(); row++) {
			for( int col = 0; col <= shape.getCols(); col++) {
				int num = shape.get(row, col);
				EnumBlock stat = num > 0 ? EnumBlock.COMPLETE : EnumBlock.NONE;
				boardTable.updateStatus(row+shape.getPosy(), col+shape.getPosx(), stat, num);
			}
		}
	}
	
	public void reservationShape( BaseShape shape ) {
		queue.add(shape);
	}
		
	protected void setupBoardTable(int cols, int rows) {
		boardTable = new BoardTable(cols, rows);
	}
	
	protected void broadcast(EnumBroadcast event, Object ... args) {
		for( BroadcastListener listener: listeners ) {
			listener.broadcast(event, args);
		}
	}
	 
	protected void updateScore(long score, int count) {
		_score += score;
		_count += count;
		broadcast(EnumBroadcast.UPDATESCORE, _score, _count);
	}
	
	protected BaseShape injectShape(Class<? extends BaseShape> type) {
		BaseShape shape = null;
		try {
			if( queue.isEmpty() )  {
				shape = type.newInstance();
			} else {
				shape = queue.poll();
			}
			
			int cols = boardTable.getCols();
			int csize = shape.getCols();
			int col = ((cols-cols%2)/2)-((csize-csize%2)/2)-1;
			shape.setPosition(col, -1);
		} catch (Exception e) { 
		}
		
		return shape;
	}
	
	protected void doShapeMove(EnumAction action, BaseShape shape) {
		BaseShape before = null;
		switch(action) {
			case BOTTOM:
				before = shape.clone();
				int row = calcBottom(shape);
				if( row >= 0 ) {
					shape.setPosition(shape.getPosx(), row);
				}
				break;
			case ROTATE:
				before = shape.clone();
				shape.rotate();
				break;
			case LEFT:
				if( checkMove(shape, action) ) {
					before = shape.clone();
					shape.setPosition(shape.getPosx()-1, shape.getPosy());
				}
				break;
			case RIGHT:
				if( checkMove(shape, action) ) {
					before = shape.clone();
					shape.setPosition(shape.getPosx()+1, shape.getPosy());
				}
				break;
			case NEXT:
			case DOWN:
				if( !checkComplate(shape) ) {
					before = shape.clone();
					shape.setPosition(shape.getPosx(), shape.getPosy()+1);
				}
				break;
			default:
				break;
		}
		
		if( before != null ) {
			for( int row = 0; row <= before.getRows(); row++) {
				for( int col = 0; col <= before.getCols(); col++) {
					EnumBlock stat = EnumBlock.NONE;
					if( boardTable.checkCompleteBlock(row+before.getPosy(), col+before.getPosx()) ) {
						stat = EnumBlock.COMPLETE;	
					}
					 
					int num = before.get(row, col);
					boardTable.updateStatus(row+before.getPosy(), col+before.getPosx(), stat, num);
				}
			}
		} 

		for( int row = 0; row <= shape.getRows(); row++) {
			for( int col = 0; col <= shape.getCols(); col++) {
				if( shape.get(row,col) > 0 ) {
					int num = shape.get(row, col);
					boardTable.updateStatus(row+shape.getPosy(), col+shape.getPosx(), EnumBlock.MOVE, num);
				} else {
					int num = boardTable.getNum(row+shape.getPosy(), col+shape.getPosx());
					boardTable.updateStatus(row+shape.getPosy(), col+shape.getPosx(), boardTable.getStatus(row+shape.getPosy(), col+shape.getPosx()), num);
				}
			}
		}		
	}

	protected boolean checkMove(BaseShape shape, EnumAction action) {
		switch(action) {
			case LEFT:
				if( shape.getPosx() > 0 ) {
					for( int row = shape.getRows(); row >= 0; row--) {
						for( int col = 0; col <= shape.getCols(); col++) {
							if(shape.get(row, col) == 0 ) {
								continue;
							}
							
							if(boardTable.getStatus(shape.getPosy()+row, shape.getPosx()+col-1) == EnumBlock.COMPLETE) {
								return false;
							}
						}
					}
					
					return true;
				}
				
				return false;
			case RIGHT:
				if( boardTable.getCols() > shape.getRight()+1 ) {
					for( int row = shape.getRows(); row >= 0; row--) {
						for( int col = 0; col <= shape.getCols(); col++) {
							if(shape.get(row, col) == 0 ) {
								continue;
							}
							
							if(boardTable.getStatus(shape.getPosy()+row, shape.getPosx()+col+1) == EnumBlock.COMPLETE) {
								return false;
							}
						}
					}
					
					return true;
				}
				return false; 
			case NEXT:
				return boardTable.getRows() > shape.getBottom()+1;
			case DOWN:
				return boardTable.getRows() > shape.getBottom()+1;
			default:
				return false;
		}
	}		
	
	protected void doComplete(BaseShape shape) {
		if( checkComplate(shape) ) {
			for( int row = 0; row <= shape.getRows(); row++) {
				for( int col = 0; col <= shape.getCols(); col++) {
					if( shape.get(row,col) > 0 ) {
						int num = shape.get(row,col);
						boardTable.updateStatus(row+shape.getPosy(), col+shape.getPosx(), EnumBlock.COMPLETE, num);
					} else {
						BoardBlock block = boardTable.get(row+shape.getPosy(), col+shape.getPosx());
						boardTable.updateStatus(row+shape.getPosy(), col+shape.getPosx(), block.getStat(), block.getNum());
					}
				}
			}
			
			return;
		}
	}
	
	protected int calcBottom(BaseShape shape) {
		for( int row = shape.getPosy(); row <= boardTable.getRows(); row++) {
			if( checkComplate(shape, row) ) {
				return row;
			}
		}
		
		return -1;
	}
	
	protected boolean checkComplate(BaseShape shape) {
		return checkComplate(shape, shape.getPosy());
	}
	
	protected boolean checkComplate(BaseShape shape, int posy) {
		for( int col = 0; col <= shape.getCols(); col++) {
			for( int row = shape.getRows(); row >= 0; row--) {
				if(shape.get(row, col) == 0 ) {
					continue;
				}
				
				if( posy+row+2 > boardTable.getRows() ) {
					return true;
				}
				
				if(boardTable.getStatus(posy+row+1, shape.getPosx()+col) == EnumBlock.COMPLETE) {
					return true;
				} 
			}
		}
		
		return false;
	}		
	
}
