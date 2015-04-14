package main;

import java.util.Date;


public class ChatMessage {
	private String message;
	private User sender, receiver;
	private Date date;
	
	
	public ChatMessage(String message, User sender, User receiver,  Date date){
		this.message = message;
		this.sender = sender;
		this.receiver = receiver;
		this.date = date;
	}
	
	public String getMessage(){
		return message;
	}
	
	public User getSender(){
		return sender;
	}
	
	public User getReceiver(){
		return receiver;
	}
	
	public Date getDate(){
		return date;
	}
	
	public void setMessage(String m){
		message = m;
	}
	
	public void setSender(User u){
		sender = u;
	}
	
	public void setReceiver(User u){
		receiver = u;
	}
	
	public void setDate(Date d){
		date = d;
	}
	
	
}
