package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import main.LoginException;
import db.Database;

public class Server {
	
	private Vector<ServerThread> stVector = new Vector<ServerThread>();
	private Vector<ServerThread> listenVector = new Vector<ServerThread>();
	private Database db;
	private ServerSocket ss1 = null;
	
	private class AcceptThread extends Thread{
		private Server server;
		public AcceptThread(Server s){
			server = s;
		}
		public void run(){
			
			try{
				while(true){
					Socket s = ss1.accept();
					ServerThread st = new ServerThread(s, server, db);
					listenVector.add(st);
					st.start();
				}
			}catch(IOException ioe){
				System.out.println("IOE in acceptthread.run(): " + ioe.getMessage());
			}
		}
	}
	
	
	public Server(){
		ServerSocket ss = null;
		
		try{
			db = new Database("localhost", true);
			System.out.println("Starting Server");
			ss = new ServerSocket(6789);
			ss1 = new ServerSocket(6790);
			
			AcceptThread at = new AcceptThread(this);
			at.start();
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
		for (ServerThread st1 : listenVector) {
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

