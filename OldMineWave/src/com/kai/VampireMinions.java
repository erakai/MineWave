package com.kai;


import com.kai.Enemy;
import com.kai.Player;
import com.kai.PlayerWand;
import com.kai.VampireBoss;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;



public class VampireMinions extends Enemy {
	private BufferedImage myimage;
	int damageTick;
	
	
	public VampireMinions (int x, int y) {
		width = 40;
		height = 40;
		this.x = x;
		this.y = y;
		health = 3;
		face = "up";
		damage = 1;
		speed = 2;
		damageTick = 0;
		try {                
		myimage = ImageIO.read(new File("Boss2Minion.png"));
		} catch (IOException ex) { System.out.println("vampire minion exception"); }
	}
	
	
	public void drawMe(Graphics g) {
		if (health > 0) {
			g.drawImage(myimage, x, y, null);
			g.setColor(Color.red);
			g.fillRect(x, y-10, (int)((double)health/3 * 40) , 5);			
		}
	}	
	
	
	public void chase(int targetX, int targetY) {
		if (Math.random() > 0.5) {
			if (x > targetX) {
				moveLeft();
			}	
			if (x < targetX) {
				moveRight();
			}	
		} else {
			if (y > targetY) {
				moveForward();
			}	
			if (y < targetY) {
				moveDown();
			}	
		}
	}
	
	public void checkCollision() {
		int dmgamount = PlayerWand.checkCollision(x,y,width,height);
		if( dmgamount > 0) {
			for (int i = 0;  i < dmgamount; i++) {	
				takeDamage(Player.damage);
			}
			if (health < 1) {
				VampireBoss.listOfMinions.remove(this);
			}	
		}
		int iCx = Player.x + Player.width/2;
		int iCy = Player.y + Player.height/2;
		int cX = x + width/2;
		int cY = y + height/2;
		
		if (Math.abs(iCx-cX) < 30 && Math.abs(iCy-cY) < 30 && health > 0) {
			if (damageTick == 0) {
				Player.takeDamage(damage);
				if (Player.health < 1) {
					Player.killedBy = "Vampire";
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