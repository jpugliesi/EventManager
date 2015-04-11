package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.GregorianCalendar;

import main.Event;
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
	public Database(String ipAddress) throws LoginException{
		try {
			
			Class.forName(jdbcDriver);
			String dbFullAddress = dbAddress + ipAddress + ":" + dbPort + "/";
			conn = DriverManager.getConnection(dbFullAddress, dbUser, dbPassword);

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
					PreparedStatement find_events_ps;
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
					}
				}
			}
			
		} catch (SQLException sqle){
			System.out.println("SQLException while checking login in checkLogin()");
			sqle.printStackTrace();
		}
		
		return user;
		
	}
	
	public int registerUser(User user){

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
				return Constants.SERVER_REGISTRATION_USERNAME_FAIL;
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
			
		} catch (SQLException e) {
			//error in register user
			System.out.println("SQLException in registerUser()");
			e.printStackTrace();
			return Constants.SERVER_CREATE_EVENT_FAIL;
		}
		
		return Constants.SERVER_REGISTRATION_SUCCESS;
	}
	
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
	 * Setup functions
	 * 
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
            } else {
            	conn.setCatalog(dbName);
            }
            
            //log creation status
            if(creationResult == 1){
            	System.out.println("EventMangerDB Successfully Created");
            } else if(creationResult == 2){
            	System.out.println("EventManagerDB already Exists");
            }
            
            //Seed the database
            System.out.println("Seeding the database");
            this.seed();
             
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
		this.registerUser(new User("Joe Blow", "joeb", "password", false, 1));
		this.registerUser(new User("Frank Smith", "fsmith", "password1", true, 2));
		this.registerUser(new User("Tim Sloan", "tsloan", "password2", false, 3));
		
		//Add Events
		this.createEvent(new Event("Event 1", "Bovard Auditorium", new GregorianCalendar(2009, 1, 1, 9, 55).getTime(), "Club1", "An event at Bovard!", 0, 1));
		this.createEvent(new Event("Event 2", "SAL 101", new GregorianCalendar(2010, 2, 1, 10, 55).getTime(), "Club2", "A club event at Sal!", 0, 1));
		this.createEvent(new Event("Event 3", "Galen Center", new GregorianCalendar(2015, 1, 1, 11, 55).getTime(), "Club3", "Club Basketball Game!", 0, 1));
		this.createEvent(new Event("Event 4", "VKC 201", new GregorianCalendar(2015, 3, 13, 14, 0).getTime(), "Club4", "Club Meeting!", 0, 1));
		this.createEvent(new Event("Event 5", "Leavy Library", new GregorianCalendar(2015, 6, 1, 10, 0).getTime(), "Club5", "Study Club!", 0, 1));
		
		
		
		//Add UserEvent Junctions
		try{
			User joeb = this.checkLogin("joeb", "password");
			
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
			
		} catch(LoginException le){
			le.printStackTrace();
		} catch(SQLException sqle){
			sqle.printStackTrace();
		}
		
		
		//Add ChatHistory
		
		
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
					"time INT NOT NULL" +
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
			db = new Database("localhost");
			
			User joe = new User("Joe Cool", "jcool", "23461543", true, 1);
			//db.registerUser(joe);
			db.shutdownDB();
		} catch (LoginException e) {
			System.out.println("DB not found on IP Address");
			e.printStackTrace();
		}
		
	}
	
	
	
}
