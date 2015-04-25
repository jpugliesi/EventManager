package gui;


import java.awt.Component;
import java.awt.Dimension;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

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

public class UserEventFeedPanel extends JScrollPane {

	private Map<String, ImageIcon> imageMap;
	public UserEventFeedPanel(JList list){
		super(list);
		//imageMap = createImageMap(nameList);
		list.setCellRenderer(new EventRenderer());
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.setPreferredSize(new Dimension(300, 300));
	}
	
	public UserEventFeedPanel update(JList list){
		return new UserEventFeedPanel(list);
	}
	
	private Map<String, ImageIcon> createImageMap(String[] list) {
		Map<String, ImageIcon> map = new HashMap<>();
		try {
			
			map.put("BlackStone LaunchPad", new ImageIcon(ImageIO.read(getClass().getResource("icon.gif"))) );
			map.put("Google USC Hiring Workshop", new ImageIcon(ImageIO.read(getClass().getResource("icon.gif"))) );
			map.put("HACKSC", new ImageIcon(ImageIO.read(getClass().getResource("icon.gif"))));
			map.put("UX Designathon", new ImageIcon(ImageIO.read(getClass().getResource("icon.gif"))));
			map.put("<html>USC Grief Entrepreneur MeetUp sdaakjsdfaslkjfhaslkjdhfalhfas</html>", new ImageIcon(ImageIO.read(getClass().getResource("icon.gif"))));
			map.put("CS201 Presentations", new ImageIcon(ImageIO.read(getClass().getResource("icon.gif"))));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return map;
	}
	public void addtomap(String eventName, ImageIcon newIcon){ //when trying to add new events
		imageMap.put(eventName, newIcon);
	}
	
//	class EventFeedRenderer extends DefaultListCellRenderer { //changes each UI CELL
//
//		Font font = new Font("helvitica", Font.PLAIN, 12);
//
//		@Override
//		public Component getListCellRendererComponent(JList list, Object value,
//				int index, boolean isSelected, boolean cellHasFocus) {
//
//			JLabel label = (JLabel) superd.getListCellRendererComponent(list,
//					value, index, isSelected, cellHasFocus);
//			label.setIcon(imageMap.get((String) value));
//			label.setHorizontalTextPosition(JLabel.RIGHT);
//			label.setFont(font);
//			return label;
//		}
//	}
	
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
	        try {
				this.event_info.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("icon.gif"))));
			} catch (IOException e) {
				e.printStackTrace();
			}
	 
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
