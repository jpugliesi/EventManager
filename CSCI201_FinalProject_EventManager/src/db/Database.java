package db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.imageio.ImageIO;
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

public class Database {
	
	private static String jdbcDriver = "com.mysql.jdbc.Driver";
	private static String dbAddress = "jdbc:mysql://";
	private static int dbPort = 3306;
    private static String dbName = "EventManagerDB";
    private static String dbUser = "root";
    private static String dbPassword = "";
    
    private Connection conn;
	
    /*
     * Be sure to call shutdownDB() once the database is no longer needed!
     */
	public Database(String ipAddress, boolean reseed) throws LoginException{
		try {
			
			Class.forName(jdbcDriver);
			String dbFullAddress = dbAddress + ipAddress + ":" + dbPort + "/";
			conn = DriverManager.getConnection(dbFullAddress, dbUser, dbPassword);

			if(reseed){
				//Search for existing databases
	            ResultSet dbsFound = conn.getMetaData().getCatalogs();
	            boolean dbExists = false;
	            while(dbsFound.next())
	            {	        
	            	//EventManagerDB exists
	            	String databaseName = dbsFound.getString(1);
	            	if(databaseName.equals(dbName)){
	            		dbExists = true;
	            	}
	            }
	            if(dbExists){
					Statement stmt = conn.createStatement();
				      
				    String sql = "DROP DATABASE EventManagerDB";
				    stmt.executeUpdate(sql);
				    stmt.close();
	            }
	            dbsFound.close();
			}
			createDatabaseIfDNE();
			conn.setCatalog(dbName);
	           
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException in Database Constructor");
			e.printStackTrace();
		} catch (SQLException sqle){
			//connection to db failed, likely an issue with the passed ipAddress
			//throw a LoginException with error code for INCORRECT_IP
			System.out.println("SQLException in Database Constructor");
			sqle.printStackTrace();
			throw new LoginException(Constants.SERVER_LOGIN_INCORRECT_IP);
		}
        
	}
	
	/*
	 * Returns a User if successful login
	 * throws LoginException with corresponding error code if username, or password is incorrect
	 */
	public User checkLogin(String username, String password_hash) throws LoginException{
		User user = null;
		
		try{			
			//find users in the database with provided username
			String sql = "SELECT * FROM users WHERE username=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			
			//count number of users in the database (should really only be 0 or 1)
			int rowcount = 0;
			if (rs.last()) {
			  rowcount = rs.getRow();
			  rs.beforeFirst();
			}
			
			if(rowcount == 0){
				//username does not exist in the database
				throw new LoginException(Constants.SERVER_LOGIN_INCORRECT_USER);
			} else {
				//retrieve user data. check if password hashes match.
				rs.next();
				int user_id = rs.getInt("user_id");
				String db_username = rs.getString("username");
				String db_password = rs.getString("password_hash");
				String full_name = rs.getString("full_name");
				boolean is_admin = rs.getBoolean("is_admin");
				int fk_prof_pic = rs.getInt("fk_profile_picture");
				
				if(!password_hash.equals(db_password)){
					//password hashes do not match.
					//Throw LoginException
					throw new LoginException(Constants.SERVER_LOGIN_INCORRECT_PASSWORD);
				} else {
					//user's password is correct. Create a new user instance and populate with the user's data
					user = new User(full_name, db_username, db_password, is_admin, fk_prof_pic);
					user.setUserID(user_id);
					
					//add events that the user is registered for
					/*PreparedStatement find_events_ps;
					ResultSet users_events;
					try{
						String find_events = "SELECT * FROM user_event_junction WHERE fk_user_id=?";
						find_events_ps = conn.prepareStatement(find_events);
						find_events_ps.setInt(1, user.getUserID());
						users_events = find_events_ps.executeQuery();
						
						while(users_events.next()){
							int event_id = users_events.getInt("event_id");
							String event_title = users_events.getString("title");
							String event_club = users_events.getString("club");
							String event_location = users_events.getString("location");
							long event_epoch_time = users_events.getLong("time");
							String event_description = users_events.getString("description");
							int event_attending = users_events.getInt("num_attending");
							int event_admin_id = users_events.getInt("fk_admin");
							
							Date event_time = new Date(event_epoch_time);
							Event found_event = new Event(event_title, event_location, event_time, event_club, event_description, event_attending, event_admin_id);
							found_event.setID(event_id);
							
							user.addEvent(found_event);
						}
						
						users_events.close();
						find_events_ps.close();
						
					} catch(SQLException sqle){
						System.out.println("SQLException retieving user's events");
						sqle.printStackTrace();
					}*/
				}
			}
			
			rs.close();
			ps.close();
			
		} catch (SQLException sqle){
			System.out.println("SQLException while checking login in checkLogin()");
			sqle.printStackTrace();
		}
		
		return user;
		
	}
	
	/*
	 * PARAM: User object
	 * RETURNS: Error/Success code
	 */
	public User registerUser(User user) throws LoginException{

		
		try{
			String sql = "SELECT * FROM users WHERE username=?";
					
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUserName());
			
			ResultSet found_users = ps.executeQuery();
			int rowcount = 0;
			if (found_users.last()) {
			  rowcount = found_users.getRow();
			  found_users.beforeFirst();
			}
			
			if(rowcount > 0){
				//username already exists in the database
				throw new LoginException(Constants.SERVER_REGISTRATION_USERNAME_FAIL);
			}
			
			found_users.close();
			ps.close();
			
		} catch(SQLException e){
			//error in searching for existing user
			System.out.println("SQLException in registerUser() while checking existing users");
			e.printStackTrace();
		}
		
		//user does not exist in the database, so add to users table
		
		//supplied password should be hashed
		try {
			String sql = "INSERT INTO users " + 
					"VALUES(?, ?, ?, ?, ?, ?)";
		
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, 0);
			ps.setString(2, user.getFullName());
			ps.setString(3, user.getUserName());
			ps.setString(4, user.getPassword());
			ps.setBoolean(5, user.isAdmin());
			ps.setInt(6, user.getProfilePictureID());
			
			ps.executeUpdate();
			
			ps.close();
			
		} catch (SQLException e) {
			//error in register user
			System.out.println("SQLException in registerUser()");
			e.printStackTrace();
			throw new LoginException(Constants.SERVER_REGISTRATION_USERNAME_FAIL);
		}
		
		User newUser = checkLogin(user.getUserName(), user.getPassword());
		
		return newUser;
	}
	
	/*
	 * PARAM: Event object
	 * RETURNS: Error/Success code
	 */
	public int createEvent(Event event){
		try {
			String sql = "INSERT INTO events " + 
					"VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, 0);
			ps.setString(2, event.getName());
			ps.setString(3, event.getLocation());
			ps.setLong(4, event.getTime().getTime());
			ps.setString(5, event.getClub());
			ps.setString(6,event.getDescription());
			ps.setInt(7, event.getNumAttending());
			ps.setInt(8, event.getAdminID());
			
			ps.executeUpdate();
			
			ps.close();
			
		} catch (SQLException e) {
			//error in register user
			System.out.println("SQLException in createEvent()");
			e.printStackTrace();
			return Constants.SERVER_CREATE_EVENT_FAIL;
		}
		
		return Constants.SERVER_CREATE_EVENT_SUCCESS;
	}
	
	/*
	 * PARAMS: User object, Event object
	 * RETURNS: error/success code
	 */
	public int rsvp(User user, Event event){
		try{
			String sql = "INSERT INTO user_event_junction VALUES(?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, user.getUserID());
			ps.setInt(2, event.getID());
			
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			//error in register user
			System.out.println("SQLException in rsvp()");
			e.printStackTrace();
			return Constants.SERVER_RSVP_FAIL;
		}
		
		return Constants.SERVER_RSVP_SUCCESS;
	}
	
	/*
	 * getUserEventVector(User)
	 * Retrieves a Vector<Event> for all of a user's rsvp'd events
	 * 
	 * Throws GetEventException if error
	 * 
	 * PARAMS: User object
	 * RETURNS: Vector<Event>
	 * 
	 */
	public Vector<Event> getUserEventVector(User user) throws GetEventException{
		
		Vector<Event> events = new Vector<Event>();
		
		try{
			String sql = "SELECT * FROM events WHERE event_id IN (SELECT fk_event_id FROM user_event_junction WHERE fk_user_id=?)";
			PreparedStatement find_events_ps = conn.prepareStatement(sql);
			find_events_ps.setInt(1, user.getUserID());
			ResultSet users_events = find_events_ps.executeQuery();
			
			while(users_events.next()){
				int event_id = users_events.getInt("event_id");
				String event_title = users_events.getString("name");
				String event_club = users_events.getString("club");
				String event_location = users_events.getString("location");
				long event_epoch_time = users_events.getLong("time");
				String event_description = users_events.getString("description");
				int event_attending = users_events.getInt("num_attending");
				int event_admin_id = users_events.getInt("fk_admin");
				
				Date event_time = new Date(event_epoch_time);
				Event found_event = new Event(event_title, event_location, event_time, event_club, event_description, event_attending, event_admin_id);
				found_event.setID(event_id);
				
				events.add(found_event);
			}
			
			users_events.close();
			find_events_ps.close();
			
		} catch(SQLException sqle){
			System.out.println("SQLException retieving user's events");
			sqle.printStackTrace();
			throw new GetEventException(Constants.SERVER_GET_USER_EVENTS_FAIL);
		}
		
		return events;
	}
	
	public ImageIcon getProfilePicture(User user) throws GetProfilePictureException{
		ImageIcon pic = null;
		try{
			String sql = "SELECT * FROM profile_pictures WHERE pic_id = ?";
			PreparedStatement find_pic = conn.prepareStatement(sql);
			find_pic.setInt(1, user.getProfilePictureID());
			ResultSet profile_pic_found = find_pic.executeQuery();
			
			profile_pic_found.next();
			int pic_id = profile_pic_found.getInt("pic_id");
			String pic_path = profile_pic_found.getString("file_path");
			
			try {
				pic = new ImageIcon(ImageIO.read(getClass().getResource(pic_path)));
			} catch (IOException e) {
				e.printStackTrace();
			}
						
			profile_pic_found.close();
			find_pic.close();
			
		} catch(SQLException sqle){
			System.out.println("SQLException retieving user's events");
			sqle.printStackTrace();
			throw new GetProfilePictureException(Constants.SERVER_GET_PROFILE_PICTURE_FAIL);
		}
		
		return pic;
	}
	
	/*
	 * getEventFeed()
	 * Returns a Vector<Event> of 10 most recent events
	 * 
	 * Throws GetEventException
	 * 
	 * PARAMS: none
	 * RETURNS: Vector<Event>
	 */
	public Vector<Event> getEventFeed() throws GetEventException{
		Vector<Event> event_feed = new Vector<Event>();
		
		try{
			String sql = "SELECT * FROM events LIMIT 10";
			Statement s = conn.createStatement();
			
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next()){
			
				int id = rs.getInt("event_id");
				String event_name = rs.getString("name");
				String event_location = rs.getString("location");
				long event_time = rs.getLong("time");
				String event_club = rs.getString("club");
				String event_description = rs.getString("description");
				int num_attending = rs.getInt("num_attending");
				int admin_id = rs.getInt("fk_admin");
				
				Event event = new Event(event_name, event_location, new Date(event_time), event_club, event_description, num_attending, admin_id);
				event.setID(id);
				
				event_feed.add(event);
			}
			
			rs.close();
			s.close();
			
		} catch (SQLException e) {
			System.out.println("SQLException in getEventFeed()");
			e.printStackTrace();
			throw new GetEventException(Constants.SERVER_EVENT_FEED_FAIL);
		}
		
		return event_feed;
	}
	
	/*
	 * getEvent(int event_id)
	 * Given an Event's event_id, returns an Event object populated with the appropriate information
	 * 
	 * Throws GetEventException if error
	 * 
	 * PARAMS: int event_id
	 * RETURNS: Event
	 */
	public Event getEvent(int event_id) throws GetEventException{
		Event event = null;
		try{
			String sql = "SELECT * FROM events WHERE event_id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, event_id);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			int id = rs.getInt("event_id");
			String event_name = rs.getString("name");
			String event_location = rs.getString("location");
			long event_time = rs.getLong("time");
			String event_club = rs.getString("club");
			String event_description = rs.getString("description");
			int num_attending = rs.getInt("num_attending");
			int admin_id = rs.getInt("fk_admin");
			
			event = new Event(event_name, event_location, new Date(event_time), event_club, event_description, num_attending, admin_id);
			event.setID(id);
			
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			//error in register user
			System.out.println("SQLException in getEvent()");
			e.printStackTrace();
			throw new GetEventException(Constants.SERVER_GET_EVENT_FAIL);
		}
		
		return event;
	}
	
	/*
	 * getChatHistory(User sender, User receiver)
	 * Returns a Vector<ChatMessage> of all messages between users
	 * 
	 * Throws GetChatHistoryException
	 * 
	 * PARAMS: User sender object, User receiver object
	 * RETURNS: Vector<ChatMessage>
	 */
	public Vector<ChatMessage> getChatHistory(User sender, User receiver) throws GetChatHistoryException{
		
		Vector<ChatMessage> messages = new Vector<ChatMessage>();

		try{
			String sql = "SELECT * FROM chat_history WHERE (fk_sender_id=? AND fk_receiver_id=?) OR (fk_sender_id=? AND fk_receiver_id=?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, sender.getUserID());
			ps.setInt(2, receiver.getUserID());
			ps.setInt(3, receiver.getUserID());
			ps.setInt(4, sender.getUserID());
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				String message = rs.getString("message");
				int sender_id = rs.getInt("fk_sender_id");
				int receiver_id = rs.getInt("fk_receiver_id");
				long time = rs.getLong("time");
				
				ChatMessage new_message = null;
				if(sender_id == sender.getUserID()){
					new_message = new ChatMessage(message, sender, receiver, new Date(time));
				} else {
					new_message = new ChatMessage(message, receiver, sender, new Date(time));
				}
				
				messages.add(new_message);
			}
			
			rs.close();
			ps.close();
		} catch(SQLException sqle){
			sqle.printStackTrace();
			throw new GetChatHistoryException(Constants.SERVER_GET_CHAT_HISTORY_FAIL);
		}
		
		return messages;
		
	}
	
	/*
	 * writeChatMessage(ChatMessage message)
	 * Writes a new chat message to the database, using the ChatMessage instance's values
	 * PARMS: ChatMessage object
	 * RETURNS: int error/success code
	 */
	public int writeChatMessage(ChatMessage message){
		try{

			String sql = "INSERT INTO chat_history VALUES(?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, message.getMessage());
			ps.setInt(2, message.getSender().getUserID());
			ps.setInt(3, message.getReceiver().getUserID());
			ps.setLong(4, message.getDate().getTime());
			
			ps.executeUpdate();
			
			ps.close();
		} catch(SQLException sqle){
			sqle.printStackTrace();
			return Constants.SERVER_SEND_MESSAGE_FAIL;
		}
		
		return Constants.SERVER_SEND_MESSAGE_SUCCESS;
	}
	
	/*
	 * getAdmins()
	 * Returns a Vector<User> who are all admins
	 * 
	 * Throws GetAdminsException
	 * 
	 * PARAMS: none
	 * RETURNS: Vector<User>
	 */
	public Vector<User> getAdmins() throws GetAdminsException{
		Vector<User> admins = new Vector<User>();
		
		try{
			String sql = "SELECT * FROM users WHERE is_admin=1";
			
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next()){
				//create a admin User and append to admin list
				int user_id = rs.getInt("user_id");
				String full_name = rs.getString("full_name");
				String db_username = rs.getString("username");
				String db_password = rs.getString("password_hash");
				
				boolean is_admin = rs.getBoolean("is_admin");
				int fk_prof_pic = rs.getInt("fk_profile_picture");
				
				User u = new User(full_name, db_username, db_password, is_admin, fk_prof_pic);
				u.setUserID(user_id);
				
				admins.add(u);
			}
			
			rs.close();
			s.close();
			
		} catch(SQLException sqle){
			sqle.printStackTrace();
			throw new GetAdminsException(Constants.SERVER_GET_ADMINS_FAIL);
		}
		
		return admins;
		
	}
	
	/*
	 * updateUser(User user)
	 * Updates an existing user's information based on the new User object's fields
	 * PARAMS: User to update
	 * RETURNS: error/success code
	 */
	public int updateUser(User user){
		
		try{
			
		    String sql = "UPDATE users SET full_name=?, username=?, password_hash=?, is_admin=?, fk_profile_picture=?, WHERE user_id=?";
		    PreparedStatement ps = conn.prepareStatement(sql);
		    
			ps.setString(1, user.getFullName());
			ps.setString(2, user.getUserName());
			ps.setString(3, user.getPassword());
			ps.setBoolean(4, user.isAdmin());
			ps.setInt(5, user.getProfilePictureID());
			ps.setInt(6, user.getUserID());
		      
		    ps.executeUpdate();
			
			ps.close();
			
		} catch(SQLException sqle){
			sqle.printStackTrace();
			return Constants.SERVER_UPDATE_PROFILE_FAIL;
		}
		
		return Constants.SERVER_UPDATE_PROFILE_SUCCESS;
		
	}
	
	
	/*
	 * Setup functions
	 */
	
	public void shutdownDB(){
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Error shuting down the database in shutdownDB()");
			e.printStackTrace();
		}
	}
	
	public void createDatabaseIfDNE(){
        
        try {
			
        	Statement statement = conn.createStatement();
            
            //Search for existing databases
            ResultSet dbsFound = conn.getMetaData().getCatalogs();
            boolean dbExists = false;
            while(dbsFound.next())
            {	        
            	//EventManagerDB exists
            	String databaseName = dbsFound.getString(1);
            	if(databaseName.equals(dbName)){
            		dbExists = true;
            	}
            }
            
            //create the EventMangerDB if it doesnt exist
            int creationResult = 2;
            if(!dbExists){
            	creationResult = statement.executeUpdate("CREATE DATABASE " + dbName);
            	conn.setCatalog(dbName);
            	createEMDBTables();
            	//Seed the database
                System.out.println("Seeding the database");
                this.seed();
            } else {
            	conn.setCatalog(dbName);
            }
            
            //log creation status
            if(creationResult == 1){
            	System.out.println("EventMangerDB Successfully Created");
            	
            } else if(creationResult == 2){
            	System.out.println("EventManagerDB already Exists");
            }
            
            dbsFound.close();
            statement.close();
		} catch (SQLException sqle){
			System.out.println("SQLException in Database Constructor");
			sqle.printStackTrace();
		}
		
	}
	
	public void seed(){
		//Add Profile Pictures
		try{
			Statement statement = conn.createStatement(); 
			String sql = "INSERT INTO profile_pictures VALUES (0, 'src/profile_pictures/boy1')";
			statement.addBatch(sql);
			
			sql = "INSERT INTO profile_pictures VALUES (0, 'src/profile_pictures/boy2')";
			statement.addBatch(sql);
			
			sql = "INSERT INTO profile_pictures VALUES (0, 'src/profile_pictures/man1')";
			statement.addBatch(sql);
			
			sql = "INSERT INTO profile_pictures VALUES (0, 'src/profile_pictures/man2')";
			statement.addBatch(sql);
			
			sql = "INSERT INTO profile_pictures VALUES (0, 'src/profile_pictures/woman1')";
			statement.addBatch(sql);
			
			sql = "INSERT INTO profile_pictures VALUES (0, 'src/profile_pictures/woman2')";
			statement.addBatch(sql);
			
			sql = "INSERT INTO profile_pictures VALUES (0, 'src/profile_pictures/woman3')";
			statement.addBatch(sql);
			
			statement.executeBatch();
			statement.close();
		} catch (SQLException sqle){
			System.out.println("SQLException while seeding profile pictures");
			sqle.printStackTrace();
		}
		
		//Add Users
		try {
			this.registerUser(new User("Joe Blow", "joeb", "password", false, 1));
			this.registerUser(new User("Frank Smith", "fsmith", "password1", true, 2));
			this.registerUser(new User("Tim Sloan", "tsloan", "password2", false, 3));
		} catch (LoginException e) {
			e.printStackTrace();
		}
		
		//Add Events
		this.createEvent(new Event("Event 1", "Bovard Auditorium", new GregorianCalendar(2009, 1, 1, 9, 55).getTime(), "Club1", "An event at Bovard!", 0, 1));
		this.createEvent(new Event("Event 2", "SAL 101", new GregorianCalendar(2010, 2, 1, 10, 55).getTime(), "Club2", "A club event at Sal!", 0, 1));
		this.createEvent(new Event("Event 3", "Galen Center", new GregorianCalendar(2015, 1, 1, 11, 55).getTime(), "Club3", "Club Basketball Game!", 0, 1));
		this.createEvent(new Event("Event 4", "VKC 201", new GregorianCalendar(2015, 3, 13, 14, 0).getTime(), "Club4", "Club Meeting!", 0, 1));
		this.createEvent(new Event("Event 5", "Leavy Library", new GregorianCalendar(2015, 6, 1, 10, 0).getTime(), "Club5", "Study Club!", 0, 1));
		
		
		
		//Add UserEvent Junctions
		User joeb = null, fsmith = null;
		try{
			joeb = this.checkLogin("joeb", "password");
			fsmith = this.checkLogin("fsmith", "password1");
		} catch(LoginException le){
			le.printStackTrace();
		}
		try{
			
			
			//get event
			String s = "SELECT * FROM events LIMIT 1";
			Statement st = conn.createStatement();
			ResultSet tmp_event_result = st.executeQuery(s);
			tmp_event_result.next();
			int event_id = tmp_event_result.getInt("event_id");
			String event_name = tmp_event_result.getString("name");
			String event_club = tmp_event_result.getString("club");
			String event_location = tmp_event_result.getString("location");
			long event_time = tmp_event_result.getLong("time");
			String event_description = tmp_event_result.getString("description");
			int event_attending = tmp_event_result.getInt("num_attending");
			int event_fk_admin = tmp_event_result.getInt("fk_admin");
			
			Event tmp_event = new Event(event_name, event_location, new Date(event_time), event_club, event_description, event_attending, event_fk_admin);
			tmp_event.setID(event_id);
			this.rsvp(joeb, tmp_event);
			
		} catch(SQLException sqle){
			sqle.printStackTrace();
		}
		
		
		//Add ChatHistory
		ChatMessage message1 = new ChatMessage("Hey", joeb, fsmith, new GregorianCalendar(2015, 4, 1, 9, 30).getTime());
		ChatMessage message2 = new ChatMessage("Hey Joe!", fsmith, joeb, new GregorianCalendar(2015, 4, 1, 9, 35).getTime());
		ChatMessage message3 = new ChatMessage("What's Up?", joeb, fsmith, new GregorianCalendar(2015, 4, 1, 9, 40).getTime());
		ChatMessage message4 = new ChatMessage("Not much man", fsmith, joeb, new GregorianCalendar(2015, 4, 1, 9, 42).getTime());
		
		int r1 = this.writeChatMessage(message1);
		int r2 = this.writeChatMessage(message2);
		int r3 = this.writeChatMessage(message3);
		int r4 = this.writeChatMessage(message4);
		
		if((r1 == r2) && (r2 == r3) && (r3 == r4) && (r4 == Constants.SERVER_SEND_MESSAGE_SUCCESS)){
			System.out.println("Succesfully sent messages");
		} else {
			System.out.println("Message Send Failure");
		}
		
		
	}
	
	public void createEMDBTables(){
		
		createEventsTable();
		createUsersTable();
		createUserEventJunctionTable();
		createChatHistoryTable();
		createProfilePicturesTable();
		createTableRelationConstraints();
		
	}
	
	public void createTableRelationConstraints(){
		try {
			Statement statement = conn.createStatement();
			
			//Add admin reference constraint to the event table
			String sql = "ALTER TABLE events ADD CONSTRAINT userReference FOREIGN KEY (fk_admin) REFERENCES users (user_id)";
			statement.addBatch(sql);
			
			sql = "ALTER TABLE users ADD CONSTRAINT profPicReference FOREIGN KEY (fk_profile_picture) REFERENCES profile_pictures (pic_id)";
			statement.addBatch(sql);
			
			//Add user reference and event reference constraints to the user_event_junction table
			sql = "ALTER TABLE user_event_junction ADD CONSTRAINT userRef FOREIGN KEY (fk_user_id) REFERENCES users (user_id)";
			statement.addBatch(sql);
			
			sql = "ALTER TABLE user_event_junction ADD CONSTRAINT eventRef FOREIGN KEY (fk_event_id) REFERENCES events (event_id)";
			statement.addBatch(sql);
			
			statement.executeBatch();
			statement.close();
			
			System.out.println("Created Table Relation constraints");
			
		} catch (SQLException e) {
			System.out.println("SQLException creating table relation constraints in createTableRelationConstraints()");
			e.printStackTrace();
		}
	}
	
	public void createEventsTable(){
		try {
			Statement statement = conn.createStatement();
			
			String sql = "CREATE TABLE events " +
	                   "(" +
	                   "event_id INT AUTO_INCREMENT NOT NULL, " +
	                   "name VARCHAR(200) NOT NULL, " +
	                   "location VARCHAR(200), " +
	                   "time LONG, " +
	                   "club VARCHAR(200), " +
	                   "description TEXT, " +
	                   "num_attending INT, " +
	                   "fk_admin INT, " +
	                   "PRIMARY KEY(event_id)" +
	                   ")";
			
			statement.executeUpdate(sql);
			statement.close();
		    System.out.println("Created events table");
		    
		} catch (SQLException e) {
			System.out.println("SQLException creating Events table in createEventsTable()");
			e.printStackTrace();
		}
	}
	
	public void createUsersTable(){
		try {
			
			Statement statement = conn.createStatement();
			
			String sql = "CREATE TABLE users " +
	                   "(" +
	                   "user_id INT AUTO_INCREMENT NOT NULL, " +
	                   "full_name VARCHAR(100) NOT NULL, " +
	                   "username VARCHAR(20) NOT NULL, " +
	                   "password_hash VARCHAR(256) NOT NULL, " +
	                   "is_admin BOOLEAN NOT NULL, " +
	                   "fk_profile_picture INT, " +
	                   "PRIMARY KEY (user_id)" +
	                   ")";
			
			statement.executeUpdate(sql);
			statement.close();
		    System.out.println("Created users table");
		    
		} catch (SQLException e) {
			System.out.println("SQLException creating Users table in createUsersTable()");
			e.printStackTrace();
		}
	}
	
	public void createUserEventJunctionTable(){
		try {
			Statement statement = conn.createStatement();
			
			String sql = "CREATE TABLE user_event_junction " +
						"(" +
						"fk_user_id INT NOT NULL, " +
						"fk_event_id INT NOT NULL" +
						")";
			
			statement.executeUpdate(sql);
			statement.close();
			System.out.println("Created user_event_junction table");
				
		} catch (SQLException e) {
			System.out.println("SQLException creating UserEventJunction table in createUserEventJunctionTable()");
			e.printStackTrace();
		}
	}
	
	public void createChatHistoryTable(){
		try {
			Statement statement = conn.createStatement();
			
			String sql = "CREATE TABLE chat_history " +
					"(" +
					"message TEXT, " +
					"fk_sender_id INT NOT NULL, " +
					"fk_receiver_id INT NOT NULL, " +
					"time LONG NOT NULL" +
					")";
			
			statement.executeUpdate(sql);
			statement.close();
			System.out.println("Created chat_history table");
			
		} catch (SQLException e) {
			System.out.println("SQLException creating ChatHistory table in createChatHistoryTable()");
			e.printStackTrace();
		}
	}

	public void createProfilePicturesTable(){
		try {
			Statement statement = conn.createStatement();
			
			String sql = "CREATE TABLE profile_pictures " +
					"(" +
					"pic_id INT AUTO_INCREMENT NOT NULL, " +
					"file_path VARCHAR(200) NOT NULL, " +
					"PRIMARY KEY (pic_id)" +
					")";
		
			statement.executeUpdate(sql);
			statement.close();
			System.out.println("Created profile_pictures table");
			
		} catch (SQLException e) {
			System.out.println("SQLException creating ProfilePictures table in createProfilePicturesTable()");
			e.printStackTrace();
		}
	}
	
	public static void main(String [] args){
		Database db;
		try {
			db = new Database("localhost", true);
			
			//Login users
			User joeb = null, fsmith = null;
			try{
				joeb = db.checkLogin("joeb", "password");
				fsmith = db.checkLogin("fsmith", "password1");
			} catch(LoginException le){
				le.printStackTrace();
			}
			
			//get chat messages between 2 users
			try {
				Vector<ChatMessage> messages = db.getChatHistory(joeb, fsmith);
				for(ChatMessage m : messages){
					System.out.println(m.getSender().getUserName() + " to " + m.getReceiver().getUserName() + " at " + m.getDate() +": " + m.getMessage());
				}
			} catch (GetChatHistoryException e) {
				e.printStackTrace();
			}
			
			//get a user's events
			try {
				Vector<Event> joes_events = db.getUserEventVector(joeb);
				System.out.println(joeb.getFullName() + "'s Events:");
				for(Event e : joes_events){
					System.out.println(e.getName());
				}
				System.out.println();
			} catch (GetEventException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//get the event feed
			Vector<Event> feed;
			try {
				feed = db.getEventFeed();
				System.out.println("Event Feed:");
				for(Event e : feed){
					System.out.println(e.getName());
				}
				System.out.println();
			} catch (GetEventException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//get admins
			try {
				Vector<User> admins = db.getAdmins();
				System.out.println("Admins:");
				for(User a : admins){
					System.out.println(a.getFullName() + " is an admin");
				}
				System.out.println();
			} catch (GetAdminsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			db.shutdownDB();
		} catch (LoginException e) {
			System.out.println("DB not found on IP Address");
			e.printStackTrace();
		}
		
	}
	
}
