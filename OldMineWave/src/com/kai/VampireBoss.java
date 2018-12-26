package com.kai;


import com.kai.*;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class VampireBoss extends Enemy {
	private BufferedImage myimage, invincible;
	int damageTick, stage, shootTick, summonTick, summonwaves;
	static ArrayList<VampireProjectiles> listOfProjectiles = new ArrayList<VampireProjectiles>();
	static ArrayList<VampireMinions> listOfMinions = new ArrayList<VampireMinions>();
	boolean immune;
	static int damage;
	
	
	
	//CALL THE LISTS METHODS
	
	
	
	
	
	
	
	
	
	public VampireBoss(int x, int y) {
		width = 70;
		height = 90;
		this.x = x;
		this.y = y;
		health = 240;
		damage = 3;
		speed = 2;
		damageTick = 0;
		shootTick = 0;
		summonTick = 140;
		summonwaves = 0;
		immune = false;
		stage = 1;
		try {                
		myimage = ImageIO.read(new File("Boss2.png"));
		} catch (IOException ex) { System.out.println("vamp exception"); }
		try {                
		invincible = ImageIO.read(new File("Boss2Invincible.png"));
		} catch (IOException ex) { System.out.println("vamp exception"); } 
	}
	
	
	public void drawMe(Graphics g) {
		if (health > 0) {
			if (!immune) {
				g.drawImage(myimage , x, y, null);
				g.setColor(Color.red);
				g.fillRect(x, y-10, (int)((double)health/240 * 70) , 5);		
			} else {
				g.drawImage(invincible, x, y, null);	
			}	
	
			for(int i = 0; i < listOfProjectiles.size(); i++) {
				listOfProjectiles.get(i).drawMe(g);
			}
			for(int i = 0; i < listOfMinions.size(); i++) {
				listOfMinions.get(i).drawMe(g);
			}				
		}	
		
	}	
	
	
	
	//stage 1: chase player around
	//stage 2: shoot at player w/o moving
	//stage 3: summon minions and become invincible
	//stage 4: chase around and shoot
	//stage 5: minion phase
	//stage 6: rage phase, higher speed, damage, chasing + faster projectiles
	public void chase (int targetX, int targetY) {
		
		for(int i = 0; i < listOfProjectiles.size(); i++) {
			listOfProjectiles.get(i).shoot();
		}
		for(int i = 0; i < listOfMinions.size(); i++) {
			listOfMinions.get(i).chase(targetX, targetY);
		}	
		
		
		
		if (health < 40) {
			stage = 6;
		} else if (health < 80) {
			stage = 5;
		} else if (health < 120) {
			stage = 4;
		} else if (health < 160) {
			stage = 3;
		} else if (health < 200) {
			stage = 2;
		} else {
			stage = 1;
		}	
		
		if (stage == 1) {
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
		} else if (stage == 2) {
			x = 565;
			y = 100;
			
			if (shootTick > 20) {
				listOfProjectiles.add(new VampireProjectiles(x+35, y, Player.x, Player.y));
				listOfProjectiles.add(new VampireProjectiles(x+35, y, Player.x, Player.y+90));		
				listOfProjectiles.add(new VampireProjectiles(x+35, y, Player.x, Player.y-90));		
				shootTick = 0;
			} else {
				shootTick++;
			}	
				
		} else if (stage == 3) {
			listOfProjectiles.clear();
			x = 565;
			y = 100;
			shootTick = 0;
			immune = true;
			if ( summonwaves > 3 && listOfMinions.size() == 0) {
				health = 119;
			}	
			
			if (summonTick > 200 && summonwaves < 4) {
				for (int i = 1;i<4;i++) {
					listOfMinions.add(new VampireMinions(20, i*150));
				}	
				for (int n = 1; n<4;n++) {
					listOfMinions.add(new VampireMinions(1180, n*150));
				}	
				
				summonwaves++;
				summonTick = 0;
			} else {
				summonTick++;	
			}	
			
			
		} else if (stage == 4) {
			immune = false;
			summonTick = 120;
			summonwaves = 0;
			
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
			
			if (shootTick > 20) {
				listOfProjectiles.add(new VampireProjectiles(x+35, y, Player.x, Player.y));	
				listOfProjectiles.add(new VampireProjectiles(x+35, y, Player.x, Player.y+90));		
				listOfProjectiles.add(new VampireProjectiles(x+35, y, Player.x, Player.y-90));						
				shootTick = 0;
			} else {
				shootTick++;
			}	
			
		} else if (stage == 5) {
			listOfProjectiles.clear();
			x = 565;
			y = 100;
			shootTick = 0;
			immune = true;
			if ( summonwaves > 2 && listOfMinions.size() == 0) {
				health = 39;
			}	
			
			if (summonTick > 150 && summonwaves < 3) {
				for (int k = 1;k<4;k++) {
					listOfMinions.add(new VampireMinions(20, k*150));
				}	
				for (int m = 1; m<4;m++) {
					listOfMinions.add(new VampireMinions(1180, m*150));
				}	
				
				summonwaves++;
				summonTick = 0;
			} else {
				summonTick++;	
			}	
		}	else if (stage == 6) {
			immune = false;
			speed = 3;
			damage = 4;
			VampireProjectiles.speed = 6;
			
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
			
			if (shootTick > 10) {
				listOfProjectiles.add(new VampireProjectiles(x+35, y, Player.x, Player.y));	
				listOfProjectiles.add(new VampireProjectiles(x+35, y, Player.x, Player.y+90));		
				listOfProjectiles.add(new VampireProjectiles(x+35, y, Player.x, Player.y-90));						
				shootTick = 0;
			} else {
				shootTick++;
			}	
			
		}	
		
		

	}
	
	public void checkCollision() {
		int dmgamount = PlayerWand.checkCollision(x,y,width,height);
		if( dmgamount > 0 && !immune) {
			for (int i = 0;  i < dmgamount; i++) {	
				takeDamage(Player.damage);
			}
			if (health < 1) {
				listOfProjectiles.clear();
				LevelHandler.listOfEnemies.remove(this);
			}	
		}
		int iCx = Player.x + Player.width/2;
		int iCy = Player.y + Player.height/2;
		int cX = x + width/2;
		int cY = y + height/2;
		
		if (Math.abs(iCx-cX) < 60 && Math.abs(iCy-cY) < 60 && health > 0) {
			if (damageTick == 0 && !immune) {
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

		for(int i = 0; i < listOfProjectiles.size(); i++) {
			listOfProjectiles.get(i).checkCollisionWithPlayer();
		}
		for(int i = 0; i < listOfMinions.size(); i++) {
			listOfMinions.get(i).checkCollision();
		}	

		
		
	}
	
	
	
	
	
}	