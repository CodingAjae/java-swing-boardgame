package net.java.boardgame.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 5466927227094830790L;
    private BaseControlPanel currentPanel; // ���� �г� ����

	public boolean checkClose() {
		return !isVisible();
	}
	
    public void changePanel(BaseControlPanel panel) {
        if (currentPanel != null) {
            getContentPane().remove(currentPanel); // ���� �г� ����
        }
        
        currentPanel = panel;
        panel.setPreferredSize(new Dimension(600, 700));
        panel.create(600, 700);

        getContentPane().add(panel); // ���ο� �г� �߰�
        getContentPane().revalidate(); // ���̾ƿ� ����
        getContentPane().repaint(); // ȭ�� �ٽ� �׸���
    }
		
	public void display( BaseControlPanel panel ) {
		setSize(600, 700);
		setMaximumSize(new Dimension(600, 700));		
		setFocusable(true);;
		
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	setTitle("GAME");

		setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		setResizable(false);

		panel.create(600, 700);
        currentPanel = panel;
		add(panel);
		
		setCenterPosition();
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
