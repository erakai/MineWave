package com.kai;


import com.kai.Beetle;

import java.awt.Graphics;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

//make a projectile class that this is a subclass of later
public class beetleProjectile {
	int x, y, px, py, distanceTick, maxDistance;
	int height = 16;
	int width = 16;
	int speed;
	private BufferedImage beetleProj;
	private Beetle myBeetle;
	
	public beetleProjectile(int x, int y, int spx, int spy, Beetle mine) {
		this.x = x;
		this.y = y;
		px = spx;
		py = spy;
		distanceTick = 0;
		maxDistance = 400;
		speed = 3;
		myBeetle = mine;
		try {                
		beetleProj = ImageIO.read(new File("OldMineWave/src/com/kai/beetleProjectile.png"));
		} catch (IOException ex) { System.out.println("beetleProj exception"); } 
	
	}
	
	public void drawMe(Graphics g) {
		g.drawImage(beetleProj, x, y, null);
	}	
	
	public void towardsPlayer() {
		if (x > px) {
			x-=speed;
		}	
		if (x < px) {
			x+=speed;
		}	
		if (y > py) {
			y-=speed;
		}	
		if (y < py) {
			y+=speed;
		}	
		
		distanceTick++;
		if (distanceTick > maxDistance) {
			myBeetle.listOfProjectiles.remove(this);
		}	
	}		
	
	public void checkCollisionWithPlayer() {
		int iCx = Player.x + Player.width/2;
		int iCy = Player.y + Player.height/2;
		int cX = x + width/2;
		int cY = y + height/2;
		
		if (Math.abs(iCx-cX) < (0.5 * (double)width + 0.5 * (double)Player.width) && Math.abs(iCy-cY) < (0.5 * (double)height + 0.5 * (double)Player.height)) {
			Player.takeDamage(Beetle.damage);
			if (Player.health < 1) {
					Player.killedBy = "Beetle";
			}	
			myBeetle.listOfProjectiles.remove(this);	
		}	
	}		
}	