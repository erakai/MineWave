package com.kai;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.Arrays;

public class DeathScreen {
	BufferedImage deathsc;
	boolean putinto;
	static boolean confirmed;
	String fileName = "leaderboards/leaderboard.txt";
	String fileName2 = "leaderboards/leaderboard2.txt";
	String fileName3 = "leaderboards/leaderboard3.txt";
	int scores = 0;
	String chars, ab, name;
	int[] ldbScores = new int[10];
	String[] ldbInfo = new String[10];
	String[] ldbAbilities = new String[10];
	
	
	public DeathScreen() {
		putinto = false;
		confirmed = false;
		name = "";
		for (int i = 0; i<10; i++) {
			ldbScores[i] = -1;
		}	
		for (int i = 0; i<10; i++) {
			ldbInfo[i] = "";
		}			
		
		
		try {
		deathsc = ImageIO.read(new File("DeathScreen.png"));
		} catch (IOException ex) { System.out.println("deathscreen exception"); }
	}	
	
	public void drawMe(Graphics g) {
		getLeaderboard();
	
		
		g.setColor(Color.white);
		Font medf = new Font(g.getFont().getFontName(), Font.PLAIN, 24);
		Font lgf = new Font(g.getFont().getFontName(), Font.PLAIN, 48);
		Font smf = new Font(g.getFont().getFontName(), Font.PLAIN, 18);
		Color ldbc = new Color(42, 112, 224);
		g.setFont(medf);		
		g.drawImage(deathsc, 0, 0, null);
		g.drawString("You died!", 35, 52);
		g.drawString("Score: " + LevelHandler.level, 35, 82);
		g.drawString("Killed by: " + Player.killedBy, 35, 112);
		g.drawImage(PlayerAbility.chosenIcon, 250, 40, null);
		g.setFont(lgf);
		g.setColor(Color.orange);
		g.drawString("Leaderboard", 790, 60);
		g.setFont(medf);
		g.drawString("Name", 696, 90); 
		g.drawString("Ability", 900, 90);
		g.drawString("Score", 1100, 90);
		g.setFont(smf);
		g.setColor(Color.white);
		g.setFont(medf);

		
		if (LevelHandler.level > ldbScores[0] && putinto == false) {
				
			g.drawString("Type name: ", 40, 330);
			g.drawString(name, 150, 390);
			g.drawString("confirm", 140, 443);	
				
				
			if (confirmed) {	
				intoLeaderboard();
				putinto = true;
			}
		}	
		
		g.setColor(ldbc);
		for (int l = 9; l > -1; l--) {
			g.drawString(ldbInfo[l], 700, 575-(l*50));
			g.drawString(ldbAbilities[l], 900, 575-(l*50));
			g.drawString("" + ldbScores[l], 1100, 575-(l*50));
		}	
	}		
	
	public void getLeaderboard() {
	   try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

			FileReader fileReader2 = new FileReader(fileName2);
            BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
			
			FileReader fileReader3 = new FileReader(fileName3);
            BufferedReader bufferedReader3 = new BufferedReader(fileReader3);			
			
            for (int ii = 0; ii < 10; ii++) {
					scores = Integer.parseInt(bufferedReader.readLine());
					ldbScores[ii] = scores;	
			}	
			
			for (int j = 0; j < 10; j++) {
					chars = bufferedReader2.readLine();
					ldbInfo[j] = chars;
			}		
		
			for (int m = 0; m < 10; m++) {
					ab = bufferedReader3.readLine();
					ldbAbilities[m] = ab;
			}				
			
            bufferedReader.close();   
			bufferedReader2.close();
			bufferedReader3.close();
        }
        catch(FileNotFoundException ex) {
             ex.printStackTrace();               
        }
        catch(IOException ex) {               
             ex.printStackTrace();
        }

		
		for (int y = 0; y < 10; y++) {
			for (int n = 0; n < 10; n++) {
				if (ldbScores[y] < ldbScores[n] && y > n) {
					int temp = ldbScores[y];
					ldbScores[y] = ldbScores[n];
					ldbScores[n] = temp;
					
					String temp2 = ldbInfo[y];
					ldbInfo[y] = ldbInfo[n];
					ldbInfo[n] = temp2;
					
					String temp3 = ldbAbilities[y];
					ldbAbilities[y] = ldbAbilities[n];
					ldbAbilities[n] = temp3;
				}	
			}	
		}	
		
	}	
	
	public void intoLeaderboard() {
		for (int i = 9; i > -1; i--) {
			if (LevelHandler.level > ldbScores[i]) {
				for (int h = 0; h < i; h++) {
					ldbScores[h] = ldbScores[h+1];	
					ldbInfo[h] = ldbInfo[h+1];
					ldbAbilities[h] = ldbAbilities[h+1];
				}				
				ldbScores[i] = LevelHandler.level;
				ldbAbilities[i] = PlayerAbility.ability;
				ldbInfo[i] = name;
				break;
				
			}	
		}

		try {
			PrintWriter writer = new PrintWriter("leaderboards\\leaderboard.txt");
			PrintWriter writer2 = new PrintWriter("leaderboards\\leaderboard2.txt");
			PrintWriter writer3 = new PrintWriter("leaderboards\\leaderboard3.txt");
		
			for (int q = 0; q < 10; q++) {
				writer.println(ldbScores[q]);
				writer2.println(ldbInfo[q]);
				writer3.println(ldbAbilities[q]);
			}	
			
			writer.close();
			writer2.close();
			writer3.close();
			
		} catch(IOException ex) { 
			ex.printStackTrace();
		}
	}	
	
	
	
	public void makeName(String l) {
		if (!confirmed) {
			if(!l.equals("dlt") && name.length() < 3) {
				name = name + l;
			} else if (l.equals("dlt") && name.length() > 0) {
				String temp = name.substring(0, name.length()-1);
				name = temp;
			}		
			
			
		}
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}	