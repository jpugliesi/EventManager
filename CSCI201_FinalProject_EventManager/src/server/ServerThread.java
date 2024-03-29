package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.ImageIcon;

import main.ChatMessage;
import main.Event;
import main.GetAdminsException;
import main.GetChatHistoryException;
import main.GetEventException;
import main.GetProfilePictureException;
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
	private boolean isListeningForChat;
	private boolean isListeningForFeed;
	
	private int errorCode;
	public ServerThread(Socket s, Server server, Database db) {
		this.server = server;
		this.s = s;
		this.db = db;
		this.isListeningForChat = false;
		this.isListeningForFeed = false;
		try {
			oos = new ObjectOutputStream(s.getOutputStream());
			oos.flush();
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
	
	public boolean isListeningForChat(){
		return isListeningForChat;
	}
	
	public boolean isListeningForFeed(){
		return this.isListeningForFeed;
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
			n = (Integer)ois.readObject();
		}catch (IOException ioe){
			//ioe.printStackTrace();
			//System.out.println("IOE in serverthread.getInt(): " + ioe.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	private User userValid(String username, String pass, boolean isAdmin){
		User u = null;
		try{
			 u = db.checkLogin(username, pass, isAdmin);

		} catch (LoginException le){
			errorCode = le.getErrorCode();
		}
		
		return u;
		
		
	}
	
	public void sendUser(User u){
		try {
			oos.writeObject(u);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendEventFeed(Vector<Event> feed){
		try{
			oos.writeObject(feed);
			oos.flush();
		} catch(IOException e){
			e.printStackTrace();
		}
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
	
	
	
	
	private Vector<Event> getAdminEventVector(User admin){
		System.out.println("Get Admin Events");
		Vector<Event> ev = null;
		
		try{
			ev = db.getAdminEventVector(admin);
			System.out.println("IN getAdminEventVector: " + ev.get(0).getName());
		} catch (GetEventException gee){
			errorCode = gee.getErrorCode();
		}
		
		return ev;
	}
	
	
	
	private int rsvp(Event e, User u){
		return db.rsvp(u,e);
	}
	
	private Event recommendEvent(User u){
		Event e = null;
		try{
			e = db.recommendEvent(u);
		} catch(GetEventException ge){
			return null;
		}
		return e;
	}
	
	private ImageIcon getUsersPic(User u){
		ImageIcon ii = null;
		try{
			ii = db.getProfilePicture(u);
		} catch (GetProfilePictureException gppe){
			errorCode = gppe.getErrorCode();
		}
		
		return ii;
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
	
	
	
	private boolean getBool(){
		boolean b = false;
		
		try{
			b = (boolean)ois.readObject();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return b;
	}

	public void run() {
		try {
			
			while(true){
				int command = getInt();
				if(command == Constants.CLIENT_LOGIN){ //login
					String userName = getString();
					String pass = getString();
					boolean isAdmin = getBool();
					
				
					User u = userValid(userName, pass, isAdmin);
					
					
					if (u != null){
						System.out.println("login successful!");
						oos.writeObject(Constants.SERVER_LOGIN_SUCCESS);
						oos.flush();
						oos.writeObject(u); //valid
						oos.flush();
					}
					else{
						System.out.println("invalid login");
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
					//System.out.println("prompted for feed");
					oos.writeObject(getEventVector()); //sends the event feed vector
					
					oos.flush();
				}
				else if (command == Constants.CLIENT_CREATE_EVENT){ //create Event
					Event newEvent = getEvent();
					oos.writeObject(createEvent(newEvent));
					oos.flush();
					
					
					server.sendMessageToClients(Constants.SERVER_UPDATE_EVENT_FEED);
					
					
	
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
					
					server.sendMessageToClients(Constants.SERVER_UPDATE_CHAT_HISTORY);
					server.sendUserToClient(cm.getSender());
					server.sendUserToClient(cm.getReceiver());
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
				else if (command == Constants.CLIENT_GET_RECOMMENDED_EVENT){
					User u = getUser();
					
					Event e = recommendEvent(u);

					if(e != null){
						oos.writeObject(Constants.SERVER_GET_RECOMMENDED_EVENT_SUCCESS);
						oos.flush();
						oos.writeObject(e);
						oos.flush();
					} else {
						oos.writeObject(Constants.SERVER_GET_RECOMMENDED_EVENT_FAIL);
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
				else if (command == Constants.CLIENT_GET_PROFILE_PICTURE){
					User u = getUser();
					
					ImageIcon icon = getUsersPic(u);
					if(icon != null){
						oos.writeObject(Constants.SERVER_GET_PROFILE_PICTURE_SUCCESS);
						oos.flush();
						oos.writeObject(icon);
						oos.flush();
					}
					else{
						oos.writeObject(errorCode);
						oos.flush();
					}
					
				}
				else if (command == Constants.CLIENT_UPDATE_EVENT){
					Event e = getEvent();
					int result = db.updateEvent(e);
					oos.writeObject(result);
					oos.flush();
					
				}
				
				else if (command == Constants.CLIENT_LISTENING_FOR_CHAT){
					this.isListeningForChat = true;
				}
				
				else if(command == Constants.CLIENT_LISTENING_FOR_EVENT_FEED){
					this.isListeningForFeed = true;
				}
				else if(command == Constants.SHUTDOWN){
					db.shutdownDB();
				}
				else if (command == Constants.CLIENT_GET_ADMIN_EVENTS){
					User admin = getUser();
					Vector<Event> adminEvents = getAdminEventVector(admin);
					System.out.println("Have event vector");
					
					if (adminEvents != null){
						
						oos.writeObject(Constants.CLIENT_GET_ADMIN_EVENTS_SUCCESS);
						oos.flush();
						oos.writeObject(adminEvents);
						oos.flush();
						System.out.println(adminEvents.get(0).getName());
					} else{
						oos.writeObject(Constants.CLIENT_GET_ADMIN_EVENTS_FAIL);
						oos.flush();
					}
				}
	
			}
			
			
		
		} catch (IOException ioe) {
			server.removeServerThread(this);
			System.out.println(s.getInetAddress() + ":" + s.getPort() + " disconnected.");
		} 
	}
}