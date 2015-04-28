package constants;

import javax.swing.ImageIcon;

public class Constants {
	
	
	public static ImageIcon [] PROFILE_PICTURES = {new ImageIcon("profile_pictures/boy1.png"), new ImageIcon("profile_pictures/boy2.png"), new ImageIcon("profile_pictures/man1.png"), new ImageIcon("profile_pictures/man2.png"), new ImageIcon("profile_pictures/woman1.png"), new ImageIcon("profile_pictures/woman2.png"), new ImageIcon("profile_pictures/woman3.png"), new ImageIcon("profile_pictures/dog1.png"), new ImageIcon("profile_pictures/dog2.png"), new ImageIcon("profile_pictures/dog3.png"), new ImageIcon("profile_pictures/dog4.png"), new ImageIcon("profile_pictures/dog5.png")};
	
	//Login Codes
	//Database.checkLogin(String username, String password_hash) returns User
	public static int CLIENT_LOGIN = 1;
	public static int SERVER_LOGIN_INCORRECT_USER = 1;
	public static int SERVER_LOGIN_INCORRECT_PASSWORD = 2;
	public static int SERVER_LOGIN_INCORRECT_IP = 3;
	public static int SERVER_LOGIN_SUCCESS = 0;
	
	//Registration Codes
	//Database.registerUser(User);
	public static int CLIENT_REGISTER = 2;
	public static int SERVER_REGISTRATION_SUCCESS = 1;
	public static int SERVER_REGISTRATION_USERNAME_FAIL = 0; //username already taken
	public static int SERVER_REGISTRATION_PASSWORD_FAIL = 2; //non-matching passwords (should be checked on the server side)
	
	//Event Feed Retrieval Codes
	//Database.getEventFeed();
	public static int CLIENT_GET_EVENT_FEED = 3;
	public static int SERVER_EVENT_FEED_SUCCESS = 1;
	public static int SERVER_EVENT_FEED_FAIL = 0;
	
	//Create Event Codes
	public static int CLIENT_CREATE_EVENT = 4;
	public static int SERVER_CREATE_EVENT_SUCCESS = 1;
	public static int SERVER_CREATE_EVENT_FAIL = 0;
	
	//Load Chat History
	public static int CLIENT_GET_CHAT_HISTORY = 5;
	public static int SERVER_GET_CHAT_HISTORY_SUCCESS = 1;
	public static int SERVER_GET_CHAT_HISTORY_FAIL = 0;
	
	//Send chat message
	public static int CLIENT_SEND_MESSAGE = 6;
	public static int SERVER_SEND_MESSAGE_SUCCESS = 1;
	public static int SERVER_SEND_MESSAGE_FAIL = 0;
	
	//Get User Event Vector
	public static int CLIENT_GET_USER_EVENTS = 7;
	public static int SERVER_GET_USER_EVENTS_SUCCESS = 1;
	public static int SERVER_GET_USER_EVENTS_FAIL = 0;
	
	//Send an RSVP
	//Database.rsvp(User, Event)
	public static int CLIENT_RSVP = 8;
	public static int SERVER_RSVP_SUCCESS = 1;
	public static int SERVER_RSVP_FAIL = 0;
	
	//Get Event
	public static int CLIENT_GET_EVENT = 9;
	public static int SERVER_GET_EVENT_SUCCESS = 9;
	public static int SERVER_GET_EVENT_FAIL = 9;
	
	//Get All Admins for chat
	public static int CLIENT_GET_ADMINS = 10;
	public static int SERVER_GET_ADMINS_SUCCESS = 1;
	public static int SERVER_GET_ADMINS_FAIL = 0;
	
	//Asynchronous Loading for Chat and Events
	public static int SERVER_UPDATE_EVENT_FEED = 55;
	public static int SERVER_UPDATE_CHAT_HISTORY = 56;
	
	//Update user profile
	public static int CLIENT_UPDATE_PROFILE = 11;
	public static int SERVER_UPDATE_PROFILE_SUCCESS = 1;
	public static int SERVER_UPDATE_PROFILE_FAIL = 0;
	
	//Profile Picture
	public static int CLIENT_GET_PROFILE_PICTURE = 12;
	public static int SERVER_GET_PROFILE_PICTURE_SUCCESS = 1;
	public static int SERVER_GET_PROFILE_PICTURE_FAIL = 0;
	
	//Update Events
	public static int CLIENT_UPDATE_EVENT = 13;
	public static int SERVER_UPDATE_EVENT_SUCCESS = 1;
	public static int SERVER_UPDATE_EVENT_FAIL = 0;
	
	//tell server that this client is listening for an update prompt
	public static int CLIENT_LISTENING_FOR_CHAT = 14;
	public static int CLIENT_LISTENING_FOR_EVENT_FEED = 15;
	
	
	public static int CLIENT_GET_ADMIN_EVENTS = 16;
	public static int CLIENT_GET_ADMIN_EVENTS_SUCCESS = 1;
	public static int CLIENT_GET_ADMIN_EVENTS_FAIL = 0;
	
	public static int CLIENT_GET_RECOMMENDED_EVENT = 17;
	public static int SERVER_GET_RECOMMENDED_EVENT_SUCCESS = 1;
	public static int SERVER_GET_RECOMMENDED_EVENT_FAIL = 0;
	
	
	//close the database
	public static int SHUTDOWN = 12;
	
	//server ip address
	public static String SERVER_IP = "localhost";
	
	public static ImageIcon BOY1 = new ImageIcon("profile_pictures/boy1.png");
	public static ImageIcon BOY2 = new ImageIcon("profile_pictures/boy2.png");
	public static ImageIcon MAN1 = new ImageIcon("profile_pictures/man1.png");
	public static ImageIcon MAN2 = new ImageIcon("profile_pictures/man2.png");
	public static ImageIcon WOMAN1 = new ImageIcon("profile_pictures/woman1.png");
	public static ImageIcon WOMAN2 = new ImageIcon("profile_pictures/woman2.png");
	public static ImageIcon WOMAN3 = new ImageIcon("profile_pictures/woman3.png");
	public static ImageIcon DOG1 = new ImageIcon("profile_pictures/dog1.png");
	public static ImageIcon DOG2 = new ImageIcon("profile_pictures/dog2.png");
	public static ImageIcon DOG3 = new ImageIcon("profile_pictures/dog3.png");
	public static ImageIcon DOG4 = new ImageIcon("profile_pictures/dog4.png");
	public static ImageIcon DOG5 = new ImageIcon("profile_pictures/dog5.png");
	public static ImageIcon DOG6 = new ImageIcon("profile_pictures/dog6.png");


	
	public static int DEFAULT_PORT = 6789;
	
	

}
