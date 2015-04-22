package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import main.ChatMessage;
import main.User;
import constants.Constants;

public class ClientGetChatHistoryThread extends Thread{
	
	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	User sender;
	User receiver;
	Vector<ChatMessage> v;
	
	public ClientGetChatHistoryThread(User sender, User receiver) {
		this.sender = sender;
		this.receiver = receiver;
	}
	
	public void run() {
		try {
			socket = new Socket(Constants.SERVER_IP, 6789);
			
			//setup input and output stream
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());

			outputStream.writeObject(Constants.CLIENT_GET_CHAT_HISTORY);
			outputStream.flush();
			
			outputStream.writeObject(sender);
			outputStream.flush();
			
			outputStream.writeObject(receiver);
			outputStream.flush();			
			
			int code = (Integer) inputStream.readObject();
			//success case
			if (code == Constants.SERVER_GET_CHAT_HISTORY_SUCCESS) {
				v = (Vector<ChatMessage>) inputStream.readObject();
				System.out.println("success loading chat history");
			}
			else if (code == Constants.SERVER_GET_CHAT_HISTORY_FAIL) {
				System.out.println("fail loading chat history");
			}
			
			//TODO
			//populate the chat board with ChatMessage vector
			
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} 
	}
	
	public Vector<ChatMessage> getMessageHistory(){
		return v;
	}
	
	
}
