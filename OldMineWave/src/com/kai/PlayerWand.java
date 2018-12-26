package com.kai;


import com.kai.Mine;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class PlayerWand {
	static ArrayList<Mine> listOfMines = new ArrayList<Mine>();
	
	public void drawMines(Graphics g) {
		for(int i = 0; i < listOfMines.size(); i++) {
			listOfMines.get(i).drawMe(g);		
		}	
	}	
		
	public static void newMine(int x, int y) {
		listOfMines.add(new Mine(x, y));
	}	
	
	public static int checkCollision(int iX, int iY, int iW, int iH) {
		int collision = 0;
		for(int i = 0; i < listOfMines.size(); i++) {
			if(listOfMines.get(i).checkCollision(iX,iY,iW,iH)) {
				collision +=1;
				listOfMines.remove(i);
				i--;
			}
		}		
		return collision;
	}	
	
	
}	