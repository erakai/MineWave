package com.kai;


import com.kai.Enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

//later make sprites for each way its facing

public class Insect extends Enemy {
	private BufferedImage insectStraight;
	private BufferedImage insectAttacking;
	//private BufferedImage insectLeft;
	//private BufferedImage insectRight;
	private boolean Iattacking;
	int damageTick;
	
	public Insect (int x, int y) {
		width = 55;
		height = 55;
		this.x = x;
		this.y = y;
		health = 6;
		face = "up";
		damage = 2;
		Iattacking = false;
		speed = 2;
		damageTick = 0;
		try {                
		insectStraight = ImageIO.read(new File("insectStraight.png"));
		} catch (IOException ex) { System.out.println("ins exception"); }
		try {                
		insectAttacking = ImageIO.read(new File("insectAttacking.png"));
		} catch (IOException ex) { System.out.println("ins exception"); } 
	}
	
	
	public void drawMe(Graphics g) {
		if (health > 0) {
			if (Iattacking) {
				g.drawImage(insectAttacking, x, y, null);
			} else {
				g.drawImage(insectStraight, x, y, null);
			}
			g.setColor(Color.red);
			g.fillRect(x, y-10, (int)((double)health/6 * 48) , 5);			
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
		
		double a = Math.abs(x - targetX);
		double b = Math.abs(y - targetY);
		double distance = Math.sqrt((a*a) + (b*b));
		
		
		if (distance < 75) {
			Iattacking = true;
			speed = 4;
		} else {
			Iattacking = false;
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
					Player.killedBy = "Insect";
				}	
				damageTick++;
			} else {
				damageTick++;
				if (damageTick == 40) {
					damageTick = 0;
				}	
			}	
			
		}	
	}	
	
}	

















