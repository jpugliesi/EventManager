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

import constants.Constants;


public class RegisterThread extends Thread {

	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private String username;
	private String password;
	
	public RegisterThread(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public void run() {
		try {
			socket = new Socket(Constants.SERVER_IP, 6789);
			
			//setup input and output stream
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());
			

			outputStream.writeObject(Constants.CLIENT_REGISTER);
			outputStream.flush();
			outputStream.writeObject(username);
			outputStream.flush();
			outputStream.writeObject(password);
			outputStream.flush();
			
			int code = (Integer) inputStream.readObject();
			//success case
			if (code == Constants.SERVER_REGISTRATION_SUCCESS) {
				JDialog jd = new JDialog();
				jd.setSize(300,250);
				jd.setLocation(400,100);
				jd.setTitle("Registration successed!You can now Login.");
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
			}
			
			//fail cases
			else if (code == Constants.SERVER_REGISTRATION_USERNAME_FAIL){
				JDialog jd = new JDialog();
				jd.setSize(300,250);
				jd.setLocation(400,100);
				jd.setTitle("Invalid Login");
				JLabel label = new JLabel("Invalid Username, Please try again.");
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
			else if (code == Constants.SERVER_REGISTRATION_PASSWORD_FAIL){
				JDialog jd = new JDialog();
				jd.setSize(300,250);
				jd.setLocation(400,100);
				jd.setTitle("Invalid Login");
				JLabel label = new JLabel("Invalid password, Please try again.");
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
			
			
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
}

