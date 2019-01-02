package com.kai.game.entities;

import com.kai.game.GameObject;
import com.kai.game.Updatable;
import com.kai.game.master.Screen;

import java.awt.*;

public abstract class Entity extends GameObject implements Updatable, DoesCombat {
    private int speed;
    private final int maxHealth;
    private double health;

    //TODO: Remove instance width/height values and use constants for each class.

    //TODO: Replace the constants for width/height for each class and use getters.

    public Entity(Image self, int x, int y, int width, int height, int speed, int maxHealth) {
        super(self, x, y, width, height);
        this.speed = speed;
        this.maxHealth = maxHealth;
        health = maxHealth;
    }

    public double distanceTo (Entity otherEntity) {
        return distanceTo(otherEntity.getX(), otherEntity.getY());
    }

    public double distanceTo (int tX, int tY) {
        double a = Math.abs(getX() - tX);
        double b = Math.abs(getY() - tY);
        return(Math.sqrt((a*a) + (b*b)));
    }

    public void moveUp() {
        if (getY() > 0) {
            setY(getY()-speed);
        }
    }

    public void moveDown() {
        if (getY() < Screen.WINDOW_HEIGHT-height) {
            setY(getY()+speed);
        }
    }

    public void moveRight() {
        if (getX() < Screen.WINDOW_WIDTH-width) {
            setX(getX()+speed);
        }
    }

    public void moveLeft() {
         if (getX() > 0) {
             setX(getX() - speed);
         }
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public double getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void takeDamage(int amount) {
        this.health -= amount;
    }

    public void heal(double amount) {
        this.health += amount;
        if (getHealth() > getMaxHealth()) {
            this.health = this.maxHealth;
        }
    }

    public void regenerate(int lifePerSecond) {
        //Should be called in an update method.
        heal((((double)(lifePerSecond))/Screen.FRAMES_PER_SECOND));
    }

}
