package com.kai.game.entities.bosses;

import com.kai.game.core.LevelHandler;
import com.kai.game.core.Screen;
import com.kai.game.entities.Projectile;
import com.kai.game.entities.enemies.Bat;
import com.kai.game.util.Parameters;
import com.kai.game.util.ResourceManager;

import java.awt.*;

public class Vampire extends Boss {

    private static final double SPAWN_RATE = 2.5;
    private static final int PROJECTILE_VARIANCE = 200;
    private static int PROJECTILES_TO_SHOOT = 3;

    private boolean immune;
    private int currentSpawnTick, maxSpawnTick;

    public Vampire(int x, int y) {
        super(ResourceManager.getImage("VampireBoss.png"), x, y, 60, 90,
                3, 280, "Vampire", 2, 1, 500, 6);
        immune = false;
        currentSpawnTick = maxSpawnTick;
        maxSpawnTick = (int)(SPAWN_RATE * Parameters.FRAMES_PER_SECOND);
    }

    @Override
    public void createProjectile(int tX, int tY) {
        projectiles.add(new Projectile(this, ResourceManager.getImage("VampireProj.png")
                , getHardX()+30, getHardY()+45, 16, 16, 7, tX, tY, getRange(), getDamage()));

        for (int i = 0; i < PROJECTILES_TO_SHOOT-1; i++) {
            projectiles.add(new Projectile(this, ResourceManager.getImage("VampireProj.png")
                    , getHardX() + 30, getHardY() + 45, 16, 16, 7, tX + ((int) (Math.random() * PROJECTILE_VARIANCE)), tY + ((int) (Math.random() * PROJECTILE_VARIANCE)), getRange(), getDamage()));
        }
    }


    //stage 1: Follow player.
    //stage 2: Teleport to middle and shoot 3 shots at player.
    //stage 3: Summon minions and go invincible.
    //stage 4: Chase player while shooting.
    //stage 5: Spawn minions while shooting from middle.
    //stage 6: Decrease speed and attacks per second + damage, slowly chase player.


    @Override
    public void chase(int targetX, int targetY) {

        switch(stage) {
            case 1:
                defaultMoveTowards(targetX, targetY);
                break;
            case 2:
                manageProjectileShooting(targetX, targetY);
                break;
            case 3:
                manageSpawning();
                break;
            case 4:
                manageProjectileShooting(targetX, targetY);
                defaultMoveTowards(targetX, targetY);
                break;
            case 5:
                manageProjectileShooting(targetX, targetY);
                manageSpawning();
                break;
            case 6:
                manageSpawning();
                manageProjectileShooting(targetX, targetY);
                altMoveTowards(targetX, targetY);
                break;
        }

    }

    private void manageSpawning() {
        currentSpawnTick++;
        if (currentSpawnTick > maxSpawnTick) {
            currentSpawnTick = 0;
            for (int i = 0; i < 5; i++) {
                LevelHandler.addEnemy(new Bat(LevelHandler.getXAwayFromPlayer(Bat.BAT_WIDTH), LevelHandler.getYAwayFromPlayer(Bat.BAT_HEIGHT)));
            }
        }
    }



    @Override
    public void checkTransitions() {
        int startingStage = stage;
        switch(startingStage) {
            case 1:
                if (healthTransition(0.80)) {
                    //Switching to shooting phase.
                    nextStage();

                    teleportToMiddle();
                }
                break;
            case 2:
                if (healthTransition(0.65)) {
                    //Switching to minion phase.
                    nextStage();

                    currentSpawnTick = maxSpawnTick;
                    startInvincibility();
                }
                break;
            case 3:
                if (timeTransition("stage 2", 10)) {
                    //Switching to chasing phase.
                    nextStage();

                    leaveInvincibility();
                }
                break;
            case 4:
                if (healthTransition(0.40)) {
                    //Switching to minion + shooting phase.
                    nextStage();

                    maxSpawnTick *= 0.7;
                    currentSpawnTick = maxSpawnTick;
                    teleportToMiddle();
                    startInvincibility();
                }
                break;
            case 5:
                if (timeTransition("stage 4", 15)) {
                    //Switching to rage phase.
                    nextStage();

                    maxSpawnTick *= 1.5;
                    leaveInvincibility();
                    setSpeed(2);
                    PROJECTILES_TO_SHOOT = 4;
                    setAttacksPerSecond(2.5);
                }
                break;
        }
    }

    private void teleportToMiddle() {
        setX(570);
        setY(255);
    }

    //TODO: Replace these invincibility methods with a system of buffs/curses.
    private void startInvincibility() {
        setSelf(ResourceManager.getImage("VampireInvincible.png"));
        immune = true;
    }

    private void leaveInvincibility() {
        setSelf(ResourceManager.getImage("VampireBoss.png"));
        immune = false;
    }

    @Override
    public void takeDamage(int amount) {
        if (!immune) {
            super.takeDamage(amount);
        }
    }

    @Override
    public void drawMe(Graphics g) {
        if (!immune) {
            super.drawMe(g);
        } else {
            g.drawImage(getSelfImage(), getX(), getY(), null);
            for (Projectile p: projectiles) {
                p.drawMe(g);
            }
        }
    }
}
