package com.kai.game.entities.enemies;

import com.kai.game.entities.Projectile;
import com.kai.game.scene.Lava;
import com.kai.game.util.ResourceManager;
import com.kai.game.core.Screen;

public class Beetle extends ProjectileEnemy {



    public Beetle(int x, int y) {
        super(ResourceManager.getImage("beetle.png"), x, y, 40, 40,
                2, 5, "Beetle", 3, 1.2, 1000);
    }
    //aps:1.2

    @Override
    public void chase(int targetX, int targetY) {
        defaultProjectileEnemyChase(targetX, targetY);
    }

    public void createProjectile(int tX, int tY) {
        projectiles.add(new Projectile(this, ResourceManager.getImage("beetleProjectile.png")
                , getHardX(), getHardY(), 16, 16, 5, tX, tY, getRange(), getDamage()));
    }

}
