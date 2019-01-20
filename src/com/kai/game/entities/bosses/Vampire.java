package com.kai.game.entities.bosses;

import com.kai.game.core.LevelHandler;
import com.kai.game.core.Screen;
import com.kai.game.entities.Projectile;
import com.kai.game.entities.enemies.Bat;
import com.kai.game.util.Parameters;
import com.kai.game.util.ResourceManager;

import java.awt.*;

public class Vampire extends Boss {

    private static final int PROJECTILE_VARIANCE = 200;
    private static int PROJECTILES_TO_SHOOT = 3;


    public Vampire(int x, int y) {
        super(ResourceManager.getImage("VampireBoss.png"), x, y, 60, 90,
                3, 280, "Vampire", 2, 1, 800, 6, 2.5);

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
                defaultMoveTowards(targetX, targetY);
                break;
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
                if (timeTransition("stage 3", 8)) {
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
                if (timeTransition("stage 5", 12)) {
                    //Switching to rage phase.
                    nextStage();

                    maxSpawnTick *= 1.5;
                    leaveInvincibility();
                    setSpeed(2);
                    PROJECTILES_TO_SHOOT = 4;
                    setAttacksPerSecond(2);
                }
                break;
        }
    }

    @Override
    public void spawnMinion() {
        for (int i = 0; i < 5; i++) {
            spawn(new Bat(LevelHandler.getXAwayFromPlayer(200), LevelHandler.getYAwayFromPlayer(200)));
        }
    }

    //TODO: Replace these invincibility methods with a system of buffs/curses.
    @Override
    public void startInvincibility() {
        setSelf(ResourceManager.getImage("VampireInvincible.png"));
        setImmune(true);
    }

    @Override
    public void leaveInvincibility() {
        setSelf(ResourceManager.getImage("VampireBoss.png"));
        setImmune(false);
    }


}
