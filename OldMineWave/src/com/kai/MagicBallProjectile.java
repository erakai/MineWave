package com.kai;


import com.kai.MagicalBall;

import java.awt.Graphics;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class MagicBallProjectile {
	int x, y, px, py;
	int height = 16;
	int width = 16;
	int speed;
	private BufferedImage myprojimage;
	private MagicalBall myMagicBall;

	public MagicBallProjectile(int x, int y, int spx, int spy, MagicalBall mine) {
		this.x = x;
		this.y = y;
		px = spx;
		py = spy;
		speed = 3;
		myMagicBall = mine;
		try {                
		myprojimage = ImageIO.read(new File("EnemyBallProj.png"));
		} catch (IOException ex) { System.out.println("magic ball projectile exception"); } 
	
	}
	
	public void drawMe(Graphics g) {
		g.drawImage(myprojimage, x, y, null);
	}	
	
	public void towardsPlayer() {
			double deltaX = px - x;
			double deltaY = py - y;
			double direction = Math.atan2(deltaY,deltaX); 
			x = (int)((x + + (speed * Math.cos(direction))));
			y = (int)((y+ + (speed * Math.sin(direction))));
		
		if (Math.abs(x - px) < 10 && Math.abs(y - py) < 10) {
			myMagicBall.listOfProjectiles.remove(this);
		}	
	}		
	
	public void checkCollisionWithPlayer() {
		int iCx = Player.x + Player.width/2;
		int iCy = Player.y + Player.height/2;
		int cX = x + width/2;
		int cY = y + height/2;
		
		if (Math.abs(iCx-cX) < (0.5 * (double)width + 0.5 * (double)Player.width) && Math.abs(iCy-cY) < (0.5 * (double)height + 0.5 * (double)Player.height)) {
			Player.takeDamage(MagicalBall.damage);
			if (Player.health < 1) {
					Player.killedBy = "Magic Ball";
			}	
			myMagicBall.listOfProjectiles.remove(this);	
		}	
	}		
}	