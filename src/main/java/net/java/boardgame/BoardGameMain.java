package net.java.boardgame;

import net.java.boardgame.control.BaseBoardControl;
import net.java.boardgame.control.HexaBoardController;
import net.java.boardgame.control.TetrisBoardControl;
import net.java.boardgame.enums.EnumBroadcast;
import net.java.boardgame.event.KeyboardEvent;
import net.java.boardgame.event.TimerEvent;
import net.java.boardgame.listener.BroadcastListener;
import net.java.boardgame.view.MainFrame;
import net.java.boardgame.view.control.GameControlPanel;
import net.java.boardgame.view.control.SelectControlPanel;

public class BoardGameMain implements BroadcastListener {
	protected MainFrame frame = null;
	protected TimerEvent tevent = null;	
	protected KeyboardEvent kevent = null;
	
	public static void main(String[] args) throws Exception {
		BoardGameMain main = new BoardGameMain();
		main.executeSelectGame();
	}
	
	public BoardGameMain() {
		frame = new MainFrame();
		kevent = new KeyboardEvent();
		tevent = new TimerEvent();
	}
	
	@Override
	public void broadcast(EnumBroadcast event, Object... args) {
		if( event == EnumBroadcast.SELECT && args.length >= 1) {
			String select = String.valueOf(args[0]);
	
			BaseBoardControl boardControl = null;
			if( select.equals("tetris")) {
				boardControl = new TetrisBoardControl();
			} else if( select.equals("hexa")) {
				boardControl = new HexaBoardController();
			}
			
			if( boardControl != null ) {
				kevent.setBoardControl(boardControl);
				tevent.setBoardControl(boardControl);
				
				GameControlPanel controlPanel = new GameControlPanel();
				controlPanel.setBoardControl(boardControl);
				frame.changePanel(controlPanel);
				frame.addKeyListener(kevent);
				
				
				tevent.start();
			}
			
		} else if ( event == EnumBroadcast.EXIT && frame != null ) {
			tevent.stop();
			System.exit(0);
		}
	}
	
	public void executeSelectGame() throws Exception  {
		SelectControlPanel panel = new SelectControlPanel();
		panel.setListener(this);
		frame.display(panel);
		
		while(true) {
			Thread.sleep(100);
			if( frame.checkClose() ) {
				tevent.stop();
				System.exit(0);
			}
		}
	}

}
