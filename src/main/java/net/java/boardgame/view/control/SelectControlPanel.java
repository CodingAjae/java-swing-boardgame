package net.java.boardgame.view.control;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import net.java.boardgame.enums.EnumBroadcast;
import net.java.boardgame.listener.BroadcastListener;
import net.java.boardgame.view.BaseControlPanel;

public class SelectControlPanel extends BaseControlPanel implements ActionListener{
	private static final long serialVersionUID = -6502840284114035683L;
	
	private BroadcastListener listener;
    private JButton tetrisButton;
    private JButton hexaButton;
    private JButton cancelButton;
    
	public void setListener(BroadcastListener listener) {
		this.listener = listener;
	}

    @Override
	public void create(int width, int height) {
		setSize(width, height);		
		setLayout(new BorderLayout());
		setMaximumSize(new Dimension(width, height));

        // 버튼 패널 생성
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        // 테트리스 버튼
        tetrisButton = new JButton("테트리스");
        tetrisButton.setActionCommand("tetris");
        tetrisButton.setAlignmentX(CENTER_ALIGNMENT);
        tetrisButton.addActionListener(this);

        // 핵사 게임 버튼
        hexaButton = new JButton("핵사 게임");
        hexaButton.setActionCommand("hexa");
        hexaButton.setAlignmentX(CENTER_ALIGNMENT);
        hexaButton.addActionListener(this);

        // 취소 버튼
        cancelButton = new JButton("취소");
        cancelButton.setActionCommand("cancel");
        cancelButton.setAlignmentX(CENTER_ALIGNMENT);
        cancelButton.addActionListener(this);

        // 버튼을 세로 정렬하면서 간격 추가
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(tetrisButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(hexaButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(cancelButton);
        buttonPanel.add(Box.createVerticalStrut(20));

        add(buttonPanel, BorderLayout.CENTER);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if( listener != null ) {
			if( e.getActionCommand().equals("tetris") ) {
				listener.broadcast(EnumBroadcast.SELECT, e.getActionCommand());
			} else if( e.getActionCommand().equals("hexa") ) {
				listener.broadcast(EnumBroadcast.SELECT, e.getActionCommand());
			} else if( e.getActionCommand().equals("cancel") ) {
				listener.broadcast(EnumBroadcast.EXIT);
			}
		}
	}
}
