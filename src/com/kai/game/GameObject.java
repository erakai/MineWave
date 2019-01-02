package com.kai.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    private Image self;
    private int x, y;
    public int width, height;

    public GameObject(Image self, int x, int y, int width, int height) {
        this.self = self;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void drawMe(Graphics g);

    public boolean checkCollision(GameObject otherObject) {
        return ((otherObject.getX() < getX()+width) &&
                (otherObject.getX()+otherObject.width > getX()) &&
                (otherObject.getY() < getY()+height) &&
                (otherObject.getY()+otherObject.height > getY()));
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getSelfImage() {
        return self;
    }

    public void setSelf(Image self) {
        this.self = self;
    }
}
