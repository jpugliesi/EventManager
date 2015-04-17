package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import main.Event;
import main.User;
import constants.Constants;

public class RSVPThread extends Thread{
	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private User u;
	private Event e;
	
	public RSVPThread(User u, Event e) {
		this.u = u;
		this.e = e;
	}
	
	public void run() {
		try {
		socket = new Socket(Constants.SERVER_IP, 6789);
		
		//setup input and output stream
		outputStream = new ObjectOutputStream(socket.getOutputStream());
		inputStream = new ObjectInputStream(socket.getInputStream());

		outputStream.writeObject(Constants.CLIENT_RSVP);
		outputStream.flush();
		
		outputStream.writeObject(u);
		outputStream.flush();
		
		outputStream.writeObject(e);
		outputStream.flush();
		
		int code = (Integer) inputStream.readObject();
		//success case
		if (code == Constants.SERVER_RSVP_SUCCESS) {
			JDialog jd = new JDialog();
			jd.setSize(300,250);
			jd.setLocation(400,100);
			jd.setTitle("RSVP");
			JLabel label = new JLabel("Success RSVP.Thank you!");
			JButton button = new JButton("Got it!");
			button.addActionListener(new ActionListener() {
				public void actionPerformed (ActionEvent ae) {
					jd.dispose();
				}
			});
			jd.add(label);	
			jd.add(button, BorderLayout.SOUTH);
			jd.setModal(true);
			jd.setVisible(true);
		}
		
		//fail case
		else if (code == Constants.SERVER_RSVP_FAIL) {
			JDialog jd = new JDialog();
			jd.setSize(300,250);
			jd.setLocation(400,100);
			jd.setTitle("RSVP");
			JLabel label = new JLabel("Error. Please try again.");
			JButton button = new JButton("OK");
			button.addActionListener(new ActionListener() {
				public void actionPerformed (ActionEvent ae) {
					jd.dispose();
				}
			});
			jd.add(label);	
			jd.add(button, BorderLayout.SOUTH);
			jd.setModal(true);
			jd.setVisible(true);
		}
		
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} 
	}
}
