package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import main.Event;
import constants.Constants;

public class EventFeedThread extends Thread{
	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private Vector<Event> v;
	
	
	public EventFeedThread() {
		try {
			socket = new Socket(Constants.SERVER_IP, 6789);
			
			//setup input and output stream
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());

			outputStream.writeObject(Constants.CLIENT_GET_EVENT_FEED);
			outputStream.flush();
			
			v = (Vector<Event>) inputStream.readObject();
				
			
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
}
