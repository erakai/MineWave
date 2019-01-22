package com.kai.game.entities;

import com.kai.game.core.GameObject;
import com.kai.game.entities.enemies.Enemy;
import com.kai.game.hud.SelectionScreen;
import com.kai.game.skills.*;
import com.kai.game.util.MRectangle;
import com.kai.game.util.ResourceManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Player extends Entity implements UsesProjectiles, UsesSkills {

    private StatManager playerStats;

    private String dir;
    private int currentMines;
    public final MRectangle MINE_SIZE = new MRectangle(25, 25);
    private boolean moveRight, moveLeft, moveUp, moveDown;
    private List<Projectile> projectiles;
    private List<Projectile> removeProjectileQueue;
    private List<Skill> skills;
    public boolean SHOOT = false;
    private String killedBy;

    public Player(int x, int y, int width, int height) {
        super(null, x, y, width, height, 4, 20);
        this.dir = "up";
        currentMines = 0;

        playerStats = new StatManager(this, 20, 5, 5, 4, 13, 7);

        skills = new ArrayList<>();

        equipSkill(SelectionScreen.getCurrentlySelected(this));

        projectiles = new ArrayList<>();
        removeProjectileQueue = new ArrayList<>();
    }

    public void createProjectile(int targetX, int targetY) {
        if (!SHOOT) {
            if (distanceTo(targetX, targetY) <= (getPlayerRange())) {
                createProjectile(targetX, targetY, true);
            }
        } else {
            createProjectile(targetX, targetY, true);
        }
    }

    public void createProjectile(int targetX, int targetY, boolean ignoreRange) {
        if (ignoreRange) {
            if (currentMines < getMaxMines()) {
                currentMines++;
                if (!SHOOT) {
                    projectiles.add(new Projectile(this, ResourceManager.getImage("Mine.png", MINE_SIZE.getWidth(), MINE_SIZE.getHeight()),
                            (int) (targetX - (MINE_SIZE.getHardWidth() / 2.0)), (targetY - (MINE_SIZE.getHardHeight() / 2)), MINE_SIZE.getHardWidth(), MINE_SIZE.getHardHeight(), 0, targetX, targetY, 0, getPlayerDamage()));
                } else {
                    GunSkill gs = (GunSkill) getSkills().get(0);
                    if (gs.currentShotTick > gs.maxShotTick) {
                        gs.currentShotTick = 0;
                        //Shooting has a farther range than placing mines
                        Projectile p = new Projectile(this, ResourceManager.getImage("Mine.png", MINE_SIZE.getWidth(), MINE_SIZE.getHeight()),
                                getX() + getWidth() / 2 - 12, getY() + getHeight() / 2 - 12, MINE_SIZE.getHardWidth(), MINE_SIZE.getHardHeight(), 8, targetX, targetY, 1200, getPlayerDamage());
                        p.updateTarget();
                        projectiles.add(p);
                    } else {
                        currentMines--;
                    }
                }
            }
        } else {
            createProjectile(targetX, targetY);
        }
    }

    public void createProjectile(Projectile p) {
        if (currentMines < getMaxMines()) {
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

    @Override
    public void removeAllInQueue() {
        projectiles.removeAll(removeProjectileQueue);
        removeProjectileQueue.clear();
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
        //Drawing range circle (unless using gunSkill)
        if (!(getSkills().get(0) instanceof GunSkill)) {
            g.setColor(new Color(83, 90, 106));
            g.drawOval( (getCenterX() - getPlayerRange()), (getCenterY() - getPlayerRange()), getPlayerRange()*2, getPlayerRange()*2);
        }
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


        if (SHOOT) {
            ((GunSkill)getSkills().get(0)).currentShotTick++;
        }
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

    @Override
    public void updateSelfImage() {
        super.updateSelfImage();
        for (Projectile p: projectiles) {
            p.updateSelfImage();
        }
    }

    @Override
    public void attack(Entity target) {
        attack(target, getPlayerDamage());
    }

    public void attack(Entity target, int ovrDamage) {target.takeDamage(ovrDamage );}

    public int getScaledWidth(int oldWidth) {
        return (int)(oldWidth/22.0 * getWidth());
    }

    public int getScaledHeight(int oldHeight) {
        return (int)(oldHeight/60.0 * getHeight());
    }

    public int getPlayerDamage() {
        return playerStats.getStat("damage");
    }

    public int getCurrentMines() {
        return currentMines;
    }

    public int getMaxMines() {
        return playerStats.getStat("max mines");
    }

    public String getKilledBy() {
        return killedBy;
    }

    public void setKilledBy(String killedBy) {
        this.killedBy = killedBy;
    }

    public List<Projectile> getProjectiles() { return projectiles; }

    public int getPlayerRange() { return (getSmallPlayerRange() * 25); }

    public int getSmallPlayerRange() { return (playerStats.getStat("range"));}

    public int getSpeed() { return playerStats.getStat("speed"); }

    public int getMaxHealth() { return playerStats.getStat("max health"); }

    public HashMap<String, Integer> getStats() { return playerStats.getStats(); }

    public int getDefense() { return playerStats.getStat("defense"); }

    public void takeDamage(double amount) {
        //  - Damage taken = enemy damage * ((100 - player defense) / 100.0)
        super.takeDamage(amount * ((100-getDefense())/ 100.0));
    }
}
