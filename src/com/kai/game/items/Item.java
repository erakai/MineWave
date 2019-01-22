package com.kai.game.items;

import com.kai.game.core.GameObject;
import com.kai.game.entities.Player;
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

    public Item(String name, int rarity, String description, HashMap<String, Integer> stats, List<ItemBehavior> behaviors, int[] imageCoordinates) {
        super(ResourceManager.getItemImage(imageCoordinates[0], imageCoordinates[1], 48, 48), -50, -50, 48, 48);
        this.name = name;
        this.imageCoordinates = imageCoordinates;
        this.stats = stats;
        this.rarity = rarity;
        this.description = description;
        this.behaviors = behaviors;
    }

    public Item (Item item) {
        this(item.getName(), item.getRarity(), item.getDescription(), item.getStats(), item.getBehaviors(), item.getImageCoordinates());
    }

    @Override
    public void drawMe(Graphics g) {
        g.drawImage(getSelfImage(), getX(), getY(), null);
    }

    @Override
    public void onEquip(Player owner) {
        for (String stat: getStats().keySet()) {
            owner.increaseStat(stat, stats.get(stat));
        }
        for (ItemBehavior b: behaviors) {
            b.onEquip(owner);
        }
    }

    @Override
    public void onUnEquip(Player owner) {
        for (String stat: getStats().keySet()) {
            owner.decreaseStat(stat, stats.get(stat));
        }
        for (ItemBehavior b: behaviors) {
            b.onUnEquip(owner);
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
