package gui;

import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import client.ClientGetAdminEventsThread;
import constants.Environment;
import main.Event;

public class ManageEventDialog extends JDialog {
	
	private final JPanel mainPanel = new JPanel();
	private UserEventFeedPanel eventList;
	private JList<Event> eventJList;
	private DefaultListModel<Event> listModel;
	private JLabel chooseEventLabel;
	private Vector<Event> ev;
	
	
	
	public ManageEventDialog(){
		chooseEventLabel = new JLabel("Choose an Event to edit below");
		ClientGetAdminEventsThread client = new ClientGetAdminEventsThread(Environment.currentAdmin);
		client.start();
		
		ev = client.getAdminEvent();
		listModel = new DefaultListModel<Event>();
		
		for (Event e : ev){
			listModel.addElement(e);
		}
		
		eventJList = new JList<Event>(listModel);
		
		eventList = new UserEventFeedPanel(eventJList);
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(chooseEventLabel);
		mainPanel.add(eventList);
		
		this.add(mainPanel);
		this.setSize(400,400);
		this.setLocation(20,20);
		
		
		
	}
	
	

}
