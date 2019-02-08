package com.kai.game.entities.enemies;

import com.kai.game.util.ResourceManager;
import com.kai.game.core.Screen;

public class Insect extends Enemy {

    private boolean enraged;
    private static final int DISTANCE_TO_ENRAGE = 60;

    public Insect(int x, int y) {
        super(ResourceManager.getImage("insect.png"), x, y, 48, 50, 3, 8, "Insect", 1, 2);
        this.enraged = false;
    }

    @Override
    public void chase(int targetX, int targetY) {
        defaultMoveTowards(targetX, targetY);
    }

    
    @Override
    public void update() {
        super.update();
        if (distanceTo(Screen.getPlayer()) < DISTANCE_TO_ENRAGE && !enraged) {
            enrage();
        }
    }

    //Once the insect gets close to the player, it becomes faster, heals for 50%, and changes its image.
    private void enrage() {
        enraged = true;
        setSpeed(5);
        setSelf(ResourceManager.getImage("insectAttacking.png"));
        heal(getMaxHealth()/2);
    }

    public boolean isEnraged() {
        return enraged;
    }
}
