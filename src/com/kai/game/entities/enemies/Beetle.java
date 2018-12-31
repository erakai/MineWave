package com.kai.game.entities.enemies;

import com.kai.game.entities.Projectile;
import com.kai.game.master.ResourceManager;
import com.kai.game.master.Screen;

import java.awt.*;

public class Beetle extends ProjectileEnemy {

    public static final int BEETLE_WIDTH = (int) (40.0 / 1200.0 * Screen.WINDOW_WIDTH);
    public static final int BEETLE_HEIGHT = (int) (40.0 / 600.0 * Screen.WINDOW_HEIGHT);

    public static final int BEETLE_PROJ_WIDTH = (int) (16.0 / 1200.0 * Screen.WINDOW_WIDTH);
    public static final int BEETLE_PROJ_HEIGHT = (int) (16.0 / 600.0 * Screen.WINDOW_HEIGHT);

    public Beetle(int x, int y) {
        super(ResourceManager.getImage("beetle.png", BEETLE_WIDTH, BEETLE_HEIGHT), x, y, BEETLE_WIDTH, BEETLE_HEIGHT,
                2, 5, "Beetle", 3, 0.8, 800);
    }

    public void createProjectile(int tX, int tY) {
        projectiles.add(new Projectile(this, ResourceManager.getImage("beetleProjectile.png", BEETLE_PROJ_WIDTH, BEETLE_PROJ_HEIGHT)
                , getX(), getY(), BEETLE_PROJ_WIDTH, BEETLE_PROJ_HEIGHT, 4, tX, tY, getRange(), getDamage()));
    }

}
