package gui;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
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

public class AdminLoginPanel extends JFrame {
	static AdminMainPanel mainPanel;
	AdminLoginPanel(){
		super("Admin Login");
		setSize(325,265); 
		setLocation (500,100); 
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		


		
		JPanel centerPanel= new JPanel(); 
		centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		setLayout(new BorderLayout());
		JLabel username= new JLabel("User Name:    ");
		JLabel pass= new JLabel("Password:      ");
		JLabel pass2= new JLabel("Re enter Pass:");
		JTextField jtf1= new JTextField(15);
		//JTextField jtf2= new JTextField(15);
		JPasswordField jtf2= new JPasswordField(15);
		JPasswordField jtf3= new JPasswordField(15);

		JButton loginButton= new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	//go to next screen
            	 setVisible(false);
            	new AdminMainPanel();
            }
        }); 
	
		JPanel buttonPanel2 = new JPanel();
		buttonPanel2.setLayout(new BorderLayout());
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		
		buttonPanel.add(loginButton);
		buttonPanel2.add(buttonPanel,BorderLayout.SOUTH);
		
		BufferedImage image = null;
		JLabel dTitle=new JLabel();
		
		 try {
			 image = ImageIO.read(getClass().getResource("Title_Admin.jpeg"));
			 image= resizeImage( image, 325, 100, image.TYPE_INT_RGB);
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
		add(centerPanel, BorderLayout.CENTER);
		add(buttonPanel2,BorderLayout.SOUTH);
		setResizable(false);
		setVisible(true);
	}
	public static void main (String []args){
		new AdminLoginPanel();
	}
	  public BufferedImage resizeImage(BufferedImage originalImage, int width, int height, int type) throws IOException {  
	        BufferedImage resizedImage = new BufferedImage(width, height, type);  
	        Graphics2D g = resizedImage.createGraphics();  
	        g.drawImage(originalImage, 0, 0, width, height, null);  
	        g.dispose();  
	        return resizedImage;  
	    }  
	
}


class AdminMainPanel extends JFrame{
	  JButton manageEventButton;
	  JButton createEventButton;
	  JButton profileButton;
	  JButton inboxButton;
		
		AdminMainPanel(){
			super("Admin HomePage");
			setSize(700 ,700); 
			setLocation (500,100); 
			setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
			setLayout(new GridLayout(2,2,10,10));
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);
			setUpButtons();	
			JPanel panel1= new JPanel();
			JPanel panel2= new JPanel();
			JPanel panel3= new JPanel();
			JPanel panel4= new JPanel();
			
			addActionListeners();
			
			panel1.add(manageEventButton);
			panel2.add(createEventButton);
			panel3.add(profileButton);
			panel4.add(inboxButton);
			
			add(panel1);
			add(panel2);
			add(panel3);
			add(panel4);
				
			setVisible(true);
			
		}
		void setUpButtons(){
			manageEventButton= new JButton(); 
			createEventButton= new JButton(); 
			profileButton= new JButton(); 
			inboxButton= new JButton(); 
			    try 
		        {
			    	BufferedImage image = null;
			       image = ImageIO.read(getClass().getResource("Create_Event.jpeg"));
			       image= resizeImage( image, 300, 250, image.TYPE_INT_RGB);
			       createEventButton.setIcon(new ImageIcon(image));
			    
		            
		            
		            image = ImageIO.read(getClass().getResource("Manage_Event.jpeg"));
				    image= resizeImage( image, 300, 250, image.TYPE_INT_RGB);
				    manageEventButton.setIcon(new ImageIcon(image));
				    //manageEventButton.setAlignmentX(getContentPane().CENTER_ALIGNMENT);
		            
			        
		            image = ImageIO.read(getClass().getResource("My_Profile.jpeg"));
				    image= resizeImage( image, 300, 250, image.TYPE_INT_RGB);
				    profileButton.setIcon(new ImageIcon(image));
				 //   profileButton.setAlignmentX(getContentPane().CENTER_ALIGNMENT);
			        
			        image = ImageIO.read(getClass().getResource("Inbox.jpeg"));
				    image= resizeImage( image, 300, 250, image.TYPE_INT_RGB);
				    inboxButton.setIcon(new ImageIcon(image));
				//    inboxButton.setAlignmentX(getContentPane().CENTER_ALIGNMENT);
			        
			        
		        } 
		        catch (IOException ex) {
		        	
		        }
			   
		}
		void addActionListeners(){ 
			
		//CREATE EVENT -----------------------------------------------------------------------------
			createEventButton.addActionListener(new ActionListener() {
				 
	            public void actionPerformed(ActionEvent e)
	            {
	                //Execute when button is pressed
	            	final JDialog jd = new JDialog();
					jd.setTitle("Create Event");
					jd.setSize(700,700);
					jd.setLocationRelativeTo(null);
					jd.setResizable(false);
					
					JPanel centerPanel= new JPanel(); 
					centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
					jd.setLayout(new BorderLayout());
					JLabel username= new JLabel("User Name:    ");
					JLabel pass= new JLabel("Password:      ");
					JLabel pass2= new JLabel("Re enter Pass:");
					JTextField jtf1= new JTextField(15);
					//JTextField jtf2= new JTextField(15);
					JPasswordField jtf2= new JPasswordField(15);
					JPasswordField jtf3= new JPasswordField(15);
			
					JButton okButton= new JButton("OK");
				//	okButton.setHorizontalAlignment(SwingConstants.RIGHT);
					okButton.addActionListener(new ActionListener() {
						 
			            public void actionPerformed(ActionEvent e)
			            {
			            	//makes sure jtf2 and jtf3 are the same pass
			            	//pull the username and one of the password textfields and before it closes
			                jd.dispose();
			            
			            }
			        }); 
					JButton cancelButton= new JButton("Cancel");
					cancelButton.addActionListener(new ActionListener() {
						 
			            public void actionPerformed(ActionEvent e)
			            {
			                jd.dispose();
			            
			            }
			        }); 
					JPanel buttonPanel2 = new JPanel();
					buttonPanel2.setLayout(new BorderLayout());
					JPanel buttonPanel = new JPanel();
					buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
					
					
					buttonPanel.add(cancelButton);
					buttonPanel.add(okButton);
					buttonPanel2.add(buttonPanel,BorderLayout.SOUTH);
					
					BufferedImage image = null;
					JLabel dTitle=new JLabel();
					
					 try {
						 image = ImageIO.read(getClass().getResource("Title.jpeg"));
						 image= resizeImage( image, 325, 100, image.TYPE_INT_RGB);
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
					jd.add(buttonPanel2,BorderLayout.SOUTH);
					jd.setVisible(true);
	            }
	        }); 
	//MY PROFILE -----------------------------------------------------------------------------
			profileButton.addActionListener(new ActionListener() {
				 
	            public void actionPerformed(ActionEvent e)
	            {
	                //Execute when button is pressed
	            	final JDialog jd = new JDialog();
					jd.setTitle("User Login");
					jd.setSize(325,265);
					jd.setLocationRelativeTo(null);
					jd.setResizable(false);
					
					JPanel centerPanel= new JPanel(); 
					centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
					jd.setLayout(new BorderLayout());
					JLabel username= new JLabel("User Name:    ");
					JLabel pass= new JLabel("Password:      ");
					JLabel pass2= new JLabel("Re enter Pass:");
					JTextField jtf1= new JTextField(15);
					//JTextField jtf2= new JTextField(15);
					JPasswordField jtf2= new JPasswordField(15);
					JPasswordField jtf3= new JPasswordField(15);
			
					JButton okButton= new JButton("OK");
				//	okButton.setHorizontalAlignment(SwingConstants.RIGHT);
					okButton.addActionListener(new ActionListener() {
						 
			            public void actionPerformed(ActionEvent e)
			            {
			            	//makes sure jtf2 and jtf3 are the same pass
			            	//pull the username and one of the password textfields and before it closes
			                jd.dispose();
			            
			            }
			        }); 
					JButton cancelButton= new JButton("Cancel");
					cancelButton.addActionListener(new ActionListener() {
						 
			            public void actionPerformed(ActionEvent e)
			            {
			                jd.dispose();
			            
			            }
			        }); 
					JPanel buttonPanel2 = new JPanel();
					buttonPanel2.setLayout(new BorderLayout());
					JPanel buttonPanel = new JPanel();
					buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
					
					
					buttonPanel.add(cancelButton);
					buttonPanel.add(okButton);
					buttonPanel2.add(buttonPanel,BorderLayout.SOUTH);
					
					BufferedImage image = null;
					JLabel dTitle=new JLabel();
					
					 try {
						 image = ImageIO.read(getClass().getResource("Title.jpeg"));
						 image= resizeImage( image, 325, 100, image.TYPE_INT_RGB);
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
					jd.add(buttonPanel2,BorderLayout.SOUTH);
					jd.setVisible(true);
	            }
	        }); 
			//Inbox -----------------------------------------------------------------------------
		}
		  public BufferedImage resizeImage(BufferedImage originalImage, int width, int height, int type) throws IOException {  
		        BufferedImage resizedImage = new BufferedImage(width, height, type);  
		        Graphics2D g = resizedImage.createGraphics();  
		        g.drawImage(originalImage, 0, 0, width, height, null);  
		        g.dispose();  
		        return resizedImage;  
		    }  
	}