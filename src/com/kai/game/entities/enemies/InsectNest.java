package com.kai.game.entities.enemies;

import com.kai.game.entities.Entity;
import com.kai.game.util.ResourceManager;

public class InsectNest extends Enemy  {
    private static final int MAX_SPAWNED_INSECTS = 5;
    private int spawnedInsects = 0;

    public InsectNest(int x, int y) {
        super(ResourceManager.getImage("insectNest.png"),
                x, y, 80, 80, 0, 11, "Insect Nest", 0, 0.3);
        setDamageTick((getMaxDamageTick()/2));
    }

    //DamageTick represents spawn rate here.

    @Override
    public void chase(int targetX, int targetY) {
        if (getDamageTick() >= getMaxDamageTick()) {
            spawn(new Insect(getX() + (getWidth()/2), getY() + (getHeight()/2)));
            spawnedInsects++;
            if (spawnedInsects >= MAX_SPAWNED_INSECTS) {
                setHealth(0);
            }
            setDamageTick(0);
        }
    }

    @Override
    public void attack(Entity target) {
        //Spawns faster when you are on top of it
        setDamageTick(getDamageTick() + 3);
    }

    @Override
    public void onDeath(double m) {
        super.onDeath(m);
        spawn(new Insect(getX()+(getWidth()/2), getY()+(getHeight()/2)));
        spawn(new Insect(getX(), getY()));
    }

    @Override
    public void update() {
        setDamageTick(getDamageTick() + 1);
    }
}
