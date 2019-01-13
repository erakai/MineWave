package com.kai.game.hud;

import com.kai.game.core.GameObject;
import com.kai.game.util.MFont;
import com.kai.game.util.MPoint;
import com.kai.game.util.Parameters;
import com.kai.game.util.ResourceManager;
import com.kai.game.core.Screen;

import java.awt.*;

public class MainMenu extends GameObject {

    private MPoint titleText = new MPoint(270, 240);
    private MPoint clickToPlay = new MPoint(425, 470);

    public MainMenu() {
        super(ResourceManager.getImage("StartMenu.png", Screen.WINDOW_WIDTH, Screen.WINDOW_HEIGHT), 0, 0, 1200, 600);
    }

    //TODO: Update image for title screen, have options menu, have play button light up when moused over.

    @Override
    public void drawMe(Graphics g) {
        Parameters.ORIGINAL_FONT = g.getFont();
        g.drawImage(getSelfImage(), getX(), getY(), null);
        g.setFont(new MFont(8.4));
        g.setColor(new Color(90, 120, 242));
        g.drawString("MINE WAVE", titleText.getX(), titleText.getY());
        g.setFont(new MFont(4.8));
        g.setColor(new Color(136, 165, 215));
        g.drawString("click to play", clickToPlay.getX(), clickToPlay.getY());
    }


}
