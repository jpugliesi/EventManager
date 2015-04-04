package test;

public class Event {
	private int eventID;
	private String eventName, eventLocation, eventTime, eventDesc;
	
	public Event(int eventID, String eventName, String eventLocation, String eventTime, String eventDesc){
		this.eventID = eventID;
		this.eventName = eventName;
		this.eventLocation = eventLocation;
		this.eventTime = eventTime;
		this.eventDesc = eventDesc;
	}
	
	public Event(){
		
	}

}
