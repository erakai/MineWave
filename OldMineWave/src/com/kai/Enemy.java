package com.kai;


import java.awt.Graphics;

public abstract class Enemy {
	int health, x, y, damage, speed, width, height;
	String face;

	
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
		if (y < 580) {	
			y += speed;
		}
	}	
	

	public void takeDamage(int d) {
		health -= d;
	}	
		
	public abstract void chase(int x, int y);
	public abstract void checkCollision();
	public abstract void drawMe(Graphics g);

}