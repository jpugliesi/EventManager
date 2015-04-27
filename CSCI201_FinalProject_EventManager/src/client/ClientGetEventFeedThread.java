package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import main.Event;
import constants.Constants;

public class ClientGetEventFeedThread extends Thread{
	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private Vector<Event> v = null;
	private ReentrantLock lock = new ReentrantLock();
	private Condition signal = lock.newCondition();
	
	
	public ClientGetEventFeedThread() {
	
	}
	
	public  void run() {
		try {
			socket = new Socket(Constants.SERVER_IP, 6789);
			
			//setup input and output stream
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());

			outputStream.writeObject(Constants.CLIENT_GET_EVENT_FEED);
			outputStream.flush();
			
			try{
				//System.out.println("Acquiring Event Feed lock in run");
				lock.lock();
				//System.out.println("Acquired Event Feed lock in run");
				v = (Vector<Event>) inputStream.readObject();
				//System.out.println("read the Event Feed object now signaling");
				signal.signalAll();
				//System.out.println("VECTOR SIZE1: " + v.size());
			} finally {
				lock.unlock();
			}
			
						
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			return;
		}
	}
	
	
	public  Vector<Event> getEventFeed(){
		lock.lock();
		try{
			if(v == null){
				System.out.println("wait for lock before return");
				signal.await();
			}
		
		} catch (InterruptedException ie){ 
			ie.printStackTrace();
		} finally{
			lock.unlock();
		}
		return v;
		
		
		
	}
	
}
