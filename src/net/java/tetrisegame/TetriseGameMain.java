package net.java.tetrisegame;

import net.java.tetrisegame.control.BoardControl;
import net.java.tetrisegame.control.KeyboardControl;
import net.java.tetrisegame.control.TimerControl;
import net.java.tetrisegame.view.MainFrame;

public class TetriseGameMain {
    static final int COL_COUNT = 10;
    static final int ROW_COUNT = 22;

	public static void main(String[] args) throws Exception {
		BoardControl boardControl = new BoardControl();
		boardControl.setupBoardTable(COL_COUNT, ROW_COUNT);
		
		KeyboardControl kevent = new KeyboardControl();
		kevent.setBoardControl(boardControl);
		
		MainFrame frame = new MainFrame();
		frame.setBoardControl(boardControl);
		frame.setKeyboardEventHandler(kevent);
		frame.display(false);
		
		TimerControl tevent = new TimerControl();
		tevent.setBoardControl(boardControl);
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
