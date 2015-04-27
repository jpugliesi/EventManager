package main;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;

import constants.Constants;

public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int userID, profilePictureID;
	private String fullName, userName, password;
	private boolean isAdmin;
	private Vector<Event> eventVector;
	private volatile ImageIcon profilePicture;


	
	public User(String fullName, String userName, String password, boolean isAdmin, int profilePictureID){
		this.fullName = fullName;
		this.userName = userName;
		this.password = password;
		this.isAdmin = isAdmin;
		this.profilePictureID = profilePictureID;
		this.eventVector = new Vector<Event>();
		//setProfilePicture();


	}
	
	public User(){
		
	}
	
	public void setProfilePicture(){
		if(profilePictureID == 1){
			profilePicture = Constants.BOY1;
		}
		else if (profilePictureID == 2){
			profilePicture = Constants.BOY2;
		}
		else if (profilePictureID == 3){
			profilePicture = Constants.MAN1;

		}
		else if (profilePictureID == 4){
			profilePicture = Constants.MAN2;

		}
		else if (profilePictureID == 5){
			profilePicture = Constants.WOMAN1;
			
		}
		else if (profilePictureID == 6){
			profilePicture = Constants.WOMAN2;
		}
		else if (profilePictureID == 7){
			profilePicture = Constants.WOMAN3;
		}		
		
	}
	
	public void setProfilePictureID(int id){
		this.profilePictureID = id;
		this.setProfilePicture();
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
	
	public void setProfilePicture(ImageIcon pp){
		profilePicture = pp;
	}
	
	public ImageIcon getProfilePicture(){
		return profilePicture;
	}
	
	

}
