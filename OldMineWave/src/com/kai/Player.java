package com.kai;


import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class Player {
	private boolean Pattacking;
	static int damage, x, y, width, height, health, maxMines;
	int speed;
	String face;
	static String killedBy;
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		health = 20;
		face = "up";
		damage = 3;
		maxMines = 7;
		killedBy = "nothing";
		
		speed = 3;
		width = 22;
		height = 60;
	}
	
	//use a spriteSheet instead of graphics
	public void drawMe(Graphics g) {
		if (health > 0) {	
			g.setColor(Color.black);
			g.fillRect(x, y+20, 20, 40);
			g.setColor(Color.gray);
			g.fillOval(x, y, 20, 20);
			if (face.equals("up") || face.equals("down")) {	
				g.fillRect(x-6, y+28, 6, 24);
				g.fillRect(x+20, y+28, 6, 24);

					
			} else if (face.equals("right")) {	
				g.fillRect(x+13, y+28, 6, 24);

			} else if (face.equals("left")) {
				g.fillRect(x+1, y+28, 6, 24);

			}	
			
			
		} else {
			x = 600;
			y = 300;
			Screen.end = true;
		}	
	}
	

	
	//not using player as a subclass of enemy because I'm lazy and want everything in player to be static
	public void moveLeft() {
		face = "left";
		if (x > 0) {	
			x -= speed;
		}
	}	

	public void moveRight() {
		face = "right";
		if (x < 1190) {
			x += speed;
		}
	}	
	
	public void moveForward() {
		face = "up";
		if (y > 0) {	
			y -= speed;
		}
	}
	
	public void moveDown() {
		face = "down";
		if (y < 590) {	
			y += speed;
		}
	}	
	

	public static void takeDamage(int d) {
		health -= d;
	}	
		
}