package com.kai.game.entities;

import com.kai.game.GameObject;
import com.kai.game.Updatable;
import com.kai.game.entities.enemies.Enemy;
import com.kai.game.master.Screen;

import java.awt.*;
import java.util.List;

public class Projectile extends GameObject implements Updatable {
    private UsesProjectiles owner;
    private int targetX, targetY;
    private int speed, range;

    private int distanceTraveled;

    //TODO: Have projectiles not be drawn/updated inside of their owners, so they don't disappear on owner death.

    public Projectile(UsesProjectiles owner, Image self, int x, int y, int width, int height, int speed, int targetX, int targetY, int range) {
        super(self, x, y, width, height);
        this.speed = speed;
        this.range = range;
        this.owner = owner;
        this.targetX = targetX;
        this.targetY = targetY;

        if (!(owner instanceof Player)) {
            updateTarget();
        }

        distanceTraveled = 0;
    }

    //TODO: Manually setting the target to off screen so the projectiles travel the full range seems bad. Alternatives?

    //TODO: Method doesn't work when you are to the left of the enemy (Proj gets stuck on x = 0). Fix?
    private void updateTarget() {
        double m = (    ((double)(targetY - getY())) / ((double)(targetX - getX()))    );
        int b = (int)(getY() - (m * getX()));
        if (targetX > getX()) {
            targetX = getCloseNumber(1.2, Screen.WINDOW_WIDTH, targetX);
            targetY = (int)((m * targetX) + b);
        } else if (targetX < getX()) {
            targetX -= (getCloseNumber(1.2, Screen.WINDOW_WIDTH, targetX));
            targetY = (int)((m * targetX) + b);
        } else {
            if (targetY > getY()) {
                targetY *= 5;
            } else if (targetY < getY()) {
                targetY -= (targetY * 5);
            }
        }
    }

    private int getCloseNumber(double multiAmount, int bound, int start) {
        double num = start;
        do {
            num *= multiAmount;
        } while (num < bound && !(num == 0));
        return (int)num;
    }

    @Override
    public void drawMe(Graphics g) {
        g.drawImage(getSelfImage(), getX(), getY(), null);
    }

    @Override
    public void update() {
        double deltaX = targetX - getX();
        double deltaY = targetY - getY();
        double direction = Math.atan2(deltaY, deltaX);

        int xTravelAmount = (int)((speed * Math.cos(direction)));
        int yTravelAmount = (int)((speed * Math.sin(direction)));

        updateDistanceTraveled(xTravelAmount, yTravelAmount);

        if (distanceTraveled > range && speed > 0) {
            owner.addToRemoveQueue(this);
        }

        setX(getX() + xTravelAmount);
        setY(getY() + yTravelAmount);
    }

    private void updateDistanceTraveled(int xAmount, int yAmount) {
        distanceTraveled += xAmount;
        distanceTraveled += yAmount;
    }

    public void allCollisions (List<GameObject> objectsToCheckWith) {
        for (GameObject obj: objectsToCheckWith) {
            if (obj.checkCollision(this)) {
                owner.onProjectileCollision(obj, this);
            }
        }
    }




}