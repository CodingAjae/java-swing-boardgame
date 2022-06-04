package net.java.tetrisegame.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.java.tetrisegame.enums.EnumAction;
import net.java.tetrisegame.enums.EnumBlock;
import net.java.tetrisegame.enums.EnumBroadcast;
import net.java.tetrisegame.listener.BroadcastListener;
import net.java.tetrisegame.model.BaseShape;
import net.java.tetrisegame.model.BoardTable;
import net.java.tetrisegame.model.shape.LLShape;
import net.java.tetrisegame.model.shape.LineShape;
import net.java.tetrisegame.model.shape.RLShape;
import net.java.tetrisegame.model.shape.SShape;
import net.java.tetrisegame.model.shape.SquareShape;
import net.java.tetrisegame.model.shape.TShape;
import net.java.tetrisegame.model.shape.ZShape;

public class BoardControl {
	private List<Class<? extends BaseShape>> shapes = null;
	private BoardTable boardTable;
	
	private Random rand = new Random();
	private BaseShape active = null;
	
	public List<BroadcastListener> listeners;

	public BoardControl() {
		listeners = new ArrayList<BroadcastListener>();
		shapes = new ArrayList<Class<? extends BaseShape>>();
		shapes.add(LineShape.class);
		shapes.add(LLShape.class);
		shapes.add(RLShape.class);
		shapes.add(SquareShape.class);
		shapes.add(SShape.class);
		shapes.add(TShape.class);
		shapes.add(ZShape.class);
	}
	
	public BoardTable getBoardTable() {
		return boardTable;
	}
	public void setupBoardTable(int cols, int rows) {
		boardTable = new BoardTable(cols, rows);
	}
	
	public void addListener(BroadcastListener listener) {
		listeners.add(listener);
	}
	public void broadcast(EnumBroadcast event) {
		for( BroadcastListener listener: listeners ) {
			listener.broadcast(event);
		}
	}
	
	
	public void injectActionShape(Class<? extends BaseShape> type) {
		if( active == null )  {
			try {
				int cols = boardTable.getCols();
				active = type.newInstance();
				int csize = active.getCols();
				int col = ((cols-cols%2)/2)-((csize-csize%2)/2)-1;
				active.setPosition(col, 0);
				doAction(EnumAction.SETUP);
			} catch (Exception e) { 
				e.printStackTrace();
			}
		}
	}
	
	public void doAction(EnumAction action) {
		if( action == EnumAction.NEXT ) {
			if( checkGameover() ) {
				broadcast(EnumBroadcast.GAMEOVER);
				
				for(int row = 0; row <= boardTable.getRows(); row++) {
					for( int col =0; col  <= boardTable.getCols(); col++ ) {
						boardTable.resetStatus(row, col);
					}
				}
				
				broadcast(EnumBroadcast.UPDATEUI);
				return;
			}
			
			
			if( active == null ) {
				int val = rand.nextInt(shapes.size());
				injectActionShape(shapes.get(val));
				return;
			} else if( checkComplate(active) ) {
				doComplete();
				return;
			}
		}
		
		if( active != null ) {
			BaseShape before = null;
			switch(action) {
				case BOTTOM:
					before = active.clone();
					int row = calcBottom(active);
					if( row >= 0 ) {
						active.setPosition(active.getPosx(), row);
					}
					break;
				case ROTATE:
					before = active.clone();
					active.rotate();
					break;
				case LEFT:
					if( checkMove(active, action) ) {
						before = active.clone();
						active.setPosition(active.getPosx()-1, active.getPosy());
					}
					break;
				case RIGHT:
					if( checkMove(active, action) ) {
						before = active.clone();
						active.setPosition(active.getPosx()+1, active.getPosy());
					}
					break;
				case NEXT:
				case DOWN:
					if( !checkComplate(active) ) {
						before = active.clone();
						active.setPosition(active.getPosx(), active.getPosy()+1);
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
						
						boardTable.updateStatus(row+before.getPosy(), col+before.getPosx(), stat);
					}
				}
			} 

			for( int row = 0; row <= active.getRows(); row++) {
				for( int col = 0; col <= active.getCols(); col++) {
					if( active.get(row,col) > 0 ) {
						boardTable.updateStatus(row+active.getPosy(), col+active.getPosx(), EnumBlock.MOVE);
					} else {
						boardTable.updateStatus(row+active.getPosy(), col+active.getPosx(), boardTable.getStatus(row+active.getPosy(), col+active.getPosx()));
					}
				}
			}
			
			if( action == EnumAction.BOTTOM ) {
				doComplete();
			}
			
			doClearlines();
			broadcast(EnumBroadcast.UPDATEUI);
		}
	}
	
	public boolean checkGameover() {
		for( int col = 0; col <= boardTable.getCols(); col++ ) {
			if( boardTable.getStatus(0, col) == EnumBlock.COMPLETE) {
				return true;
			}
		}
		
		return false;
	}
	
	public int doClearlines() {
		List<Integer> lines = new ArrayList<Integer>();
		for( int row = boardTable.getRows()-1; row >= 0; row--) {
			int count = 0;
			for( int col = 0; col <= boardTable.getCols(); col++ ) {
				if( boardTable.getStatus(row, col) == EnumBlock.COMPLETE ) {
					count++;
				}
			}
			
			if( count == 0 ) {
				break;
			} else if( count >= boardTable.getCols() ) {
				lines.add(row);
			}
 		}
		
		if( lines.size() > 0 ) {
			int move = 0;
			for( int row = boardTable.getRows()-1; row >= 0; row--) {
				if( lines.contains(row) )  {
					move++;
				} else if( move > 0 ) {
					for( int col = 0; col <= boardTable.getCols(); col++ ) {
						EnumBlock stat = boardTable.getStatus(row, col);
						boardTable.updateStatus(row+move, col, stat);
						boardTable.resetStatus(row, col);
					}
				}
			}
		}
		
		return lines.size();
	}
	
	
	public void doComplete() {
		if( checkComplate(active) ) {
			for( int row = 0; row <= active.getRows(); row++) {
				for( int col = 0; col <= active.getCols(); col++) {
					if( active.get(row,col) > 0 ) {
						boardTable.updateStatus(row+active.getPosy(), col+active.getPosx(), EnumBlock.COMPLETE);
					} else {
						boardTable.updateStatus(row+active.getPosy(), col+active.getPosx(), boardTable.getStatus(row+active.getPosy(), col+active.getPosx()));
					}
				}
			}
			
			active = null;
			return;
		}
	}
	
	public int calcBottom(BaseShape shape) {
		for( int row = shape.getPosy(); row <= boardTable.getRows(); row++) {
			if( checkComplate(shape, row) ) {
				return row;
			}
		}
		
		return -1;
	}
	
	public boolean checkComplate(BaseShape shape) {
		return checkComplate(shape, shape.getPosy());
	}
	
	public boolean checkComplate(BaseShape shape, int posy) {
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
	
	public boolean checkMove(BaseShape shape, EnumAction action) {
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
}
