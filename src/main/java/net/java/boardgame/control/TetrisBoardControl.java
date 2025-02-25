package net.java.boardgame.control;

import java.util.ArrayList;
import java.util.List;

import net.java.boardgame.enums.EnumAction;
import net.java.boardgame.enums.EnumBlock;
import net.java.boardgame.enums.EnumBroadcast;
import net.java.boardgame.model.BaseShape;
import net.java.boardgame.model.shape.LLShape;
import net.java.boardgame.model.shape.LineShape;
import net.java.boardgame.model.shape.RLShape;
import net.java.boardgame.model.shape.SShape;
import net.java.boardgame.model.shape.SquareShape;
import net.java.boardgame.model.shape.TShape;
import net.java.boardgame.model.shape.ZShape;

public class TetrisBoardControl extends BaseBoardControl {
    static final int COL_COUNT = 10;
    static final int ROW_COUNT = 22;
    static final int UNIT = 10;
    
	private List<Class<? extends BaseShape>> shapes = null;
	private BaseShape active = null;
	private List<Integer> clearlines = null;
	
	public TetrisBoardControl() {
		shapes = new ArrayList<Class<? extends BaseShape>>();
		shapes.add(LineShape.class);
		shapes.add(LLShape.class);
		shapes.add(RLShape.class);
		shapes.add(SquareShape.class);
		shapes.add(SShape.class);
		shapes.add(TShape.class);
		shapes.add(ZShape.class);

		setTitle("TITRISE");
		setupBoardTable(COL_COUNT, ROW_COUNT);
	}
	
	public void doAction(EnumAction action) {
		if( action == EnumAction.NEXT ) {
			if( clearlines != null && clearlines.size() > 0 ) {
				doClearLines(clearlines);
				clearlines.clear();

				int val = rand.nextInt(shapes.size());
				active = injectShape(shapes.get(val));
				broadcast(EnumBroadcast.UPDATEUI);
				broadcast(EnumBroadcast.INJECT);
				return;
			}
			
			if( active == null ) {
				int val = rand.nextInt(shapes.size());
				active = injectShape(shapes.get(val));
				broadcast(EnumBroadcast.UPDATEUI);
				broadcast(EnumBroadcast.INJECT);
				return;
			}
		}
		
		if( active != null ) {
			doShapeMove(action, active);
			
			boolean bComplate = false;
			if( action == EnumAction.BOTTOM || checkComplate(active) ) { 
				if( checkGameover() ) {
					broadcast(EnumBroadcast.GAMEOVER);
					
					for(int row = 0; row <= boardTable.getRows(); row++) {
						for( int col =0; col  <= boardTable.getCols(); col++ ) {
							boardTable.resetStatus(row, col);
						}
					}
					
					broadcast(EnumBroadcast.UPDATEUI);
					return;
				} else {
					doComplete(active);
					active = null;
					bComplate = true;
				}
			}
			
			clearlines = calcClearLines();
			if( clearlines.size() > 0 ) {
				updateScore(clearlines.size()*UNIT, clearlines.size());
				doEmphasizeLines(clearlines, 1);
				broadcast(EnumBroadcast.CLEARBLOCKS);
			} else if( bComplate ) {
				broadcast(EnumBroadcast.INJECT);
			}
			
			broadcast(EnumBroadcast.UPDATEUI);
		}
	}
	
	private void doEmphasizeLines(List<Integer> lines, int num) {
		for( int row = boardTable.getRows()-1; row >= 0; row--) {
			if( lines.contains(row) )  {
				for( int col = 0; col <= boardTable.getCols(); col++ ) {
					EnumBlock stat = boardTable.getStatus(row, col);
					boardTable.updateStatus(row, col, stat, num);
				}
			}
		}
	}
	
	private void doClearLines(List<Integer> lines) {
		int move = 0;
		for( int row = boardTable.getRows()-1; row >= 0; row--) {
			if( lines.contains(row) )  {
				move++;
			} else if( move > 0 ) {
				for( int col = 0; col <= boardTable.getCols(); col++ ) {
					EnumBlock stat = boardTable.getStatus(row, col);
					int num = boardTable.getNum(row, col);
					boardTable.updateStatus(row+move, col, stat, num);
					boardTable.resetStatus(row, col);
				}
			}
		}
	}

	private List<Integer> calcClearLines() {
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
		
		return lines;
	}
	
	protected boolean checkGameover() {
		for( int col = 0; col <= boardTable.getCols(); col++ ) {
			if( boardTable.getStatus(0, col) == EnumBlock.COMPLETE) {
				return true;
			}
			if( boardTable.getStatus(1, col) == EnumBlock.COMPLETE) {
				return true;
			}
		}
		
		return false;
	}
}
