package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import constants.Constants;
import main.User;

public class ClientListenForEventFeedThread {
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private boolean updateFeed = false;
	
	public ClientListenForEventFeedThread(){
		
		try{
			socket = new Socket(Constants.SERVER_IP, 6789);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			
			oos.writeObject(Constants.CLIENT_LISTENING);
			oos.flush();
		} catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	public void run() {
		try{
			while(true){
				int update = (Integer)ois.readObject();
				
				if(update == Constants.SERVER_UPDATE_EVENT_FEED){
					updateFeed = true;
				}
			}
		} catch(IOException ioe){
			ioe.printStackTrace();
		} catch (ClassNotFoundException cnfe){
			cnfe.printStackTrace();
		}
	}
	
	public boolean updateFeed(){
		return this.updateFeed;
	}

	
}
