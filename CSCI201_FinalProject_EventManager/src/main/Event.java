package main;

import java.io.Serializable;
import java.util.Date;

import javax.swing.ImageIcon;

public class Event implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int eventID, peopleAttending, eventAdminID, profilePictureID;
	private String eventName, eventLocation, eventDesc, eventClub;
	private Date eventTime;
	private ImageIcon profilePicture;
	
	public Event(String eventName, String eventLocation, Date eventTime, String eventClub, String eventDesc, int peopleAttending, int adminID){
		this.eventName = eventName;
		this.eventLocation = eventLocation;
		this.eventTime = eventTime;
		this.eventClub = eventClub;
		this.eventDesc = eventDesc;
		this.peopleAttending= peopleAttending;
		this.eventAdminID = adminID;
	}
	
	public Event(){
		
	}
	
	public void setProfilePictureID(int id){
		this.profilePictureID = id;
	}
	
	public void setProfilePicture(){
		
	}
	
	public int getID(){
		return eventID;
	}
	
	public void setID(int id){
		this.eventID = id;
	}
	
	public String getName(){
		return this.eventName;
	}
	
	public String getLocation(){
		return this.eventLocation;
	}
	
	public String getClub(){
		return this.eventClub;
	}
	
	public int getNumAttending(){
		return this.peopleAttending;
	}
	
	public Date getTime(){
		return this.eventTime;
	}
	
	public String getDescription(){
		return this.eventDesc;
	}
	
	public int getAdminID(){
		return this.eventAdminID;
	}
	
	public void setNumAttending(int numattending){
		this.peopleAttending = numattending;
	}

}
