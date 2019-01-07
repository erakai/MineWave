package com.kai.game.util;

import com.kai.game.core.Screen;

import java.awt.*;

public class MFont extends Font {

    public MFont(String name, int style, double sizeMultiplier) {
        super(name, style, (int)(Parameters.ORIGINAL_FONT.getSize()*(Screen.WINDOW_WIDTH/(1200.0/sizeMultiplier))));
    }

    public MFont(double sizeMultiplier) {
        this(Parameters.ORIGINAL_FONT.getName(), Parameters.ORIGINAL_FONT.getStyle(), sizeMultiplier);
    }
}
