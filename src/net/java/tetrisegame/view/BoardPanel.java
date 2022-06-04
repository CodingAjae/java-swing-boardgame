package net.java.tetrisegame.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import net.java.tetrisegame.control.BoardControl;
import net.java.tetrisegame.enums.EnumBlock;
import net.java.tetrisegame.enums.EnumBroadcast;
import net.java.tetrisegame.listener.BroadcastListener;
import net.java.tetrisegame.model.BoardBlock;
import net.java.tetrisegame.model.Rectangle;

public class BoardPanel extends JPanel implements BroadcastListener {
	private static final long serialVersionUID = 3553978786320630492L;
	
	private BoardControl boardControl;
	
	public void setBoardControl(BoardControl boardControl) {
		this.boardControl = boardControl;
		this.boardControl.addListener(this);
	}

	public BoardPanel() {
        setFocusable(true);
		setBackground(Color.DARK_GRAY);
	}
		
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		boardControl.getBoardTable().setSize(getSize());
		for( BoardBlock boardblock : boardControl.getBoardTable().extract() ) {
			_draw_rectangle(g, boardblock.getOuter(), null);
			if( boardblock.getStat() != EnumBlock.NONE ) {
				_draw_rectangle(g, boardblock.getInner(), boardblock.getStat().getColor());
			}
			
		}
	}
	
	private void _draw_rectangle(Graphics g, Rectangle rect, Color color) {
		if( color != null ) {
			g.setColor(color);
			g.fill3DRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), true);
		} else  {
			g.setColor(Color.BLACK);
			g.drawRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
		}
	}
	
	@Override
	public void broadcast(EnumBroadcast event) {
		if( event == EnumBroadcast.UPDATEUI ) {
			updateUI();
		}
	}

}
