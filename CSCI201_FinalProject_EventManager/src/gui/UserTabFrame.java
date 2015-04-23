package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import main.Event;
import main.User;
import client.ClientGetAdminsThread;
import client.ClientGetEventFeedThread;
import client.ClientUpdateProfileThread;

public class UserTabFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// UserEventPanel frame = new UserEventPanel();
	// frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	/**
	 * Create the frame.
	 */
	public UserTabFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 356, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		JPanel panel1 = new JPanel();
		panel1.setLayout(null);
		//creates a new thread, gets the events from the thread
		ClientGetEventFeedThread feedThread = new ClientGetEventFeedThread(); 																    		
		feedThread.start(); 
		Vector<Event> eventFeed = feedThread.getEventFeed();
		constants.Environment.eventFeed = eventFeed;
		//and adds it to our local listmodel
		DefaultListModel<Event> listModel = new DefaultListModel<>();
		for(Event e : eventFeed){
			listModel.addElement(e);
		}
		
		JList<Event> list = new JList<Event>(listModel);
		UserEventFeedPanel firstPanel = new UserEventFeedPanel(list);
		firstPanel.setBounds(20, 6, 285, 349);
		panel1.add(firstPanel);
		tabbedPane.addTab("Event Feed", null, panel1, null);

		
		//get chat display
		JPanel panel2 = new JPanel();
		panel2.setLayout(null);

		ClientGetAdminsThread chatThread = new ClientGetAdminsThread();
		chatThread.start();
		Vector<User> chatFeed = chatThread.getAdmins();
		
		DefaultListModel<User> listModel2 = new DefaultListModel<>();
		for(User u : chatFeed){
			u.setProfilePicture();
			
			listModel2.addElement(u);
		}
		JList<User> list2 = new JList<User>(listModel2);
		UserChatPanel secondPanel = new UserChatPanel(list2);
		secondPanel.setBounds(20, 6, 285, 349);
		panel2.add(secondPanel);
		tabbedPane.addTab("Chat", null, panel2, null);

		JPanel panel3 = new JPanel();
		tabbedPane.addTab("My Profile", null, panel3, null);
		panel3.setLayout(null);

		JLabel profileName = new JLabel("Zack Kim");
		profileName.setHorizontalAlignment(SwingConstants.LEFT);
		profileName.setFont(new Font("Helvetica Neue", Font.BOLD, 21));
		profileName.setBounds(17, 31, 124, 33);
		panel3.add(profileName);

		JLabel lblPicturePlaceholder = new JLabel("Picture PlaceHolder");
		lblPicturePlaceholder.setIcon(new ImageIcon(UserTabFrame.class
				.getResource("/gui/icon.gif")));
		lblPicturePlaceholder.setBounds(240, 20, 65, 44);
		panel3.add(lblPicturePlaceholder);

		JButton editProfileButton = new JButton("Edit Profile");
		
		editProfileButton.setBounds(211, 76, 94, 29);
		editProfileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JDialog jd = new JDialog();
				jd.setSize(350,300);
				jd.setLocation(400,50);
				jd.setTitle("Edit Profile");
				JLabel label1 = new JLabel("fullname");
				JLabel label2 = new JLabel("username");
				JLabel label3 = new JLabel("password");
				JTextField jtf1 = new JTextField(20);
				JTextField jtf2 = new JTextField(20);
				JTextField jtf3 = new JTextField(20);
				JPanel jp1 = new JPanel();
				JPanel jp2 = new JPanel();
				JPanel jp3 = new JPanel();
				jp1.add(label1); jp1.add(jtf1);
				jp1.add(label2); jp1.add(jtf2);
				jp1.add(label3); jp1.add(jtf3);
				
				JPanel mainPanel = new JPanel();
				mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
				mainPanel.add(jp1);
				mainPanel.add(jp2);
				mainPanel.add(jp3);
				
				JButton bt = new JButton("Submit");
				//upon clicking the "Submit" button, update GUI and initiate thread
				bt.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae){
						String fullname = jtf1.getText();
						String username = jtf2.getText();
						String password = jtf3.getText();
						//update GUI
						profileName.setText(fullname);
						//create new User
						User newUser = new User(fullname, username, password, false, 1);
						//initiate update thread
						ClientUpdateProfileThread updateThread = new ClientUpdateProfileThread(newUser);
						updateThread.start();
						jd.dispose();
					}
				});
				jd.add(mainPanel);
				jd.add(bt, BorderLayout.SOUTH);
				
				jd.setModal(true);
				jd.setVisible(true);
			}
		});
		panel3.add(editProfileButton);

		JButton btnLogout = new JButton("Logout");
		btnLogout.setBounds(208, 367, 117, 29);
		panel3.add(btnLogout);

		JLabel lblEventsAttended = new JLabel("Events Attended");
		lblEventsAttended.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
		lblEventsAttended.setBounds(17, 120, 139, 16);
		panel3.add(lblEventsAttended);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(17, 148, 198, 207);
		panel3.add(scrollPane);

		JLabel descriptionLabel = new JLabel(
				"<html>Sophomore Computer Science Major. Go Trojans!</html>");
		descriptionLabel.setHorizontalAlignment(SwingConstants.LEFT);
		descriptionLabel.setFont(new Font("Helvetica Neue", Font.ITALIC, 11));
		descriptionLabel.setVerticalAlignment(SwingConstants.TOP);
		descriptionLabel.setBounds(17, 76, 166, 43);
		panel3.add(descriptionLabel);
	}
}
