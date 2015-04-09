package constants;

import java.net.Socket;
import java.net.URL;

import main.User;

public class Environment {
	
	public User currentUser;
	public Socket socket;
	public URL ipAddress;
	
	public Environment(User user, Socket socket, URL ipAddress){
		this.currentUser = user;
		this.socket = socket;
		this.ipAddress = ipAddress;
	}
	
	public void updateEnvironment(User user, Socket socket, URL ipAddress){
		this.currentUser = user;
		this.socket = socket;
		this.ipAddress = ipAddress;
	}

}
