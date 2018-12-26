package com.kai.game.scene;

import com.kai.game.GameObject;
import com.kai.game.master.Draw;
import com.kai.game.master.Screen;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Environment extends GameObject {
    private List<GameObject> sceneObjects;

    public Environment() {
        super(Draw.getImage("background.png", Screen.WINDOW_WIDTH, Screen.WINDOW_HEIGHT), 0, 0, Screen.WINDOW_WIDTH, Screen.WINDOW_HEIGHT);
        sceneObjects = new ArrayList<>();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(getSelfImage(), getX(), getY(), null);

        for (GameObject o: sceneObjects) {
            o.paintComponent(g);
        }
    }

    public List<GameObject> sceneCollisions(GameObject obj) {
        List<GameObject> trueCollide = new ArrayList<>();
        for (GameObject o: sceneObjects) {
            if (obj.checkCollision(o)) {
                trueCollide.add(o);
            }
        }
        return trueCollide;
    }

}
