package com.kai.game.items;

import com.kai.game.core.GameObject;
import com.kai.game.core.Updatable;
import com.kai.game.entities.Player;
import com.kai.game.util.MPoint;
import com.kai.game.util.MTimer;
import com.kai.game.util.Parameters;
import com.kai.game.util.ResourceManager;

import java.awt.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class LootInstance extends GameObject {
    //A multiplier of 2 means the drop chances are doubled.
    private double lootChanceMultiplier;
    //If true, no common items will be dropped.
    private boolean onlyHighTier;
    private List<Item> containedItems;
    private boolean displayContents = false;

    private Image lootInstanceContents;
    private MTimer timer;

    private int duration;

    public LootInstance(int x, int y, double lootChanceMultiplier, boolean onlyHighTier) {
        super(ResourceManager.getImage("chest.png", 48, 32), x, y, 48, 32);
        this.lootChanceMultiplier = lootChanceMultiplier;
        this.onlyHighTier = onlyHighTier;
        this.lootInstanceContents = ResourceManager.getImage("LootInstanceContents.png");
        timer = new MTimer();

        duration = 10;

        containedItems = new ArrayList<>();
        populateLoot();

    }

    public LootInstance(int x, int y) {
        this(x, y, 1, false);
    }

    public void testClicked(Player owner, int mouseX, int mouseY) {
        for (int i = 0;i < containedItems.size(); i++) {
            if (containedItems.get(i).distanceTo(mouseX, mouseY) <= 35) {
                Item newEquip = containedItems.get(i);
                int index = (int)(Math.random() * 2);
                Item oldEquip = null;
                if (owner.getRings()[index] != null) {
                    oldEquip = owner.getRings()[index];
                }
                owner.equipRing(index, newEquip);
                if (oldEquip != null) {
                    containedItems.set(i, oldEquip);
                } else {
                    containedItems.remove(i);
                }
            }
        }
    }

    private MPoint contentPoint = new MPoint(968, 518);
    private MPoint contentLength = new MPoint(50, 34);

    @Override
    public void drawMe(Graphics g) {
        g.drawImage(getSelfImage(), getX(), getY(), null);
        if (displayContents) {
            drawContents(g);
        }
    }

    private void drawContents(Graphics g) {
        g.drawImage(lootInstanceContents, contentPoint.getX(), contentPoint.getY(), null);
        for (int i = 0; i < containedItems.size(); i++) {
            Item item = containedItems.get(i);
            item.setX(contentPoint.getX() + (21 + (i* contentLength.getX())));
            item.setY(contentPoint.getY() + (contentLength.getY()));
            item.drawMe(g);
        }
    }

    public void checkIfStandingOn(Player player) {
        displayContents = checkCollision(player);
    }

    private void populateLoot() {
        if (!onlyHighTier) {
            if (randomNumber(10000) <= (Parameters.COMMON_CHANCE * 10000 * lootChanceMultiplier)) {
                addItemToLoot(getRandomItem(1));
            }
        }
        if (randomNumber(10000) <= (Parameters.UNCOMMON_CHANCE * 10000 * lootChanceMultiplier)) {
            addItemToLoot(getRandomItem(2));
        }
        if (randomNumber(10000) <= Parameters.RARE_CHANCE * 10000 * lootChanceMultiplier) {
            addItemToLoot(getRandomItem(3));
        }
        if (randomNumber(10000) <= Parameters.MYSTIC_CHANCE * 10000 * lootChanceMultiplier) {
            duration *= 2;
            addItemToLoot(getRandomItem(4));
        }
    }

    private void addItemToLoot(Item item) {
        containedItems.add(item);
    }

    private Item getRandomItem(int rarity) {
        HashMap<String, Item> allPossibleItems = ItemLoader.getAllItems();
        for (String name: allPossibleItems.keySet()) {
            if (allPossibleItems.get(name).getRarity() == rarity) {
                return ItemLoader.getItem(name);
            }
        }
        return null;
    }

    private int randomNumber(int range) {
        return (int)(Math.random() * range + 1);
    }

    public List<Item> getContainedItems() {
        return containedItems;
    }

    public MTimer getTimer() {
        return timer;
    }

    public int getDuration() {
        return duration;
    }
}
