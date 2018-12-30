package com.kai;


import com.kai.Enemy;
import com.kai.LevelHandler;
import com.kai.Player;
import com.kai.PlayerWand;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class Turret extends Enemy {
	private BufferedImage turretImage;
	int shootTick;
	ArrayList<TurretProjectile> listOfProjectiles = new ArrayList<TurretProjectile>();
	static int damage;
	
	public Turret(int x, int y) {
		width = 20;
		height = 100;
		this.x = x;
		this.y = y;
		health = 10;
		damage = 2;
		speed = 0;
		shootTick = 0;
		try {                
		turretImage = ImageIO.read(new File("OldMineWave/src/com/kai/Turret.png"));
		} catch (IOException ex) { System.out.println("turret exception"); }    
	}
	
	
	public void drawMe(Graphics g) {
		g.drawImage(turretImage, x, y, null);
		g.setColor(Color.red);
		g.fillRect(x, y-10, (int)((double)health/10 * 20) , 5);			
		for(int i = 0; i < listOfProjectiles.size(); i++) {
			listOfProjectiles.get(i).drawMe(g);
		}	
		
	}
	
	public void chase(int targetX, int targetY) {
		if (shootTick > 100) {
			listOfProjectiles.add(new TurretProjectile(x, y, x, y-500, this));			//shoots up
			listOfProjectiles.add(new TurretProjectile(x, y, x-500, y-500, this));			//shoots up left
			listOfProjectiles.add(new TurretProjectile(x, y, x-500, y, this));			//shoots left
			listOfProjectiles.add(new TurretProjectile(x, y, x-500, y+500, this));			//shoots down left
			listOfProjectiles.add(new TurretProjectile(x, y, x, y+500, this));			//shoots down
			listOfProjectiles.add(new TurretProjectile(x, y, x+500, y+500, this));			//shoots down right
			listOfProjectiles.add(new TurretProjectile(x, y, x+500, y, this));			//shoots right
			listOfProjectiles.add(new TurretProjectile(x, y, x+500, y-500, this));			//shoots up right
			shootTick = 0;
		} else {
			shootTick++;
		}	
		
		for(int i = 0; i < listOfProjectiles.size(); i++) {
			listOfProjectiles.get(i).shoot();
		}	
	}

	public void checkCollision() {
		for(int i = 0; i < listOfProjectiles.size(); i++) {
			listOfProjectiles.get(i).checkCollisionWithPlayer();
		}	
		int dmgamount = PlayerWand.checkCollision(x,y,width,height);
		if( dmgamount > 0) {
			for (int i = 0;  i < dmgamount; i++) {	
				takeDamage(Player.damage);
			}
			if (health < 1) {
				LevelHandler.listOfEnemies.remove(this);
			}	
		}
	}

	
}	