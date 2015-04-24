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
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JSeparator;

public class chatWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			chatWindow dialog = new chatWindow();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public chatWindow() {
		setBounds(100, 100, 450, 530);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JButton sendButton = new JButton("Send");
			sendButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//send button
				}
			});
			sendButton.setBounds(327, 436, 117, 29);
			contentPanel.add(sendButton);
		}
		{
			textField = new JTextField();
			textField.setBounds(6, 435, 322, 28);
			contentPanel.add(textField);
			textField.setColumns(10);
		}
		{
			JLabel icon = new JLabel("");
			icon.setBounds(6, 0, 132, 68);
			BufferedImage image=null;
			try {
				image = ImageIO.read(getClass().getResource("Title.jpeg"));
				image = resizeImage(image, 110 , 60, image.TYPE_INT_RGB);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			icon.setIcon(new ImageIcon(image));
			icon.setAlignmentX(getContentPane().CENTER_ALIGNMENT);
			
			
			contentPanel.add(icon);
		}
		{
			JTextArea textArea = new JTextArea();
			textArea.setLineWrap(true);
			textArea.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
			textArea.setBounds(16, 80, 416, 331);
			contentPanel.add(textArea);
		}
		
		JSeparator separator = new JSeparator();
		separator.setBounds(6, 65, 438, 12);
		contentPanel.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(6, 423, 450, 12);
		contentPanel.add(separator_1);
		
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
	
	public BufferedImage resizeImage(BufferedImage originalImage, int width,
			int height, int type) throws IOException {
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
	}
}
