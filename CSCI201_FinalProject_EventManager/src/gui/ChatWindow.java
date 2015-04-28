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
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.StyledDocument;

import main.ChatMessage;
import main.User;
import client.ClientGetChatHistoryThread;
import client.ClientSendMessageThread;
import constants.Environment;

import javax.swing.JTextPane;

public class ChatWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private User otherPerson;
	JTextPane messageArea; 
	JScrollPane scrollPane ;
	private JButton sendButton;
	private boolean isAdmin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ChatWindow dialog = new ChatWindow(null, false);
			dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ChatWindow(User otherPerson, boolean isAdmin) {
		this.isAdmin = isAdmin;
		this.otherPerson = otherPerson;
		setBounds(100, 100, 450, 494);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			sendButton = new JButton("Send");
			getRootPane().setDefaultButton(sendButton);
			sendButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//send button
					
					User cUser = null;
					if(ChatWindow.this.isAdmin){
						cUser = Environment.currentAdmin;
					} else {
						cUser = Environment.currentUser;
					}
					ChatMessage message = new ChatMessage(ChatWindow.this.textField.getText(), cUser, ChatWindow.this.otherPerson, new Date(System.currentTimeMillis()));
					ClientSendMessageThread sendMessageThread = new ClientSendMessageThread(message);
					sendMessageThread.start();
					ChatWindow.this.textField.setText("");
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
		
		JSeparator separator = new JSeparator();
		separator.setBounds(6, 65, 438, 12);
		contentPanel.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(6, 423, 450, 12);
		contentPanel.add(separator_1);
		{
			JButton closeButton = new JButton("Close");
			closeButton.setBounds(365, 6, 79, 29);
			contentPanel.add(closeButton);
			closeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ChatWindow.this.setVisible(false);
				}
			});
			closeButton.setActionCommand("Cancel");
		}
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(6, 482, 450, 10);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		}
		{
			scrollPane= new JScrollPane();
			scrollPane.setBounds(16, 80, 415, 344);
			contentPanel.add(scrollPane);
			messageArea = new JTextPane();
			scrollPane.setViewportView(messageArea);
		}
		setVisible(true);
		
		updateChat();
	}
	
	public BufferedImage resizeImage(BufferedImage originalImage, int width,
			int height, int type) throws IOException {
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
	}
	
	
	
	public void updateChat(){
		User cUser = null;
		if(this.isAdmin){
			cUser = Environment.currentAdmin;
		} else {
			cUser = Environment.currentUser;
		}
		ClientGetChatHistoryThread chatHistoryThread = new ClientGetChatHistoryThread(cUser, this.otherPerson);
		chatHistoryThread.start();
		while(!chatHistoryThread.finished()){ }
		
		if(chatHistoryThread.success()){
			Vector<ChatMessage> messageHistory = chatHistoryThread.getMessageHistory();
			messageArea.setText("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			StyledDocument doc = messageArea.getStyledDocument();
			
			for(ChatMessage message : messageHistory){
				try
				{
				      doc.insertString(doc.getLength(), message.getSender().getUserName() + ": " + message.getMessage() + "\n", null);
				}
				catch(Exception e) { System.out.println(e); }
				
			}
			
			JScrollBar vertical = scrollPane.getVerticalScrollBar();
			vertical.setValue( vertical.getMaximum() );
		} else {
			System.out.println("Error updating chat");
		}
	}
	
	public User getOtherPerson(){
		return this.otherPerson;
	}
}
