package com.kai.game.entities.bosses;


import com.kai.game.core.Screen;
import com.kai.game.entities.Entity;
import com.kai.game.entities.Projectile;
import com.kai.game.entities.enemies.ProjectileEnemy;
import com.kai.game.items.LootInstance;
import com.kai.game.util.*;

import java.awt.*;

public abstract class Boss extends ProjectileEnemy {
    private MTimer timer;

    protected boolean immune = false;
    protected double SPAWN_RATE = 2.5;

    protected int currentSpawnTick, maxSpawnTick;

    protected Image healthBar;

    private int meleeDamageTick, maxMeleeDamageTick;
    //TODO: Draw boss health bar somewhere where it can be much longer and not directly above the boss.

    /*
    Name of marked times in timer:
        Object creation - "start"
        Stages - "stage {stage number}"
     */

    int stage;
    private int maxStages;

    Boss(Image self, int x, int y, int width, int height, int speed, int maxHealth, String name,
                int damage, double attacksPerSecond, int range, int maxStages, double spawnRate) {
        super(self, x, y, width, height, speed, maxHealth, name, damage, attacksPerSecond, range);

        healthBar = ResourceManager.getImage("BossHealthBar.png");

        this.SPAWN_RATE = spawnRate;
        this.stage = 1;
        this.maxStages = maxStages;
        timer = new MTimer();

        maxSpawnTick = (int)(SPAWN_RATE * Parameters.FRAMES_PER_SECOND);
        currentSpawnTick = maxSpawnTick;

        maxMeleeDamageTick = (int)(attacksPerSecond * Parameters.FRAMES_PER_SECOND);
        meleeDamageTick = maxMeleeDamageTick;
    }

    public abstract void createProjectile(int tX, int tY);
    public abstract void chase(int targetX, int targetY);
    public abstract void checkTransitions();
    public abstract void spawnMinion();


    public void meleeAttack(Entity e) {
        if (meleeDamageTick >= maxMeleeDamageTick) {
            attack(e, getDamage());
            meleeDamageTick = 0;
        }
    }

    @Override
    public void update() {
        super.update();
        checkTransitions();
        if (meleeDamageTick < maxMeleeDamageTick) {
            meleeDamageTick++;
        }
    }

    void nextStage() {
        transitionToStage(stage + 1);
    }

    //Override this and call the super
    void transitionToStage(int newStage) {
        timer.markTime("stage " + newStage);
        this.stage = newStage;
    }

    boolean timeTransition(String markedTimeKey, int necessaryLength) {
        return (timer.getSecondsSinceMarkedTime(markedTimeKey) >= necessaryLength);
    }

    boolean healthTransition(double percentageRequired) {
        return (getHealth() / (double)getMaxHealth() <= percentageRequired);
    }

    public void teleportToMiddle() {
        setX(570);
        setY(255);
    }

    public void startInvincibility() {
        setImmune(true);
    }

    public void leaveInvincibility() {
        setImmune(false);
    }

    public void setImmune(boolean immune) {
        this.immune = immune;
    }

    @Override
    public void takeDamage(double amount) {
        if (!immune) {
            super.takeDamage(amount);
        }
    }

    protected void manageSpawning() {
        currentSpawnTick++;
        if (currentSpawnTick > maxSpawnTick) {
            currentSpawnTick = 0;
            spawnMinion();
        }
    }


    private MPoint healthBarPoint = new MPoint(0, 600-47);
    private MRectangle healthBarRectangle = new MRectangle(new MPoint(16, healthBarPoint.getHardY()+16), new MPoint(1183, healthBarPoint.getHardY()+31));

    @Override
    public void drawMe(Graphics g) {
        if (!immune) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.gray);
        }
        g.drawImage(getSelfImage(), getX(), getY(), null);
        for (Projectile p: projectiles) {
            p.drawMe(g);
        }
        g.drawImage(healthBar, healthBarPoint.getX(), healthBarPoint.getY(), null);
        g.fillRect(healthBarRectangle.getTopLeft().getX(), healthBarRectangle.getTopLeft().getY(), ((int) ( getHealth() / getMaxHealth() * healthBarRectangle.getWidth())), healthBarRectangle.getHeight());
    }

    public void setSPAWN_RATE(double SPAWN_RATE) {
        this.SPAWN_RATE = SPAWN_RATE;
        maxSpawnTick = (int)(SPAWN_RATE * Parameters.FRAMES_PER_SECOND);
    }

    @Override
    public void onDeath() {
        Screen.getRoomHandler().newLootInstance(new LootInstance(getCenterX(), getCenterY(), 80, false, false));
    }
}
