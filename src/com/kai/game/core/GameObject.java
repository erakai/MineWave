package com.kai.game.core;

import com.kai.game.util.MPoint;
import com.kai.game.util.MRectangle;
import com.kai.game.util.ResourceManager;

import java.awt.*;

public abstract class GameObject {
    private Image self;

    private MPoint location;

    private int x, y;

    public MRectangle dimensions;

    public GameObject(Image self, int x, int y, int width, int height) {
        this.self = self;
        this.location = new MPoint(x, y);
        dimensions = new MRectangle(width, height);
    }

    public abstract void drawMe(Graphics g);

    public boolean checkCollision(GameObject otherObject) {
        return ((otherObject.getX() < getX()+getWidth()) &&
                (otherObject.getX()+otherObject.getWidth() > getX()) &&
                (otherObject.getY() < getY()+getHeight()) &&
                (otherObject.getY()+otherObject.getHeight() > getY()));
    }

    public void updateSelfImage() {
        if (getSelfImage() != null) {
            setSelf(ResourceManager.resizeImage(getSelfImage(), getWidth(), getHeight()));
        }
    }

    public int getWidth() {
        return dimensions.getWidth();
    }

    public int getHeight() {
        return dimensions.getHeight();
    }

    public int getX() {
        return location.getX();
    }

    public void setX(int x) {
        location.setX(x);
    }

    public int getY() {
        return location.getY();
    }

    public int getHardX() {
        return location.getHardX();
    }

    public int getHardY() {
        return location.getHardY();
    }

    public void setY(int y) {
        location.setY(y);
    }

    public Image getSelfImage() {
        return self;
    }

    public void setSelf(Image self) {
        this.self = self;
    }
}
