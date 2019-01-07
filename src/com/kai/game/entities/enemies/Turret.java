package com.kai.game.entities.enemies;

import com.kai.game.entities.Projectile;
import com.kai.game.util.ResourceManager;
import com.kai.game.core.Screen;

import java.awt.*;

public class Turret extends ProjectileEnemy {

    public static final int TURRET_WIDTH = (int)(20.0/1200.0 * Screen.WINDOW_WIDTH);
    public static final int TURRET_HEIGHT = (int)(100.0/600.0 * Screen.WINDOW_HEIGHT);

    private static final int TURRET_PROJ_WIDTH = (int)(31.0/1200.0 * Screen.WINDOW_WIDTH);
    private static final int TURRET_PROJ_HEIGHT = (int)(31.0/600.0 * Screen.WINDOW_HEIGHT);

    public Turret(int x, int y) {
        super(ResourceManager.getImage("Turret.png", TURRET_WIDTH, TURRET_HEIGHT ), x, y, TURRET_WIDTH, TURRET_HEIGHT,
                0, 25, "Turret", 2, 0.5, 250);
    }

    @Override
    public void createProjectile(int tX, int tY) {
        Image self =  ResourceManager.getImage("TurretProjectile.png", TURRET_PROJ_WIDTH, TURRET_PROJ_HEIGHT);


        projectiles.add(new Projectile(this, self
                , getX(), getY(), TURRET_PROJ_WIDTH, TURRET_PROJ_HEIGHT, 2, getX()-500, getY()-500, getRange(), getDamage()));

        projectiles.add(new Projectile(this, self
                , getX(), getY(), TURRET_PROJ_WIDTH, TURRET_PROJ_HEIGHT, 2, getX()-500,         getY(), getRange(), getDamage()));

        projectiles.add(new Projectile(this, self
                , getX(), getY(), TURRET_PROJ_WIDTH, TURRET_PROJ_HEIGHT, 2, getX()-500, getY()+500, getRange(), getDamage()));

        projectiles.add(new Projectile(this, self
                , getX(), getY(), TURRET_PROJ_WIDTH, TURRET_PROJ_HEIGHT, 2, getX()+500, getY()+500, getRange(), getDamage()));

        projectiles.add(new Projectile(this, self
                , getX(), getY(), TURRET_PROJ_WIDTH, TURRET_PROJ_HEIGHT, 2, getX()+500,         getY(), getRange(), getDamage()));

        projectiles.add(new Projectile(this, self
                , getX(), getY(), TURRET_PROJ_WIDTH, TURRET_PROJ_HEIGHT, 2, getX()+500, getY()-500, getRange(), getDamage()));

        projectiles.add(new Projectile(this, self
                , getX(), getY(), TURRET_PROJ_WIDTH, TURRET_PROJ_HEIGHT, 2, getX(), getY()-500, getRange(), getDamage()));

        projectiles.add(new Projectile(this, self
                , getX(), getY(), TURRET_PROJ_WIDTH, TURRET_PROJ_HEIGHT, 2,         getX(),     getY()+500, getRange(), getDamage()));

    }


}
