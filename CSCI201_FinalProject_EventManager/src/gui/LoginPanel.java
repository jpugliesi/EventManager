package gui;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginPanel extends JPanel {
	
	private JLabel welcomeLabel, errorLabel, usernameLabel, passwordLabel;
	private JTextField usernameJTF, passwordJTF;
	private JButton loginButton, createNewAccountButton;
	private JPanel usernamePanel, passwordPanel;

	
	public LoginPanel(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		welcomeLabel = new JLabel("Welcome");
		errorLabel = new JLabel("Invalid username/password");
		errorLabel.setVisible(false);
		
		usernameLabel = new JLabel("username: ");
		passwordLabel = new JLabel("password: ");
		usernameJTF = new JTextField(20);
		passwordJTF = new JTextField(20);
		usernamePanel = new JPanel();
		passwordPanel = new JPanel();
		usernamePanel.add(usernameLabel);
		usernamePanel.add(usernameJTF);
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordJTF);
		
		usernameLabel.setLabelFor(usernameJTF);
		passwordLabel.setLabelFor(passwordJTF);
		
		loginButton = new JButton("Log In");
		createNewAccountButton = new JButton("Create New Account");
		
		add(welcomeLabel);
		add(errorLabel);
		add(usernamePanel);
		add(passwordPanel);
		add(loginButton);
		add(createNewAccountButton);
		
	}
	
	public void displayError(){
		
	}
}
