package net.java.tetrisegame.control;

import net.java.tetrisegame.enums.EnumAction;
import net.java.tetrisegame.enums.EnumBroadcast;
import net.java.tetrisegame.listener.BroadcastListener;

public class TimerControl extends Thread implements BroadcastListener {
	private int sleep = 1000;
	private BoardControl boardControl;
	
	public void setBoardControl(BoardControl boardControl) {
		this.boardControl = boardControl;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				boardControl.doAction(EnumAction.NEXT);
				sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void broadcast(EnumBroadcast event) {
		
	}
}
