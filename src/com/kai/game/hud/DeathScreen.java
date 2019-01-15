package com.kai.game.hud;

import com.kai.game.core.GameObject;
import com.kai.game.util.ResourceManager;

import java.awt.*;

public class DeathScreen extends GameObject {

    public DeathScreen(String playerKilledBy) {
        super(ResourceManager.getImage("DeathScreen.png"), 0, 0, 1200, 600);
    }

    @Override
    public void drawMe(Graphics g) {
        g.drawImage(getSelfImage(), getX(), getY(), null);
    }
}
