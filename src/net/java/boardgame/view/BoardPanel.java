package net.java.boardgame.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import net.java.boardgame.control.BaseBoardControl;
import net.java.boardgame.enums.EnumBlock;
import net.java.boardgame.enums.EnumBroadcast;
import net.java.boardgame.enums.EnumColor;
import net.java.boardgame.listener.BroadcastListener;
import net.java.boardgame.model.BoardBlock;
import net.java.boardgame.model.Rectangle;

public class BoardPanel extends JPanel implements BroadcastListener {
	private static final long serialVersionUID = 3553978786320630492L;
	
	private BaseBoardControl boardControl;
	
	public void setBoardControl(BaseBoardControl boardControl) {
		this.boardControl = boardControl;
		this.boardControl.addListener(this);
	}

	public void create(int width, int height) {
		setMaximumSize(new Dimension(width, height));
		setSize(width, height);
        setFocusable(true);
	}
	
	public String getTitle() {
		return boardControl.getTitle();
	}
	
	@Override
	public void broadcast(EnumBroadcast event, Object ... args) {
		if( event == EnumBroadcast.UPDATEUI ) {
			updateUI();
		}
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		boardControl.getBoardTable().setSize(getSize());
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0,0,getSize().width,getSize().height);
		
		for( BoardBlock boardblock : boardControl.getBoardTable().extract() ) {
			_draw_rectangle(g, boardblock.getOuter(), null);
			if( boardblock.getStat() != EnumBlock.NONE ) {
				Color color = EnumColor.search(boardblock.getNum()).getColor();
				_draw_rectangle(g, boardblock.getInner(), color);
			}
		}
	}
	
	private void _draw_rectangle(Graphics g, Rectangle rect, Color color) {
		if( color != null ) {
			g.setColor(color);
			g.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
		} else  {
			g.setColor(Color.BLACK);
			g.drawRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
		}
	}
}
