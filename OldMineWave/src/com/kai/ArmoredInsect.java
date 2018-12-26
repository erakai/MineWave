package com.kai;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;



public class ArmoredInsect extends Enemy {
	private BufferedImage selfidle;
	int damageTick;
	
	public ArmoredInsect(int x, int y) {
		width = 50;
		height = 50;
		this.x = x;
		this.y = y;
		health = 13;
		damage = 4;
		speed = 1;
		damageTick = 0;
		try {                
		selfidle = ImageIO.read(new File("ArmoredInsect1.png"));
		} catch (IOException ex) { System.out.println("armored ins exception"); }
	}		
	
	public void drawMe(Graphics g) {
		if (health > 0) {
			g.drawImage(selfidle, x, y, null);
			g.setColor(Color.red);
			g.fillRect(x, y-10, (int)((double)health/13 * 48) , 5);			
		}
	}	
	
	public void chase(int targetX, int targetY) {
		if (x > targetX) {
			moveLeft();
		}	
		if (x < targetX) {
			moveRight();
		}	
		if (y > targetY) {
			moveForward();
		}	
		if (y < targetY) {
			moveDown();
		}	
	}

	
	public void checkCollision() {
		int dmgamount = PlayerWand.checkCollision(x,y,width,height); 
		if( dmgamount > 0) {
			for (int i = 0;  i < dmgamount; i++) {	
				takeDamage(Player.damage);
			}
			if (health < 1) {
				LevelHandler.listOfEnemies.remove(this);
			}	
		}
		int iCx = Player.x + Player.width/2;
		int iCy = Player.y + Player.height/2;
		int cX = x + width/2;
		int cY = y + height/2;
		
		if (Math.abs(iCx-cX) < 25 && Math.abs(iCy-cY) < 25 && health > 0) {
			if (damageTick == 0) {
				Player.takeDamage(damage);
				if (Player.health < 1) {
					Player.killedBy = "Armored Insect";
				}	
				damageTick++;
			} else {
				damageTick++;
				if (damageTick == 60) {
					damageTick = 0;
				}	
			}	
			
		}	
	}	
	
	
}	