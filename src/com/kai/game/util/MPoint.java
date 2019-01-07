package com.kai.game.util;

import com.kai.game.core.Screen;

public class MPoint {

    //TODO: Use scaling points rather then straight ints for HUD values.

    private int x, y;

    public MPoint() {
        this(0, 0);
    }

    public MPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return ((int)((double)x/1200.0 * Screen.WINDOW_WIDTH));
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return ((int)((double)y/600.0 * Screen.WINDOW_HEIGHT));
    }

    public void setY(int y) {
        this.y = y;
    }
}
