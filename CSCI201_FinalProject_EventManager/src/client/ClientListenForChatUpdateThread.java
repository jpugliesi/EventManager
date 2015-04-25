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

public class ClientListenForChatUpdateThread extends Thread{
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private boolean updateChat = false;
	private User sender, receiver;
	private boolean isAdmin;
	
	private ArrayList<chatWindow> chatWindows = new ArrayList<chatWindow>();
	
	public ClientListenForChatUpdateThread(boolean isAdmin){
		this.isAdmin = isAdmin;
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
			System.out.println("Running chat listener thread");
			while(true){
				int update = (Integer)ois.readObject();
				
				System.out.println("Notified to update chat");
				if(update == Constants.SERVER_UPDATE_CHAT_HISTORY){
					updateChat = true;
					
					this.sender = (User) ois.readObject();
					this.receiver = (User) ois.readObject();
					
					boolean chatWindowOpen = false;

					User otherPerson = null;
					
					String username;
					if(isAdmin){
						username = Environment.currentAdmin.getUserName();
					} else {
						username = Environment.currentUser.getUserName();
					}
					if(sender.getUserName().equals(username)){
						otherPerson = receiver;
					} else {
						otherPerson = sender;
					}
					
					for(chatWindow chat : chatWindows){
						if(chat.getOtherPerson().getUserName().equals(otherPerson.getUserName())){
							chatWindowOpen = true;
							chat.setVisible(true);
							chat.updateChat();
						}
					}
					if(!chatWindowOpen){
						chatWindow chat = new chatWindow(otherPerson, isAdmin);
						chatWindows.add(chat);
						chat.updateChat();
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
	
	public void addNewWindow(chatWindow window){
		chatWindows.add(window);
	}
}
