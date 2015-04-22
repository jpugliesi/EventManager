package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import main.ChatMessage;
import main.Event;
import main.GetAdminsException;
import main.GetChatHistoryException;
import main.GetEventException;
import main.LoginException;
import main.User;
import constants.Constants;
import db.Database;


public class ServerThread extends Thread {
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Server server;
	private Socket s;
	private String username;
	private Database db;
	
	private int errorCode;
	public ServerThread(Socket s, Server server, Database db) {
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
	 

	public void sendCode(int n) {
		try {
			oos.writeObject(n);
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
	
	private int getInt(){
		int n = -1;
		try{
			n = (int)ois.readInt();
		}catch (IOException ioe){
			System.out.println("IOE in serverthread.getInt(): " + ioe.getMessage());
		}
		return n;
	}
	
	private ChatMessage getChatMessage(){
		ChatMessage cm = null;
		
		try{
			cm = (ChatMessage)ois.readObject();
		} catch (ClassNotFoundException cnfe){
			System.out.println("CNFE in serverthread.getChatMessage(): " + cnfe.getMessage());
		} catch (IOException ioe){
			System.out.println("IOE in serverthread.getChatMessage(): " + ioe.getMessage());
		}
		
		return cm;
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
	
	private User registerUser(User u){
		User user = null;
		try{
			user = db.registerUser(u);
		}catch(LoginException le){
			errorCode = le.getErrorCode();
		}
		
		return user;
	}
	
	private int createEvent(Event e){
		return db.createEvent(e);
	}
	
	private Vector<Event> getEventVector(){
		Vector<Event> v = null;
		try{
			v = db.getEventFeed();
		}catch (GetEventException gee){
			errorCode = gee.getErrorCode();
		}
	
		return v;
	}
	
	//implement the collection of historic messages
	private Vector<ChatMessage> getChatHistory(User sender, User receiver){
		Vector<ChatMessage> history = null;
		try{
			history = db.getChatHistory(sender, receiver);
		} catch (GetChatHistoryException gche){
			errorCode = gche.getErrorCode();
		}
		return history;
	}
	

	private Vector<Event> getUserEventVector(User u){
		Vector<Event> v = null;
		
		try{
			v = db.getUserEventVector(u);
		} catch (GetEventException gee){
			errorCode = gee.getErrorCode();
		}
		
		return v;
	}
	
	private int rsvp(Event e, User u){
		return db.rsvp(u,e);
	}
	
	private Event getEvent(int id){
		Event e = null;
		
		try{
			e = db.getEvent(id);
		} catch (GetEventException gee){
			errorCode = gee.getErrorCode();
		}
		
		return e;
	}
	
	private Vector<User> getAdmins(){
		Vector<User> admins = null;
		try{
			admins = db.getAdmins();
		} catch (GetAdminsException gae){
			errorCode = gae.getErrorCode();
		}
		
		return admins;
	}
	
	private Event sendEvent(int id){
		Event e = new Event();
		return e;
	}

	public void run() {
		try {
			
			while(true){
				int command = getInt();
				if(command == Constants.CLIENT_LOGIN){ //login
					String userName = getString();
					String pass = getString();
					//hash password first
					User u = userValid(userName, pass);
					
					if (u != null){
						oos.writeObject(Constants.SERVER_LOGIN_SUCCESS);
						oos.flush();
						oos.writeObject(u); //valid
						oos.flush();
					}
					else{
						oos.writeObject(errorCode);
						oos.flush();
					}
					
				}
				else if (command == Constants.CLIENT_REGISTER){ //create user	
					User newUser = getUser();
					User u = registerUser(newUser);
					if(u!= null){
						oos.writeObject(Constants.SERVER_REGISTRATION_SUCCESS);
						oos.flush();
						oos.writeObject(u);
						oos.flush();
					}else{
						oos.writeObject(errorCode);
						oos.flush();
					}
					oos.writeObject(registerUser(newUser)); //sends int of success code
					oos.flush();
				}
				else if (command == Constants.CLIENT_GET_EVENT_FEED){ //get events
					oos.writeObject(getEventVector()); //sends the event feed vector
					oos.flush();
				}
				else if (command == Constants.CLIENT_CREATE_EVENT){ //create Event
					Event newEvent = getEvent();
					oos.writeObject(createEvent(newEvent));
					oos.flush();
					
					server.sendMessageToClients(55);
	
				}
				else if (command == Constants.CLIENT_GET_CHAT_HISTORY){ //load chat history
					User sender = getUser();
					User receiver = getUser();
					Vector<ChatMessage> ch = getChatHistory(sender, receiver);
			
					if(ch != null){
						oos.writeObject(Constants.SERVER_GET_CHAT_HISTORY_SUCCESS);
						oos.flush();
						oos.writeObject(ch);
						oos.flush();
					}else{
						oos.writeObject(errorCode);
						oos.flush();
					}
					
					
				}
				else if (command == Constants.CLIENT_SEND_MESSAGE){ //send message
					
					ChatMessage cm = getChatMessage();
					
					oos.writeObject(db.writeChatMessage(cm));
					oos.flush();
					
					server.sendMessageToClients(56);
				}
				else if (command == Constants.CLIENT_GET_USER_EVENTS){ //get event vector for a given user
					User u = getUser();
					Vector<Event> ev = getUserEventVector(u);
					
					if(ev != null){
						oos.writeObject(Constants.SERVER_GET_USER_EVENTS_SUCCESS);
						oos.flush();
						oos.writeObject(ev);
						oos.flush();
					}
					else{
						oos.writeObject(errorCode);
						oos.flush();
					}
				}
				else if (command == Constants.CLIENT_RSVP){ //rsvp 
					User u = getUser();
					Event e = getEvent();
					oos.writeObject(rsvp(e,u));
					oos.flush();
				}
				else if (command == Constants.CLIENT_GET_EVENT){ //get event
					int id = getInt();
					Event e = getEvent(id);
					
					if (e != null){
						oos.writeObject(Constants.SERVER_GET_EVENT_SUCCESS);
						oos.flush();
						oos.writeObject(e);
						oos.flush();
					} else{
						oos.writeObject(errorCode);
						oos.flush();
					}
					
				}
				else if (command == Constants.CLIENT_GET_ADMINS){
					Vector<User> admins = getAdmins();
					
					if (admins != null){
						oos.writeObject(Constants.SERVER_GET_ADMINS_SUCCESS);
						oos.flush();
						oos.writeObject(admins);
						oos.flush();
					}else{
						oos.writeObject(errorCode);
						oos.flush();
					}
				}
				else if (command == Constants.CLIENT_UPDATE_PROFILE){
					User newUser = getUser();
					
					oos.writeObject(db.updateUser(newUser));
					oos.flush();
				}
				else if(command == Constants.SHUTDOWN){
					db.shutdownDB();
				}
	
			}
		
		} catch (IOException ioe) {
			server.removeServerThread(this);
			System.out.println(s.getInetAddress() + ":" + s.getPort() + " disconnected.");
		} 
	}
}