package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class EventManagerGUI extends JFrame {
	private LoginPanel lp;
	
	
	public EventManagerGUI() {
		//set size location visibility
		setLocation(10,10);
		setSize(500,600);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lp = new LoginPanel();
		lp.setVisible(true);
		add(lp, BorderLayout.CENTER);
	
		
		
		
	}

	public static void main(String[] args) {
		new EventManagerGUI();
	}

}
