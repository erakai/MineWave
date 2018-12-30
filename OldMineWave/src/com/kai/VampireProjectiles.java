

package com.kai;

import java.awt.Graphics;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


//future: make a projectile superclass so I don't have to copy and paste this every time I wanna make new projectiles
public class VampireProjectiles {
	int x, y, px, py, maxDistance;
	int height, width;
	static int speed;
	private BufferedImage vampireproj;
	
	public VampireProjectiles(int x, int y, int spx, int spy) {
		height = 16;
		width = 16;
		this.x = x;
		this.y = y;
		px = spx;
		py = spy;
		speed = 5;
		try {                
		vampireproj = ImageIO.read(new File("OldMineWave/src/com/kai/Boss2Projectile.png"));
		} catch (IOException ex) { System.out.println("vampireproj exception"); } 
	
	}
	
	
	public void drawMe(Graphics g) {
		g.drawImage(vampireproj, x, y, null);
	}	
	
	
	public void shoot() {
			double deltaX = px - x;
			double deltaY = py - y;
			double direction = Math.atan2(deltaY,deltaX); 
  


			x = (int)((x + + (speed * Math.cos(direction))));
			y = (int)((y+ + (speed * Math.sin(direction))));
		
		if (Math.abs(x - px) < 10 && Math.abs(y - py) < 10) {
			VampireBoss.listOfProjectiles.remove(this);
		}	
	}
	
	public void checkCollisionWithPlayer() {
		int iCx = Player.x + Player.width/2;
		int iCy = Player.y + Player.height/2;
		int cX = x + 30/2;
		int cY = y + 30/2;
		
		if (Math.abs(iCx-cX) < (0.5 * (double)width + 0.5 * (double)Player.width) && Math.abs(iCy-cY) < (0.5 * (double)height + 0.5 * (double)Player.height)) {
			Player.takeDamage(VampireBoss.damage);
			if (Player.health < 1) {
				Player.killedBy = "Vampire";
			}	
			VampireBoss.listOfProjectiles.remove(this);	
		}	
	}
	
	
	
	
	
}
	
	
	
	
	