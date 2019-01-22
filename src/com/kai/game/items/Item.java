package com.kai.game.items;

import com.kai.game.core.GameObject;
import com.kai.game.entities.Player;
import com.kai.game.util.MFont;
import com.kai.game.util.MPoint;
import com.kai.game.util.ResourceManager;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Item extends GameObject implements ItemBehavior {
    private String name;
    private HashMap<String, Integer> stats;
    private int rarity;
    private String description;
    private List<ItemBehavior> behaviors;
    private int[] imageCoordinates;

    private boolean hoveredOver = false;

    public Item(String name, int rarity, String description, HashMap<String, Integer> stats, List<ItemBehavior> behaviors, int[] imageCoordinates) {
        super(ResourceManager.getItemImage(imageCoordinates[0], imageCoordinates[1], 40, 40), -50, -50, 40, 40);
        this.name = name;
        this.imageCoordinates = imageCoordinates;
        this.stats = stats;
        this.rarity = rarity;
        this.description = description;
        this.behaviors = behaviors;
    }

    public Item (Item item) {
        this(item.getName(), item.getRarity(), item.getDescription(), item.getStats(), item.getBehaviors(), item.getImageCoordinates());
        //if you are here from a null pointer exception, you have the wrong item name somewhere. it's case sensitive
    }

    @Override
    public void drawMe(Graphics g) {
        g.drawImage(getSelfImage(), getX(), getY(), null);
        if (hoveredOver) {
            drawHUD(g);
        }
    }

    private MPoint nameText = new MPoint(15, 30);
    private void drawHUD(Graphics g) {
        /*
        NAME
        Rarity: RARITY
        DESCRIPTION
        STATS
        BEHAVIOR DESCRIPTIONS
         */

        g.setFont(new MFont(1.2));

        int stringHeight = g.getFontMetrics().getAscent() + 5;
        int stringWidth = (int)(g.getFontMetrics().stringWidth(description) * 0.90);
        int totalStringHeight = stringHeight * (5 + stats.size() + (behaviors.size()-1));

        g.setColor(new Color(50, 78, 105));
        g.fillRect(10, 10, stringWidth, totalStringHeight+10);

        setRarityColor(rarity, g);
        g.drawString(name, nameText.getX(), nameText.getY());

        g.drawString("Rarity: " + convertRarityToString(rarity), nameText.getX(), nameText.getY() + stringHeight);

        g.setFont(new MFont(1));
        g.drawString(description, nameText.getX(), nameText.getY() + (stringHeight*2));
        int i = 1;

        g.setColor(Color.WHITE);
        g.drawLine(nameText.getX(),nameText.getY() + (stringHeight * (2 + i)) , stringWidth-5, nameText.getY() + (stringHeight * (2 + i) - 5));
        i++;

        g.setFont(new MFont(1.2));
        g.setColor(new Color(187, 187, 187));

        for (String stat: stats.keySet()) {
            String properStat = stat.substring(0, 1).toUpperCase() + stat.substring(1);
            g.drawString(properStat + ": " + stats.get(stat), nameText.getX(), nameText.getY() + (stringHeight * (2+i)));
            i++;
        }

        for (ItemBehavior ib: behaviors) {
            if (ib != null) {
                g.drawString(ib.getDescription(), nameText.getX(), nameText.getY() + (stringHeight * (2 + i)));
                i++;
            }
        }
    }

    private String convertRarityToString(int rarity) {
        switch(rarity) {
            case 1:
                return "Common";
            case 2:
                return "Uncommon";
            case 3:
                return "Rare";
            case 4:
                return "Mystic";
            default:
                return null;
        }
    }

    public static void setRarityColor(int rarity, Graphics g) {
        switch(rarity) {
            case 1:
                g.setColor(Color.white);
                break;
            case 2:
                g.setColor(Color.GREEN);
                break;
            case 3:
                g.setColor(Color.CYAN);
                break;
            case 4:
                g.setColor(Color.RED);
                break;
        }
    }

    public void checkHover(int mX, int mY) {
        hoveredOver = distanceTo(mX, mY) <= (getWidth()+10)/2;
    }

    @Override
    public void onEquip(Player owner) {
        for (String stat: getStats().keySet()) {
            owner.increaseStat(stat, stats.get(stat));
            if (stat.equals("max health")) {
                owner.heal(stats.get(stat));
            }
        }
        for (ItemBehavior b: behaviors) {
            //TODO: I shouldn't have to be checking if the item behaviors are null, but for some reason a null behavior is added.
            if (b != null) {
                b.onEquip(owner);
            }
        }
    }

    @Override
    public void onUnEquip(Player owner) {
        for (String stat: getStats().keySet()) {
            owner.decreaseStat(stat, stats.get(stat));
            if (stat.equals("max health")) {
                owner.takeDamage(stats.get(stat));
                if (owner.getHealth() < 1) {
                    owner.setHealth(1);
                    //TODO: This could be abused. Fix.
                }
            }
        }
        for (ItemBehavior b: behaviors) {
            if (b != null) {
                b.onUnEquip(owner);
            }
        }
    }

    public HashMap<String, Integer> getStats() {
        return stats;
    }

    public int getRarity() {
        return rarity;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public List<ItemBehavior> getBehaviors() {
        return behaviors;
    }

    public int[] getImageCoordinates() {
        return imageCoordinates;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", stats=" + stats +
                ", rarity=" + rarity +
                ", description='" + description + '\'' +
                ", behaviors=" + behaviors +
                ", imageCoordinates=" + Arrays.toString(imageCoordinates) +
                '}';
    }
}
