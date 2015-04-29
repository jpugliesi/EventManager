package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import main.Event;
import client.ClientRSVPThread;
import constants.Environment;

public class DetailedEventPage extends JDialog {

	private JPanel contentPane;
	private Event event;
	private JLabel num_attending;

	/**
	 * Create the frame.
	 */
	public DetailedEventPage(Event event) {
		super();
		this.event = event;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		BufferedImage image=null;
		try {
			image = ImageIO.read(getClass().getResource("icon.gif"));

		//	image = resizeImage(image, 325, 100, image.TYPE_INT_RGB);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JLabel eventImage = new JLabel();
		eventImage.setIcon(event.getProfilePicture());
		
		eventImage.setBounds(33, 41, 71, 43);
		contentPane.add(eventImage);
		
		JLabel event_name = new JLabel(event.getName());
		event_name.setFont(new Font("Helvetica Neue", Font.PLAIN, 18));
		event_name.setVerticalAlignment(SwingConstants.TOP);
		event_name.setBounds(253, 27, 165, 60);
		contentPane.add(event_name);
		
		JLabel event_desc = new JLabel("<html>" + event.getDescription() + "<br>" + event.getClub() + "</html>");
		event_desc.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		event_desc.setBounds(18, 110, 165, 143);
		contentPane.add(event_desc);
		
		num_attending = new JLabel("Attendees: " + event.getNumAttending());
		num_attending.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		num_attending.setBounds(18, 140, 165, 143);
		contentPane.add(num_attending);
		
		Date d = event.getTime();
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int month = cal.get(Calendar.MONTH);
		int date = cal.get(Calendar.DATE);
		int year = cal.get(Calendar.YEAR);
		int hours = cal.get(Calendar.HOUR) + 1;
		int minute = cal.get(Calendar.MINUTE);
		String am_pm = "";
		if (cal.get(Calendar.AM_PM) == Calendar.PM) {
			am_pm = "PM";
		} else {
			am_pm = "AM";
		}
		JLabel lblDate = new JLabel("Date: " + month + "/" + date + "/" + year);
		lblDate.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		lblDate.setBounds(302, 149, 200, 16);
		contentPane.add(lblDate);
		
		JLabel lblTime = new JLabel("Time: " + hours + ":" + minute + " " + am_pm);
		lblTime.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		lblTime.setBounds(302, 177, 200, 16);
		contentPane.add(lblTime);
		
		JLabel lblLocation = new JLabel("Location: " + event.getLocation());
		lblLocation.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		lblLocation.setBounds(302, 205, 200, 26);
		contentPane.add(lblLocation);
		
		JButton btnRsvpNow = new JButton("RSVP");
		btnRsvpNow.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		btnRsvpNow.setBounds(6, 231, 117, 29);
		contentPane.add(btnRsvpNow);
		
		btnRsvpNow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				
				event.setNumAttending(event.getNumAttending()+1);
				ClientRSVPThread rsvpThread = new ClientRSVPThread(Environment.currentUser, event);
				rsvpThread.start();
				
				while(!rsvpThread.finished()){ }
				
				if(rsvpThread.rsvpSuccessful()){
					num_attending.setText("Attendees: " + event.getNumAttending());
					JDialog tmp_jd = new JDialog();
					tmp_jd.setSize(300,250);
					tmp_jd.setLocation(400,100);
					tmp_jd.setTitle("RSVP success!");
					JLabel label = new JLabel("You have rsvp'd to: " + event.getName());
					JButton button = new JButton("Cool!");
					button.addActionListener(new ActionListener() {
						public void actionPerformed (ActionEvent ae) {
							tmp_jd.dispose();
						}
					});
					tmp_jd.add(label);
					tmp_jd.add(button, BorderLayout.SOUTH);
					tmp_jd.setModal(true);
					tmp_jd.setVisible(true);
					
				} else {
					JDialog tmp_jd = new JDialog();
					tmp_jd.setSize(300,250);
					tmp_jd.setLocation(400,100);
					tmp_jd.setTitle("RSVP failure");
					JLabel label = new JLabel("Something went wrong while rsvping, please try again");
					JButton button = new JButton("Ok");
					button.addActionListener(new ActionListener() {
						public void actionPerformed (ActionEvent ae) {
							tmp_jd.dispose();
						}
					});
					tmp_jd.add(label);
					tmp_jd.add(button, BorderLayout.SOUTH);
					tmp_jd.setModal(true);
					tmp_jd.setVisible(true);
				}
				
			}
		});
	}
	public BufferedImage resizeImage(BufferedImage originalImage, int width,
			int height, int type) throws IOException {
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
	}

}
