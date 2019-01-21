package com.kai.game.entities.bosses;

import com.kai.game.core.LevelHandler;
import com.kai.game.core.Screen;
import com.kai.game.entities.SpecialDeath;
import com.kai.game.entities.enemies.Lavakut;
import com.kai.game.scene.Lava;
import com.kai.game.util.Parameters;
import com.kai.game.util.ResourceManager;
import com.kai.game.entities.enemies.Lavakut.LavakutProjectile;


public class LavaGiant extends Boss implements SpecialDeath {

    private static final double DROP_LAVA_RATE = 0.2;

    private int dropLavaTick, maxDropLavaTick;

    public LavaGiant(int x, int y) {
        super(ResourceManager.getImage("LavaGiant.png"), x, y, 50, 50,
                1, 340, "Lava Giant", 3, 1, 550, 5, 2);

        maxDropLavaTick = (int)(Parameters.FRAMES_PER_SECOND * DROP_LAVA_RATE);
        dropLavaTick = maxDropLavaTick;
    }



    @Override
    public void createProjectile(int tX, int tY) {
        switch(stage) {
            case 1: case 2: case 3: case 4:
                projectiles.add(new LavakutProjectile(this, getHardX(), getHardY(), 16, 16
                        , 7, tX, tY, getRange(), getDamage(), 1, 4, 3));
                break;
            case 5:
                int newTargetX = (int)(Math.random() * 1200);
                int newTargetY = (int)(Math.random() * 600);
                int newRange = (int)(Math.random() * 1200);

                projectiles.add(new LavakutProjectile(this, getHardX()+ getWidth()/2, getHardY() + getHeight()/2, 16, 16
                        , 5, newTargetX, newTargetY, newRange, getDamage(), 1, 3, 3));
                break;
        }
    }

    @Override
    public void chase(int targetX, int targetY) {
        switch (stage) {
            case 1:
                manageProjectileShooting(targetX, targetY);
                if (distanceTo(targetX, targetY) >= getRange() - 300) {
                    altMoveTowards(targetX, targetY);
                } else {
                    altMoveAwayFrom(targetX, targetY);
                }
                break;
            case 2:
                manageLavaDropping();
                defaultMoveTowards(targetX, targetY);
                break;
            case 3:
                manageSpawning();
                manageProjectileShooting(targetX, targetY);
                defaultMoveTowards(targetX, targetY);
                break;
            case 4:
                manageSpawning();
                defaultMoveTowards(targetX, targetY);
                manageLavaDropping();
                break;
            case 5:
                manageProjectileShooting(targetX, targetY);
                manageSpawning();
                break;
        }
    }

    private void manageLavaDropping() {
        if (dropLavaTick <= maxDropLavaTick) {
            dropLavaTick++;
        }
        if (dropLavaTick > maxDropLavaTick) {
            createLava(getHardX(), getHardY());
            dropLavaTick = 0;
        }
    }

    private void createLava(int placementX, int placementY) {
        spawn(new Lava(placementX, placementY, 15, 1, 2, true));
    }

    /*
    1. acts like a lavakut, shoots lava from distance
    2. chases you while leaving lava behind it
    3. lavkuts spawn while he is shooting at you (invincible)
    4. spawns lavakuts while chasing you
    5. teleports to middle, shoots a ton of lavakut projectiles in random places
     */

    @Override
    public void checkTransitions() {
        int startingStage = stage;
        switch (startingStage) {
            case 1:
                if (healthTransition(0.80)) {
                    //switching to chasing with lava behind
                    nextStage();

                    heal(20);
                    setSpeed(4);
                }
                break;
            case 2:
                if (healthTransition(0.60)) {
                    //switching to edge lava and spawning and shooting while invincible
                    nextStage();

                    startInvincibility();
                    setSpeed(2);
                }
                break;
            case 3:
                if (timeTransition("stage 3", 15)) {
                    //Switching to spawning + chasing
                    nextStage();

                    setSpeed(4);
                    setSPAWN_RATE(1.6);
                    leaveInvincibility();
                }
                break;
            case 4:
                if (healthTransition(0.40)) {
                    //Switching to random shooting phase
                    nextStage();

                    Screen.getEnvironment().clearAllSceneObjects();
                    setSpeed(0);
                    teleportToMiddle();
                    heal(100);
                    setSPAWN_RATE(3);
                    setAttacksPerSecond(10);
                }
                break;
        }
    }

    @Override
    public void spawnMinion() {
        spawn(new Lavakut(LevelHandler.getXAwayFromPlayer(300), LevelHandler.getYAwayFromPlayer(300)));
    }

    @Override
    public void startInvincibility() {
        setSelf(ResourceManager.getImage("LavaGiantInvincible.png"));
        setImmune(true);
    }

    @Override
    public void leaveInvincibility() {
        setSelf(ResourceManager.getImage("LavaGiant.png"));
        setImmune(false);
    }

    @Override
    public void onDeath() {
        Screen.getEnvironment().clearAllSceneObjects();
    }
}
