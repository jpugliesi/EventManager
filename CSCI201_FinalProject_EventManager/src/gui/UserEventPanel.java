package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.JList;

public class UserEventPanel extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					UserEventPanel frame = new UserEventPanel();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public UserEventPanel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 356, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		panel.setLayout(null);

		String[] nameList = { "BlackStone LaunchPad", "Google USC Hiring Workshop", "HACKSC", "UX Designathon", "USC Grief Entrepreneur MeetUp sdaakjsdfaslkjfhaslkjdhfalhfas",
		"CS201 Presentations" };
		JList list = new JList(nameList); 
		UserEventFeedPanel firstPanel= new UserEventFeedPanel(nameList, list);
		firstPanel.setBounds(20, 6, 285, 349);
		panel.add(firstPanel);
		tabbedPane.addTab("Event Feed", null, panel, null);

		JPanel panel2 = new JPanel();
		panel2.setLayout(null);
		String[] nameList2 = { "Kieran Strolorz", "John Pugliesi", "Zack Kim", "Vincent Jin", "Jeffrey Miller",
		"Ryan Chase" };
		JList list2 = new JList(nameList2); 
		UserChatPanel secondPanel = new UserChatPanel(nameList2,list2);
		secondPanel.setBounds(20, 6, 285, 349);
		panel2.add(secondPanel);
		tabbedPane.addTab("Chat", null, panel2, null);

		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("My Profile", null, panel_2, null);
	}

	


}
