package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import main.User;
import constants.Constants;


public class ClientRegisterThread extends Thread {

	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private User user;
	private User registeredUser = null;
	private boolean success = false;
	private volatile boolean finished = false;
	private int code;
	
	public ClientRegisterThread(User user) {
		this.user = user;
	}
	
	public void run() {
		try {
			socket = new Socket(Constants.SERVER_IP, 6789);
			
			//setup input and output stream
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());
			

			outputStream.writeObject(Constants.CLIENT_REGISTER);
			outputStream.flush();
			outputStream.writeObject(user);
			outputStream.flush();
			
			code = (Integer) inputStream.readObject();
			//success case
			if (code == Constants.SERVER_REGISTRATION_SUCCESS) {
				
				success = true;
				registeredUser = (User) inputStream.readObject();
				
			}
			
			//fail cases
			else if (code == Constants.SERVER_REGISTRATION_USERNAME_FAIL){
				success = false;
				
			}
			else if (code == Constants.SERVER_REGISTRATION_PASSWORD_FAIL){
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
	
	public boolean registrationSuccessful(){
		return success;
	}
	
	public User getRegisteredUser(){
		return registeredUser;
	}
	
	public boolean finished(){
		return finished;
	}
	
	public int getErrorCode(){
		return code;
	}
	
}

