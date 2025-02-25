package net.java.boardgame.event;

import net.java.boardgame.control.BaseBoardControl;
import net.java.boardgame.enums.EnumAction;
import net.java.boardgame.enums.EnumBroadcast;
import net.java.boardgame.listener.BroadcastListener;

public class TimerEvent extends Thread implements BroadcastListener {
	private BaseBoardControl boardControl;
	
	private int count = 0;
	private int unit = 100;
	
	public void setBoardControl(BaseBoardControl boardControl) {
		boardControl.addListener(this);
		this.boardControl = boardControl;
	}
	
	@Override
	public void broadcast(EnumBroadcast event, Object ... args) {
		if( event == EnumBroadcast.CLEARBLOCKS ) {
			timer(0,50);
		} else if( event == EnumBroadcast.INJECT ) {
			timer(0,10);
		}
	}
	
		
	@Override
	public void run() {
		count = 1000;
		while(true){
			try {
				if( count > 0 && count > unit ) {
					timer(0, 100);
					boardControl.doAction(EnumAction.NEXT);
				}
			} finally {
				tsleep(10);
				count ++;
			}
		} 
	}
	
	private void timer(int count, int unit) {
		this.count = count;
		this.unit = unit;
	}
	
	private void tsleep(long millis) {
		try {
			sleep(millis);
		} catch (InterruptedException e) { }
	}
}
