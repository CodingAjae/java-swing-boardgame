package net.java.boardgame.view.control;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.java.boardgame.control.BaseBoardControl;
import net.java.boardgame.enums.EnumBroadcast;
import net.java.boardgame.listener.BroadcastListener;
import net.java.boardgame.view.BaseControlPanel;
import net.java.boardgame.view.page.BoardPagePanel;
import net.java.boardgame.view.page.StatusPagePanel;

public class GameControlPanel extends BaseControlPanel implements BroadcastListener {
	private static final long serialVersionUID = 7646647515385897554L;
	private BoardPagePanel boardPanel = new BoardPagePanel();
	private StatusPagePanel controlPanel = new StatusPagePanel();

	public void setBoardControl(BaseBoardControl boardControl) {
		boardControl.addListener(this);
		boardPanel.setBoardControl(boardControl);
		controlPanel.setBoardControl(boardControl);
	}


	@Override
	public void broadcast(EnumBroadcast event, Object... args) {
		if( event == EnumBroadcast.GAMEOVER ) {
			JOptionPane.showMessageDialog(null, "Game Over");
		}		
	}

	@Override
	public void create(int width, int height) {
		setSize(width, height);		
		setLayout(new BorderLayout());
		setMaximumSize(new Dimension(width, height));
		
		boardPanel.create(303, 700);
		controlPanel.create(297, 700);
		
        JPanel layout = new JPanel();
        layout.setLayout(new BoxLayout(layout, BoxLayout.X_AXIS));
        layout.add(boardPanel);
        layout.add(controlPanel);
		add(layout);
		
		
	}

	
	
}
