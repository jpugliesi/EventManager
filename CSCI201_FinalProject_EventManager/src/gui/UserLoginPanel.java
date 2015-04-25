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

import constants.Constants;
import constants.Environment;
import main.User;
import client.ClientLoginThread;
import client.ClientRegisterThread;

public class UserLoginPanel extends JFrame {
	private JLabel titleLogo;
	private JButton registerButton;
	private JButton loginButton;

	UserLoginPanel() {
		super("Event Manager");
		setSize(500, 590);
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
				JTextField usernameTF = new JTextField(15);
				// JTextField jtf2= new JTextField(15);
				final JPasswordField passTF1 = new JPasswordField(15);
				final JPasswordField passTF2 = new JPasswordField(15);

				JButton okButton = new JButton("OK");
				// okButton.setHorizontalAlignment(SwingConstants.RIGHT);
				okButton.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						// makes sure jtf2 and jtf3 are the same pass
						// pull the username and one of the password textfields
						// and before it closes
						if (passTF1.getText().equals(passTF2.getText())
								&& !passTF1.getText().equals("")
								&& !usernameTF.getText().equals("")
								&& !nameTF.getText().equals("")) {
							ClientRegisterThread registerThread = new ClientRegisterThread(new User(nameTF.getText(), usernameTF.getText(), passTF1.getText(), false, 1));
							registerThread.start();
							while(!registerThread.finished()){
								
							}
							
							if(registerThread.registrationSuccessful()){
								Environment.currentUser = registerThread.getRegisteredUser();
								new UserTabFrame().setVisible(true);
								
								JDialog tmp_jd = new JDialog();
								tmp_jd.setSize(300,250);
								tmp_jd.setLocation(400,100);
								tmp_jd.setTitle("Registration success!");
								JLabel label = new JLabel("You are now registered!");
								JButton button = new JButton("Got it!");
								button.addActionListener(new ActionListener() {
									public void actionPerformed (ActionEvent ae) {
										tmp_jd.dispose();
									}
								});
								tmp_jd.add(label);
								tmp_jd.add(button, BorderLayout.SOUTH);
								tmp_jd.setModal(true);
								tmp_jd.setVisible(true);
								
								dispose();
								tmp_jd.dispose();
							} else if(registerThread.getErrorCode() == Constants.SERVER_REGISTRATION_USERNAME_FAIL){
								JDialog tmp_jd = new JDialog();
								tmp_jd.setSize(300,250);
								tmp_jd.setLocation(400,100);
								tmp_jd.setTitle("Invalid Login");
								JLabel label = new JLabel("Invalid Username, Please try again.");
								JButton button = new JButton("Got it!");
								button.addActionListener(new ActionListener() {
									public void actionPerformed (ActionEvent ae) {
										tmp_jd.dispose();
									}
								});
								tmp_jd.add(label);	
								tmp_jd.add(button, BorderLayout.SOUTH);
								tmp_jd.setModal(false);
								tmp_jd.setVisible(true);
							} else if(registerThread.getErrorCode() == Constants.SERVER_REGISTRATION_PASSWORD_FAIL){
								JDialog tmp_jd = new JDialog();
								tmp_jd.setSize(300,250);
								tmp_jd.setLocation(400,100);
								tmp_jd.setTitle("Invalid Login");
								JLabel label = new JLabel("Invalid password, Please try again.");
								JButton button = new JButton("Got it!");
								button.addActionListener(new ActionListener() {
									public void actionPerformed (ActionEvent ae) {
										tmp_jd.dispose();
									}
								});
								tmp_jd.add(label);	
								tmp_jd.add(button, BorderLayout.SOUTH);
								tmp_jd.setModal(false);
								tmp_jd.setVisible(true);
							}
						} else {
							JDialog fail_jd = new JDialog();
							fail_jd.setSize(300,250);
							fail_jd.setLocation(400,100);
							fail_jd.setTitle("Invalid Registration");
							JLabel label = new JLabel("Your information does not match!");
							JButton button = new JButton("Got it!");
							button.addActionListener(new ActionListener() {
								public void actionPerformed (ActionEvent ae) {
									fail_jd.dispose();
								}
							});
							fail_jd.add(label);	
							fail_jd.add(button, BorderLayout.SOUTH);
							fail_jd.setModal(true);
							fail_jd.setVisible(true);
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
				centerPanel.add(usernameTF);
				centerPanel.add(pass);
				centerPanel.add(passTF1);
				centerPanel.add(pass2);
				centerPanel.add(passTF2);
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
				JTextField jtf1 = new JTextField(15);
				JPasswordField jtf2 = new JPasswordField(15);

				JButton okButton = new JButton("OK");
				// okButton.setHorizontalAlignment(SwingConstants.RIGHT);
				okButton.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						// makes sure jtf2 and jtf3 are the same pass
						// pull the username and one of the password textfields
						// and before it closes
						if ( !jtf2.getText().equals("")
								&& !username.getText().equals("")) 
						{
							ClientLoginThread loginThread= new ClientLoginThread(jtf1.getText(),jtf2.getText(), false);
							loginThread.start(); 
							while(!loginThread.finished()){

							}

							
		
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
				centerPanel.add(new JPanel());
				jd.add(centerPanel, BorderLayout.CENTER);
				jd.add(buttonPanel2, BorderLayout.SOUTH);
				jd.setVisible(true);
			}
		});
	

	}

	void setUpButtons() {
		registerButton = new JButton();
		loginButton = new JButton();
	
		titleLogo = new JLabel();
		try {
			BufferedImage image = null;
			image = ImageIO.read(getClass().getResource("Register.jpeg"));
			image = resizeImage(image, 450, 155, image.TYPE_INT_RGB);
			registerButton.setIcon(new ImageIcon(image));
			registerButton.setAlignmentX(getContentPane().CENTER_ALIGNMENT);

		

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
