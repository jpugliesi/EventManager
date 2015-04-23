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
	
	
	
	public ClientLoginThread(String username, String password) {
		this.username = username;
		this.password = password;		
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
		
		
		
		inputStream = new ObjectInputStream(socket.getInputStream());
			
		
		int code = (Integer)inputStream.readObject();
		//received User object is not null, log in success
		System.out.println("received code " + code);
		if (code == Constants.SERVER_LOGIN_SUCCESS) {
			success=true;
			u = (User) inputStream.readObject();
			//TODO
			//Move to the User's Event Page , pass User u
			constants.Environment.currentUser = u;
			received = 1;
			finished = true;
			System.out.println("finished set to true");


		}
		
		//received User object have problem, log in fail cases
		else if (code == Constants.SERVER_LOGIN_INCORRECT_USER) {
			success=true;
			JDialog jd = new JDialog();
			jd.setSize(300,250);
			jd.setLocation(400,100);
			jd.setTitle("Invalid Login");
			JLabel label = new JLabel("Incorrect Username, Please try again.");
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
			received = 1;
			finished = true;


		}
		else if (code == Constants.SERVER_LOGIN_INCORRECT_PASSWORD) {
			JDialog jd = new JDialog();
			jd.setSize(300,250);
			jd.setLocation(400,100);
			jd.setTitle("Invalid Login");
			JLabel label = new JLabel("Incorrect Password, Please try again.");
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
			received = 1;
			finished = true;


		}
		else if (code == Constants.SERVER_LOGIN_INCORRECT_IP) {
			JDialog jd = new JDialog();
			jd.setSize(300,250);
			jd.setLocation(400,100);
			jd.setTitle("Invalid Login");
			JLabel label = new JLabel("Incorrect IP_Address, Please try again.");
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
		System.out.println("end of run()");
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
		
		System.out.println("I am about to return and received is " + received);
		return success;
	}
}
