package constants;

public class Constants {
	
	//Login Codes
	//Database.checkLogin(User) returns int
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
	public static int SERVER_REGISTRATION_PASSWORD_FAIL = 2; //non-matching passwords
	
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
	

}