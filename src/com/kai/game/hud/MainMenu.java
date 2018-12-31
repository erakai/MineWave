package com.kai.game.hud;

import com.kai.game.GameObject;
import com.kai.game.master.ResourceManager;
import com.kai.game.master.Screen;

import java.awt.*;

public class MainMenu extends GameObject {

    public static Font ogfont;

    public MainMenu() {
        super(ResourceManager.getImage("StartMenu.png", Screen.WINDOW_WIDTH, Screen.WINDOW_HEIGHT), 0, 0, Screen.WINDOW_WIDTH, Screen.WINDOW_HEIGHT);
    }

    //TODO: Update image for title screen, have options menu, have play button light up when moused over.

    @Override
    public void drawMe(Graphics g) {
        ogfont = g.getFont();
        g.drawImage(getSelfImage(), getX(), getY(), null);
        g.setFont(new Font(g.getFont().getFontName(), g.getFont().getStyle(), (ogfont.getSize()*((int)(width/142.85)))));
        g.setColor(new Color(90, 120, 242));
        g.drawString("MINE WAVE", (int)((double)280/1224 * width), (int)((double)312/768 * height));
        g.setFont(new Font(g.getFont().getFontName(), g.getFont().getStyle(), ((int)(ogfont.getSize()*((double)width/250)))));
        g.setColor(new Color(136, 165, 215));
        g.drawString("click to play", (int)((double)428/1224 * width), (int)((double)600/768 * height));
    }


}
