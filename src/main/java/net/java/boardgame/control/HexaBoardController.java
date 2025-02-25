package net.java.boardgame.control;

import java.util.ArrayList;
import java.util.List;

import net.java.boardgame.enums.EnumAction;
import net.java.boardgame.enums.EnumBlock;
import net.java.boardgame.enums.EnumBroadcast;
import net.java.boardgame.model.BaseShape;
import net.java.boardgame.model.HexaBlockChecker;
import net.java.boardgame.model.BoardBlock;
import net.java.boardgame.model.shape.HexaShape;

public class HexaBoardController extends BaseBoardControl {
    static final int COL_COUNT = 10;
    static final int ROW_COUNT = 22;
    static final int UNIT = 10;

    private BaseShape active = null;
	private HexaBlockChecker checker = null;
    
    public HexaBoardController() {
		setTitle("HEXAGAME");
		setupBoardTable(COL_COUNT, ROW_COUNT);
	}
    
	@Override
	public void doAction(EnumAction action) {
		if( action == EnumAction.NEXT ) {
			if( checker != null ) {
				checker = doClearBlocks(checker);
				if( checker.hasChecker() ) {
					if(checkClearBlocks(checker)) {
						doEmphasizeBlocks(checker, 1);
						broadcast(EnumBroadcast.CLEARBLOCKS);
					} else {
						checker = null;
						broadcast(EnumBroadcast.INJECT);
					}
				} else {
					checker = null;
					broadcast(EnumBroadcast.INJECT);
				}
				
				return;
			} else if( active == null ) {
				active = injectShape(HexaShape.class);
				broadcast(EnumBroadcast.UPDATEUI);
				broadcast(EnumBroadcast.INJECT);
				return;
			}
		}

		if( active != null ) {
			boolean bComplate = false;
			HexaBlockChecker current = new HexaBlockChecker();
			
			doShapeMove(action, active);
			if( action == EnumAction.BOTTOM || checkComplate(active) ) {
				if( checkGameover() ) {
					broadcast(EnumBroadcast.GAMEOVER);
					
					for(int row = 0; row <= boardTable.getRows(); row++) {
						for( int col = 0; col <= boardTable.getCols(); col++ ) {
							boardTable.resetStatus(row, col);
						}
					}
					
					broadcast(EnumBroadcast.UPDATEUI);
					return;
				} else {
					doComplete(active);
 					current.putCheckShape(active);
					active = null;
					bComplate = true;
				}
			}
			
			if( bComplate ) {
				if(checkClearBlocks(current)) {
					doEmphasizeBlocks(current, 1);
					checker = current;
					broadcast(EnumBroadcast.CLEARBLOCKS);
				} else {
					broadcast(EnumBroadcast.INJECT);
				}
			} 
			
			broadcast(EnumBroadcast.UPDATEUI);
		}
	}
	
	private boolean checkGameover() {
		int col = 4;
		if( boardTable.getStatus(0, col) == EnumBlock.COMPLETE) {
			return true;
		}
		if( boardTable.getStatus(1, col) == EnumBlock.COMPLETE) {
			return true;
		}
		if( boardTable.getStatus(2, col) == EnumBlock.COMPLETE) {
			return true;
		}
		return false;
	}
	
	private void doEmphasizeBlocks(HexaBlockChecker checker, int num) {
		int clearBlocks = 0;
		for(BoardBlock block: checker.extractClears() ) {
			EnumBlock stat = boardTable.getStatus(block.getRow(), block.getCol());
			boardTable.updateStatus(block.getRow(), block.getCol(), stat, num);
			clearBlocks++;
		}
		
		
		updateScore(clearBlocks*UNIT, clearBlocks);
	}

	public HexaBlockChecker doClearBlocks( HexaBlockChecker checker )  {
		List<Integer> cols = new ArrayList<Integer>();
		for(BoardBlock block: checker.extractClears() ) {
			boardTable.updateStatus(block.getRow(), block.getCol(), EnumBlock.NONE, 0);
			if( !cols.contains(block.getCol()) ) {
				cols.add(block.getCol());
			}
		}

		broadcast(EnumBroadcast.UPDATEUI);
		
		HexaBlockChecker newChecker = new HexaBlockChecker();
		for( int col : cols ) {
			int moves = 0;
			
			List<BoardBlock> blocks = new ArrayList<BoardBlock>();
			for( int row = 0; row < boardTable.getRows(); row++ ) {
				blocks.add(boardTable.get(row, col));
			}
			
			for( int row = boardTable.getRows(); row > 0; row-- ) {
				BoardBlock block = blocks.get(row - 1);
				if( moves > 0 ) {
					boardTable.updateStatus(row, col, EnumBlock.NONE, 0);
					boardTable.updateStatus(row + moves - 1, col, block.getStat(), block.getNum());
					if(boardTable.getNum(row, col) > 0 ) {
						newChecker.putCheckBlock(boardTable.get(row, col));
					}
				}
				
				if( block.getStat() == EnumBlock.NONE ) {
					moves++;
				} 
			}
		}
		
		broadcast(EnumBroadcast.UPDATEUI);
		
		return newChecker;
	}
	
	public boolean checkClearBlocks( HexaBlockChecker checker )  {
		System.out.println("== check =========");
		List<BoardBlock> checks = null;
		for( BoardBlock block: checker.extractChecks() ) {
			System.out.println("--");
			int num = block.getNum();
			
			checks = new ArrayList<BoardBlock>();
			try {/* (-)规氢    */
				checks.add(block);
				for( int col = block.getCol()-1; col >= 0; col-- ) {
					if( boardTable.getNum(block.getRow(), col) == num) {
						System.out.println("add(a)");
						checks.add(boardTable.get(block.getRow(), col));
					} else {
						break;
					}
				}
				for( int col = block.getCol()+1; col < boardTable.getCols(); col++ ) {
					if( boardTable.getNum(block.getRow(), col) == num) {
						System.out.println("add(a)");
						checks.add(boardTable.get(block.getRow(), col));
					} else {
						break;
					}
				}
				if( checks.size() >= 3) {
					System.out.println("clear(a)|size="+checks.size());
					checker.putClearBlocks(checks);
				}
			} finally {
				checks.clear();
				checks = null;
			}
			
			checks = new ArrayList<BoardBlock>();
			try {/* (|)规氢    */
				checks.add(block);
				for( int row = block.getRow()-1; row >=0; row-- ) {
					if( boardTable.getNum(row, block.getCol()) == num) {
						System.out.println("add(b)");
						checks.add(boardTable.get(row, block.getCol()));
					} else {
						break;
					}
				}
				for( int row = block.getRow()+1; row < boardTable.getRows(); row++ ) {
					if( boardTable.getNum(row, block.getCol()) == num) {
						System.out.println("add(b)");
						checks.add(boardTable.get(row, block.getCol()));
					} else {
						break;
					}
				}
				if( checks.size() >= 3) {
					System.out.println("clear(b)|size="+checks.size());
					checker.putClearBlocks(checks);
				}
			} finally {
				checks.clear();
				checks = null;
			}
			
			checks = new ArrayList<BoardBlock>();
			try {/* (/)规氢    */
				checks.add(block);
				
				int row = block.getRow()+1;
				for( int col = block.getCol()-1; col >= 0; col-- ) {
					if( boardTable.getNum(row, col) == num) {
						System.out.println("add(c)");
						checks.add(boardTable.get(row, col));
					} else {
						break;
					}
					
					row++;
				}
				
				row = block.getRow()-1;
				for( int col = block.getCol()+1; col < boardTable.getCols(); col++) {
					if( boardTable.getNum(row, col) == num) {
						System.out.println("add(c)");
						checks.add(boardTable.get(row, col));
					} else {
						break;
					}
					
					row--;
				}
				if( checks.size() >= 3) {
					System.out.println("clear(c)|size="+checks.size());
					checker.putClearBlocks(checks);
				}
			} finally {
				checks.clear();
				checks = null;
			}

			checks = new ArrayList<BoardBlock>();
			try {/* (\)规氢    */
				checks.add(block);
				
				int row = block.getRow()-1;
				for( int col = block.getCol()-1; col >= 0; col-- ) {
					if( boardTable.getNum(row, col) == num) {
						System.out.println("add(d)");
						checks.add(boardTable.get(row, col));
					} else {
						break;
					}
					
					row--;
				}
				
				row = block.getRow()+1;
				for( int col = block.getCol()+1; col < boardTable.getCols(); col++) {
					if( boardTable.getNum(row, col) == num) {
						System.out.println("add(c)");
						checks.add(boardTable.get(row, col));
					} else {
						break;
					}
					
					row++;
				}
				if( checks.size() >= 3) {
					System.out.println("clear(d)|size="+checks.size());
					checker.putClearBlocks(checks);
				}
			} finally {
				checks.clear();
				checks = null;
			}
		}
		
		return checker.hasClears();
	}	
}
