package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ScrollPaneConstants;

import main.Event;

public class UserEventsPanel extends JScrollPane {

	private Map<String, ImageIcon> imageMap;
	
	public UserEventsPanel(JList<Event> list){
		super(list);
		//imageMap = createImageMap(nameList);
		list.setCellRenderer(new EventRenderer());
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.setPreferredSize(new Dimension(300, 300));
	}
		
	class EventRenderer extends JPanel implements ListCellRenderer<Event>{
		
		private JLabel event_info;
		
		
		public EventRenderer(){
			super();
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.setOpaque(true);
			
			event_info = new JLabel("");
			this.add(event_info);
			
		}
		
		@Override
	    public Component getListCellRendererComponent(JList<? extends Event> list, Event event, int index,
	            boolean isSelected, boolean cellHasFocus) {
	 
			//setIcon(imageIcon);
			
	        this.event_info.setText("<html><b>" + event.getName() + "</b><br>" + event.getDescription() + "</html>");
			this.event_info.setIcon(event.getProfilePicture());
			
	 
	        if (isSelected) {
	            setBackground(list.getSelectionBackground());
	            setForeground(list.getSelectionForeground());
	        } else {
	            setBackground(list.getBackground());
	            setForeground(list.getForeground());
	        }
	 
	        return this;
	    }
	}
}

