package main;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int eventID, peopleAttending, eventAdminID;
	private String eventName, eventLocation, eventDesc, eventClub;
	private Date eventTime;
	
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

}
