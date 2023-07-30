package net.java.boardgame.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.java.boardgame.control.BaseBoardControl;
import net.java.boardgame.enums.EnumBroadcast;
import net.java.boardgame.event.KeyboardEvent;
import net.java.boardgame.listener.BroadcastListener;

public class MainFrame extends JFrame implements BroadcastListener {
	private static final long serialVersionUID = 5466927227094830790L;
	private BoardPanel boardPanel = new BoardPanel();
	private ControlPanel controlPanel = new ControlPanel();

	public void setBoardControl(BaseBoardControl boardControl) {
		boardControl.addListener(this);
		boardPanel.setBoardControl(boardControl);
		controlPanel.setBoardControl(boardControl);
	}

	public void setKeyboardEventHandler(KeyboardEvent event) {
		boardPanel.addKeyListener(event);
	}

	@Override
	public void broadcast(EnumBroadcast event, Object ... args) {
		if( event == EnumBroadcast.GAMEOVER ) {
			JOptionPane.showMessageDialog(null, "Game Over");
		}
	}
	
	public boolean checkClose() {
		return !isVisible();
	}
	
	public void display( boolean bMax ) {
		setSize(600, 700);
		setMaximumSize(new Dimension(600, 700));		
		
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(boardPanel.getTitle());
		setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		setResizable(false);
        
		add(boardPanel);
		add(controlPanel);
		
		boardPanel.create(303, 700);
		controlPanel.create(297, 700);
		
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
