package test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import main.Event;
import main.User;

public class Test {
	
	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	
	private User validUser;
	private User invalidUserWrongPassword;
	private User invalidUserWrongUsername;
	private Vector<Event> testEvents;
	
	public Test(){
		try {
			socket = new Socket("localhost", 6789);
			
			//setup input and output stream
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());
			
			outputStream.flush();
			
			//create a test user for use across each test case
			testEvents = getTestEvents();
			validUser = new User(0, 0, "Joe Blow", "joeb", "password", false, testEvents);
			invalidUserWrongPassword = new User(0, 0, "Joe Blow", "joeb", "pord", false, testEvents); 
			invalidUserWrongUsername = new User(0, 0, "Joe Blow", "jb", "password", false, testEvents);
			
		} catch (IOException e) {
			System.out.println("Error in Test creating socket: " + e.getMessage());
		}
	}
	
	private Vector<Event> getTestEvents(){
		Vector<Event> events = new Vector<Event>();
		
		events.add(new Event(0,1, "Event 1", "Bovard Auditorium", "9:00am", "An event at Bovard!"));
		events.add(new Event(1,1, "Event 2", "SAL 101", "10:00am", "A club event at Sal!"));
		events.add(new Event(2,1, "Event 3", "Galen Center", "11:00am", "Club Basketball Game!"));
		events.add(new Event(3,1, "Event 4", "VKC 201", "12:00pm", "Club Meeting!"));
		events.add(new Event(4,1, "Event 5", "Leavy Library", "9:30pm", "Study Club!"));
		
		return events;
	}


	public boolean testLogin(){
		boolean response = false;
		try{
			
			outputStream.writeObject("1");
			outputStream.flush();
			
			outputStream.writeObject(validUser);
			outputStream.flush();
			response = (boolean) inputStream.readObject();	
			
		} catch(IOException ioe){
			System.out.println(ioe.getMessage());
		} catch(ClassNotFoundException cnfe){
			System.out.println(cnfe.getMessage());
		}
		return response;
	}
	
	public boolean testRegisterUser(){
		boolean validUserPass = false, invalidUsernamePass = false, invalidPasswordPass = false;
		boolean response = false;
		try{
			
			outputStream.writeObject("2");
			outputStream.flush();
			outputStream.writeObject(validUser);
			outputStream.flush();
			validUserPass = (boolean) inputStream.readObject();
			
			outputStream.writeObject("2");
			outputStream.flush();
			outputStream.writeObject(invalidUserWrongPassword);
			outputStream.flush();
			invalidPasswordPass = (boolean) inputStream.readObject();

			outputStream.writeObject("2");
			outputStream.flush();
			outputStream.writeObject(invalidUserWrongUsername);
			outputStream.flush();
			invalidUsernamePass = (boolean) inputStream.readObject();
			
			
			
		} catch(IOException ioe){
			System.out.println(ioe.getMessage());
		} catch(ClassNotFoundException cnfe){
			System.out.println(cnfe.getMessage());
		}
		
		if(validUserPass && invalidUsernamePass && invalidPasswordPass){ 
			response = true;
		}
		return response;
	}
	
	public boolean testGetEvents(){
		boolean response = false;
		try{
			
			outputStream.writeObject("3");
			outputStream.flush();

			Vector<Event> events = (Vector<Event>) inputStream.readObject();
			if(events.size() != 0){
				response = true;
			}
			
		} catch(IOException ioe){
			System.out.println(ioe.getMessage());
		} catch(ClassNotFoundException cnfe){
			System.out.println(cnfe.getMessage());
		}
		return response;
	}
	
	public boolean testCreateEvent(){
		boolean response = false;
		try {

			outputStream.writeObject("4");
			outputStream.flush();

			Event newEvent = new Event(5,1, "Event 6", "Ground Zero Cafe", "7:00pm", "Singing Contest");
			outputStream.writeObject(newEvent);
			outputStream.flush();

			response = inputStream.readBoolean();
		} catch(IOException ioe) {
			System.out.println(ioe.getMessage());
		} 
		return response;
	}

 
	public boolean testLoadChatHistory() {
		boolean response = false;
		try{
			User u1 = new User();
			User u2 = new User();
			
			outputStream.writeObject("5");
			outputStream.flush();
			
			outputStream.writeObject(u1);//sender
			outputStream.flush();
			outputStream.writeObject(u2);//receiver
			outputStream.flush();
			
			Vector<String> history = (Vector<String>) inputStream.readObject();
			
			if (history.size() != 0){
				response = true;
			}
			
		} catch(IOException ioe){
			System.out.println(ioe.getMessage());
		} catch(ClassNotFoundException cnfe){
			System.out.println(cnfe.getMessage());
		}
		return response;
	}

	public boolean testSendChatMessage() {
		boolean response = false;
		try {

			outputStream.writeObject("6");
			outputStream.flush();

			User sender = new User();
			User receiver = new User();
			String message  = "hello";

			outputStream.writeObject(sender);
			outputStream.flush();
			outputStream.writeObject(receiver);
			outputStream.flush();
			outputStream.writeObject(message);
			outputStream.flush();
			response = inputStream.readBoolean();
			
		} catch(IOException ioe){
			System.out.println(ioe.getMessage());
		} 

		return response;
	}

	public boolean testGetUserEventVector(){
		boolean response = false;
		try{
			
			outputStream.writeObject("7");
			outputStream.flush();
			
			outputStream.writeObject(validUser); 
			
			Vector<Event> users_events = (Vector<Event>)inputStream.readObject();
			if (users_events.size() != 0){
				response = true;
			}
				
		} catch(IOException ioe){
			System.out.println(ioe.getMessage());
		} catch(ClassNotFoundException cnfe){
			System.out.println(cnfe.getMessage());
		}
		return response;
		
	}
	public boolean testSendRSVP(){
		boolean response = false;
		try{
			
			outputStream.writeObject("8");
			outputStream.flush();
			Event newEvent= new Event(0,0, "Event 1", "Bovard Auditorium", "9:00am", "An event at Bovard!");
			outputStream.writeObject(newEvent);
			outputStream.flush();
			outputStream.writeObject(validUser);
			outputStream.flush();
			
			response= (boolean)inputStream.readObject();
			
			
		} catch(IOException ioe){
			System.out.println(ioe.getMessage());
		} catch(ClassNotFoundException cnfe){
			System.out.println(cnfe.getMessage());
		}
		return response;
		
	}
	public boolean testGetEvent(){
		boolean response = false;
		try{
			
			outputStream.writeObject("9");
			outputStream.flush();
			outputStream.writeObject("0"); //event ID no.0
			outputStream.flush();
			
			
			Event findEvent= (Event)inputStream.readObject();
			
			response= true; 
			
		} catch(IOException ioe){
			System.out.println(ioe.getMessage());
		} catch(ClassNotFoundException cnfe){
			System.out.println(cnfe.getMessage());
		}
		return response;
		
	}
	
	public static void main(String [] args){
		Test test = new Test();
		
		//Test Login
		if(test.testLogin()){
			System.out.println("Login: PASSED");
		} else {
			System.out.println("Login: FAILED");
		}
		
		//Test register user
		if(test.testRegisterUser()){
			System.out.println("Register User: PASSED");
		} else {
			System.out.println("Register User: FAILED");
		}
		
		if(test.testGetEvents()){
			System.out.println("Get Events: PASSED");
		} else {
			System.out.println("Get Events: FAILED");
		}
		
		
		//7.Test getting User's Event Vector
		if(test.testGetUserEventVector()){
			System.out.println("Get User's Events: PASSED");
		} else {
			System.out.println("Get User's Events: FAILED");
		}
		//8.Test sending RSVP
		if(test.testSendRSVP()){
			System.out.println("Sending RSVP: PASSED");
		} else {
			System.out.println("Sending RSVP: FAILED");
		}
		//9.Test getting event for detailed event page
		if(test.testGetEvent()){
			System.out.println("Get User's Events: PASSED");
		} else {
			System.out.println("Get User's Events: FAILED");
		}
		
	}
	
	
	
	
	
}
