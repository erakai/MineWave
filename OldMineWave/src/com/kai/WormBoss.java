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

//need to add turrets after this
public class WormBoss extends Enemy {
	private BufferedImage wormRight, wormUpRight, wormUp, wormUpLeft, wormLeft, wormDownLeft, wormDown, wormDownRight;
	int chargeTick, damageTick;
	int chargeX, chargeY;
	
	public WormBoss(int x, int y) {
		width = 50;
		height = 50;
		this.x = x;
		this.y = y;
		health = 50;
		face = "wormRight";
		damage = 8;
		speed = 7;
		chargeTick = 0;
		damageTick = 0;
		chargeX = -1;
		chargeY = -1;
		
		try {                
		wormRight = ImageIO.read(new File("OldMineWave/src/com/kai/WormBossSprites/WormRight.png"));
		} catch (IOException ex) { System.out.println("worm exception"); }
		try {                
		wormUpRight = ImageIO.read(new File("OldMineWave/src/com/kai/WormBossSprites/WormUpRight.png"));
		} catch (IOException ex) { System.out.println("worm exception"); }
		try {                
		wormUp = ImageIO.read(new File("OldMineWave/src/com/kai/WormBossSprites/WormUp.png"));
		} catch (IOException ex) { System.out.println("worm exception"); }
		try {                
		wormUpLeft = ImageIO.read(new File("OldMineWave/src/com/kai/WormBossSprites/WormUpLeft.png"));
		} catch (IOException ex) { System.out.println("worm exception"); }
		try {                
		wormLeft = ImageIO.read(new File("OldMineWave/src/com/kai/WormBossSprites/WormLeft.png"));
		} catch (IOException ex) { System.out.println("worm exception"); }
		try {                
		wormDownLeft = ImageIO.read(new File("OldMineWave/src/com/kai/WormBossSprites/WormDownLeft.png"));
		} catch (IOException ex) { System.out.println("worm exception"); }
		try {                
		wormDown = ImageIO.read(new File("OldMineWave/src/com/kai/WormBossSprites/WormDown.png"));
		} catch (IOException ex) { System.out.println("worm exception"); }
		try {                
		wormDownRight = ImageIO.read(new File("OldMineWave/src/com/kai/WormBossSprites/WormDownRight.png"));
		} catch (IOException ex) { System.out.println("worm exception"); }
	}	

	public void drawMe(Graphics g) {
				
		if ((Player.x - x) > 20) {
			if ((Player.y - y) > 20) {
				face = "wormDownRight";
			}	else if (Player.y - y < -20) {
				face = "wormUpRight";
			}	else {
				face = "wormRight";
			}	
		} else if ((Player.x - x) < 20) {
			if ((Player.y - y) > 20) {
				face = "wormDownLeft";
			}	else if (Player.y - y < -20) {
				face = "wormUpLeft";
			}	else {
				face = "wormLeft";
			}	
		} else {
			if (Player.y > y) {
				face = "wormDown";
			} else {
				face = "wormUp";
			}	
		}
		
		if (health > 0) {
			if (face.equals("wormRight")) {
				g.drawImage(wormRight, x, y, null);
			} else if (face.equals("wormUpRight")) {
				g.drawImage(wormUpRight, x, y, null);
			} else if (face.equals("wormUp")) {
				g.drawImage(wormUp, x, y, null);
			} else if (face.equals("wormUpLeft")) {
				g.drawImage(wormUpLeft, x, y, null);
			} else if (face.equals("wormLeft")) {
				g.drawImage(wormLeft, x, y, null);
			} else if (face.equals("wormDownLeft")) {
				g.drawImage(wormDownLeft, x, y, null);
			} else if (face.equals("wormDown")) {
				g.drawImage(wormDown, x, y, null);
			} else if (face.equals("wormDownRight")) {
				g.drawImage(wormDownRight, x, y, null);
			}
			g.setColor(Color.red);
			g.fillRect(x, y-10, (int)((double)health/60 * 60) , 5);						
		}	
	}	

	public void chase(int tX, int tY) {
		//needs to see change face to whatever direction is closest to facing the player
		//needs to have a chargeTick. when it reaches w/e itll charge at super speed at the player

		if (chargeTick < 70) {
			chargeTick++;	
		} else {
		//if there is no chargeX/Y already, get them
		//find the line  towards that X/Y
		//go on that line towards that X/Y
		//if at that X/Y set chargeX/Y to -1 and chargeTick to 0
		//y = mx + b
			if (chargeX == -1) {
				chargeX = Player.x;
				chargeY = Player.y;
			}	
			if (Math.abs(chargeX - x) > 10 && Math.abs(chargeY - y) > 10 ){
				double m = (double)(chargeY - y) / (double)(chargeX - x);	
				double b = y - (m * x);
				if (x > chargeX) {
					x-=speed;
					y = (int)((m*x) + b);
				} else if (x < chargeX) {
					x+=speed;
					y = (int)((m*x) + b);
				} else if (x == chargeX) {
					if (y < chargeY) {
						y+=speed;
					} else if (y > chargeY) {
						y-=speed;
					}	
				}		
			} else {
				chargeX = -1;
				chargeY = -1;
				chargeTick = 0;
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
					Player.killedBy = "Worm Boss";
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















