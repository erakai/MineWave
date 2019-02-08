package com.kai.game.scene;

import com.kai.game.core.GameObject;
import com.kai.game.core.Screen;
import com.kai.game.core.Updatable;
import com.kai.game.entities.Entity;
import com.kai.game.entities.Player;
import com.kai.game.util.MTimer;

import java.awt.*;

public abstract class SceneObject extends GameObject implements Updatable {
    protected MTimer timer;
    private boolean markedForRemoval = false;
    private int duration;
    private String name;

    public SceneObject(String name, Image self, int x, int y, int width, int height, int duration) {
        super(self, x, y, width, height);
        this.name = name;
        this.duration = duration;
        timer = new MTimer();
    }

    @Override
    public void update() {
        if (timer.getSecondsSinceStart() >= duration) {
            setMarkedForRemoval(true);
        }
    }

    @Override
    public void drawMe(Graphics g) {
        g.drawImage(getSelfImage(), getX(), getY(), null);
    }

    //When an entity comes into contact with this sceneObject.
    public abstract void onTouch(Entity e);

    public boolean isMarkedForRemoval() {
        return markedForRemoval;
    }

    public void setMarkedForRemoval(boolean markedForRemoval) {
        this.markedForRemoval = markedForRemoval;
    }

    public void damage(Entity e, int amount) {
        e.takeDamage(amount);
        if (e instanceof Player && e.getHealth() < 1) {
            Screen.playerDied(name);
        }
    }
 }
