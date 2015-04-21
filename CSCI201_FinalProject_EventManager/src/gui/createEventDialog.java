package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class createEventDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			createEventDialog dialog = new createEventDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public createEventDialog() {
		setTitle("Create Event");
		setBounds(100, 100, 450, 376);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblEventName = new JLabel("Event Title");
			lblEventName.setBounds(39, 120, 77, 22);
			contentPanel.add(lblEventName);
		}
		
		textField = new JTextField();
		textField.setBounds(203, 115, 241, 33);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JLabel lblEventDescription = new JLabel("Description");
		lblEventDescription.setBounds(39, 182, 117, 16);
		contentPanel.add(lblEventDescription);
		
		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setBounds(203, 168, 228, 82);
		contentPanel.add(textArea);
		{
			String months[] = { "January", "February", "March", "April", "May", "June", "July", "August","September", "October","November", "December"};
			
			JComboBox comboBox = new JComboBox(months);
			comboBox.setBounds(165, 268, 117, 27);
			contentPanel.add(comboBox);
			
		}
		{
			JLabel lblDateAndTime = new JLabel("Date and Time");
			lblDateAndTime.setBounds(35, 272, 107, 16);
			contentPanel.add(lblDateAndTime);
		}
		{
			String days[] = { "1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
			JComboBox daycomboBox = new JComboBox(days);
			daycomboBox.setBounds(281, 268, 66, 27);
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
