package com.kai.game.hud;

import com.kai.game.GameObject;
import com.kai.game.Updatable;
import com.kai.game.entities.Player;
import com.kai.game.master.ResourceManager;
import com.kai.game.master.Screen;
import com.kai.game.skills.Skill;

import java.awt.*;

public class InGameDisplay extends GameObject implements Updatable {

    private static final int HUD_WIDTH = (int)(600.0/1200.0 * Screen.WINDOW_WIDTH);
    private static final int HUD_HEIGHT = (int)(100.0/600.0 * Screen.WINDOW_HEIGHT);

    private double healthToDraw;
    private int maxHealthToDraw;
    private int minesToDraw;
    private int maxMinesToDraw;
    private int currentLevelToDraw;
    private Skill skillToDraw;

    public InGameDisplay(int x, int y) {
        super(ResourceManager.getImage("IngameHUD.png", HUD_WIDTH, HUD_HEIGHT), x, y, HUD_WIDTH, HUD_HEIGHT);
        healthToDraw = 1;
        maxHealthToDraw = 1;
        minesToDraw = 0;
        maxMinesToDraw = 1;
    }

    @Override
    public void drawMe(Graphics g) {
        g.drawImage(getSelfImage(), getX(), getY(), null);

        //Drawing current health:
        g.setColor(Color.RED);
        g.fillRect(getScaledX(311), getScaledY(535), (int)((double)healthToDraw/maxHealthToDraw * getScaledX(264)) , getScaledY(29));

        //Drawing current mine count:
        g.setColor(Color.YELLOW);
        g.fillRect(getScaledX(648), getScaledY(535), (int)((((double)minesToDraw / maxMinesToDraw)) * getScaledX(264)), getScaledY(29));

        //Drawing current level:
        g.setFont(MainMenu.ogfont);
        g.setColor(Color.RED);
        g.setFont( new Font(g.getFont().getFontName(), Font.PLAIN, (int)(g.getFont().getSize()*(Screen.WINDOW_WIDTH/(1200.0/1.5)))));
        g.drawString("Level: " + currentLevelToDraw, getScaledX(570), getScaledY(595));

        //Drawing the first playerSkill:
        g.setColor(Color.WHITE);
        g.setFont(MainMenu.ogfont);
        g.setFont( new Font(g.getFont().getFontName(), Font.PLAIN, (int)(g.getFont().getSize()*(Screen.WINDOW_WIDTH/(1200.0/0.6)))));
        g.drawString("E", getScaledX(606), getScaledY(518));
        if (skillToDraw.checkCooldown()) {
            g.drawImage(skillToDraw.getSelfImage(), getScaledX(585), getScaledY(522), null);
        } else {
            drawCooldownBox(g, getScaledX(585), getScaledY(522), skillToDraw);
        }
    }

    private void drawCooldownBox(Graphics g, int bX, int bY, Skill onCooldown) {
        g.setColor(Color.BLACK);
        g.fillRect(bX, bY, getScaledX(50), getScaledY(50));
        g.setColor(Color.WHITE);
        g.setFont(MainMenu.ogfont);
        g.setFont( new Font(g.getFont().getFontName(), Font.PLAIN, (int)(g.getFont().getSize()*(Screen.WINDOW_WIDTH/(1200.0/1.5)))));
        if (onCooldown.secondsUntilReady() < 10) {
            g.drawString(String.valueOf(onCooldown.secondsUntilReady()), getScaledX(603), getScaledY(550));
        } else {
            g.drawString(String.valueOf(onCooldown.secondsUntilReady()), getScaledX(598), getScaledY(550));
        }
    }

    @Override
    public void update() {
        healthToDraw = getPlayer().getHealth();
        maxHealthToDraw = getPlayer().getMaxHealth();
        minesToDraw = getPlayer().getCurrentMines();
        maxMinesToDraw = getPlayer().getMaxMines();
        currentLevelToDraw = Screen.getLevelHandler().getDisplayedLevel();
        skillToDraw = getPlayer().getSkills().get(0);
    }

    public void setHealthToDraw(int healthToDraw) {
        this.healthToDraw = healthToDraw;
    }

    public void setMaxHealthToDraw(int maxHealthToDraw) {
        this.maxHealthToDraw = maxHealthToDraw;
    }

    public void setMinesToDraw(int minesToDraw) {
        this.minesToDraw = minesToDraw;
    }

    public void setMaxMinesToDraw(int maxMinesToDraw) {
        this.maxMinesToDraw = maxMinesToDraw;
    }

    private Player getPlayer() {
        return Screen.getPlayer();
    }

    private int getScaledX(int oldWidth) {
        return (int)(oldWidth/600.0 * this.width);
    }

    private int getScaledY(int oldHeight) {
        return (int)(oldHeight/100.0 * this.height);
    }
}
