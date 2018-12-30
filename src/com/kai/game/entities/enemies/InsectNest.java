package com.kai.game.entities.enemies;

import com.kai.game.entities.Entity;
import com.kai.game.entities.SpecialDeath;
import com.kai.game.master.LevelHandler;
import com.kai.game.master.ResourceManager;
import com.kai.game.master.Screen;

import java.awt.*;

public class InsectNest extends Enemy  implements SpecialDeath {

    public static final int INSECT_NEST_WIDTH = (int)(80.0/1200.0 * Screen.WINDOW_WIDTH);
    public static final int INSECT_NEST_HEIGHT = (int)(80.0/600.0 * Screen.WINDOW_HEIGHT);

    public InsectNest(int x, int y) {
        super(ResourceManager.getImage("insectNest.png", INSECT_NEST_WIDTH, INSECT_NEST_HEIGHT),
                x, y, INSECT_NEST_WIDTH, INSECT_NEST_HEIGHT, 0, 11, "Insect Nest", 0, 0.3);
        setDamageTick((getMaxDamageTick()/2));
    }

    //DamageTick represents spawn rate here.

    //TODO: Replace the spawning here with a spawn() method in Enemy.

    @Override
    public void chase(int targetX, int targetY) {
        if (getDamageTick() >= getMaxDamageTick()) {
            LevelHandler.addEnemy(new Insect(getX() + (width/2), getY() + (height/2)));
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
        LevelHandler.addEnemy(new Insect(getX()+(width/2), getY()+(height/2)));
        LevelHandler.addEnemy(new Insect(getX(), getY()));
    }

    @Override
    public void update() {
        setDamageTick(getDamageTick() + 1);
    }
}
