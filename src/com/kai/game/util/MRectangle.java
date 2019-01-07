package com.kai.game.util;

import com.kai.game.core.Screen;

public class MRectangle {

    private int width, height;
    private MPoint topLeft, bottomRight;

    public MRectangle(int width, int height, MPoint topLeft, MPoint bottomRight) {
        this.width = width;
        this.height = height;
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public MRectangle(int width, int height) {
        this(width, height, null, null);
    }

    public int getWidth() {
        return ((int)((double)width/1200.0 * Screen.WINDOW_WIDTH));
    }

    public int getHeight() {
        return ((int)((double)height/600.0 * Screen.WINDOW_HEIGHT));
    }

    public MPoint getTopLeft() {
        return topLeft;
    }

    public MPoint getBottomRight() {
        return bottomRight;
    }

}
