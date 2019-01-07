package com.kai.game.hud;

import com.kai.game.GameObject;
import com.kai.game.util.MFont;
import com.kai.game.util.Parameters;
import com.kai.game.util.ResourceManager;
import com.kai.game.core.Screen;

import java.awt.*;

public class MainMenu extends GameObject {

    public MainMenu() {
        super(ResourceManager.getImage("StartMenu.png", Screen.WINDOW_WIDTH, Screen.WINDOW_HEIGHT), 0, 0, Screen.WINDOW_WIDTH, Screen.WINDOW_HEIGHT);
    }

    //TODO: Update image for title screen, have options menu, have play button light up when moused over.

    @Override
    public void drawMe(Graphics g) {
        Parameters.ORIGINAL_FONT = g.getFont();
        g.drawImage(getSelfImage(), getX(), getY(), null);
        g.setFont(new MFont(8.4));
        g.setColor(new Color(90, 120, 242));
        g.drawString("MINE WAVE", (int)((double)280/1224 * getWidth()), (int)((double)312/768 * getHeight()));
        g.setFont(new MFont(4.8));
        g.setColor(new Color(136, 165, 215));
        g.drawString("click to play", (int)((double)428/1224 * getWidth()), (int)((double)600/768 * getHeight()));
    }


}
