package gui;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;

import main.Event;

public class ManageEventDialog extends JDialog {
	
	private final JPanel mainPanel = new JPanel();
	private UserEventFeedPanel eventList;
	private JList<Event> eventJList;
	private DefaultListModel<Event> listModel;
	
	
	
	public ManageEventDialog(){
		
	}
	

}
