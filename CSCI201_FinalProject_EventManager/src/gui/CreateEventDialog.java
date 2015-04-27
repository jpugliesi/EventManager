package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

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

import main.Event;
import main.LoginException;
import client.ClientCreateEventThread;
import constants.Environment;
import db.Database;

public class CreateEventDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField title_field;
	private JTextField club_field;
	private JTextField location_field;
	private JComboBox<String> month_comboBox;
	private JTextArea description_field;
	
	private JComboBox<String> day_comboBox;
	private JComboBox<String> hour_comboBox;
	private JComboBox<String> minute_comboBox;
	private JButton okButton;
	private JRadioButton am;
	private JRadioButton pm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Database db;
			db = new Database("localhost", true);
			
			Vector<Event> events = db.getEventFeed();
			
			Event e = events.get(0);
			
			CreateEventDialog dialog = new CreateEventDialog(e);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (LoginException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	//create event
	public CreateEventDialog() {
		setupDialog();
	}
	
	public CreateEventDialog(Event event){
		setupDialog();
		
		String event_name = event.getName();
		String event_club = event.getClub();
		String event_description = event.getDescription();
		String event_location = event.getLocation();
		Date event_time = event.getTime();
				
		Calendar cal = Calendar.getInstance();
		cal.setTime(event_time);
		String month = getMonthNameFromIndex(cal.get(Calendar.MONTH));
		String date = String.valueOf(cal.get(Calendar.DATE));
		String year = String.valueOf(cal.get(Calendar.YEAR));
		String hours = String.valueOf(cal.get(Calendar.HOUR_OF_DAY) + 1);
		String minute = String.valueOf(cal.get(Calendar.MINUTE));
		boolean is_am = true;
		if (cal.get(Calendar.AM_PM) == Calendar.PM) {
			is_am = false;
		}
		
		title_field.setText(event_name);
		club_field.setText(event_club);
		description_field.setText(event_description);
		location_field.setText(event_location);
		
		month_comboBox.setSelectedItem(month);
		day_comboBox.setSelectedItem(date);
		hour_comboBox.setSelectedItem(hours);
		minute_comboBox.setSelectedItem(minute);
		if(is_am){
			am.setSelected(true);
			pm.setSelected(false);
		} else {
			am.setSelected(false);
			pm.setSelected(true);
		}

	}
	
	private String getMonthNameFromIndex(int index){
		switch(index){
			case 0: return "January";
			case 1: return "February";
			case 2: return "March";
			case 3: return "April";
			case 4: return "May";
			case 5: return "June";
			case 6: return "July";
			case 7: return "August";
			case 8: return "September";
			case 9: return "October";
			case 10: return "November";
			case 11: return "December";
			
		}
		return "";
	}
	
	private void setupDialog(){
		setTitle("Create Event");
		setBounds(100, 100, 450, 511);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		
		contentPanel.setLayout(null);
		{
			JLabel lblEventName = new JLabel("Event Title");
			lblEventName.setBounds(16, 171, 77, 22);
			contentPanel.add(lblEventName);
		}
		
		title_field = new JTextField();
		title_field.setFont(new Font("Helvetica Neue", Font.PLAIN, 15));
		title_field.setBounds(203, 166, 241, 33);
		contentPanel.add(title_field);
		title_field.setColumns(10);
		
		JLabel lblEventDescription = new JLabel("Description");
		lblEventDescription.setBounds(16, 262, 117, 16);
		contentPanel.add(lblEventDescription);
		
		description_field = new JTextArea();
		description_field.setFont(new Font("Helvetica Neue", Font.PLAIN, 15));
		description_field.setLineWrap(true);
		description_field.setBounds(213, 262, 228, 91);
		contentPanel.add(description_field);
		
		{
			String months[] = { "January", "February", "March", "April", "May", "June", "July", "August","September", "October","November", "December"};
			
			month_comboBox = new JComboBox<String>(months);
			month_comboBox.setBounds(122, 386, 117, 27);
			contentPanel.add(month_comboBox);
			
		}
		{
			JLabel lblDateAndTime = new JLabel("Date and Time");
			lblDateAndTime.setBounds(16, 390, 107, 16);
			contentPanel.add(lblDateAndTime);
		}
		{
			String days[] = { "1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
			day_comboBox = new JComboBox<String>(days);
			day_comboBox.setBounds(244, 386, 66, 27);
			contentPanel.add(day_comboBox);
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
			hour_comboBox = new JComboBox<String>(hours);
			hour_comboBox.setBounds(322, 386, 61, 27);
			contentPanel.add(hour_comboBox);
		}
		{
			String minute[] = { "00","15","30","45"};
			minute_comboBox = new JComboBox<String>(minute);
			minute_comboBox.setBounds(378, 386, 66, 27);
			contentPanel.add(minute_comboBox);
		}
		ButtonGroup group= new ButtonGroup();
		am = new JRadioButton("AM");
		am.setSelected(true);
		am.setBounds(338, 421, 52, 23);
		contentPanel.add(am);
		
		pm = new JRadioButton("PM");
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
			club_field = new JTextField();
			club_field.setFont(new Font("Helvetica Neue", Font.PLAIN, 15));
			club_field.setBounds(203, 114, 241, 33);
			contentPanel.add(club_field);
			club_field.setColumns(10);
		}
		{
			JLabel locationLabel = new JLabel("Location");
			locationLabel.setBounds(16, 223, 61, 16);
			contentPanel.add(locationLabel);
		}
		{
			location_field = new JTextField();
			location_field.setFont(new Font("Helvetica Neue", Font.PLAIN, 15));
			location_field.setBounds(203, 212, 241, 33);
			contentPanel.add(location_field);
			location_field.setColumns(10);
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
				String m = (String) month_comboBox.getSelectedItem();
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
				
				String d = (String) day_comboBox.getSelectedItem();
				Integer day = Integer.parseInt(d);
				
				String h = (String) hour_comboBox.getSelectedItem();
				Integer hour = Integer.parseInt(h);
				
				String mi = (String) minute_comboBox.getSelectedItem();
				Integer minute = Integer.parseInt(mi);
				Date eventTime = new GregorianCalendar(2015, month , day, hour, minute).getTime();	
				String eventName = title_field.getText();
				String eventLocation = location_field.getText();
				String eventClub = club_field.getText();
				String eventDesc = description_field.getText();
				
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
		
		title_field = new JTextField("Title");
		title_field.setBounds(203, 115, 241, 33);
		contentPanel.add(title_field);
		title_field.setColumns(10);
		
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
			JComboBox daycomboBox = new JComboBox<String>(days);
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
			JComboBox hourComboBox = new JComboBox<String>(hours);
			hourComboBox.setBounds(317, 268, 61, 27);
			contentPanel.add(hourComboBox);
		}
		{
			String minute[] = { "00","15","30","45"};
			JComboBox minuteComboBox = new JComboBox<String>(minute);
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
