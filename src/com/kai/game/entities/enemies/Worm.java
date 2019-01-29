package com.kai.game.entities.enemies;

import com.kai.game.items.LootInstance;
import com.kai.game.util.ResourceManager;
import com.kai.game.core.Screen;

import java.awt.*;

public class Worm extends Enemy {


    private double rotationDegreeDir;

    private int currentTargetedX, currentTargetedY;
    private int maxChargeTick, currentChargeTick;

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


    private void updateRotationDegreeDir(int targetX, int targetY) {
        double opposite = Math.abs(targetX - (getX() + getWidth()/2.0));
        double adjacent = Math.abs(targetY - (getY()));
        double deg = Math.toDegrees(Math.atan( (opposite/adjacent) ));
        if (getY() < targetY && getX() < targetX) {
            deg = (180 - deg);
        }
        if (getY() < targetY && getX() > targetX) {
            deg = (180 + deg);
        }
        if (getY() > targetY && getX() > targetX) {
            deg = (-1 * deg);
        }
        rotationDegreeDir = deg;
    }

    @Override
    public void chase(int targetX, int targetY) {
        updateRotationDegreeDir(targetX, targetY);

        if (currentChargeTick >= maxChargeTick) {
            currentTargetedY = targetY;
            currentTargetedX = targetX;
            currentChargeTick = 0;
        }

        if (currentTargetedX != -1 && currentTargetedY != -1 && !(distanceTo(currentTargetedX, currentTargetedY) < 5)) {
            defaultMoveTowards(currentTargetedX, currentTargetedY);
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
    public void onDeath() {
        Screen.getPlayer().heal(Screen.getPlayer().getStats().get("life on kill"));
        Screen.getRoomHandler().newLootInstance(new LootInstance(getCenterX(), getCenterY(), 10, true, true));
    }
}
