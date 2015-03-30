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
<<<<<<< HEAD
		mpp = new MyProfilePanel();
		add(mpp);
	
		
		
=======
		lp = new LoginPanel();
		lp.setVisible(true);
		add(lp, BorderLayout.CENTER);
>>>>>>> a34738dad3a9a499eeeb6a1656a0a9669b986dc3
		
	}

	public static void main(String[] args) {
		new EventManagerGUI();
	}

}
