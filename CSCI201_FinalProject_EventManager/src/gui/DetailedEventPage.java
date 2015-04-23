package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JButton;

public class DetailedEventPage extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DetailedEventPage frame = new DetailedEventPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DetailedEventPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
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
		
		JLabel lblEventNamePlaceholder = new JLabel("<html>Event Name PlaceHolder</html>");
		lblEventNamePlaceholder.setFont(new Font("Helvetica Neue", Font.PLAIN, 18));
		lblEventNamePlaceholder.setVerticalAlignment(SwingConstants.TOP);
		lblEventNamePlaceholder.setBounds(253, 27, 165, 60);
		contentPane.add(lblEventNamePlaceholder);
		
		JLabel lblHksajdfhasklfhalkshfaklshfkalshfsalkhfkalshfasdlkhfklsahdfkaslfhalska = new JLabel("<html>hksajd fhasklfh alkshfaklshfka lshfsalkhfkal shfasdlkhfk lsahdfkaslfha lska</html>");
		lblHksajdfhasklfhalkshfaklshfkalshfsalkhfkalshfasdlkhfklsahdfkaslfhalska.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		lblHksajdfhasklfhalkshfaklshfkalshfsalkhfkalshfasdlkhfklsahdfkaslfhalska.setBounds(18, 110, 165, 143);
		contentPane.add(lblHksajdfhasklfhalkshfaklshfkalshfsalkhfkalshfasdlkhfklsahdfkaslfhalska);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		lblDate.setBounds(302, 149, 61, 16);
		contentPane.add(lblDate);
		
		JLabel lblTime = new JLabel("Time");
		lblTime.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		lblTime.setBounds(302, 177, 61, 16);
		contentPane.add(lblTime);
		
		JLabel lblLocation = new JLabel("Location");
		lblLocation.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		lblLocation.setBounds(302, 205, 61, 26);
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
