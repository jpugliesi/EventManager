package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import main.ChatMessage;
import constants.Constants;

public class ClientSendMessageThread extends Thread{
	
	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	ChatMessage msg;
	private boolean success = false;
	
	public ClientSendMessageThread(ChatMessage msg) {
		this.msg = msg;
	}
	
	public void run () {
		try {
			
			socket = new Socket(Constants.SERVER_IP, 6789);
			
			//setup input and output stream
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());

			outputStream.writeObject(Constants.CLIENT_SEND_MESSAGE);
			outputStream.flush();
			
			outputStream.writeObject(msg);
			outputStream.flush();
			
			int code = (Integer) inputStream.readObject();
			
			if (code == Constants.SERVER_SEND_MESSAGE_SUCCESS) {
				success = true;
				//System.out.println("Success send message");
			}
			else if (code == Constants.SERVER_SEND_MESSAGE_FAIL) {
				success = false;
				//System.out.println("Fail send message");
			}
					
		} catch (IOException ioe) {
			success = false;
			System.out.println(ioe.getMessage());
		} catch (ClassNotFoundException e) {
			success = false;
			System.out.println(e.getMessage());
		} 
		
	}
	
	public boolean sendMessageSuccessful(){
		return success;
	}
	
}
