package test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

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
		
		events.add(new Event(0, "Event 1", "Bovard Auditorium", "9:00am", "An event at Bovard!"));
		events.add(new Event(0, "Event 2", "SAL 101", "10:00am", "A club event at Sal!"));
		events.add(new Event(0, "Event 3", "Galen Center", "11:00am", "Club Basketball Game!"));
		events.add(new Event(0, "Event 4", "VKC 201", "12:00pm", "Club Meeting!"));
		events.add(new Event(0, "Event 5", "Leavy Library", "9:30pm", "Study Club!"));
		
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
		return true;
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
	}
	
	
	
	
	
}
