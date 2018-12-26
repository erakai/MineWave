package com.kai;


import com.kai.Player;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class PlayerAbility {
	static String ability;
	BufferedImage startBackground, teleportImage, shieldImage, comboImage;
	static BufferedImage chosenIcon;
	static int cooldown, teleportCooldown, shieldCooldown, comboCooldown;
	boolean teleportText, shieldText, comboText;
	int cdtick;
	
	public PlayerAbility() {
		cooldown = 0;
		teleportCooldown = 13;
		shieldCooldown = 9;
		comboCooldown = 7;
		
		try {                
		teleportImage = ImageIO.read(new File("AbilitySprites/TeleportImage.png"));
		} catch (IOException ex) { System.out.println("ability exception"); }
		try {                
		shieldImage = ImageIO.read(new File("AbilitySprites/ShieldImage.png"));
		} catch (IOException ex) { System.out.println("ability exception"); }
		try {                
		comboImage = ImageIO.read(new File("AbilitySprites/ComboImage.png"));
		} catch (IOException ex) { System.out.println("ability exception"); }
		try {
		startBackground = ImageIO.read(new File("StartBackground.png"));
		} catch (IOException ex) { System.out.println("ability exception"); }
	}	
	
	public void drawMe(Graphics g) {
		if (Screen.start) {
			g.drawImage(startBackground, 0 ,0 ,null);
			
			g.drawImage(teleportImage, 20, 275, null);
			g.drawImage(shieldImage, 575, 275, null);
			g.drawImage(comboImage, 1130, 275, null);
		

			g.setColor(Color.white);
			Font newFont = new Font(g.getFont().getFontName(), Font.PLAIN, 24);
			g.setFont(newFont);	
			
			g.drawString("Click on an icon to read what it does.", 30, 65);
			g.drawString("Abilities are activated by holding space and left clicking.", 30, 95);
			g.drawString("Press enter to equip the currently selected ability.", 30, 125);
			
			g.drawString("W/A/S/D to move.", 900, 65);
			g.drawString("Left click to place a mine.", 900, 95);
			g.drawString("Maximum of 7 mines.", 900, 125);
			
			if (teleportText) {
				g.drawString("Teleport: 12 second cooldown.", 30, 445);
				g.drawString("Teleports the player to the mouse, leaving a trail of mines behind them.", 30, 475);
				g.drawString("", 30, 505);
			}	else if (shieldText) {
				g.drawString("Shield: 8 second cooldown.", 30, 445);
				g.drawString("Creates a protective barrier of mines around the player.", 30, 475);
				g.drawString("Not effective against enemies ontop of the player.", 30, 505);
			} else if (comboText) {
				g.drawString("Combo: 6 second cooldown.", 30, 445);
				g.drawString("Places all 7 mines at the mouse.", 30, 475);
				g.drawString("", 30, 505);
			}	

					
		} else {
			if (cooldown > 0) {
				g.setColor(Color.black);
				g.fillRect(585, 522, 50, 50);
				g.setColor(Color.white);
				Font newFont = new Font(g.getFont().getFontName(), Font.PLAIN, 24); 
				g.setFont(newFont);
				g.drawString("" + cooldown, 603, 555);
			}	else {
				if (ability.equals("teleport")) {
					g.drawImage(teleportImage, 585, 522, null);	
				}	else if (ability.equals("shield")) {
					g.drawImage(shieldImage, 585, 522, null);
				}	else if (ability.equals("combo")) {
					g.drawImage(comboImage, 585, 522, null);
				}
			}	
				
		}	
	}	
		
	public void infoText(int mx, int my) {
		if (mx > 20 && mx < 70 && my < 325 && my > 275) {
			teleportText = true;			
			shieldText = false;
			comboText = false;
		}	else if (mx > 575 && mx < 625 && my < 325 && my > 275) {
			shieldText = true;
			teleportText = false;
			comboText = false;
		} 	else if (mx > 1130 && mx < 1180 && my < 325 && my > 275) {
			comboText = true;
			teleportText = false;
			shieldText = false;
		}	
	}
	public boolean setAbility() {
		if (teleportText) {
			ability = "teleport";
			chosenIcon = teleportImage;
			return false;
		}	else if (shieldText) {
			ability = "shield";
			chosenIcon = shieldImage;
			return false;
		} 	else if (comboText) {
			chosenIcon = comboImage;
			ability = "combo";
			return false;
		} else {
			return true;
		}
	}	
	
	public void useAbility(int mx, int my) {
		PlayerWand.listOfMines.clear();
		
		if (ability.equals("teleport")) {
			double m = 0;
			m = (double)(my - Player.y) / (double)(mx - Player.x);
			double b = Player.y - (m * Player.x);
			
			double a = (mx - Player.x)/7;

			for (int i = 0; i < (Player.maxMines); i++) {
					double minex = 0;
					double miney = 0;
					minex = Player.x + (i * a);
					miney = (m * minex) + b;
					PlayerWand.newMine((int)minex, (int)miney);
					
			}
			Player.x = mx-10;
			Player.y = my-30;
			

			cooldown = teleportCooldown;
		}	else if (ability.equals("shield")) {
			PlayerWand.newMine(Player.x+30, Player.y-10);
			PlayerWand.newMine(Player.x-10, Player.y-10);
			PlayerWand.newMine(Player.x+30, Player.y+30);
			PlayerWand.newMine(Player.x-10, Player.y+30);
			PlayerWand.newMine(Player.x-10, Player.y+70);
			PlayerWand.newMine(Player.x+30, Player.y+70);
			cooldown = shieldCooldown; 
		}	else if (ability.equals("combo")) {
			for (int i = 0; i < (Player.maxMines * 2); i+=2) {	
				PlayerWand.newMine(mx + i, my + i);
			}
			cooldown = comboCooldown;
		}
}
	

	
	public void updateCooldown() {
		if (cdtick > 99 && cooldown > 0) {
			cdtick = 0;
			cooldown -= 1;
		} else {
			cdtick++;
		}	
	}	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
}	