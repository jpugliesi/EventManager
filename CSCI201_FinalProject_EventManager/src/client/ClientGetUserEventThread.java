package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import main.Event;
import main.User;
import constants.Constants;

public class ClientGetUserEventThread extends Thread{
	
	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private User u;
	private Vector<Event> ev = null;
	private ReentrantLock lock = new ReentrantLock();
	private Condition signal = lock.newCondition();
	
	
	public ClientGetUserEventThread(User u) {
		this.u = u;
	}
	
	public void run() {
		try {
			socket = new Socket(Constants.SERVER_IP, 6789);
		
			//setup input and output stream
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());

			outputStream.writeObject(Constants.CLIENT_GET_USER_EVENTS);
			outputStream.flush();
			
			outputStream.writeObject(u);
			outputStream.flush();
			
			int code = (Integer) inputStream.readObject();
			//success case
			if (code == Constants.SERVER_GET_USER_EVENTS_SUCCESS) {
				try{
					lock.lock();
					ev = (Vector<Event>) inputStream.readObject();
					signal.signalAll();
				} finally{
					lock.unlock();
				}
				//System.out.println("Success get user event vector");
				//TODO
				//populate the GUI with event vector
			}
			//fail case
			else if (code == Constants.SERVER_GET_USER_EVENTS_FAIL) {
				System.out.println("Fail get user event vector");
			}			
		
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} 
	}
	
	public Vector<Event> getUserEvents(){
		lock.lock();
		try{
			if (ev == null){
				signal.await();
			}
		} catch(InterruptedException ie){
			ie.printStackTrace();
		} finally{
			lock.unlock();
		}
		return ev;
	}
}
