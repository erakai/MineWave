package com.kai.game.entities.enemies;

import com.kai.game.entities.Entity;
import com.kai.game.entities.SpecialDeath;
import com.kai.game.core.LevelHandler;
import com.kai.game.util.ResourceManager;
import com.kai.game.core.Screen;

public class InsectNest extends Enemy  implements SpecialDeath {

    public InsectNest(int x, int y) {
        super(ResourceManager.getImage("insectNest.png"),
                x, y, 80, 80, 0, 11, "Insect Nest", 0, 0.3);
        setDamageTick((getMaxDamageTick()/2));
    }

    //DamageTick represents spawn rate here.

    //TODO: Replace the spawning here with a spawn() method in Enemy.

    @Override
    public void chase(int targetX, int targetY) {
        if (getDamageTick() >= getMaxDamageTick()) {
            LevelHandler.addEnemy(new Insect(getX() + (getWidth()/2), getY() + (getHeight()/2)));
            setDamageTick(0);
        }
    }

    @Override
    public void attack(Entity target) {
        //Spawns faster when you are on top of it
        setDamageTick(getDamageTick() + 3);
    }

    @Override
    public void onDeath() {
        LevelHandler.addEnemy(new Insect(getX()+(getWidth()/2), getY()+(getHeight()/2)));
        LevelHandler.addEnemy(new Insect(getX(), getY()));
    }

    @Override
    public void update() {
        setDamageTick(getDamageTick() + 1);
    }
}
