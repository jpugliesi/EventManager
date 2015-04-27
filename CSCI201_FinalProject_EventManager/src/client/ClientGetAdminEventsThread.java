package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import constants.Constants;
import main.User;
import main.Event;

public class ClientGetAdminEventsThread extends Thread{

	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private User admin;
	private Vector<Event> adminEvents = null;
	private ReentrantLock lock = new ReentrantLock();
	private Condition signal = lock.newCondition();
	
	public ClientGetAdminEventsThread(User admin){
		this.admin = admin;
	}
	
	public void run(){
		try{
			socket = new Socket(Constants.SERVER_IP, Constants.DEFAULT_PORT);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			
			oos.writeObject(Constants.CLIENT_GET_ADMIN_EVENTS);
			oos.flush();
		} catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
}
