package com.kai.game.entities;

import com.kai.game.GameObject;
import com.kai.game.Updatable;

import java.awt.*;

public abstract class Entity extends GameObject implements Updatable {
    private int speed;

    public Entity(Image self, int x, int y, int width, int height, int speed) {
        super(self, x, y, width, height);
        this.speed = speed;
    }

    public void moveUp() {
        setY(getY()-speed);
    }

    public void moveDown() {
        setY(getY()+speed);
    }

    public void moveRight() { setX(getX()+speed); }

    public void moveLeft() {
        setX(getX()-speed);
    }

}
