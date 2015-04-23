package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import main.User;
import constants.Constants;

public class ClientListenForUpdateThread extends Thread{
	private Socket socket;
	private ObjectInputStream inputStream;
	private boolean updateEventFeed = false;
	private boolean updateChat = false;
	private User userToUpdate;
	
	public void ClientListenForUpdateThread(){
		
	}
	
	public void run(){
		try {
			socket = new Socket(Constants.SERVER_IP, 6790);
			inputStream = new ObjectInputStream(socket.getInputStream());

			
			while(true){
				int num = (int)inputStream.readObject();
				if (num == Constants.SERVER_UPDATE_EVENT_FEED){
					updateEventFeed = true;
				}
				else if (num == Constants.SERVER_UPDATE_CHAT_HISTORY){
					updateChat = true;
					userToUpdate = (User)inputStream.readObject();
				}
			}
			
			
		} catch (IOException ioe){
			
		} catch(ClassNotFoundException cnfe){
			
		}
		
		
	}
	
	public User getUserToUpdate(){
		return userToUpdate;
	}
	
	public boolean updateChat(){
		return updateChat;
	}
	
	public boolean updateEventFeed(){
		return updateEventFeed;
	}

}
