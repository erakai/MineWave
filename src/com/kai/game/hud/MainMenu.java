package com.kai.game.hud;

import com.kai.game.core.GameObject;
import com.kai.game.util.*;
import com.kai.game.core.Screen;

import java.awt.*;

public class MainMenu extends GameObject {

    private MPoint titleText = new MPoint(270, 240);
    private MPoint clickToPlay = new MPoint(425, 470);
    private MRectangle playButton = new MRectangle(new MPoint(405, 404), new MPoint(795, 496));

    private boolean playHover = false;

    public MainMenu() {
        super(ResourceManager.getImage("StartMenu.png", Screen.WINDOW_WIDTH, Screen.WINDOW_HEIGHT), 0, 0, 1200, 600);
    }

    //TODO: Update image for title screen, have options menu.



    public boolean checkHover(int mouseX, int mouseY) {
        playHover = (mouseX > playButton.getTopLeft().getX() && mouseX < playButton.getBottomRight().getX() &&
                mouseY > playButton.getTopLeft().getY() && mouseY < playButton.getBottomRight().getY());
        return playHover;
    }


    @Override
    public void drawMe(Graphics g) {
        Parameters.ORIGINAL_FONT = g.getFont();
        g.drawImage(getSelfImage(), getX(), getY(), null);
        g.setFont(new MFont(8.4));
        g.setColor(new Color(41, 29, 184));
        g.drawString("MINE WAVE", titleText.getX(), titleText.getY());
        g.setFont(new MFont(4.8));
        if (playHover) {
            g.setColor(new Color(43, 64, 121));
            g.fillRect(playButton.getTopLeft().getX(), playButton.getTopLeft().getY(), playButton.getWidth(), playButton.getHeight());
        }
        g.setColor(new Color(56, 39, 255));
        g.drawString("click to play", clickToPlay.getX(), clickToPlay.getY());
    }


}
