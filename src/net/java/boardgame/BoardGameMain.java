package net.java.boardgame;

import net.java.boardgame.control.BaseBoardControl;
import net.java.boardgame.control.HexaBoardController;
import net.java.boardgame.control.TitriseBoardControl;
import net.java.boardgame.event.KeyboardEvent;
import net.java.boardgame.event.TimerEvent;
import net.java.boardgame.model.BaseShape;
import net.java.boardgame.view.MainFrame;

public class BoardGameMain {
	public static void main(String[] args) throws Exception {
		BoardGameMain main = new BoardGameMain();
		
		BaseBoardControl board =  main.createBoardGame(HexaBoardController.class);
		
		BaseShape shape = new BaseShape();
		shape.set(0,0,0,0,0,0,0,0,0,0);
		shape.set(0,0,0,0,3,5,2,4,0,0);
		shape.set(0,0,0,0,3,2,5,4,0,0);
		board.setupBoardShape(shape);
		
		main.executeBoardGame(board, true);
	}
	
	private MainFrame frame = null;
	private TimerEvent tevent = null;	
	private KeyboardEvent kevent = null;
	
	public BoardGameMain() {
		frame = new MainFrame();
		kevent = new KeyboardEvent();
		tevent = new TimerEvent();
		frame.setKeyboardEventHandler(kevent);
	}
	
	public void execute(int code) throws Exception {
		BaseBoardControl boardControl = null;
		if( code == 1 ) {
			boardControl = createBoardGame(TitriseBoardControl.class);
		} else if( code == 2 ) {
			boardControl = createBoardGame(HexaBoardController.class);
		}
		
		executeBoardGame(boardControl, true);
	}
	
	public BaseBoardControl createBoardGame(Class<? extends BaseBoardControl> type) throws Exception {
		return type.newInstance();
	}
	
	@SuppressWarnings("deprecation")
	public void executeBoardGame(BaseBoardControl boardControl, boolean timer) throws Exception {
		kevent.setBoardControl(boardControl);
		frame.setBoardControl(boardControl);
		tevent.setBoardControl(boardControl);
		frame.display(false);
		
		if( timer ) {
			tevent.start();
		}
		
		while(true) {
			Thread.sleep(100);
			if( frame.checkClose() ) {
				if( timer ) {
					tevent.stop();
				}
				
				System.exit(0);
			}
		}
	}
}
