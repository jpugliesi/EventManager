package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;

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
			lblEventName.setBounds(35, 37, 77, 22);
			contentPanel.add(lblEventName);
		}
		
		textField = new JTextField();
		textField.setBounds(203, 32, 241, 33);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JLabel lblEventDescription = new JLabel("Event Description");
		lblEventDescription.setBounds(35, 90, 117, 16);
		contentPanel.add(lblEventDescription);
		
		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setBounds(203, 90, 228, 125);
		contentPanel.add(textArea);
		{
			String months[] = { "January", "February", "March", "April", "May"};
			JComboBox comboBox = new JComboBox(months);
			comboBox.setBounds(35, 268, 117, 27);
			contentPanel.add(comboBox);
			
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
