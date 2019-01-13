package com.kai.game.entities.enemies;

import com.kai.game.core.Screen;
import com.kai.game.util.ResourceManager;

import java.awt.*;

public class Bat extends Enemy {

    public static final int BAT_WIDTH = (int)(40.0/1200.0 * Screen.WINDOW_WIDTH);
    public static final int BAT_HEIGHT = (int)(40.0/600.0 * Screen.WINDOW_HEIGHT);

    public Bat(int x, int y) {
        super(ResourceManager.getImage("Bat.png", BAT_WIDTH, BAT_HEIGHT), x, y, 40, 40,
                2, 3, "Bat", 1, 1);
    }

    public void chase(int targetX, int targetY) {
        defaultMoveTowards(targetX, targetY);
    }

}
