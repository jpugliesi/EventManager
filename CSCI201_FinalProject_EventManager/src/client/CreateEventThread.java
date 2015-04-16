package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import main.Event;
import constants.Constants;

public class CreateEventThread extends Thread {

	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private Event e;
	
	public CreateEventThread() {
	
	}
	
	public void run() {
		try {
			socket = new Socket(Constants.SERVER_IP, 6789);
		
			//setup input and output stream
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());

			outputStream.writeObject(Constants.CLIENT_CREATE_EVENT);
			outputStream.flush();
			
			e = (Event) inputStream.readObject();
			
			//TODO
			//add Event to the database or update 
			
			
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
}
