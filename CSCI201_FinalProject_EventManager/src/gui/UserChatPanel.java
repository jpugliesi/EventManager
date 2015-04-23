package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ScrollPaneConstants;

import client.ClientGetProfilePictureThread;
import main.User;

public class UserChatPanel extends JScrollPane {

	private Map<String, ImageIcon> imageMap;
	
	

	
	public UserChatPanel(JList list) {
		super(list);
		//imageMap = createImageMap(nameList);
		list.setCellRenderer(new ChatRenderer());
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.setPreferredSize(new Dimension(300, 300));
	}

/*	private Map<String, ImageIcon> createImageMap(String[] list) {
		Map<String, ImageIcon> map = new HashMap<>();
		try {
			BufferedImage image = null;
			image = ImageIO.read(getClass().getResource("boy1.png"));
			image = resizeImage(image, 60, 60, image.TYPE_INT_ARGB);
			map.put("Kieran Strolorz", new ImageIcon(image));
			image = ImageIO.read(getClass().getResource("boy2.png"));
			image = resizeImage(image, 60, 60, image.TYPE_INT_ARGB);
			map.put("John Pugliesi", new ImageIcon(image));
			image = ImageIO.read(getClass().getResource("boy2.png"));
			image = resizeImage(image, 60, 60, image.TYPE_INT_ARGB);
			map.put("Zack Kim", new ImageIcon(image));
			image = ImageIO.read(getClass().getResource("woman1.png"));
			image = resizeImage(image, 60, 60, image.TYPE_INT_ARGB);
			map.put("Vincent Jin", new ImageIcon(image));
			image = ImageIO.read(getClass().getResource("woman2.png"));
			image = resizeImage(image, 60, 60, image.TYPE_INT_ARGB);
			map.put("Jeffrey Miller", new ImageIcon(image));
			image = ImageIO.read(getClass().getResource("woman3.png"));
			image = resizeImage(image, 60, 60, image.TYPE_INT_ARGB);
			map.put("Ryan Chase", new ImageIcon(image));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return map;
	}*/

	public void addtomap(String eventName, ImageIcon newIcon) { 
		// when trying															// to add new																// events
		imageMap.put(eventName, newIcon);
	}

	public BufferedImage resizeImage(BufferedImage originalImage, int width,
			int height, int type) throws IOException {
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
	}

/*	class EventFeedRenderer extends DefaultListCellRenderer { // changes each UI
																// CELL

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
	}*/
	
	class ChatRenderer extends JPanel implements ListCellRenderer<User>{
		private JLabel user_name;
		
		public ChatRenderer(){
			super();
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.setOpaque(true);
			
			user_name = new JLabel("");
			this.add(user_name);
		}
		
		public Component getListCellRendererComponent(JList<? extends User> list, User user, int index, 
				boolean isSelected, boolean cellHasFocus) {
			
			this.user_name.setText("<html><b>" + user.getFullName() + "</b>"+"</html>");
			
			ClientGetProfilePictureThread cgppt = new ClientGetProfilePictureThread(user);
			cgppt.start();
			
			ImageIcon icon = cgppt.getProfilePicture();
			
			this.user_name.setIcon(icon);
			/*try{
				this.user_name.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("boy1.png"))));
			}catch (IOException e){
				e.printStackTrace();
			}*/
			
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
