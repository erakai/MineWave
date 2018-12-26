package com.kai;


import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Menu {
	private BufferedImage menu;
	
	public void updateMenu(Graphics g, int h) {
		try {                
          menu = ImageIO.read(new File("Menu.png"));
        } catch (IOException ex) {
			System.out.println("exception");
		}
		g.drawImage(menu, 310, 500, null);
		g.setColor(Color.red);
		g.fillRect(311, 533, (int)((double)h/20 * 264) , 29);
		g.setColor(Color.yellow);
		double length = (((double)PlayerWand.listOfMines.size() / (double)Player.maxMines)) * 264;
		g.fillRect(648,533, (int)(length), 29);
	}	







}