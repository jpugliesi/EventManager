package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import constants.Constants;


public class LoginThread extends Thread{

	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private String username;
	private String password;
	
	
	public LoginThread(String username, String password) {
		this.username = username;
		this.password = password;		
	}
	
	public void run() {
		try {
		socket = new Socket(Constants.SERVER_IP, 6789);
		
		//setup input and output stream
		outputStream = new ObjectOutputStream(socket.getOutputStream());
		inputStream = new ObjectInputStream(socket.getInputStream());
		

		outputStream.writeObject(Constants.CLIENT_LOGIN);
		outputStream.flush();
		outputStream.writeObject(username);
		outputStream.flush();
		outputStream.writeObject(password);
		outputStream.flush();
		
		} catch (IOException ioe) {
			
		}
		
		
		
	}
}
