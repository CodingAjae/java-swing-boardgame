package net.java.tetrisegame.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.java.tetrisegame.control.BoardControl;
import net.java.tetrisegame.control.KeyboardControl;
import net.java.tetrisegame.enums.EnumBroadcast;
import net.java.tetrisegame.listener.BroadcastListener;

public class MainFrame extends JFrame implements BroadcastListener {
	private static final long serialVersionUID = 5466927227094830790L;
	
	private BoardPanel boardPanel = new BoardPanel();

	public void setBoardControl(BoardControl boardControl) {
		boardControl.addListener(this);
		boardPanel.setBoardControl(boardControl);
	}

	public void setKeyboardEventHandler(KeyboardControl event) {
		boardPanel.addKeyListener(event);
	}

	@Override
	public void broadcast(EnumBroadcast event) {
		if( event == EnumBroadcast.GAMEOVER ) {
			JOptionPane.showMessageDialog(null, "Game Over");
		}
	}
	
	public boolean checkClose() {
		return !isVisible();
	}
	
	public void display( boolean bMax ) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
		setSize(303, 699);
		setTitle("테트리스");
		add(boardPanel);
		
		setCenterPosition();
		if( bMax ) {
			setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH );
		}
		
		setVisible(true);
	}
	
	private void setCenterPosition() {
		Dimension frameSize = getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		int left = (screenSize.width-frameSize.width)/2;
		int top = (screenSize.height-frameSize.height)/2;
		setLocation(left,top);
	}
}
