package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import constants.Constants;
import main.User;

public class ClientListenForChatUpdateThread {
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private boolean updateChat = false;
	private User sender, receiver;
	
	public ClientListenForChatUpdateThread(User sender, User receiver){
		this.sender = sender;
		this.receiver = receiver;
		try{
			socket = new Socket(Constants.SERVER_IP, 6789);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			
			oos.writeObject(Constants.CLIENT_LISTENING_FOR_CHAT);
			oos.flush();
		} catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	public void run() {
		try{
			while(true){
				int update = (Integer)ois.readObject();
				
				if(update == Constants.SERVER_UPDATE_CHAT_HISTORY){
					updateChat = true;
				}
			}
		} catch(IOException ioe){
			ioe.printStackTrace();
		} catch (ClassNotFoundException cnfe){
			cnfe.printStackTrace();
		}
	}
	
	public boolean updateChat(){
		return this.updateChat;
	}
	
	public User getSender(){
		return this.sender;
	}
	
	public User getReceiver(){
		return this.receiver;
	}
	
	
}
