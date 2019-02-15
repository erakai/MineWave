package com.kai.game.entities.enemies;

import com.kai.game.core.Screen;
import com.kai.game.entities.Projectile;
import com.kai.game.entities.UsesProjectiles;
import com.kai.game.scene.Lava;
import com.kai.game.util.ResourceManager;

import java.awt.*;

public class Lavakut extends ProjectileEnemy {

    public Lavakut(int x, int y) {
        super(ResourceManager.getImage("Lavakut.png"), x, y, 35, 35,
                1, 20, "Lavakut", 1, 1, 400);
    }

    @Override
    public void createProjectile(int tX, int tY) {
        projectiles.add(new LavakutProjectile(this, getHardX(), getHardY(), 16, 16
        , 6, tX, tY, getRange(), getDamage(), 1, 5, 2));
    }

    @Override
    public void chase(int targetX, int targetY) {
        manageProjectileShooting(targetX, targetY);
        if (distanceTo(targetX, targetY) >= getRange() - 100) {
            altMoveTowards(targetX, targetY);
        } else {
            altMoveAwayFrom(targetX, targetY);
        }
    }

    public static class LavakutProjectile extends Projectile {
        private int lavaDuration, lavaDamage;
        private double lavaAPS;

        public LavakutProjectile(UsesProjectiles owner,int x, int y, int width, int height, int speed, int targetX, int targetY, int range, double damage, int lavaDamage, int lavaDuration, double lavaAPS) {
            super(owner, ResourceManager.getImage("LavakutProjectile.png"), x, y, width, height, speed, targetX, targetY, range, damage);
            this.lavaDamage = lavaDamage;
            this.lavaDuration = lavaDuration;
            this.lavaAPS = lavaAPS;
        }

        @Override
        public void update() {
            super.update();
            if (getDistanceTraveled() > this.getRange() && this.getSpeed() > 0) {
                Screen.addSceneObject(new Lava(this.getHardX() - (75 / 2), this.getHardY() - (62 / 2), lavaDuration, lavaDamage, lavaAPS, true));
            }
        }
    }
}
