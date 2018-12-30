package com.kai.game.scene;

import com.kai.game.GameObject;
import com.kai.game.master.ResourceManager;
import com.kai.game.master.Screen;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Environment extends GameObject {
    private List<SceneObject> sceneObjects;

    public Environment() {
        super(ResourceManager.getImage("background.png", Screen.WINDOW_WIDTH, Screen.WINDOW_HEIGHT), 0, 0, Screen.WINDOW_WIDTH, Screen.WINDOW_HEIGHT);
        sceneObjects = new ArrayList<>();
    }

    //TODO: Different backgrounds for various levels.

    @Override
    public void drawMe(Graphics g) {
        g.drawImage(getSelfImage(), getX(), getY(), null);

        for (GameObject o: sceneObjects) {
            o.drawMe(g);
        }
    }

    public List<SceneObject> sceneCollisions(GameObject obj) {
        List<SceneObject> trueCollide = new ArrayList<>();
        for (SceneObject o: sceneObjects) {
            if (obj.checkCollision(o)) {
                trueCollide.add(o);
            }
        }
        return trueCollide;
    }

}
