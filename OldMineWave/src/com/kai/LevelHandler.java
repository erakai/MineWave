package com.kai;


import com.kai.*;

import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Random;
import java.awt.Font;
//needs to have a method to check collisions for every insect/enemy
//needs to have a method to call chase for every insect/enemy
//needs to have a method to draw every insect/enemy
//only have insects for now do cool stuff later
//every level must change the enemy spawns
//after every enemy is dead (once dead enemy removes itself from list) change the level 


public class LevelHandler {
	static ArrayList<Enemy> listOfEnemies = new ArrayList<Enemy>();

	static int level;
	private BufferedImage background, bossincimage;
	static boolean bossinc;
	int bossinctick;
	boolean alreadyinc;
	Random rand;
	
	public LevelHandler() {
		level = 0;
		rand = new Random();
		try {
		background = ImageIO.read(new File("OldMineWave/src/com/kai/Background.png"));
		} catch (IOException ex) { System.out.println("background exception"); }
		try {                
		bossincimage = ImageIO.read(new File("OldMineWave/src/com/kai/bossinc.png"));
		} catch (IOException ex) { System.out.println("lvlhandler exception"); }
	}	
	
	public void drawBackground(Graphics g) {
		g.drawImage(background, 0, 0, null);
	}	

	//just to draw level in top right
	public void drawMe(Graphics g) {
		g.setColor(Color.red);
		Font newFont = new Font(g.getFont().getFontName(), Font.PLAIN, 24);
		g.setFont(newFont);	
		g.drawString("Level: " + level, 570, 595);
		
		
		if (bossinc) {
			g.drawImage(bossincimage, 500, 150, null);
			g.setColor(Color.red);
			Font f2 = new Font(g.getFont().getFontName(), Font.PLAIN, 20);
			g.setFont(f2);	
			g.drawString("BOSS INCOMING", 519, 182);
			if (bossinctick < 300) {
				bossinctick++;	
			} else {
				bossinc = false;
				bossinctick = 0;
				level -= 1;
			}
		}	
	}	
	
	public void checkEnemyCollisions() {
		for(int i = 0; i < listOfEnemies.size(); i++) {
			listOfEnemies.get(i).checkCollision();
		}	
	}
	
	public void callEnemyChase() {
		for(int i = 0; i < listOfEnemies.size(); i++) {
			listOfEnemies.get(i).chase(Player.x, Player.y);
		}	
	}

	public void drawEnemies(Graphics g) {
		for(int i = 0; i < listOfEnemies.size(); i++) {
			listOfEnemies.get(i).drawMe(g);		
		}		
	}	
	
	
	public void createNewLevel() {
		if (listOfEnemies.size() == 0 && !bossinc) {
			level++;
			
			if (level == 1) {
				levelOne();
			} else if (level == 2) {
				levelTwo();
			} else if (level == 3 || level == 4) {
				levelThree();
			}	else if (level > 4 &&  level < 8) {
				levelFour();
			}	else if (level == 8) {
				PlayerWand.listOfMines.clear();
				bossOne();
			}	else if (level > 8 && level < 16) {
				alreadyinc = false;
				levelFive();
			}	else if (level == 16) {
				PlayerWand.listOfMines.clear();
				bossTwo();
			} else if (level > 16) {
				alreadyinc = false;
				levelSix();
			}	
		}	
	}	
		
	
	public int randomX() {
		int rx = 0;
		do {
			rx = rand.nextInt(1200)+1;
		} while (Math.abs(Player.x - rx) < 60);	
		return rx;
	}	
	
	public int randomY() {
		int ry = 0;
		do {
			ry = rand.nextInt(600)+1;
		} while	(Math.abs(Player.y - ry) < 60);
		return ry;
	}	
	
	
	public void levelOne() {
		int lvl1 = rand.nextInt(1) + 1;
		
		switch (lvl1) {
			case 1: 
				listOfEnemies.add(new Insect(randomX(), randomY()));
				break;
		}
	}
	
	public void levelTwo() {
		int lvl2 = rand.nextInt(4) + 1;
		
		switch (lvl2) {
			case 1: 
				listOfEnemies.add(new Insect(randomX(), randomY()));
				listOfEnemies.add(new Insect(randomX(), randomY()));
				break;
			case 2: 
				listOfEnemies.add(new InsectNest(randomX(), randomY()));
				break;
			case 3:
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				break;
			case 4:
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				break;
		}
		
	}
	
	public void levelThree() {
		int lvl3 = rand.nextInt(2) + 1;
		
		switch (lvl3) {
			case 1:
				listOfEnemies.add(new Insect(randomX(), randomY()));
				listOfEnemies.add(new Insect(randomX(), randomY()));
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				break;
			case 2:
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				listOfEnemies.add(new InsectNest(randomX(), randomY()));
				break;
			case 3:
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				listOfEnemies.add(new Insect(randomX(), randomY()));
				break;
		}	
	}	
	
	
	public void levelFour() {
		int lvl4 = rand.nextInt(6) + 1;
		
		switch (lvl4) {
			case 1:
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				break;
			case 2:
				listOfEnemies.add(new InsectNest(randomX(), randomY()));
				listOfEnemies.add(new InsectNest(randomX(), randomY()));
				listOfEnemies.add(new Insect(randomX(), randomY()));
				listOfEnemies.add(new Insect(randomX(), randomY()));
				break;
			case 3:
				listOfEnemies.add(new Insect(randomX(), randomY()));
				listOfEnemies.add(new Insect(randomX(), randomY()));
				listOfEnemies.add(new Insect(randomX(), randomY()));
				listOfEnemies.add(new Insect(randomX(), randomY()));
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				break;
			case 4:
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));						
				break;
			case 5:
				listOfEnemies.add(new InsectNest(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				break;
			case 6:
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				break;
		}	
	}	
	
	public void levelFive() {
		int lvl5 = rand.nextInt(6) + 1;
		
		switch (lvl5) {
			case 1:
				listOfEnemies.add(new InsectNest(randomX(), randomY()));
				listOfEnemies.add(new InsectNest(randomX(), randomY()));
				listOfEnemies.add(new InsectNest(randomX(), randomY()));
				break;
			case 2:
				listOfEnemies.add(new Turret(randomX(), randomY()));
				listOfEnemies.add(new Turret(randomX(), randomY()));	
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));						
				break;
			case 3:
				listOfEnemies.add(new Insect(randomX(), randomY()));
				listOfEnemies.add(new Insect(randomX(), randomY()));
				listOfEnemies.add(new Insect(randomX(), randomY()));
				listOfEnemies.add(new Insect(randomX(), randomY()));
				listOfEnemies.add(new Insect(randomX(), randomY()));
				listOfEnemies.add(new Insect(randomX(), randomY()));
				break;
			case 4:
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));	
				break;
			case 5:
				listOfEnemies.add(new Turret(randomX(), randomY()));
				listOfEnemies.add(new Turret(randomX(), randomY()));
				listOfEnemies.add(new InsectNest(randomX(), randomY()));
				listOfEnemies.add(new InsectNest(randomX(), randomY()));
				break;
			case 6:
				listOfEnemies.add(new Insect(randomX(), randomY()));
				listOfEnemies.add(new Insect(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));					
				listOfEnemies.add(new Turret(randomX(), randomY()));
				break;
		}	
		
	}	
	
	
	public void levelSix() {
		int lvl6 = rand.nextInt(14) + 1;
		//1. 6 beetles
		//2. 5 beetles + 2 insects
		//3. 3 beetles + 3 turrets
		//4. 3 beetles + 2 turrets + 2 armored insects
		//5. 3 balls + 3 armored insects + 1 beetles
		//6. 2 insect nests + 2 balls + 2 beetles + 1 turrets
		//7. 3 insect nests + 1 ball + 1 armored insect
		//8. 3 insects + 3 armored insects + 3 beetles
		//9. 7 insects
		//10. 4 turrets + 2 balls
		//11. 5 armored insects + 3 beetles
		//12. 2 insect nests + 4 armored insects
		//13. 2 insect nests + 3 turrets
		//14. 4 balls + 4 beetles
		
		
		
		switch (lvl6) {
			case 1:
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				break;
			case 2:
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Insect(randomX(), randomY()));
				listOfEnemies.add(new Insect(randomX(), randomY()));
				break;
			case 3:
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Turret(randomX(), randomY()));	
				listOfEnemies.add(new Turret(randomX(), randomY()));	
				listOfEnemies.add(new Turret(randomX(), randomY()));	
				break;
			case 4:
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Turret(randomX(), randomY()));	
				listOfEnemies.add(new Turret(randomX(), randomY()));
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				break;
			case 5:
				listOfEnemies.add(new MagicalBall(randomX(), randomY()));
				listOfEnemies.add(new MagicalBall(randomX(), randomY()));
				listOfEnemies.add(new MagicalBall(randomX(), randomY()));
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				break;
			case 6:
				listOfEnemies.add(new InsectNest(randomX(), randomY()));
				listOfEnemies.add(new InsectNest(randomX(), randomY()));
				listOfEnemies.add(new MagicalBall(randomX(), randomY()));
				listOfEnemies.add(new MagicalBall(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Turret(randomX(), randomY()));	
				break;
			case 7:
				listOfEnemies.add(new InsectNest(randomX(), randomY()));
				listOfEnemies.add(new InsectNest(randomX(), randomY()));
				listOfEnemies.add(new InsectNest(randomX(), randomY()));
				listOfEnemies.add(new MagicalBall(randomX(), randomY()));
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				break;
			case 8:
				listOfEnemies.add(new Insect(randomX(), randomY()));
				listOfEnemies.add(new Insect(randomX(), randomY()));
				listOfEnemies.add(new Insect(randomX(), randomY()));
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				break;
			case 9:
				listOfEnemies.add(new Insect(randomX(), randomY()));
				listOfEnemies.add(new Insect(randomX(), randomY()));
				listOfEnemies.add(new Insect(randomX(), randomY()));
				listOfEnemies.add(new Insect(randomX(), randomY()));
				listOfEnemies.add(new Insect(randomX(), randomY()));
				listOfEnemies.add(new Insect(randomX(), randomY()));	
				listOfEnemies.add(new Insect(randomX(), randomY()));
				break;
			case 10:
				listOfEnemies.add(new Turret(randomX(), randomY()));	
				listOfEnemies.add(new Turret(randomX(), randomY()));
				listOfEnemies.add(new Turret(randomX(), randomY()));	
				listOfEnemies.add(new Turret(randomX(), randomY()));
				listOfEnemies.add(new MagicalBall(randomX(), randomY()));
				listOfEnemies.add(new MagicalBall(randomX(), randomY()));
				break;
			case 11:
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));	
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				break;
			case 12:
				listOfEnemies.add(new InsectNest(randomX(), randomY()));
				listOfEnemies.add(new InsectNest(randomX(), randomY()));
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));	
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				listOfEnemies.add(new ArmoredInsect(randomX(), randomY()));
				break;
			case 13:
				listOfEnemies.add(new InsectNest(randomX(), randomY()));
				listOfEnemies.add(new InsectNest(randomX(), randomY()));
				listOfEnemies.add(new Turret(randomX(), randomY()));	
				listOfEnemies.add(new Turret(randomX(), randomY()));	
				listOfEnemies.add(new Turret(randomX(), randomY()));	
				break;
			case 14:
				listOfEnemies.add(new MagicalBall(randomX(), randomY()));
				listOfEnemies.add(new MagicalBall(randomX(), randomY()));
				listOfEnemies.add(new MagicalBall(randomX(), randomY()));
				listOfEnemies.add(new MagicalBall(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				listOfEnemies.add(new Beetle(randomX(), randomY()));
				break;
		}	
		
		
	}	
	
	
	
	public void bossOne() {
		int boss1 = rand.nextInt(1)+1;
		if (!alreadyinc) {
			bossinc = true;
			alreadyinc = true;
		}	
		
		
		if (!bossinc) {
			switch (boss1) {
			case 1:
				listOfEnemies.add(new WormBoss(randomX(), randomY()));
				listOfEnemies.add(new Turret(130,110));
				listOfEnemies.add(new Turret(130,490));
				listOfEnemies.add(new Turret(1060,110));
				listOfEnemies.add(new Turret(1060,490));

				break;
			
			}	
		}
		
		
		
	}		
	
	public void bossTwo() {
		if (!alreadyinc) {
			bossinc = true;
			alreadyinc = true;
		}	
		int boss2 = rand.nextInt(1)+1;
		if (!bossinc) {	
			switch (boss2) {
			case 1:
				listOfEnemies.add(new VampireBoss(565, 100));
				break;
		
			}
		}	
	
	}
}	