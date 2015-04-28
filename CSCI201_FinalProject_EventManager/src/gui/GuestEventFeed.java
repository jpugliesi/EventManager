package gui;

import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;

import client.ClientGetEventFeedThread;
import main.Event;





public class GuestEventFeed extends JDialog{
	private JPanel mainPanel;
	private Vector<Event> eventFeed;
	private DefaultListModel dlm;
	private JList feedJL;
	private UserEventFeedPanel jsp;
	
	
	public GuestEventFeed(){
		setSize(350,500);
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		
		ClientGetEventFeedThread feedThread = new ClientGetEventFeedThread();
		feedThread.start();
		
		eventFeed = feedThread.getEventFeed();
		dlm = new DefaultListModel<>();
		
		for(Event e : eventFeed){
			dlm.addElement(e);
		}
		feedJL = new JList<Event>(dlm);
		
		jsp = new UserEventFeedPanel(feedJL);
		jsp.setBounds(20, 6, 285, 349);
		mainPanel.add(jsp);
		this.add(mainPanel);
		setVisible(true);

	}
	
	public static void main(String [] args){
		new GuestEventFeed();
	}

}
