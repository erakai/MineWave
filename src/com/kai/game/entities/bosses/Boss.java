package com.kai.game.entities.bosses;


import com.kai.game.entities.enemies.ProjectileEnemy;
import com.kai.game.util.MTimer;

import java.awt.*;

public abstract class Boss extends ProjectileEnemy {
    private MTimer timer;

    //TODO: Draw boss health bar somewhere where it can be much longer and not directly above the boss.

    /*
    Name of marked times in timer:
        Object creation - "start"
        Stages - "stage {stage number}"
     */

    int stage;
    private int maxStages;

    Boss(Image self, int x, int y, int width, int height, int speed, int maxHealth, String name,
                int damage, double attacksPerSecond, int range, int maxStages) {
        super(self, x, y, width, height, speed, maxHealth, name, damage, attacksPerSecond, range);

        this.stage = 1;
        this.maxStages = maxStages;
        timer = new MTimer();
    }

    public abstract void createProjectile(int tX, int tY);
    public abstract void chase(int targetX, int targetY);
    public abstract void checkTransitions();

    @Override
    public void update() {
        super.update();
        checkTransitions();
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


}
