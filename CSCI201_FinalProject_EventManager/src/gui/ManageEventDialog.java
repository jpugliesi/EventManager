package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
	private UserEventFeedPanel eventList = null;
	private JList<Event> eventJList = null;
	private DefaultListModel<Event> listModel = null;
	private JLabel chooseEventLabel = null;
	private Vector<Event> ev = null;
	
	
	
	public ManageEventDialog(){
		
		System.out.println("NEW MANAGE EVENT DIALOG");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		chooseEventLabel = new JLabel("Choose an Event to edit below");
		ClientGetAdminEventsThread client = new ClientGetAdminEventsThread(Environment.currentAdmin);
		client.start();
		while(!client.finished()){
			
		}
		
		ev = client.getAdminEvent();
		System.out.println("MANAGE EVENT NAME: " +  ev.get(0).getName());
		listModel = new DefaultListModel<Event>();
		
		for (Event e : ev){
			listModel.addElement(e);
		}
		
		eventJList = new JList<Event>(listModel);
		
		eventList = new UserEventFeedPanel(eventJList);
		
		setActionListeners();
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(chooseEventLabel);
		mainPanel.add(eventList);
		
		this.add(mainPanel);
		this.setSize(400,400);
		this.setLocation(20,20);
		
	}
	
	private void setActionListeners(){
		
		eventJList.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        if (evt.getClickCount() == 2) {

		            // Double-click detected
		            int index = eventJList.locationToIndex(evt.getPoint());
		            Event e = eventJList.getSelectedValue();
		            new CreateEventDialog(e).setVisible(true);
		            dispose();
		        }
		    }
		});
		
	}
	
	

}
