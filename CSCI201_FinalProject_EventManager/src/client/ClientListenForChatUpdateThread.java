package client;

import gui.chatWindow;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import main.User;
import constants.Constants;
import constants.Environment;

public class ClientListenForChatUpdateThread {
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private boolean updateChat = false;
	private User sender, receiver;
	
	private ArrayList<chatWindow> chatWindows = new ArrayList<chatWindow>();
	
	public ClientListenForChatUpdateThread(){
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
				
				if(update == Constants.SERVER_UPDATE_CHAT_HISTORY){
					updateChat = true;
					
					this.sender = (User) ois.readObject();
					this.receiver = (User) ois.readObject();
					
					boolean chatWindowOpen = false;
					
					
					User otherPerson = null;
					if(sender.getUserName().equals(Environment.currentUser.getUserName())){
						otherPerson = receiver;
					} else {
						otherPerson = sender;
					}
					
					for(chatWindow chat : chatWindows){
						if(chat.otherPerson().getUserName().equals(this.sender.getUserName()) ||
								chat.otherPerson().getUserName().equals(this.receiver.getUserName())){
							chatWindowOpen = true;
							chat.setVisible(true);
							chat.updateChat();
						}
					}
					if(!chatWindowOpen){
						chatWindow chat = new chatWindow(receiver);
						chatWindows.add(chat);
						chatWindow.updateChat();
					}
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
	
	public void reset(){
		this.updateChat = false;
	}
	
	
	
	
}
