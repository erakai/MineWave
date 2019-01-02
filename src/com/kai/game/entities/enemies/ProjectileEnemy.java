package com.kai.game.entities.enemies;

import com.kai.game.GameObject;
import com.kai.game.entities.Entity;
import com.kai.game.entities.Player;
import com.kai.game.entities.Projectile;
import com.kai.game.entities.UsesProjectiles;
import com.kai.game.master.ResourceManager;
import com.kai.game.master.Screen;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class ProjectileEnemy extends Enemy implements UsesProjectiles {
    private int range;

    List<Projectile> projectiles;
    private List<Projectile> removeProjectileQueue;

    public ProjectileEnemy(Image self, int x, int y, int width, int height, int speed, int maxHealth, String name, int damage, double attacksPerSecond, int range) {
        super(self, x, y, width, height, speed, maxHealth, name, damage, attacksPerSecond);
        this.range = range;

        projectiles = new ArrayList<>();
        removeProjectileQueue = new ArrayList<>();
    }

    //TODO: Do something more elegant than having the same projectile code both here and in player.

    @Override
    public void onProjectileCollision(GameObject collidedWith, Projectile p) {
        if (collidedWith instanceof Player) {
            this.attack((Entity)collidedWith, p.getDamage());
        }
        addToRemoveQueue(p);
    }

    @Override
    public void addToRemoveQueue(Projectile p) {
        if (!removeProjectileQueue.contains(p)) {
            removeProjectileQueue.add(p);
        }
    }

    @Override
    public void callAllProjectileCollisions(List<GameObject> objectsToCheckWith) {
        for (Projectile p: projectiles) {
            p.allCollisions(objectsToCheckWith);
        }
    }

    @Override
    public void removeAllInQueue() {
        projectiles.removeAll(removeProjectileQueue);
        removeProjectileQueue.clear();
    }


    @Override
    public void attack(Entity target, int ovrDamage) {
        target.takeDamage(ovrDamage);
    }

    @Override
    public void attack(Entity target) {
        attack(target, getDamage());
    }

    //DamageTick represents how often to shoot in this case.
    @Override
    public void chase(int targetX, int targetY) {
        manageProjectileShooting(targetX, targetY);
        defaultMoveTowards(targetX, targetY);
    }

    public void manageProjectileShooting(int targetX, int targetY) {
        if (getDamageTick() >= getMaxDamageTick()) {
            setDamageTick(0);
            createProjectile(targetX, targetY);
        } else {
            setDamageTick(getDamageTick() + 1);
        }

    }

    @Override
    public void update() {
        removeAllInQueue();
        for (Projectile p: projectiles) {
            p.update();
        }
    }

    @Override
    public void drawMe(Graphics g) {
        super.drawMe(g);
        for (Projectile p: projectiles) {
            p.drawMe(g);
        }
    }

    public int getRange() {
        return range;
    }

}
