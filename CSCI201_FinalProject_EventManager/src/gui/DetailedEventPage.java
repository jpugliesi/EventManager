package gui;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import main.Event;

public class DetailedEventPage extends JDialog {

	private JPanel contentPane;
	private Event event;

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
		eventImage.setIcon(new ImageIcon(image));
		
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
