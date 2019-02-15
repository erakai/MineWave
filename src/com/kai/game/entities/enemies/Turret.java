package com.kai.game.entities.enemies;

import com.kai.game.entities.Projectile;
import com.kai.game.util.ResourceManager;
import com.kai.game.core.Screen;

import java.awt.*;

public class Turret extends ProjectileEnemy {
    private Image self;

    public static final int TURRET_WIDTH = (int)(20.0/1200.0 * Screen.WINDOW_WIDTH);
    public static final int TURRET_HEIGHT = (int)(100.0/600.0 * Screen.WINDOW_HEIGHT);


    //TODO: Sometimes the center right and left shots of turret don't move faster than the ones above and below them. Why?

    public Turret(int x, int y) {
        super(ResourceManager.getImage("Turret.png"), x, y, 20, 100,
                0, 25, "Turret", 2, 0.5, 800);
        self = ResourceManager.getImage("TurretProjectile.png");
    }

    @Override
    public void chase(int targetX, int targetY) {
        defaultProjectileEnemyChase(targetX, targetY);
    }

    @Override
    public void createProjectile(int tX, int tY) {

        projectiles.add(new Projectile(this, self
                , getHardX(), getHardY(), 31, 31, 2, getHardX()-500, getHardY()-500, getRange(), getDamage()));

        projectiles.add(new Projectile(this, self
                , getHardX(), getHardY(), 31, 31, 2, getHardX()-500,         getHardY(), getRange(), getDamage()));

        projectiles.add(new Projectile(this, self
                , getHardX(), getHardY(), 31, 31, 2, getHardX()-500, getHardY()+500, getRange(), getDamage()));

        projectiles.add(new Projectile(this, self
                ,getHardX(), getHardY(), 31, 31, 2, getHardX()+500, getHardY()+500, getRange(), getDamage()));

        projectiles.add(new Projectile(this, self
                , getHardX(), getHardY(), 31, 31, 2, getHardX()+500,         getHardY(), getRange(), getDamage()));

        projectiles.add(new Projectile(this, self
                , getHardX(), getHardY(), 31, 31, 2, getHardX()+500, getHardY()-500, getRange(), getDamage()));

        projectiles.add(new Projectile(this, self
                , getHardX(), getHardY(), 31, 31, 2, getHardX(), getHardY()-500, getRange(), getDamage()));

        projectiles.add(new Projectile(this, self
                , getHardX(), getHardY(), 31, 31, 2,         getHardX(),     getHardY()+500, getRange(), getDamage()));

    }


}
