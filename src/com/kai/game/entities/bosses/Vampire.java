package com.kai.game.entities.bosses;

import java.awt.*;

public class Vampire extends Boss {

    public Vampire(Image self, int x, int y, int width, int height, int speed, int maxHealth,
                   String name, int damage, double attacksPerSecond, int range, int maxStages) {
        super(self, x, y, width, height, speed, maxHealth, name, damage, attacksPerSecond, range, maxStages);
    }

    @Override
    public void createProjectile(int tX, int tY) {

    }

    @Override
    public void chase(int targetX, int targetY) {

    }
}
