package com.kai.game.entities;

import java.util.HashMap;

public class StatManager {
    private final Player player;

    /*
    StatManager should house all stats of the attached player.

    = Stats =
        * Max Mines
            - The max amount of mines you can have placed at once
            - Affects things like combo
            - Starts at 7
        * Range
            - Radius of range circle = range * 25
            - Starts at 13
        * Speed
            - How many pixels the player can move per frame
            - Starts at 4
        * Damage
            - How much damage one mine does to an enemy
            - Starts at 5
        * Defense
            - Damage taken = enemy damage * ((100 - player defense) / 100.0)
            - Starts at 5
        * Max Life
            - Whenever max life is gained/lost, gain/lose that much current life
            - Starts at 20
    */

    private HashMap<String, Integer> stats;

    public StatManager(Player player, int startingMaxLife, int startingDefense, int startingDamage, int startingSpeed, int startingRange, int startingMaxMines) {
        this.player = player;

        stats = new HashMap<>();

        stats.put("max health", startingMaxLife);
        stats.put("defense", startingDefense);
        stats.put("damage", startingDamage);
        stats.put("speed", startingSpeed);
        stats.put("range", startingRange);
        stats.put("max mines", startingMaxMines);
    }

    public void increaseStat(String stat, int amount) {
        stats.replace(stat, getStat(stat) + amount);
    }

    public void decreaseStat(String stat, int amount) {
        stats.replace(stat, getStat(stat) - amount);
    }

    public int getStat(String stat) {
        return stats.get(stat);
    }

    public void setStat(String stat, int newAmount) {
        stats.replace(stat, newAmount);
    }

    public HashMap<String, Integer> getStats() {
        return stats;
    }

}
