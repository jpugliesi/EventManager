package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import client.ClientLoginThread;

public class UserLoginPanel extends JFrame {
	private JLabel titleLogo;
	private JButton registerButton;
	private JButton loginButton;
	private JButton guestButton;

	UserLoginPanel() {
		super("Event Manager");
		setSize(500, 725);
		setLocation(500, 100);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(new Color(100, 190, 250));
		setUpButtons();

		addActionListeners();

		add(titleLogo);
		// add(Box.createVerticalGlue());
		add(registerButton);
		add(loginButton);
		add(guestButton);

		setResizable(false);
		setVisible(true);
		
		
	}

	void addActionListeners() {
		registerButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// Execute when button is pressed
				final JDialog jd = new JDialog();
				jd.setTitle("Register Your Information");
				jd.setSize(325, 300);
				jd.setLocationRelativeTo(null);
				jd.setResizable(false);
				jd.getContentPane().setBackground(new Color(100, 190, 250));

				JPanel centerPanel = new JPanel();
				centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
				jd.setLayout(new BorderLayout());
				final JLabel name = new JLabel("Name (First+Last):");
				final JLabel username = new JLabel("User Name:           ");
				JLabel pass = new JLabel("Password:             ");
				JLabel pass2 = new JLabel("Re enter Pass:       ");
				JTextField nameTF = new JTextField(15);
				JTextField jtf1 = new JTextField(15);
				// JTextField jtf2= new JTextField(15);
				final JPasswordField jtf2 = new JPasswordField(15);
				final JPasswordField jtf3 = new JPasswordField(15);

				JButton okButton = new JButton("OK");
				// okButton.setHorizontalAlignment(SwingConstants.RIGHT);
				okButton.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						// makes sure jtf2 and jtf3 are the same pass
						// pull the username and one of the password textfields
						// and before it closes
						if (jtf2.getText().equals(jtf3.getText())
								&& !jtf2.getText().equals("")
								&& !username.getText().equals("")
								&& !name.getText().equals("")) {

							jd.dispose();
						}

					}
				});
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						jd.dispose();

					}
				});
				JPanel buttonPanel2 = new JPanel();
				buttonPanel2.setLayout(new BorderLayout());
				JPanel buttonPanel = new JPanel();
				buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

				buttonPanel.add(cancelButton);
				buttonPanel.add(okButton);
				buttonPanel2.add(buttonPanel, BorderLayout.SOUTH);

				BufferedImage image = null;
				JLabel dTitle = new JLabel();

				try {
					image = ImageIO.read(getClass().getResource("Title.jpeg"));
					image = resizeImage(image, 325, 100, image.TYPE_INT_RGB);
					dTitle.setIcon(new ImageIcon(image));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				centerPanel.add(dTitle);
				centerPanel.add(name);
				centerPanel.add(nameTF);
				centerPanel.add(username);
				centerPanel.add(jtf1);
				centerPanel.add(pass);
				centerPanel.add(jtf2);
				centerPanel.add(pass2);
				centerPanel.add(jtf3);
				centerPanel.add(new JPanel());
				jd.add(centerPanel, BorderLayout.CENTER);
				jd.add(buttonPanel2, BorderLayout.SOUTH);
				jd.setVisible(true);
			}
		});

		loginButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// Execute when button is pressed
				final JDialog jd = new JDialog();
				jd.setTitle("User Login");
				jd.setSize(325, 265);
				jd.setLocationRelativeTo(null);
				jd.setResizable(false);

				JPanel centerPanel = new JPanel();
				centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
				jd.setLayout(new BorderLayout());
				JLabel username = new JLabel("User Name:    ");
				JLabel pass = new JLabel("Password:      ");
				JLabel pass2 = new JLabel("Re enter Pass:");
				JTextField jtf1 = new JTextField(15);
				// JTextField jtf2= new JTextField(15);
				JPasswordField jtf2 = new JPasswordField(15);
				JPasswordField jtf3 = new JPasswordField(15);

				JButton okButton = new JButton("OK");
				// okButton.setHorizontalAlignment(SwingConstants.RIGHT);
				okButton.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						// makes sure jtf2 and jtf3 are the same pass
						// pull the username and one of the password textfields
						// and before it closes
						if (jtf2.getText().equals(jtf3.getText())
								&& !jtf2.getText().equals("")
								&& !username.getText().equals("")) 
						{
							ClientLoginThread loginThread= new ClientLoginThread(jtf1.getText(),jtf2.getText());
							loginThread.start(); 
							
							
							if(loginThread.loginSuccessful()){
							new UserTabFrame().setVisible(true);
							dispose();
							jd.dispose();
							}
	
						}
					}
				});
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						jd.dispose();

					}
				});
				JPanel buttonPanel2 = new JPanel();
				buttonPanel2.setLayout(new BorderLayout());
				JPanel buttonPanel = new JPanel();
				buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

				buttonPanel.add(cancelButton);
				buttonPanel.add(okButton);
				buttonPanel2.add(buttonPanel, BorderLayout.SOUTH);

				BufferedImage image = null;
				JLabel dTitle = new JLabel();

				try {
					image = ImageIO.read(getClass().getResource("Title.jpeg"));
					image = resizeImage(image, 325, 100, image.TYPE_INT_RGB);
					dTitle.setIcon(new ImageIcon(image));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				centerPanel.add(dTitle);
				centerPanel.add(username);
				centerPanel.add(jtf1);
				centerPanel.add(new JPanel());
				centerPanel.add(pass);
				centerPanel.add(jtf2);
				centerPanel.add(pass2);
				centerPanel.add(jtf3);
				centerPanel.add(new JPanel());
				jd.add(centerPanel, BorderLayout.CENTER);
				jd.add(buttonPanel2, BorderLayout.SOUTH);
				jd.setVisible(true);
			}
		});
		guestButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// Execute when button is pressed
				System.out.println("You clicked the button");
			}
		});

	}

	void setUpButtons() {
		registerButton = new JButton();
		loginButton = new JButton();
		guestButton = new JButton();
		titleLogo = new JLabel();
		try {
			BufferedImage image = null;
			image = ImageIO.read(getClass().getResource("Register.jpeg"));
			image = resizeImage(image, 450, 155, image.TYPE_INT_RGB);
			registerButton.setIcon(new ImageIcon(image));
			registerButton.setAlignmentX(getContentPane().CENTER_ALIGNMENT);

			image = ImageIO.read(getClass().getResource("Guest.jpeg"));
			image = resizeImage(image, 400, 125, image.TYPE_INT_RGB);
			guestButton.setIcon(new ImageIcon(image));
			guestButton.setAlignmentX(getContentPane().CENTER_ALIGNMENT);

			image = ImageIO.read(getClass().getResource("Login.jpeg"));
			image = resizeImage(image, 450, 155, image.TYPE_INT_RGB);
			loginButton.setIcon(new ImageIcon(image));
			loginButton.setAlignmentX(getContentPane().CENTER_ALIGNMENT);

			image = ImageIO.read(getClass().getResource("Title.jpeg"));
			image = resizeImage(image, 500, 175, image.TYPE_INT_RGB);
			titleLogo.setIcon(new ImageIcon(image));
			titleLogo.setAlignmentX(getContentPane().CENTER_ALIGNMENT);

		} catch (IOException ex) {

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

	public static void main(String[] args) {
		new UserLoginPanel();
	}

}
