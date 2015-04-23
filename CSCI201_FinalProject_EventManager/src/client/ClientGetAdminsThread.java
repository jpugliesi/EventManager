package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import main.User;
import constants.Constants;

public class ClientGetAdminsThread extends Thread{
	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private Vector<User> admins;
	private ReentrantLock lock = new ReentrantLock();
	private Condition signal = lock.newCondition();
	
	public ClientGetAdminsThread() {
		
	}
	
	public void run() {
		try{
			socket = new Socket(Constants.SERVER_IP, 6789);
		
			//setup input and output stream
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());

			outputStream.writeObject(Constants.CLIENT_GET_ADMINS);
			outputStream.flush();
			
			int code = (Integer) inputStream.readObject();
			//success case
			if (code == Constants.SERVER_GET_ADMINS_SUCCESS) {
				try{
					lock.lock();
					admins = (Vector<User>) inputStream.readObject();
					signal.signalAll();
					

				}finally{
					lock.unlock();
				}
				//System.out.println("Success get admins");
				//TODO
				//populate GUI with the admins vector
			}
			//fail case
			else if (code == Constants.SERVER_GET_ADMINS_FAIL) {
				System.out.println("Fail get admins");
			}			
		
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} finally{
			return;
		}
	}
	
	public Vector<User> getAdmins(){
		lock.lock();
		try{
			if(admins == null){
				signal.await();
			}
		} catch (InterruptedException ie){
			ie.printStackTrace();
		} finally{
			lock.unlock();
		}
		return admins;
	}
		
}
