package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import main.Event;
import constants.Constants;

public class ClientCreateEventThread extends Thread {

	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private Event e;
	
	public ClientCreateEventThread(Event e) {
		this.e = e;
	}
	
	public void run() {
		try {
			socket = new Socket(Constants.SERVER_IP, 6789);
		
			//setup input and output stream
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());

			outputStream.writeObject(Constants.CLIENT_CREATE_EVENT);
			outputStream.flush();
			
			outputStream.writeObject(e);
			outputStream.flush();
			
			int code = (Integer) inputStream.readObject();
			
			if (code == Constants.SERVER_CREATE_EVENT_SUCCESS) {
				System.out.println("Success created event");
			}
			else if (code == Constants.SERVER_CREATE_EVENT_FAIL) {
				System.out.println("Fail created event");
			}			
			
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
}
