package com.kai.game.skills;

import com.kai.game.entities.Entity;
import com.kai.game.core.Screen;
import com.kai.game.entities.Player;
import com.kai.game.util.MRectangle;
import com.kai.game.util.ResourceManager;

import java.awt.*;
import java.util.Arrays;

public abstract class Skill {
    private String name;
    private Entity owner;
    private Image selfImage;

    //TODO: Add a HUD that shows the description of the skill when the skill is hovered over in the hot bar.

    public String[] description;

    //TODO: Instead of a cooldown, make it so you have to kill x enemies to recharge a skill (to balance out heal).
    private int cooldown;

    private long lastUsed;

    public static final MRectangle SKILL_SIZE = new MRectangle(50, 50);

    private boolean passive;

    public Skill(String name, Entity owner, Image img, int cooldown, String[] description) {
        this.name = name;
        this.owner = owner;
        this.selfImage = img;
        this.description = description;
        this.cooldown = cooldown;
        this.passive = false;
        lastUsed = -1 * (cooldown * 1000);
    }

    public Skill(String name, Entity owner, Image img, boolean passive, String[] description) {
        this(name, owner, img, 0, description);
        this.passive = passive;

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

    public static Skill getFreshSkill(String skillName, Player p) {
        switch (skillName) {
            case "TeleportSkill":
                return new TeleportSkill(p);
            case "ShieldSkill":
                return new ShieldSkill(p);
            case "ComboSkill":
                return new ComboSkill(p);
            case "GreatMineSkill":
                return new GreatMineSkill(p);
            case "HealSkill":
                return new HealSkill(p);
            default:
                return null;
        }
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

    public boolean isPassive() {
        return passive;
    }

    public void setPassive(boolean passive) {
        this.passive = passive;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "name='" + name + '\'' +
                ", owner=" + owner +
                ", selfImage=" + selfImage +
                ", description=" + Arrays.toString(description) +
                ", cooldown=" + cooldown +
                ", lastUsed=" + lastUsed +
                ", passive=" + passive +
                '}';
    }
}
