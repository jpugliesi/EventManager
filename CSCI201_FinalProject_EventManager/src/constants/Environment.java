package constants;

import java.net.Socket;
import java.net.URL;
import java.util.Vector;

import main.Event;
import main.User;

public class Environment {
	
	public static User currentUser;
	public static Socket socket;
	public static URL ipAddress;
	public static Vector<Event> eventFeed;
	public static User currentAdmin;
	
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
