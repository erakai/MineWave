package com.kai.game.entities.bosses;

import com.kai.game.core.RoomHandler;
import com.kai.game.entities.Projectile;
import com.kai.game.entities.enemies.Turret;
import com.kai.game.util.ResourceManager;

import java.awt.*;

public class LightningWorm extends Boss {
    private Image projImage;

    public LightningWorm(int x, int y) {
        super(ResourceManager.getImage("LightningWorm.png"), x, y, 60, 60, 9, 180, "Lightning Worm",
                2, 1, 50, 4, 4);
        projImage = ResourceManager.getImage("TurretProjectile.png");

        rotationDegreeDir = 0;
        maxChargeTick = getMaxDamageTick();

        currentTargetedX = -1;
        currentTargetedY = -1;
    }

    /*
     * Stages:
     * 1. charges normally
     * 2. charges with lightning balls
     * 3. spawns turrets and charges normally
     * 4. spawns turrets and charges with lightning balls
     */

    @Override
    public void createProjectile(int tX, int tY) {
        tProj(getHardX()-500, getHardY()-500);
        tProj(getHardX()-500, getHardY());
        tProj(getHardX()-500, getHardY()+500);
        tProj(getHardX()+500, getHardY()+500);
        tProj(getHardX()+50, getHardY());
        tProj(getHardX()+500, getHardY()-500);
        tProj(getHardX(), getHardY()+500);
    }

    private void tProj(int tX, int tY) {
        if (stage == 4) {
            projectiles.add(new Projectile(this, projImage
                , getHardX(), getHardY(), 31, 31, 2, tX, tY, getRange() + (int)(Math.random() * 50), 0.1));
        } else {
            projectiles.add(new Projectile(this, projImage
                    , getHardX(), getHardY(), 31, 31, 10, tX, tY, 500, 2));
        }
    }

    @Override
    public void chase(int targetX, int targetY) {
        switch(stage) {
            case 1:
                doCharge(targetX, targetY);
                break;
            case 2:
                doCharge(targetX, targetY);
                manageProjectileShooting(targetX, targetY);
                break;
            case 3:
                manageSpawning();
                doCharge(targetX, targetY);
                break;
            case 4:
                manageSpawning();
                doCharge(targetX, targetY);
                createProjectile(targetX, targetY);
                break;
        }
    }

    @Override
    public void checkTransitions() {
        int sstage = stage;
        switch(sstage) {
            case 1:
                if (healthTransition(0.7)) {
                    nextStage();
                }
                break;
            case 2:
                if(healthTransition(0.5)) {
                    nextStage();
                }
                break;
            case 3:
                if(healthTransition(0.3)) {
                    setSPAWN_RATE(2);
                    nextStage();
                }
                break;
        }
    }

    @Override
    public void update() {
        super.update();
        if (currentChargeTick < maxChargeTick) {
            currentChargeTick++;
        }
    }

    @Override
    public void spawnMinion() {
        spawn(new Turret(RoomHandler.getXAwayFromPlayer(20), RoomHandler.getYAwayFromPlayer(80)));
    }


    @Override
    public void drawMe(Graphics g) {
        if (!immune) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.gray);
        }
        g.drawImage(ResourceManager.rotate(getSelfImage(), rotationDegreeDir), getX(), getY(), null);
        for (Projectile p: projectiles) {
            p.drawMe(g);
        }
        g.drawImage(healthBar, healthBarPoint.getX(), healthBarPoint.getY(), null);
        g.fillRect(healthBarRectangle.getTopLeft().getX(), healthBarRectangle.getTopLeft().getY(), ((int) ( getHealth() / getMaxHealth() * healthBarRectangle.getWidth())), healthBarRectangle.getHeight());
    }
}
