package com.kai;


import com.kai.Enemy;
import com.kai.Insect;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

//big rock thing that becomes 2 insects on death
//in chase() has a tick that every x seconds spawns an insect
public class InsectNest extends Enemy {
	private BufferedImage insectNest;
	int spawnTick, damageTick, moveTick;
	
	public InsectNest(int x, int y) {
		width = 80;
		height = 80;
		this.x = x;
		this.y = y;
		health = 12;
		face = "up";
		damage = 1;
		speed = 1;
		spawnTick = 200;
		damageTick = 0;
		moveTick = 0;
		try {                
		insectNest = ImageIO.read(new File("OldMineWave/src/com/kai/insectNest.png"));
		} catch (IOException ex) { System.out.println("nest exception"); }
	}	
		
	public void drawMe(Graphics g) {
		if (health > 0) {
			g.drawImage(insectNest, x, y, null);
			g.setColor(Color.red);
			g.fillRect(x, y, (int)((double)health/10 * 80) , 5);			
		}
	}	
	
	public void chase(int targetX, int targetY) {
	/*	if (moveTick % 2 == 0) {
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
			
			if (moveTick > 300) {
				moveTick = 0;
			}	
		}
		moveTick++;*/
		if (spawnTick < 250) {
			spawnTick++;	
		} else {
			spawnTick = 0;
			LevelHandler.listOfEnemies.add(new Insect(x+30, y+30));
		}	
	}	
	
	
	public void checkCollision() {
		int dmgamount = PlayerWand.checkCollision(x,y,width,height);
		if( dmgamount > 0) {
			for (int i = 0;  i < dmgamount; i++) {	
				takeDamage(Player.damage);
			}
			if (health < 1) {
				LevelHandler.listOfEnemies.add(new Insect(x, y+70));
				LevelHandler.listOfEnemies.add(new Insect(x, y-70));
				LevelHandler.listOfEnemies.remove(this);
			}	
		}
		int iCx = Player.x + Player.width/2;
		int iCy = Player.y + Player.height/2;
		int cX = x + width/2;
		int cY = y + height/2;
		
		if (Math.abs(iCx-cX) < 50 && Math.abs(iCy-cY) < 50 && health > 0) {
			if (damageTick == 0) {
				Player.takeDamage(damage);
				if (Player.health < 1) {
					Player.killedBy = "Insect Nest";
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