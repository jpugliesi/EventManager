package db;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import main.Event;
import main.LoginException;
import main.User;
import constants.Constants;

public class ServerThread extends Thread {
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private EMServer server;
	private Socket s;
	private String username;
	private Database db;
	
	private int errorCode;
	public ServerThread(Socket s, EMServer server, Database db) {
		this.server = server;
		this.s = s;
		this.db = db;
		try {
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
		} catch (IOException ioe) {
			System.out.println("IOE in ServerThread constructor: " + ioe.getMessage());
		}
	}
	
	private void addName(String name){
		username = name;
	}
	
	public String getUsername () {
		return username;
	}
	 

	public void sendMessage(String str) {
		try {
			oos.writeObject(str);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	public void sendInt(int i){
		try{
			oos.writeObject(i);
			oos.flush();
		} catch (IOException ioe){
			System.out.println("IOE in server.sendInt(): " + ioe.getMessage());
		}
	}
	
	private String getString(){
		String c = "";
		try {
			c = (String) ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch(EOFException eofe){
			//dont print
		} catch (IOException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	private User getUser(){
		User u = null;
		
		try{
			u = (User) ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return u;
	}
	
	private Event getEvent(){
		Event e = new Event();
		
		try{
			e = (Event)ois.readObject();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return e;
	}
	
	private User userValid(String username, String pass){
		User u = null;
		try{
			 u = db.checkLogin(username, pass);

		} catch (LoginException le){
			errorCode = le.getErrorCode();
		}
		
		return u;
		
		
	}
	
	private int registerUser(User u){//returns true if user creation worked
		
		return db.registerUser(u);
	}
	
	private boolean createEvent(Event e){
		return true;
	}
	
	private Vector<Event> getEventVector(){
		Vector<Event> v = new Vector<Event>();
		
	
		return v;
	}
	
	//implement the collection of historic messages
	private Vector<String> getChatHistory(User sender, User receiver){
		Vector<String> history = new Vector<String>();
		
		return history;
	}
	
	private boolean sendMessage(User sender, User receiver, String message){
		return true;
	}
	
	private boolean rsvp(Event e, User u){
		return true;
	}
	
	private Event sendEvent(int id){
		Event e = new Event();
		return e;
	}

	public void run() {
		try {
			
			while(true){
				String line = getString();
				if(line.equals("1")){ //login
					String userName = getString();
					String pass = getString();
					//hash password first
					User u = userValid(userName, pass);
					oos.writeObject(u); //valid
					oos.flush();
					if (u != null){
						oos.writeObject(Constants.SERVER_LOGIN_SUCCESS);
					}
					else{
						oos.writeObject(errorCode);
						oos.flush();
					}
					
				}
				else if (line.equals("2")){ //create user	
					User newUser = getUser();
					oos.writeObject(registerUser(newUser)); //sends int of success code
					oos.flush();
				}
				else if (line.equals("3")){ //get events
					oos.writeObject(getEventVector());
					oos.flush();
				}
				else if (line.equals("4")){ //create Event
					Event newEvent = getEvent();
					oos.writeObject(createEvent(newEvent));
					oos.flush();
	
				}
				else if (line.equals("5")){ //load chat history
					User sender = getUser();
					User receiver = getUser();
					
					oos.writeObject(getChatHistory(sender, receiver));
					oos.flush();
					
				}
				else if (line.equals("6")){ //send message
					User sender = getUser();
					User receiver = getUser();
					String message = getString();
					
					oos.writeObject(sendMessage(sender, receiver, message));
					oos.flush();	
				}
				else if (line.equals("7")){ //get event vector for a given user
					User u = getUser();
					u.testAttendedEvents();
					oos.writeObject(u.getEventVector());
					oos.flush();
				}
				else if (line.equals("8")){ //rsvp 
					Event e = getEvent();
					User u = getUser();
					oos.writeObject(rsvp(e,u));
					oos.flush();
				}
				else if (line.equals("9")){ //get event
					int id = Integer.parseInt(getString());
					oos.writeObject(sendEvent(id));
					oos.flush();
				}
	
			}
		
		} catch (IOException ioe) {
			server.removeServerThread(this);
			System.out.println(s.getInetAddress() + ":" + s.getPort() + " disconnected.");
		} 
	}
}