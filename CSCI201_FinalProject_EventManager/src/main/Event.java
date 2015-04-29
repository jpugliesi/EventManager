package main;

import java.io.Serializable;
import java.util.Date;

import javax.swing.ImageIcon;

import constants.Constants;

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
		this.setProfilePicture();
	}
	
	public Event(){
		eventName = "";
		this.setProfilePicture();
	}
	
	public void setProfilePictureID(int id){
		this.profilePictureID = id;
	}
	
	public void setProfilePicture(){
		if (eventName.contains("Fencing")){
			this.profilePicture = Constants.FENCING;
		}
		else if (eventName.contains("Drones")){
			this.profilePicture = Constants.DRONES;
		}
		else if (eventName.contains("Hike")){
			this.profilePicture = Constants.HIKE;
		}
		else if (eventName.contains("Badminton")){
			this.profilePicture = Constants.BADMINTON;
		}
		else if (eventName.contains("Prix")){
			this.profilePicture = Constants.PRIX;
		}
		else if (eventName.contains("Baseball")){
			this.profilePicture = Constants.BASEBALL;
		}
		else if (eventName.contains("Taekwondo")){
			this.profilePicture = Constants.TAEK;
		}
		else if (eventName.contains("Surf")){
			this.profilePicture = Constants.SURF;
		}
		else if (eventName.contains("LavaLab")){
			this.profilePicture = Constants.LAVA;
		}
		else if (eventName.contains("Frisbee")){
			this.profilePicture = Constants.FRISBEE;
		}
		else if (eventName.contains("Market")){
			this.profilePicture = Constants.MARKET;
		}
		else if (eventName.contains("Leaders")){
			this.profilePicture = Constants.LEADER;
		}
		else if (eventName.contains("Auto")){
			this.profilePicture = Constants.AUTO;
		}
		else if (eventName.contains("ACM")){
			this.profilePicture = Constants.ACM;
		}
		else{
			this.profilePicture = Constants.USC;
		}
		
		
		
		
	}
	
	public ImageIcon getProfilePicture(){
		return profilePicture;
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
	
	public boolean equals(Object obj){
		if (obj == null) {
	        return false;
	    }
	    if (getClass() != obj.getClass()) {
	        return false;
	    }
	    final Event other = (Event) obj;

	    if (this.getID() == other.getID()) {
	        return true;
	    }
	    return false;
	}

}
