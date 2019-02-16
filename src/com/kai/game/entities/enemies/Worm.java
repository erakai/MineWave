package com.kai.game.entities.enemies;

import com.kai.game.items.LootInstance;
import com.kai.game.util.ResourceManager;
import com.kai.game.core.Screen;

import java.awt.*;

public class Worm extends Enemy {




    //TODO: Fix glitch where Worm doesn't charge if you are hugging left side.

    public Worm(int x, int y) {
        super(ResourceManager.getImage("Worm.png"), x, y, 50, 50,
                9, 85, "Worm", 4, 1);
        rotationDegreeDir = 0;
        maxChargeTick = getMaxDamageTick();

        currentTargetedX = -1;
        currentTargetedY = -1;
    }

    @Override
    public void drawMe(Graphics g) {
        g.drawImage(ResourceManager.rotate(getSelfImage(), rotationDegreeDir), getX(), getY(), null);
        g.setColor(Color.red);
        g.fillRect(getX() + 2, getY()-(int)(10.0/600.0 * Screen.WINDOW_HEIGHT), (int)(getHealth()/getMaxHealth() * (getWidth())) , (int)(5.0/600.0 * Screen.WINDOW_HEIGHT));
    }


    @Override
    public void chase(int targetX, int targetY) {
        doCharge(targetX, targetY);
    }


    @Override
    public void update() {
        super.update();
        if (currentChargeTick < maxChargeTick) {
            currentChargeTick++;
        }
    }

    @Override
    public void onDeath(double multiplier) {
        super.onDeath(10);
    }
}
