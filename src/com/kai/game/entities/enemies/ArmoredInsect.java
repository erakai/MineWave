package com.kai.game.entities.enemies;

import com.kai.game.master.ResourceManager;
import com.kai.game.master.Screen;

import java.awt.*;

public class ArmoredInsect extends Enemy {

    public static final int ARMORED_INSECT_WIDTH = (int)(48.0/1200.0 * Screen.WINDOW_WIDTH);
    public static final int ARMORED_INSECT_HEIGHT = (int)(50.0/600.0 * Screen.WINDOW_HEIGHT);

    public ArmoredInsect(int x, int y) {
        super(ResourceManager.getImage("ArmoredInsect.png", ARMORED_INSECT_WIDTH, ARMORED_INSECT_HEIGHT), x, y, ARMORED_INSECT_WIDTH, ARMORED_INSECT_HEIGHT,
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
