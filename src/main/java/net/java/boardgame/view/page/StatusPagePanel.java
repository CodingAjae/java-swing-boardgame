package net.java.boardgame.view.page;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.java.boardgame.control.BaseBoardControl;
import net.java.boardgame.enums.EnumBroadcast;
import net.java.boardgame.listener.BroadcastListener;

public class StatusPagePanel extends JPanel implements BroadcastListener {
	private static final long serialVersionUID = 3865330388551988414L;
	
	private BaseBoardControl boardControl;
	private JLabel jscore = new JLabel("SCORE: 0");
	private JLabel jcount = new JLabel("COUNT: 0");
	private Font font = new Font("Verdana", Font.PLAIN, 18);
	
	public void create(int width, int height) {
		setSize(width, height);		
		setLayout(new BorderLayout());
		setMaximumSize(new Dimension(width, height));
		
	
		jscore.setFont(font);
		jscore.setAlignmentX(JLabel.CENTER_ALIGNMENT);

		jcount.setFont(font);
		jcount.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		
		JPanel jpanel = new JPanel();
		jpanel.setLayout(new BoxLayout(jpanel, BoxLayout.Y_AXIS));
		jpanel.add(jscore);
		jpanel.add(jcount);
		add(jpanel, BorderLayout.NORTH);
	}
	
	public void setBoardControl(BaseBoardControl boardControl) {
		this.boardControl = boardControl;
		this.boardControl.addListener(this);
	}

	@Override
	public void broadcast(EnumBroadcast event, Object... args) {
		if( event == EnumBroadcast.UPDATESCORE ) {
			jscore.setText( String.format("SCORE: %,3d", args[0]) );
			jcount.setText( String.format("COUNT: %,3d", args[1]) );
		} else if( event == EnumBroadcast.GAMEOVER ) {
			jscore.setText( String.format("SCORE: %,3d", 0) );
			jcount.setText( String.format("COUNT: %,3d", 0) );
		}
	}
}
