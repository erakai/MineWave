package com.kai.game.entities.enemies;

import com.kai.game.core.Screen;
import com.kai.game.util.ResourceManager;

import java.awt.*;

public class Bat extends Enemy {

    public Bat(int x, int y) {
        super(ResourceManager.getImage("Bat.png"), x, y, 40, 40,
                2, 3, "Bat", 1, 1);
    }

    public void chase(int targetX, int targetY) {
        defaultMoveTowards(targetX, targetY);
    }

}
