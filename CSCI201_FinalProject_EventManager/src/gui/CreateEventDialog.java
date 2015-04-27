package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import constants.Environment;
import main.Event;
import client.ClientCreateEventThread;

public class CreateEventDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField clubNameTF;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			createEventDialog dialog = new createEventDialog();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	//create event
	public CreateEventDialog() {
		setTitle("Create Event");
		setBounds(100, 100, 450, 511);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JComboBox<String> comboBox;
		JComboBox<String> dayComboBox;
		JComboBox<String> hourComboBox;
		JComboBox<String> minuteComboBox;
		JButton okButton;
		
		contentPanel.setLayout(null);
		{
			JLabel lblEventName = new JLabel("Event Title");
			lblEventName.setBounds(16, 171, 77, 22);
			contentPanel.add(lblEventName);
		}
		
		textField = new JTextField();
		textField.setFont(new Font("Helvetica Neue", Font.PLAIN, 15));
		textField.setBounds(203, 166, 241, 33);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JLabel lblEventDescription = new JLabel("Description");
		lblEventDescription.setBounds(16, 262, 117, 16);
		contentPanel.add(lblEventDescription);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Helvetica Neue", Font.PLAIN, 15));
		textArea.setLineWrap(true);
		textArea.setBounds(213, 262, 228, 91);
		contentPanel.add(textArea);
		{
			String months[] = { "January", "February", "March", "April", "May", "June", "July", "August","September", "October","November", "December"};
			
			comboBox = new JComboBox(months);
			comboBox.setBounds(122, 386, 117, 27);
			contentPanel.add(comboBox);
			
		}
		{
			JLabel lblDateAndTime = new JLabel("Date and Time");
			lblDateAndTime.setBounds(16, 390, 107, 16);
			contentPanel.add(lblDateAndTime);
		}
		{
			String days[] = { "1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
			dayComboBox = new JComboBox(days);
			dayComboBox.setBounds(244, 386, 66, 27);
			contentPanel.add(dayComboBox);
		}
		{
			JLabel logoIcon = new JLabel("");
			logoIcon.setBounds(6, 6, 352, 102);
			 try {
				 BufferedImage image=null;
				 image = ImageIO.read(getClass().getResource("Title_Admin.jpeg"));
				 image= resizeImage( image, 325, 100, image.TYPE_INT_RGB);
				 logoIcon.setIcon(new ImageIcon(image));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			contentPanel.add(logoIcon);
		}
		{
			String hours[] = { "1","2","3","4","5","6","7","8","9","10","11", "12", "13","14", "15","16","17",
					"18", "19","20", "21", "22", "23", "24"};
			hourComboBox = new JComboBox(hours);
			hourComboBox.setBounds(322, 386, 61, 27);
			contentPanel.add(hourComboBox);
		}
		{
			String minute[] = { "00","15","30","45"};
			minuteComboBox = new JComboBox(minute);
			minuteComboBox.setBounds(378, 386, 66, 27);
			contentPanel.add(minuteComboBox);
		}
		ButtonGroup group= new ButtonGroup();
		JRadioButton am = new JRadioButton("AM");
		am.setSelected(true);
		am.setBounds(338, 421, 52, 23);
		contentPanel.add(am);
		
		JRadioButton pm = new JRadioButton("PM");
		pm.setBounds(392, 421, 52, 23);
		
		group.add(am);
		group.add(pm);
		contentPanel.add(pm);
		{
			JLabel clubNameLabel = new JLabel("Club Name");
			clubNameLabel.setBounds(16, 120, 89, 16);
			contentPanel.add(clubNameLabel);
		}
		{
			clubNameTF = new JTextField();
			clubNameTF.setFont(new Font("Helvetica Neue", Font.PLAIN, 15));
			clubNameTF.setBounds(203, 114, 241, 33);
			contentPanel.add(clubNameTF);
			clubNameTF.setColumns(10);
		}
		{
			JLabel locationLabel = new JLabel("Location");
			locationLabel.setBounds(16, 223, 61, 16);
			contentPanel.add(locationLabel);
		}
		{
			textField_1 = new JTextField();
			textField_1.setFont(new Font("Helvetica Neue", Font.PLAIN, 15));
			textField_1.setBounds(203, 212, 241, 33);
			contentPanel.add(textField_1);
			textField_1.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		
		
		
		//initiate create event thread
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Integer month = 1;
				String m = (String) comboBox.getSelectedItem();
				//convert string month to integer month
				if (m.equals("January"))
					month = 1;
				else if (m.equals("February"))
					month = 2;
				else if (m.equals("March"))
					month = 3;
				else if (m.equals("April"))
					month = 4;
				else if (m.equals("May"))
					month = 5;
				else if (m.equals("June"))
					month = 6;
				else if (m.equals("July"))
					month = 7;
				else if (m.equals("August"))
					month = 8;
				else if (m.equals("September"))
					month = 9;
				else if (m.equals("October"))
					month = 10;
				else if (m.equals("November"))
					month = 11;
				else if (m.equals("December"))
					month = 12;
				
				String d = (String) dayComboBox.getSelectedItem();
				Integer day = Integer.parseInt(d);
				
				String h = (String) hourComboBox.getSelectedItem();
				Integer hour = Integer.parseInt(h);
				
				String mi = (String) minuteComboBox.getSelectedItem();
				Integer minute = Integer.parseInt(mi);
				Date eventTime = new GregorianCalendar(2015, month , day, hour, minute).getTime();	
				String eventName = textField.getText();
				String eventLocation = textField_1.getText();
				String eventClub = clubNameTF.getText();
				String eventDesc = textArea.getText();
				
				//create the event's Event object
				Event ne = new Event(eventName, eventLocation, eventTime, eventClub, eventDesc, 0, Environment.currentAdmin.getUserID());
				
				ClientCreateEventThread createThread = new ClientCreateEventThread(ne);
				createThread.start();
				dispose();
			}
		});		
	}
	
	//manage event
	public CreateEventDialog(String eventTitle, String eventDescription) {
		setTitle("Manage Event");
		setBounds(100, 100, 450, 398);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblEventName = new JLabel("Event Title");
			lblEventName.setBounds(16, 120, 77, 22);
			contentPanel.add(lblEventName);
		}
		
		textField = new JTextField("Title");
		textField.setBounds(203, 115, 241, 33);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JLabel lblEventDescription = new JLabel("Description");
		lblEventDescription.setBounds(16, 182, 117, 16);
		contentPanel.add(lblEventDescription);
		
		JTextArea textArea = new JTextArea("HELLO");
		textArea.setLineWrap(true);
		textArea.setBounds(203, 168, 228, 82);
		contentPanel.add(textArea);
		{
			String months[] = { "January", "February", "March", "April", "May", "June", "July", "August","September", "October","November", "December"};
			
			JComboBox comboBox = new JComboBox(months);
			comboBox.setBounds(127, 268, 117, 27);
			contentPanel.add(comboBox);
			
		}
		{
			JLabel lblDateAndTime = new JLabel("Date and Time");
			lblDateAndTime.setBounds(16, 272, 107, 16);
			contentPanel.add(lblDateAndTime);
		}
		{
			String days[] = { "1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
			JComboBox daycomboBox = new JComboBox(days);
			daycomboBox.setBounds(239, 268, 66, 27);
			contentPanel.add(daycomboBox);
		}
		{
			JLabel logoIcon = new JLabel("");
			logoIcon.setBounds(6, 6, 352, 102);
			 try {
				 BufferedImage image=null;
				 image = ImageIO.read(getClass().getResource("Title_Admin.jpeg"));
				 image= resizeImage( image, 325, 100, image.TYPE_INT_RGB);
				 logoIcon.setIcon(new ImageIcon(image));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			contentPanel.add(logoIcon);
		}
		{
			String hours[] = { "1","2","3","4","5","6","7","8","9","10","11", "12","13","14", "15","16","17",
					"18", "19","20", "21", "22", "23", "24"};
			JComboBox hourComboBox = new JComboBox(hours);
			hourComboBox.setBounds(317, 268, 61, 27);
			contentPanel.add(hourComboBox);
		}
		{
			String minute[] = { "00","15","30","45"};
			JComboBox minuteComboBox = new JComboBox(minute);
			minuteComboBox.setBounds(378, 268, 66, 27);
			contentPanel.add(minuteComboBox);
		}
		ButtonGroup group= new ButtonGroup();
		JRadioButton am = new JRadioButton("AM");
		am.setBounds(342, 307, 52, 23);
		contentPanel.add(am);
		
		JRadioButton pm = new JRadioButton("PM");
		pm.setBounds(389, 307, 52, 23);
		
		group.add(am);
		group.add(pm);
		contentPanel.add(pm);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	 public BufferedImage resizeImage(BufferedImage originalImage, int width, int height, int type) throws IOException {  
	        BufferedImage resizedImage = new BufferedImage(width, height, type);  
	        Graphics2D g = resizedImage.createGraphics();  
	        g.drawImage(originalImage, 0, 0, width, height, null);  
	        g.dispose();  
	        return resizedImage;  
	    }  
}