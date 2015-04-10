package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	
	private static String jdbcDriver = "com.mysql.jdbc.Driver";
	private static String dbAddress = "jdbc:mysql://localhost:3306/";
    private static String dbName = "EventManagerDB";
    private static String dbUser = "root";
    private static String dbPassword = "";
    
    private Connection conn;
	
	public Database(){
		try {
			
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(dbAddress, dbUser, dbPassword);

			createDatabaseIfDNE();
	           
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException in Database Constructor");
			e.printStackTrace();
		} catch (SQLException sqle){
			System.out.println("SQLException in Database Constructor");
			sqle.printStackTrace();
		}
        
	}
	
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
	                   "title VARCHAR(200) NOT NULL, " +
	                   "club VARCHAR(200), " +
	                   "location VARCHAR(200), " +
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
	                   "username VARCHAR(20) NOT NULL, " +
	                   "password_hash VARCHAR(256) NOT NULL, " +
	                   "full_name VARCHAR(100) NOT NULL, " +
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
		Database db = new Database();
		db.shutdownDB();
	}
	
	
	
}
