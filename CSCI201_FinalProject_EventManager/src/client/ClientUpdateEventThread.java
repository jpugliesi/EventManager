package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import constants.Constants;
import main.Event;
import main.User;

public class ClientUpdateEventThread extends Thread {
	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private Event newEvent;
	private boolean success = false;
	
	public ClientUpdateEventThread(Event newEvent){
		this.newEvent = newEvent;
	}
	
	public void run(){
		try{
			socket = new Socket(Constants.SERVER_IP, 6789);
			
			//setup input and output stream
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());

			outputStream.writeObject(Constants.CLIENT_UPDATE_EVENT);
			outputStream.flush();
			
			outputStream.writeObject(newEvent);
			outputStream.flush();
			
			int code = (Integer) inputStream.readObject();
			
			if(code == Constants.SERVER_UPDATE_EVENT_SUCCESS){
				success = true;
			}
		} catch(IOException ioe){
			success = false;
			ioe.printStackTrace();
		} catch (ClassNotFoundException cnfe){
			success = false;
			cnfe.printStackTrace();
		}
	}
	
	public boolean updateSuccessfule(){
		return success;
	}

}
