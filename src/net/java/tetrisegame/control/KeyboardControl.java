package net.java.tetrisegame.control;

import java.awt.event.KeyAdapter;

import net.java.tetrisegame.enums.EnumAction;
import net.java.tetrisegame.enums.EnumBroadcast;
import net.java.tetrisegame.listener.BroadcastListener;

public class KeyboardControl extends KeyAdapter implements BroadcastListener {
	private BoardControl boardControl;
	
	public void setBoardControl(BoardControl boardControl) {
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
	public void broadcast(EnumBroadcast event) {
	
	}
}
