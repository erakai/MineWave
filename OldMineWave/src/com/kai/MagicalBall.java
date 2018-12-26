package com.kai;


import com.kai.Enemy;
import com.kai.LevelHandler;

import java.awt.Graphics;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Random;


public class MagicalBall extends Enemy {
	private BufferedImage myimage;
	int shootTick, teleportTick;
	ArrayList<MagicBallProjectile> listOfProjectiles = new ArrayList<MagicBallProjectile>();
	static int damage;
	Random rand;
	
	public MagicalBall(int x, int y) {
		width = 60;
		height = 60;
		this.x = x;
		this.y = y;
		health = 9;
		damage = 3;
		rand = new Random();
		shootTick = 0;
		teleportTick = rand.nextInt(249)+1;
		try {                
		myimage = ImageIO.read(new File("EnemyBall.png"));
		} catch (IOException ex) { System.out.println("magical ball exception"); }    
	}	
	
	public void drawMe(Graphics g) {
		g.drawImage(myimage, x, y, null);
		g.setColor(Color.red);
		g.fillRect(x, y-10, (int)((double)health/9 * 60) , 5);			
		for(int i = 0; i < listOfProjectiles.size(); i++) {
			listOfProjectiles.get(i).drawMe(g);
		}	
		
	}
	
	public void chase(int targetX, int targetY) {
		if (shootTick > 15 && teleportTick > 75) {
			listOfProjectiles.add(new MagicBallProjectile(x+30, y+30, x+30, y-80, this));			
			listOfProjectiles.add(new MagicBallProjectile(x+30, y+30, x-50, y-50, this));		
			listOfProjectiles.add(new MagicBallProjectile(x+30, y+30, x-50, y+30, this));			
			listOfProjectiles.add(new MagicBallProjectile(x+30, y+30, x-50, y+110, this));		
			listOfProjectiles.add(new MagicBallProjectile(x+30, y+30, x+30, y+110, this));			
			listOfProjectiles.add(new MagicBallProjectile(x+30, y+30, x+110, y+110, this));			
			listOfProjectiles.add(new MagicBallProjectile(x+30, y+30, x+110, y+30, this));			
			listOfProjectiles.add(new MagicBallProjectile(x+30, y+30, x+110, y-50, this));
			shootTick = 0;
		} else {
			shootTick++;
		}	
		if (teleportTick > 250) {
			x = Player.x+10;
			y = Player.y+30;
			teleportTick = 0;
		}	else {
			teleportTick++;
		}	
		
		
		
		for(int i = 0; i < listOfProjectiles.size(); i++) {
			listOfProjectiles.get(i).towardsPlayer();
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