package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import main.Event;
import client.ClientGetEventFeedThread;

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
		
		ClientGetEventFeedThread feedThread = new ClientGetEventFeedThread();
		feedThread.start();
		Vector<Event> eventFeed = feedThread.getEventFeed();
		
		DefaultListModel<Event> listModel = new DefaultListModel<>();
		for(Event e : eventFeed){
			listModel.addElement(e);
		}
		
		JList<Event> list = new JList<Event>(listModel);
		UserEventFeedPanel firstPanel = new UserEventFeedPanel(list);
		firstPanel.setBounds(20, 6, 285, 349);
		panel1.add(firstPanel);
		tabbedPane.addTab("Event Feed", null, panel1, null);

		JPanel panel2 = new JPanel();
		panel2.setLayout(null);
		String[] nameList2 = { "Kieran Strolorz", "John Pugliesi", "Zack Kim",
				"Vincent Jin", "Jeffrey Miller", "Ryan Chase" };
		JList list2 = new JList(nameList2);
		UserChatPanel secondPanel = new UserChatPanel(nameList2, list2);
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
