package com.kai.game.skills;

import com.kai.game.entities.Entity;
import com.kai.game.core.Screen;
import com.kai.game.util.MRectangle;
import com.kai.game.util.ResourceManager;

import java.awt.*;

public abstract class Skill {
    private String name;
    private Entity owner;
    private Image selfImage;

    public String[] description;

    private int cooldown;

    private long lastUsed;

    public static final MRectangle SKILL_SIZE = new MRectangle(50, 50);


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

    public void updateSelfImage() {
        setSelfImage(ResourceManager.resizeImage(getSelfImage(), SKILL_SIZE.getWidth(), SKILL_SIZE.getHeight()));
    }

    public boolean use(int targetedX, int targetedY) {
        if (checkCooldown()) {
            lastUsed = System.currentTimeMillis();
            _use(targetedX, targetedY);
            return true;
        }
        return false;
    }

    public abstract void _use(int tX, int tY);

    public void setLastUsed(long lastUsed) {
        this.lastUsed = lastUsed;
    }

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

    public void setSelfImage(Image selfImage) {
        this.selfImage = selfImage;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
}
