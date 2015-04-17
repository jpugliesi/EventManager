package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.GregorianCalendar;
import java.util.Vector;

import main.LoginException;
import constants.Constants;
import db.Database;
import test.TestServer;

public class Server {
	
	private Vector<ServerThread> stVector = new Vector<ServerThread>();
	private Database db;
	
	
	public Server(){
		ServerSocket ss = null;
		try{
			db = new Database("localhost", true);
			System.out.println("Starting Server");
			ss = new ServerSocket(6789);
			while(true){
				System.out.println("Waiting for client to connect...");
				Socket s = ss.accept();
				System.out.println("Client " + s.getInetAddress() + ":" + s.getPort() + " connected");
				ServerThread st = new ServerThread(s, this, db);
				stVector.add(st);
				st.start();
			}
			
		} catch (LoginException le){
			
		}
		catch(IOException ioe){
			System.out.println("IOE in server constructor: " + ioe.getMessage());
		} finally {
			if (ss != null) {
				try {
					ss.close();
				} catch (IOException ioe) {
					System.out.println("IOE closing ServerSocket: " + ioe.getMessage());
				}
			}
		}
		
		
	}
	
	public void removeServerThread(ServerThread st) {
		stVector.remove(st);
	}
	
	//needs to be changed - send code only to the client(s) listening
	public void sendMessageToClients(int n) {
		for (ServerThread st1 : stVector) {
			st1.sendCode(n);	
		}
	}
		
	public Vector<String> getConnectedClients(){
		Vector<String> ret = new Vector<String>();
		
		for(ServerThread st : stVector){
			ret.add(st.getUsername());
		}
		
		return ret;
	}
	
	public static void main(String [] args){
		new Server();
	}

}
