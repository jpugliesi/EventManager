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

public class ClientRSVPThread extends Thread{
	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private User u;
	private Event e;
	private boolean success = false;
	private volatile boolean finished = false; 
	
	public ClientRSVPThread(User u, Event e) {
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
			success = true;
			
		}
		
		//fail case
		else if (code == Constants.SERVER_RSVP_FAIL) {
			success = false;
		}
		
		} catch (IOException ioe) {
			success = false;
			System.out.println(ioe.getMessage());
		} catch (ClassNotFoundException e) {
			success = false;
			System.out.println(e.getMessage());
		} 
		finished = true;
	}
	
	public boolean rsvpSuccessful(){
		return success;
	}
	
	public boolean finished(){
		return finished;
	}
}
