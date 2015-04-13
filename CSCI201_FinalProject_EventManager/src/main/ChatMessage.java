package main;

import java.util.Date;


public class ChatMessage {
	private String message;
	private User sender;
	private Date date;
	
	
	public ChatMessage(String message, User sender, Date date){
		this.message = message;
		this.sender = sender;
		this.date = date;
	}
	
	String getMessage(){
		return message;
	}
	
	
}
