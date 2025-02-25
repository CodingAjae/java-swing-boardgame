package net.java.boardgame.event;

import java.awt.event.KeyAdapter;

import net.java.boardgame.control.BaseBoardControl;
import net.java.boardgame.enums.EnumAction;
import net.java.boardgame.enums.EnumBroadcast;
import net.java.boardgame.listener.BroadcastListener;

public class KeyboardEvent extends KeyAdapter implements BroadcastListener {
	private BaseBoardControl boardControl;
	
	public void setBoardControl(BaseBoardControl boardControl) {
		this.boardControl = boardControl;
		this.boardControl.addListener(this);
	}

	@Override
	public void keyPressed(java.awt.event.KeyEvent evt) {
	    if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_LEFT) {
	    	boardControl.doAction(EnumAction.LEFT);
	    } else if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_RIGHT) {
	    	boardControl.doAction(EnumAction.RIGHT);
	    } else if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_UP) {
	    	boardControl.doAction(EnumAction.ROTATE);
	    } else if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN) {
	    	boardControl.doAction(EnumAction.DOWN);
	    } else if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_SPACE) {
	    	boardControl.doAction(EnumAction.BOTTOM);
	    }
	}
	
	@Override
	public void broadcast(EnumBroadcast event, Object ... args) {
	
	}
}
