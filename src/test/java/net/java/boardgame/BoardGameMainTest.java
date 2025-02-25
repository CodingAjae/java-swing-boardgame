package net.java.boardgame;

import net.java.boardgame.control.BaseBoardControl;
import net.java.boardgame.control.HexaBoardController;
import net.java.boardgame.control.TetrisBoardControl;
import net.java.boardgame.model.BaseShape;
import net.java.boardgame.view.control.GameControlPanel;

import org.junit.Test;

public class BoardGameMainTest extends BoardGameMain {

	@Test
	public void testHexaGame() throws Exception {
		BaseBoardControl board = new HexaBoardController();
	
		BaseShape shape = new BaseShape();
		shape.set(0,0,0,0,0,0,0,0,0,0);
		shape.set(0,0,0,0,3,5,2,4,0,0);
		shape.set(0,0,0,0,3,2,5,4,0,0);
		board.setupBoardShape(shape);

		executeBoardGame(board);
	}
	
	@Test
	public void testTetrisGame() throws Exception {
		BaseBoardControl board = new TetrisBoardControl();
		
		executeBoardGame(board);
	}
	
	
	@SuppressWarnings("deprecation")
	public void executeBoardGame(BaseBoardControl boardControl) throws Exception {
		kevent.setBoardControl(boardControl);
		tevent.setBoardControl(boardControl);
		
		GameControlPanel controlPanel = new GameControlPanel();
		controlPanel.setBoardControl(boardControl);
		
		frame.addKeyListener(kevent);
		frame.display(controlPanel);
		tevent.start();
		
		while(true) {
			Thread.sleep(100);
			if( frame.checkClose() ) {
				tevent.stop();
				System.exit(0);
			}
		}
	}	
}
