package com.kai.game.hud;

import com.kai.game.core.GameObject;
import com.kai.game.core.Updatable;
import com.kai.game.entities.Player;
import com.kai.game.items.Item;
import com.kai.game.util.*;
import com.kai.game.core.Screen;
import com.kai.game.skills.Skill;

import java.awt.*;

public class InGameDisplay extends GameObject implements Updatable {

    public static final int ALLOCATED_HEIGHT = 145;

    private double healthToDraw;
    private int maxHealthToDraw;
    private int minesToDraw;
    private int maxMinesToDraw;
    private int currentLevelToDraw;
    private int damageToDraw, defenseToDraw, speedToDraw, rangeToDraw;
    private Skill[] skillsToDraw;
    private Item[] playerRings;

    //TODO: Make InGameDisplay compatible with resizing the screen.

    public InGameDisplay(int x, int y) {
        super(ResourceManager.getImage("IngameHUD.png"), 0, 600, 1200, ALLOCATED_HEIGHT);
        healthToDraw = 1;
        maxHealthToDraw = 1;
        minesToDraw = 0;
        maxMinesToDraw = 1;
        skillsToDraw = new Skill[4];
    }


    private MPoint healthText = new MPoint(getScaledX(280), getScaledY(48));
    private MPoint mineText = new MPoint(healthText.getHardX(), getScaledY(113));

    private MPoint damageText = new MPoint(getScaledX(588), getScaledY(52));
    private MPoint rangeText = new MPoint(damageText.getHardX(), getScaledY(104));
    private MPoint defenseText = new MPoint(getScaledX(852), damageText.getHardY());
    private MPoint speedText = new MPoint(defenseText.getHardX(), rangeText.getHardY());

    private MPoint firstRingSlot = new MPoint(getScaledX(1123), getScaledY(24));
    private MPoint secondRingSlot = new MPoint(getScaledX(1123), getScaledY(82));

    private MRectangle ringSlotOne = new MRectangle(new MPoint(1120, getScaledY(21)), new MPoint(1166, getScaledY(67)));
    private MRectangle ringSlotTwo = new MRectangle(new MPoint(1120, getScaledY(79)), new MPoint(1166, getScaledY(125)));

    public void drawMe(Graphics g) {
        g.drawImage(getSelfImage(), getX(), getY(), null);

        if (Screen.checkState(GameState.RUNNING)) {
            //Drawing current health:
            g.setColor(Color.RED);
            g.fillRect(getScaledX(16), getScaledY(19), (int) ( healthToDraw / maxHealthToDraw * 350), 42);

            //Drawing current mine count:
            g.setColor(Color.YELLOW);
            g.fillRect(getScaledX(16), getScaledY(84), (int) ((((double) minesToDraw / maxMinesToDraw)) * 350), 42);

            //Drawing current level:
            g.setFont(Parameters.ORIGINAL_FONT);
            g.setColor(Color.CYAN);
            g.setFont(new MFont(1.4));
            g.drawString("Level: " + currentLevelToDraw, getScaledX(27), getScaledY(78));

            //Drawing the player skills:
            for (int i = 0; i < skillsToDraw.length; i++) {
                if (skillsToDraw[i] != null) {
                    if (i < 2) {
                        drawSkill(g, skillsToDraw[i], getScaledX(432), getScaledY(19 + ((77 - 19) * (i))));
                    } else {
                        drawSkill(g, skillsToDraw[i], getScaledX(512), getScaledY(19 + ((77 - 19) * (i-2))));
                    }
                }
            }

            //Draw skill key binds:
            g.setColor(Color.WHITE);
            g.setFont(Parameters.ORIGINAL_FONT);
            g.setFont(new MFont(0.6));
            g.drawString("E", getScaledX(422), getScaledY(44));
            g.drawString("T", getScaledX(502), getScaledY(44));
            g.drawString("R", getScaledX(422), getScaledY(102));
            g.drawString("F", getScaledX(502), getScaledY(102));

            //Draw player stats:
            g.setColor(Color.CYAN);
            g.setFont(new MFont(2));
            g.drawString(((int)healthToDraw) + "/" + maxHealthToDraw, healthText.getX(), healthText.getY());
            g.drawString(minesToDraw + "/" + maxMinesToDraw, mineText.getX(), mineText.getY());

            g.setColor(new Color(50, 153, 200));
            g.setFont(new MFont(1.8));
            g.drawString("Damage   " + damageToDraw, damageText.getX(), damageText.getY());
            g.drawString("Range     " + rangeToDraw, rangeText.getX(), rangeText.getY());
            g.drawString("Defense  " + defenseToDraw, defenseText.getX(), defenseText.getY());
            g.drawString("Speed     " + speedToDraw, speedText.getX(), speedText.getY());

            //Drawing player rings:

            //holy shit why am i setting their location 60 times a second what the fuck
            //TODO: CHANGE THIS FUCKING ABOMINATION MAKE IT STOP
            if (playerRings[0] != null) {
                playerRings[0].setX(firstRingSlot.getHardX());
                playerRings[0].setY(firstRingSlot.getHardY());
                Item.setRarityColor(playerRings[0].getRarity(), g);
                g.fillRect(ringSlotOne.getTopLeft().getX(), ringSlotOne.getTopLeft().getY(), ringSlotOne.getWidth(), ringSlotOne.getHeight());
                playerRings[0].drawMe(g);
            }
            if (playerRings[1] != null) {
                playerRings[1].setX(secondRingSlot.getHardX());
                playerRings[1].setY(secondRingSlot.getHardY());
                Item.setRarityColor(playerRings[1].getRarity(), g);
                g.fillRect(ringSlotTwo.getTopLeft().getX(), ringSlotTwo.getTopLeft().getY(), ringSlotTwo.getWidth(), ringSlotTwo.getHeight());
                playerRings[1].drawMe(g);
            }
        }
    }

    private void drawSkill(Graphics g, Skill toDraw, int x, int y) {
        if (toDraw.checkCooldown()) {
            g.drawImage(toDraw.getSelfImage(), x, y, null);
        } else {
            drawCooldownBox(g, x, y, toDraw);
        }
    }

    private void drawCooldownBox(Graphics g, int bX, int bY, Skill onCooldown) {
        g.setColor(Color.BLACK);
        g.fillRect(bX, bY,50, 50);
        g.setColor(Color.WHITE);
        g.setFont(Parameters.ORIGINAL_FONT);
        g.setFont(new MFont(1.5));
        if (onCooldown.secondsUntilReady() < 10) {
            g.drawString(String.valueOf(onCooldown.secondsUntilReady()), bX + 20, bY + 30);
        } else {
            g.drawString(String.valueOf(onCooldown.secondsUntilReady()), bX + 15, bY + 30);
        }
    }


    // i dont even want to think about how wasteful this is
    //TODO: fix this horrible  update method
    @Override
    public void update() {
        if (Screen.checkState(GameState.RUNNING)) {
            healthToDraw = getPlayer().getHealth();
            maxHealthToDraw = getPlayer().getMaxHealth();
            minesToDraw = getPlayer().getCurrentMines();
            maxMinesToDraw = getPlayer().getMaxMines();
            currentLevelToDraw = Screen.getLevelHandler().getCurrentLevel();
            defenseToDraw = getPlayer().getDefense();
            speedToDraw = getPlayer().getSpeed();
            rangeToDraw = getPlayer().getSmallPlayerRange();
            damageToDraw = getPlayer().getPlayerDamage();
            playerRings = getPlayer().getRings();
            skillsToDraw = new Skill[4];
            for (int i = 0; i < getPlayer().getSkills().size(); i++) {
                if ( i < 4) {
                    skillsToDraw[i] = getPlayer().getSkills().get(i);
                }
            }
        }
    }

    private Player getPlayer() {
        return Screen.getPlayer();
    }

    private int getScaledX(int oldWidth) {
        return (oldWidth);
    }

    private int getScaledY(int oldHeight) {
        return (Screen.WINDOW_HEIGHT + oldHeight);
    }
}
