package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import main.Event;
import constants.Constants;

public class ClientGetEventThread extends Thread{
	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private Integer id;
	private Event e = null;
	
	public ClientGetEventThread(Event e) {
		this.id = e.getID();
	}
	
	public void run() {
		try {
			socket = new Socket(Constants.SERVER_IP, 6789);
			
			//setup input and output stream
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());

			outputStream.writeObject(Constants.CLIENT_GET_EVENT);
			outputStream.flush();
			
			outputStream.writeObject(id);
			outputStream.flush();
			
			int code = (Integer) inputStream.readObject();
			//success case
			if (code == Constants.SERVER_GET_EVENT_SUCCESS) {
				//System.out.println("Success get detail event");
				e = (Event) inputStream.readObject();
				//TODO
				//direct GUI to the detailed page 
			}
			//fail case
			else if (code == Constants.SERVER_GET_EVENT_FAIL) {
				//System.out.println("Fail get detail event");
			}
			
			
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public Event getEvent(){
		return e;
	}
}
