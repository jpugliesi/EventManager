package gui;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class UserEventFeedPanel extends JScrollPane {

	private final Map<String, ImageIcon> imageMap;
	public UserEventFeedPanel(String[] nameList, JList list){
		super(list);
		imageMap = createImageMap(nameList);
		list.setCellRenderer(new EventFeedRenderer());
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.setPreferredSize(new Dimension(300, 300));
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
	
	class EventFeedRenderer extends DefaultListCellRenderer { //changes each UI CELL

		Font font = new Font("helvitica", Font.PLAIN, 12);

		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {

			JLabel label = (JLabel) super.getListCellRendererComponent(list,
					value, index, isSelected, cellHasFocus);
			label.setIcon(imageMap.get((String) value));
			label.setHorizontalTextPosition(JLabel.RIGHT);
			label.setFont(font);
			return label;
		}
	}
}
