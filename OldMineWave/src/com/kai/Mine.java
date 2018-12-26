package com.kai;


import java.awt.Graphics;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Mine {
	int x, y;
	int height = 25;
	int width = 25;
	private BufferedImage mine;
	
	public Mine(int x, int y) {
		this.x = x;
		this.y = y;
		try {                
		mine = ImageIO.read(new File("Mine.png"));
		} catch (IOException ex) { System.out.println("mine exception"); }
	}

	public void drawMe(Graphics g) {
		g.drawImage(mine,x,y,null);
	}	
	
	public boolean checkCollision(int iX, int iY, int iW, int iH) {
		int iCx = iX + iW/2;
		int iCy = iY + iH/2;
		int cX = x + width/2;
		int cY = y + height/2;
		
		if (Math.abs(iCx-cX) < (0.5 * (double)width + 0.5 * (double)iW) && Math.abs(iCy-cY) < (0.5 * (double)height + 0.5 * (double)iH)) {
			return true;
		}	
		return false;
	}





}