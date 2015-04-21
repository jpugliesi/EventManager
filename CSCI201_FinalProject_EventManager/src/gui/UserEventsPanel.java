package gui;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.List;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

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
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;

public class UserEventsPanel extends JFrame {
	private		JTabbedPane tabbedPane;
	private		JPanel		panel1;
	private		JPanel		panel2;
	private		JPanel		panel3;
	public static void main(String []args){
		new UserEventsPanel();
	}
	UserEventsPanel(){
		super("Events Manager");
		setSize(700,700); 
		setLocation (500,100); 
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		JPanel topPanel = new JPanel();
		topPanel.setLayout( new BorderLayout() );
		getContentPane().add( topPanel );

		// Create the tab pages
		createPage1();
		createPage2();
		createPage3();

		// Create a tabbed pane
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab( "Page 1", panel1 );
		tabbedPane.addTab( "Page 2", panel2 );
		tabbedPane.addTab( "Page 3", panel3 );
		topPanel.add( tabbedPane, BorderLayout.CENTER );
		setVisible(true);
	}
	public void createPage1()
	{
		panel1 = new JPanel();
		panel1.setLayout(new FlowLayout() );
		
		Object [][] data= {
				{"HackSC", "Thirty-six hours of pure hacking"},
				{"Innovation Night","Pitch your newest and most innovative ideas"},
				{"Designathon", "Try to come up with your coolest designs" }
		};
		JTable eventFeed= new JTable(10,10);
		}
//		JLabel label1 = new JLabel( "Username:" );
//		label1.setBounds( 10, 15, 150, 20 );
//		panel1.add( label1 );
//
//		JTextField field = new JTextField();
//		field.setBounds( 10, 35, 150, 20 );
//		panel1.add( field );
//
//		JLabel label2 = new JLabel( "Password:" );
//		label2.setBounds( 10, 60, 150, 20 );
//		panel1.add( label2 );
//
//		JPasswordField fieldPass = new JPasswordField();
//		fieldPass.setBounds( 10, 80, 150, 20 );
//		panel1.add( fieldPass );


	public void createPage2()
	{
		panel2 = new JPanel();
		panel2.setLayout( new BorderLayout() );

		panel2.add( new JButton( "North" ), BorderLayout.NORTH );
		panel2.add( new JButton( "South" ), BorderLayout.SOUTH );
		panel2.add( new JButton( "East" ), BorderLayout.EAST );
		panel2.add( new JButton( "West" ), BorderLayout.WEST );
		panel2.add( new JButton( "Center" ), BorderLayout.CENTER );
	}

	public void createPage3()
	{
		panel3 = new JPanel();
		panel3.setLayout( new GridLayout( 3, 2 ) );

		panel3.add( new JLabel( "Field 1:" ) );
		panel3.add( new TextArea() );
		panel3.add( new JLabel( "Field 2:" ) );
		panel3.add( new TextArea() );
		panel3.add( new JLabel( "Field 3:" ) );
		panel3.add( new TextArea() );
	}
}

	