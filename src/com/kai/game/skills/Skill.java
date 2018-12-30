package com.kai.game.skills;

import com.kai.game.entities.Entity;
import com.kai.game.master.Screen;

import java.awt.*;
import java.util.Date;

public abstract class Skill {
    private String name;
    private Entity owner;
    private Image selfImage;

    public String[] description;

    private int cooldown;

    private long lastUsed;

    public static final int SKILL_WIDTH = (int)((50/1200.0) * Screen.WINDOW_WIDTH);
    public static final int SKILL_HEIGHT = (int)((50/600.0) * Screen.WINDOW_HEIGHT);

    public Skill(String name, Entity owner, Image img, int cooldown, String[] description) {
        this.name = name;
        this.owner = owner;
        this.selfImage = img;
        this.description = description;
        this.cooldown = cooldown;

        lastUsed = -1 * (cooldown * 1000);
    }

    public void drawMe(Graphics g, int x, int y) {
        g.drawImage(selfImage, x, y, null);
    }

    public void use(int targetedX, int targetedY) {
        if (checkCooldown()) {
            lastUsed = System.currentTimeMillis();
            _use(targetedX, targetedY);
        }
    }

    public abstract void _use(int tX, int tY);

    public boolean checkCooldown() {
        return ( (System.currentTimeMillis() - lastUsed) > (cooldown*1000) );
    }

    public int secondsUntilReady() {
        return (cooldown - ((int)((System.currentTimeMillis() - lastUsed)/1000)));
    }

    public String getName() {
        return name;
    }

    public Entity getOwner() {
        return owner;
    }

    public Image getSelfImage() {
        return selfImage;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
}
