package com.kai.game.entities.enemies;

import com.kai.game.entities.Entity;
import com.kai.game.master.Screen;

import java.awt.*;

public abstract class Enemy extends Entity {
    private String name;
    private int damage;
    private double attacksPerSecond;

    private int damageTick, maxDamageTick;

    public Enemy(Image self, int x, int y, int width, int height, int speed, int maxHealth, String name, int damage, double attacksPerSecond) {
        super(self, x, y, width, height, speed, maxHealth);
        this.name = name;
        this.damage = damage;
        this.attacksPerSecond = attacksPerSecond;

        this.maxDamageTick = (int)(Screen.FRAMES_PER_SECOND/attacksPerSecond);
        damageTick = maxDamageTick;

    }

    public abstract void chase(int targetX, int targetY);


    public void defaultMoveTowards(int targetX, int targetY) {
        double deltaX = targetX - getX();
        double deltaY = targetY - getY();
        double direction = Math.atan2(deltaY, deltaX);

        setX((int)(getX() + (getSpeed() * Math.cos(direction))));
        setY((int)(getY() + (getSpeed() * Math.sin(direction))));
    }

    @Override
    public void attack(Entity target) {
        if (damageTick >= maxDamageTick) {
            target.takeDamage(getDamage());
            damageTick = 0;
        }
    }

    @Override
    public void update() {
        if (damageTick < maxDamageTick) {
            damageTick++;
        }
    }

    /*This move method means enemies walk diagonally until
            they are on the same horizontal axis as the player, and then move in a straight line.*/
    public void altMoveTowards(int targetX, int targetY) {
        if (getX() > targetX) {
            moveLeft();
        }
        if (getX() < targetX) {
            moveRight();
        }
        if (getY() > targetY) {
            moveUp();
        }
        if (getY() < targetY) {
            moveDown();
        }
    }

    public void drawMe(Graphics g) {
        g.drawImage(getSelfImage(), getX(), getY(), null);
        g.setColor(Color.red);
        g.fillRect(getX(), getY()-(int)(10.0/600.0 * Screen.WINDOW_HEIGHT), (int)(getHealth()/getMaxHealth() * (width)) , (int)(5.0/600.0 * Screen.WINDOW_HEIGHT));
    }

    public String getName() {
        return name;
    }

    public int getDamage() { return damage; }

    public int getDamageTick() {
        return damageTick;
    }

    public int getMaxDamageTick() {
        return maxDamageTick;
    }

    public void setDamageTick(int damageTick) {
        this.damageTick = damageTick;
    }

    public double getAttacksPerSecond() {
        return attacksPerSecond;
    }
}
