package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.JList;

public class UserEventPanel extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserEventPanel frame = new UserEventPanel();
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
	public UserEventPanel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Event Feed", null, panel, null);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 46, 349, 136);
		panel.add(scrollPane);
		
		ImageIcon eventIcon = new ImageIcon("icon.gif");
		JPanel cellIndex= new JPanel(new BorderLayout()); 
		//cellIndex.setLayout(new FlowLayout());
		cellIndex.add(new JLabel(eventIcon), BorderLayout.LINE_END);
	//	cellIndex.add(new JLabel(eventIcon));
		//cellIndex.add(new JLabel("This is an oversimplified description"));
	
		JPanel	listData[] =
			{
				cellIndex,cellIndex,cellIndex
			};
		JList list = new JList(listData);
		scrollPane.setViewportView(list);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_1, null);
		panel_1.setLayout(null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(62, 40, 211, 73);
		panel_1.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblHell = new JLabel("Hell");
		lblHell.setBounds(80, 29, 62, 38);
		panel_3.add(lblHell);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(150, 138, 129, 78);
		panel_1.add(panel_4);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_2, null);
}
	
//	public class MarioListRenderer extends DefaultListCellRenderer {
//
//	    Font font = new Font("helvitica", Font.BOLD, 24);
//
//	    @Override
//	    public Component getListCellRendererComponent(
//	            JList list, Object value, int index,
//	            boolean isSelected, boolean cellHasFocus) {
//
//	        JLabel label = (JLabel) super.getListCellRendererComponent(
//	                list, value, index, isSelected, cellHasFocus);
//	        label.setIcon(imageMap.get((String) value));
//	        label.setHorizontalTextPosition(JLabel.RIGHT);
//	        label.setFont(font);
//	        return label;
//	    }
//	}
}
