package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.ImageIcon;

import constants.Constants;
import main.User;

public class ClientGetProfilePictureThread extends Thread {
	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private User u;
	private volatile ImageIcon profPic = null;
	private ReentrantLock lock = new ReentrantLock();
	private Condition signal = lock.newCondition();
	
	
	public ClientGetProfilePictureThread(User u){
		this.u = u;
	}
	
	public void run(){
		try{
			socket = new Socket(Constants.SERVER_IP, 6789);
			
			
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());

			outputStream.writeObject(Constants.CLIENT_GET_PROFILE_PICTURE);
			outputStream.flush();
			
			outputStream.writeObject(u);
			outputStream.flush();
			
			int code = (Integer)inputStream.readObject();
			
			if(code == Constants.SERVER_GET_PROFILE_PICTURE_SUCCESS){
				try{
					lock.lock();
					profPic = (ImageIcon)inputStream.readObject();
					signal.signalAll();
				} finally{
					lock.unlock();
				}
			}
			
			
		} catch(IOException ioe){
			ioe.printStackTrace();
		} catch (ClassNotFoundException cnfe){
			cnfe.printStackTrace();
		}
	}
	
	public ImageIcon getProfilePicture(){
		lock.lock();
		try{
			if (profPic == null){
				signal.await();
			}
		}catch (InterruptedException ie){
			ie.printStackTrace();
		}finally{
			lock.unlock();
		}
		return profPic;
	}

}
