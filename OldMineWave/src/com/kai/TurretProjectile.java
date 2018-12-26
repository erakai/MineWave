package com.kai;


import com.kai.Player;
import com.kai.Turret;

import java.awt.Graphics;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class TurretProjectile {
	int x, y, px, py, distanceTick, maxDistance;
	int height, width, speed;
	private BufferedImage turretproj;
	Turret myTurret;
	
	public TurretProjectile(int x, int y, int spx, int spy, Turret mine) {
		height = 31;
		width = 31;
		this.x = x;
		this.y = y;
		px = spx;
		py = spy;
		distanceTick = 0;
		speed = 2;
		myTurret = mine;
		try {                
		turretproj = ImageIO.read(new File("TurretProjectile.png"));
		} catch (IOException ex) { System.out.println("turreproj exception"); } 
	
	}
	
	
	public void drawMe(Graphics g) {
		g.drawImage(turretproj, x, y, null);
	}	
	
	
	public void shoot() {
		if (x > px) {
			int m = (py - y) / (px - x);	
			int b = y - (m * x);
			x-=speed;
			y = (m*x) + b;
		} else if (x < px) {
			int m = (py - y) / (px - x);	
			int b = y - (m * x);
			x+=speed;
			y = (m*x) + b;
		} else if (x == px) {
			if (y < py) {
				y+=speed;
			} else if (y > py) {
				y-=speed;
			}	
		}	
		
		if (x == px && y == py) {
			myTurret.listOfProjectiles.remove(this);
		}	
	}		
	
	public void checkCollisionWithPlayer() {
		int iCx = Player.x + Player.width/2;
		int iCy = Player.y + Player.height/2;
		int cX = x + width/2;
		int cY = y + height/2;
		
		if (Math.abs(iCx-cX) < (0.5 * (double)width + 0.5 * (double)Player.width) && Math.abs(iCy-cY) < (0.5 * (double)height + 0.5 * (double)Player.height)) {
			Player.takeDamage(Turret.damage);
			if (Player.health < 1) {
				Player.killedBy = "Turret";
			}	
			myTurret.listOfProjectiles.remove(this);	
		}	
	}
	
	
	
	
	
}