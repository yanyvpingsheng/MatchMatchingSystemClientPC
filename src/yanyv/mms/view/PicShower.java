package yanyv.mms.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class PicShower extends JPanel {

	private BufferedImage img;
	public PicShower() {
		this.setSize(1200, 1200);
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				/*int rgb = img.getRGB((int) (e.getX() * 2.5), (int) (e.getY() * 2.5));
				Color color = new Color(rgb);
				int r = color.getRed();
				int g = color.getGreen();
				int b = color.getBlue();
				System.out.println("location: " + (e.getX() * 2.5) + ", " + (e.getY() * 2.5) + "r: " + r + " g: " + g + " b: " + b);*/
			}
			
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		int x = 0;
		int y = 0;
		
		if(img != null) g.drawImage(img, x, y, (int) (img.getWidth()/2.5), (int) (img.getHeight()/2.5), this);

		//System.out.println("绘制了");
	}

	public void setImg(BufferedImage img) {
		this.img = img;
		this.setSize((int) (img.getWidth()/2.5), (int) (img.getHeight()/2.5));
		repaint();
		//System.out.println("设置了");
	}
}
