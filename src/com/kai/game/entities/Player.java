package com.kai.game.entities;

import com.kai.game.core.GameObject;
import com.kai.game.entities.enemies.Enemy;
import com.kai.game.hud.SelectionScreen;
import com.kai.game.util.ResourceManager;
import com.kai.game.core.Screen;
import com.kai.game.skills.Skill;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player extends Entity implements UsesProjectiles, UsesSkills {
    private String dir;

    private int maxMines, currentMines;
    public final int MINE_WIDTH = (int)(25.0/1200 * Screen.WINDOW_WIDTH);
    public final int MINE_HEIGHT = (int)(25.0/600 * Screen.WINDOW_HEIGHT);

    private boolean moveRight, moveLeft, moveUp, moveDown;

    private int playerDamage;

    private List<Projectile> projectiles;
    private List<Projectile> removeProjectileQueue;

    private List<Skill> skills;

    public Player(int x, int y, int width, int height) {
        super(null, x, y, width, height, 4, 20);
        this.dir = "up";
        maxMines = 7;
        currentMines = 0;
        playerDamage = 5;

        skills = new ArrayList<>();

        equipSkill(SelectionScreen.getCurrentlySelected(this));

        projectiles = new ArrayList<>();
        removeProjectileQueue = new ArrayList<>();
    }


    @Override
    public void attack(Entity target) {
        target.takeDamage(getPlayerDamage());
    }

    public void attack(Entity target, int ovrDamage) {target.takeDamage(ovrDamage );}

    public void createProjectile(int targetX, int targetY) {
        if (currentMines < maxMines) {
            currentMines++;
            projectiles.add(new Projectile(this, ResourceManager.getImage("Mine.png", MINE_WIDTH, MINE_HEIGHT),
                    (int)(targetX-(MINE_WIDTH /2.0)), (targetY-(MINE_HEIGHT /2)), MINE_WIDTH, MINE_HEIGHT, 0, targetX, targetY, 0, getPlayerDamage()));
        }
    }

    public void createProjectile(Projectile p) {
        if (currentMines < maxMines) {
            currentMines++;
            projectiles.add(p);
        }
    }

    public void onProjectileCollision(GameObject collidedWith, Projectile p) {
        if (collidedWith instanceof Enemy) {
            this.attack((Entity)collidedWith, p.getDamage());
        }
        addToRemoveQueue(p);
    }

    @Override
    public void callAllProjectileCollisions(List<GameObject> objectsToCheckWith) {
        for (Projectile p: projectiles) {
            p.allCollisions(objectsToCheckWith);
        }
    }

    public void addToRemoveQueue(Projectile p) {
        if (!removeProjectileQueue.contains(p)) {
            removeProjectileQueue.add(p);
            currentMines--;
        }
    }

    public void removeAllMines() {
        for (Projectile p: projectiles) {
            addToRemoveQueue(p);
        }
    }

    public void update() {
        for (Projectile p: projectiles) {
            p.update();
        }

        if (moveDown) {
            moveDown();
            dir = "down";
        }
        if (moveLeft) {
            moveLeft();
            dir = "left";
        }
        if (moveRight) {
            moveRight();
            dir = "right";
        }
        if (moveUp) {
            moveUp();
            dir = "up";
        }

        removeAllInQueue();
    }

    @Override
    public void removeAllInQueue() {
        projectiles.removeAll(removeProjectileQueue);
        removeProjectileQueue.clear();
    }

    public void equipSkill(Skill toEquip) {
        skills.add(toEquip);
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public boolean useSkill(String skillName, int targetX, int targetY) {
        for (Skill s: skills) {
            if (s.getName().equals(skillName)) {
                s.use(targetX, targetY);
                return true;
            }
        }
        return false;
    }

    public void useStarterSkill(int targetX, int targetY) {
        useSkill(getSkills().get(0).getName(), targetX, targetY);
    }

    //TODO: Use an image for player.
    public void drawMe(Graphics g) {
        for (Projectile p: projectiles) {
            p.drawMe(g);
        }

        g.setColor(Color.black);
        g.fillRect(getX(), getY()+ getScaledHeight(20), getScaledWidth(20), getScaledHeight(40));
        g.setColor(Color.gray);
        g.fillOval(getX(), getY(), getScaledWidth(20), getScaledHeight(20));
        switch(dir){
            case "up": case"down":
                g.fillRect(getX()-getScaledWidth(6), getY()+ getScaledHeight(28), getScaledWidth(6), getScaledHeight(24));
                g.fillRect(getX()+ getScaledHeight(20), getY()+getScaledHeight(28),  getScaledWidth(6), getScaledHeight(24));
                break;
            case "right":
                g.fillRect(getX()+ getScaledWidth(13), getY()+ getScaledHeight(28),  getScaledWidth(6), getScaledHeight(24));
                break;
            case "left":
                g.fillRect(getX()+ getScaledWidth(1), getY()+ getScaledHeight(28),  getScaledWidth(6), getScaledHeight(24));
                break;
        }
    }

    public int getScaledWidth(int oldWidth) {
        return (int)(oldWidth/22.0 * getWidth());
    }

    public int getScaledHeight(int oldHeight) {
        return (int)(oldHeight/60.0 * getHeight());
    }

    public void movementKeyPressed(int keycode) {
        switch(keycode) {
            case 65:
                moveLeft = true;
                break;
            case 87:
                moveUp = true;
                break;
            case 68:
                moveRight = true;
                break;
            case 83:
                moveDown = true;
                break;
        }
    }

    public int getPlayerDamage() {
        return playerDamage;
    }

    public int getCurrentMines() {
        return currentMines;
    }

    public int getMaxMines() {
        return maxMines;
    }


    public void movementKeyReleased(int keycode) {
        switch(keycode) {
            case 65:
                moveLeft = false;
                break;
            case 87:
                moveUp = false;
                break;
            case 68:
                moveRight = false;
                break;
            case 83:
                moveDown = false;
                break;
        }


    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }
}
