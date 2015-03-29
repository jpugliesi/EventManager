package gui;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MyProfilePanel extends JPanel {
	private JPanel namePanel, namesPanel, buttonPanel, bottomPanel, eventPanel;
	private ImageIcon profilePicture;
	private JLabel username, userFullName, eventListLabel, userPP;
	private JButton logoutButton, editButton;
	private JList eventList;
	private JScrollPane jsp;
	
	
	public MyProfilePanel(){ //this constructor should take an int as the userID
		namePanel = new JPanel();
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		eventPanel = new JPanel();
		eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.Y_AXIS));
		namesPanel = new JPanel();
		namesPanel.setLayout(new BoxLayout(namesPanel, BoxLayout.Y_AXIS));
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		logoutButton = new JButton("Log out");
		editButton = new JButton("Edit profile");
		buttonPanel.add(logoutButton);
		buttonPanel.add(editButton);
		username = new JLabel ("username: TTroj");
		userFullName = new JLabel ("Full Name: Tommy Trojan");
		eventListLabel = new JLabel ("Events");
	
		profilePicture = new ImageIcon("icon.gif");
		userPP = new JLabel(profilePicture);
		
		namesPanel.add(username);
		namesPanel.add(userFullName);
		
		namePanel.add(userPP);
		namePanel.add(namesPanel);
		
		String [] events = {"ACM Meeting", "Trojan Trading Group", "SCuppers", "Club Ultimate Frisbee Pickup"};
		
		eventList = new JList<String>(events);
		
		jsp = new JScrollPane(eventList);
		eventPanel.add(eventListLabel);
		eventPanel.add(jsp);
		
		bottomPanel.add(eventPanel);
		bottomPanel.add(buttonPanel);
		
		add(namePanel);
		add(bottomPanel);
		
		
		
	}
}
