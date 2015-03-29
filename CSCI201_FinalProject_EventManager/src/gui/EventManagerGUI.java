package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class EventManagerGUI extends JFrame {
	private LoginPanel lp;
	private MyProfilePanel mpp;
	
	
	public EventManagerGUI() {
		//set size location visibility
		setLocation(10,10);
		setSize(300,500);
		setTitle ("My Profile");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mpp = new MyProfilePanel();
		add(mpp);
	
		
		
		
	}

	public static void main(String[] args) {
		new EventManagerGUI();
	}

}
