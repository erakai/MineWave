package com.kai;

import java.awt.Graphics;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Random;
import java.util.ArrayList;
public class Beetle extends Enemy {
	private BufferedImage beetle;
	int shootTick;
	ArrayList<beetleProjectile> listOfProjectiles = new ArrayList<beetleProjectile>();
	Random rand;
	static int damage;
	
	public Beetle(int x, int y) {
		width = 40;
		height = 40;
		this.x = x;
		this.y = y;
		health = 3;
		damage = 5;
		speed = 1;
		shootTick = 0;
		rand = new Random();
		try {                
		beetle = ImageIO.read(new File("beetle.png"));
		} catch (IOException ex) { System.out.println("beetle exception"); }    
	}	
	
	//all functions will also execute the same function in the projectiles
	public void drawMe(Graphics g) {
		g.drawImage(beetle, x, y, null);
		g.setColor(Color.red);
		g.fillRect(x, y-10, (int)((double)health/3 * 40) , 5);			
		for(int i = 0; i < listOfProjectiles.size(); i++) {
			listOfProjectiles.get(i).drawMe(g);
		}	
		
	}
	
	public void chase(int targetX, int targetY) {
		if (shootTick > 80) {
			int currentPx = Player.x;
			int currentPy = Player.y;
			listOfProjectiles.add(new beetleProjectile(x, y, currentPx, currentPy, this));
			shootTick = 0;
		} else {
			shootTick++;
		}	
		
		
		for(int i = 0; i < listOfProjectiles.size(); i++) {
			listOfProjectiles.get(i).towardsPlayer();
		}		
		
		double a = Math.abs(x - targetX);
		double b = Math.abs(y - targetY);
		double distance = Math.sqrt((a*a) + (b*b));
		
		if (280 < distance && distance < 350) {
			int dir = rand.nextInt(4) + 1;
			switch (dir) {
				case 1:
					x++;
					break;
				case 2:
					x--;
					break;
				case 3:
					y++;
					break;
				case 4:
					y--;
					break;
			}	
		} else if (distance < 281) {
			if (x > targetX) {
				moveRight();
			}	
			if (x < targetX) {
				moveLeft();
			}	
			if (y > targetY) {
				moveDown();
			}	
			if (y < targetY) {
				moveForward();
			}			
		} else if (distance > 349) {
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
	}	
	
	public void checkCollision() {
		for(int i = 0; i < listOfProjectiles.size(); i++) {
			listOfProjectiles.get(i).checkCollisionWithPlayer();
		}	
		int dmgamount = PlayerWand.checkCollision(x,y,width,height);
		if( dmgamount > 0) {
			for (int i = 0; i < dmgamount; i++) {	
				takeDamage(Player.damage);
			}
			if (health < 1) {
				LevelHandler.listOfEnemies.remove(this);
			}	
		}
	}
	
	
}	