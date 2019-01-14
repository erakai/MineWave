package com.kai.game.entities.enemies;

import com.kai.game.util.ResourceManager;
import com.kai.game.core.Screen;

public class ArmoredInsect extends Enemy {


    public ArmoredInsect(int x, int y) {
        super(ResourceManager.getImage("ArmoredInsect.png"), x, y, 48, 50,
                1, 20, "Armored Insect", 5, 0.6);
    }

    @Override
    public void chase(int targetX, int targetY) {
        altMoveTowards(targetX, targetY);
    }


    @Override
    public void update() {
        super.update();
        regenerate(1);
    }
}
