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


public class ClientLoginThread extends Thread{

	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private String username;
	private String password;
	private User u;
	private boolean success= false;
	private ReentrantLock lock = new ReentrantLock();
	private Condition signal = lock.newCondition();
	private int received = 0;
	private volatile boolean finished = false;
	private boolean isAdmin;
	private int code;
	
	
	public ClientLoginThread(String username, String password, boolean isAdmin) {
		this.username = username;
		this.password = password;		
		this.isAdmin = isAdmin;
	}
	
	public void run() {
		synchronized(lock){
			
		
		try {
		socket = new Socket(Constants.SERVER_IP, 6789);
		System.out.println("connected");
		
		//setup input and output stream
		outputStream = new ObjectOutputStream(socket.getOutputStream());
		outputStream.flush();
		
		outputStream.writeObject(Constants.CLIENT_LOGIN);
		outputStream.flush();
		outputStream.writeObject(username);
		outputStream.flush();
		outputStream.writeObject(password);
		outputStream.flush();
		outputStream.writeObject(isAdmin);
		outputStream.flush();
		
		
		
		inputStream = new ObjectInputStream(socket.getInputStream());
			
		code = (Integer)inputStream.readObject();
		//received User object is not null, log in success
		//System.out.println("received code " + code);
		if (code == Constants.SERVER_LOGIN_SUCCESS) {
			success=true;
			u = (User) inputStream.readObject();
			//TODO
			//Move to the User's Event Page , pass User u
			if(isAdmin){
				constants.Environment.currentAdmin = u;
			}
			else{
				constants.Environment.currentUser = u;
			}
			u.setProfilePicture();
			received = 1;
			finished = true;

		}
		
		//received User object have problem, log in fail cases
		else if (code == Constants.SERVER_LOGIN_INCORRECT_USER) {
			success=true;
			
			received = 1;
			finished = true;

		}
		else if (code == Constants.SERVER_LOGIN_INCORRECT_PASSWORD) {
			received = 1;
			finished = true;


		}
		else if (code == Constants.SERVER_LOGIN_INCORRECT_IP) {
			
			received = 1;
			finished = true;

		}
	
		
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} finally{

		}
		}
		//System.out.println("end of run()");
		finished = true;
	}
	
	public  User getLoggedInUser(){
		synchronized(lock){
			return u;

		}
		
	}
	
	public boolean finished(){
		return finished;
	}
	
	public synchronized boolean loginSuccessful(){
		
		//System.out.println("I am about to return and received is " + received);
		return success;
	}
	
	public int getCode(){
		return code;
	}
}
