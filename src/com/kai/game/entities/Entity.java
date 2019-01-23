package com.kai.game.entities;

import com.kai.game.core.GameObject;
import com.kai.game.core.RoomHandler;
import com.kai.game.core.Updatable;
import com.kai.game.core.Screen;
import com.kai.game.util.Parameters;

import java.awt.*;

public abstract class Entity extends GameObject implements Updatable, DoesCombat {
    private int speed;
    private final int maxHealth;
    private double health;

    //TODO: Set up a system where behaviors and stats for entities are not client sided.

    public Entity(Image self, int x, int y, int width, int height, int speed, int maxHealth) {
        super(self, x, y, width, height);
        this.speed = speed;
        this.maxHealth = maxHealth;
        health = maxHealth;
    }

    public double distanceTo (Entity otherEntity) {
        return distanceTo(otherEntity.getX(), otherEntity.getY());
    }

    public void moveUp() {
        if (Screen.getRoomHandler().isEnemiesEmpty()) {
            if (getY() > 0 || (RoomHandler.getCurrentRoomY() != 0)) {
                setY(getHardY()-getSpeed());
            }
        } else if (getY() > 0) {
            setY(getHardY()-getSpeed());
        }
    }

    public void moveDown() {
        if (Screen.getRoomHandler().isEnemiesEmpty()) {
            if ((getY() < Screen.WINDOW_HEIGHT-getHeight() || (RoomHandler.getCurrentRoomY() != RoomHandler.getRoomArray().length-1))) {
                setY(getHardY()+getSpeed());
            }
        } else if ((getY() < Screen.WINDOW_HEIGHT-getHeight())) {
            setY(getHardY()+getSpeed());
        }
    }

    public void moveRight() {
        if (Screen.getRoomHandler().isEnemiesEmpty()) {
            if ((getX() < Screen.WINDOW_WIDTH-getWidth() || (RoomHandler.getCurrentRoomX() != RoomHandler.getRoomArray().length-1))) {
                setX(getHardX()+getSpeed());
            }
        } else if ((getX() < Screen.WINDOW_WIDTH-getWidth())) {
            setX(getHardX()+getSpeed());
        }
    }

    public void moveLeft() {
        if (Screen.getRoomHandler().isEnemiesEmpty()) {
            if (getX() > 0 || (RoomHandler.getCurrentRoomX() != 0)) {
                setX(getHardX()-getSpeed());
            }
        } else if (getX() > 0) {
            setX(getHardX()-getSpeed());
        }
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void takeDamage(double amount) {
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
        heal((((double)(lifePerSecond))/ Parameters.FRAMES_PER_SECOND));
    }



}
