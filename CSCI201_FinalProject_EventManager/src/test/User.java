package test;

import java.io.Serializable;
import java.util.Vector;

public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int userID, profilePictureID;
	private String fullName, userName, password;
	private boolean isAdmin;
	private Vector<Event> eventVector;
	
	public User(int userID, int profilePictureID, String fullName, String userName, String password, boolean isAdmin, Vector<Event> eventVector){
		this.userID = userID;
		this.profilePictureID = profilePictureID;
		this.fullName = fullName;
		this.userName = userName;
		this.password = password;
		this.isAdmin = isAdmin;
		this.eventVector = eventVector;
	}
	
	public User(){
		
	}
	
	public Vector<Event> getEventVector(){
		return eventVector;
	}
	
	public String getUserName(){
		return userName;
	}
	
	public String getPassword(){
		return password;
	}
	

}
