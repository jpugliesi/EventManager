package main;

import java.io.Serializable;

import java.util.Date;
import java.util.Vector;

public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int userID, profilePictureID;
	private String fullName, userName, password;
	private boolean isAdmin;
	private Vector<Event> eventVector;

	
	public User(String fullName, String userName, String password, boolean isAdmin, int profilePictureID){
		this.fullName = fullName;
		this.userName = userName;
		this.password = password;
		this.isAdmin = isAdmin;
		this.profilePictureID = profilePictureID;
		this.eventVector = new Vector<Event>();
		
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
	
	public String getFullName(){
		return fullName;
	}
	
	public boolean isAdmin(){
		return isAdmin;
	}
	
	public int getProfilePictureID(){
		return profilePictureID;
	}
	
	public int getUserID(){
		return userID;
	}
	
	public void setUserID(int id){
		this.userID = id;
	}
	
	public void addEvent(Event e){
		this.eventVector.add(e);
	}
	
	public void setEvents(Vector<Event> events){
		this.eventVector = events;
	}

	public void testAttendedEvents() {
		this.addEvent(new Event("Event 4", "VKC 201", new Date(System.currentTimeMillis()), "ACM", "Club Meeting!", 10, 1));
		this.addEvent(new Event("Event 5", "Leavy Library", new Date(System.currentTimeMillis()), "SAS", "Study Club!", 18, 1));
		
	}


}
