package gui;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class UserChatPanel extends JScrollPane {

	private final Map<String, ImageIcon> imageMap;
	public UserChatPanel(String[] nameList, JList list){
		super(list);
		imageMap = createImageMap(nameList);
		list.setCellRenderer(new EventFeedRenderer());
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.setPreferredSize(new Dimension(300, 300));
	}
	
	private Map<String, ImageIcon> createImageMap(String[] list) {
		Map<String, ImageIcon> map = new HashMap<>();
		try {
			BufferedImage image = null;
		       image = ImageIO.read(getClass().getResource("boy1.png"));
		       image= resizeImage( image, 60, 60, image.TYPE_INT_ARGB);
			map.put("Kieran Strolorz", new ImageIcon(image) );
		      image = ImageIO.read(getClass().getResource("boy2.png"));
		       image= resizeImage( image, 60, 60, image.TYPE_INT_ARGB);
			map.put("John Pugliesi", new ImageIcon(image) );
		    image = ImageIO.read(getClass().getResource("boy2.png"));
		       image= resizeImage( image, 60, 60, image.TYPE_INT_ARGB);
			map.put("Zack Kim", new ImageIcon(image));
		    image = ImageIO.read(getClass().getResource("woman1.png"));
		       image= resizeImage( image, 60, 60, image.TYPE_INT_ARGB);
			map.put("Vincent Jin", new ImageIcon(image));
		    image = ImageIO.read(getClass().getResource("woman2.png"));
		       image= resizeImage( image, 60, 60, image.TYPE_INT_ARGB);
			map.put("Jeffrey Miller", new ImageIcon(image));
		    image = ImageIO.read(getClass().getResource("woman3.png"));
		       image= resizeImage( image, 60, 60, image.TYPE_INT_ARGB);
			map.put("Ryan Chase", new ImageIcon(image));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return map;
	}
	public void addtomap(String eventName, ImageIcon newIcon){ //when trying to add new events
		imageMap.put(eventName, newIcon);
	}
	 public BufferedImage resizeImage(BufferedImage originalImage, int width, int height, int type) throws IOException {  
	        BufferedImage resizedImage = new BufferedImage(width, height, type);  
	        Graphics2D g = resizedImage.createGraphics();  
	        g.drawImage(originalImage, 0, 0, width, height, null);  
	        g.dispose();  
	        return resizedImage;  
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
