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
        return ((otherObject.getHardX() < getHardX()+getWidth()) &&
                (otherObject.getHardX()+otherObject.getWidth() > getHardX()) &&
                (otherObject.getHardY() < getHardY()+getHeight()) &&
                (otherObject.getHardY()+otherObject.getHeight() > getHardY()));
    }

    public void updateSelfImage() {
        if (getSelfImage() != null) {
            setSelf(ResourceManager.resizeImage(getSelfImage(), getWidth(), getHeight()));
        }
    }

    public int getCenterX() {
        return (getX() + getWidth()/2);
    }

    public int getCenterY() {
        return (getY() + getHeight()/2);
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
