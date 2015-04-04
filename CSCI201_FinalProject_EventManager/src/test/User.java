package test;

import java.util.Vector;

public class User {
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
	

}
