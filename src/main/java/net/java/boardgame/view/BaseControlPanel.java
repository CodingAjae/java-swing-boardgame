package net.java.boardgame.view;

import javax.swing.JPanel;

public abstract class BaseControlPanel extends JPanel {
	private static final long serialVersionUID = 186472168032523972L;

	public abstract void create(int width, int height);
	
}
