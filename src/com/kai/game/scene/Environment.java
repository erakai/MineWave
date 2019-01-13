package com.kai.game.scene;

import com.kai.game.core.GameObject;
import com.kai.game.util.ResourceManager;
import com.kai.game.core.Screen;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Environment extends GameObject {
    private List<SceneObject> sceneObjects;

    //shouldn't do something like this, not maintainable
    private boolean transitioned = false;

    public Environment() {
        super(ResourceManager.getImage("background.png", Screen.WINDOW_WIDTH, Screen.WINDOW_HEIGHT), 0, 0, 1200, 600);
        sceneObjects = new ArrayList<>();
    }

    //TODO: Different backgrounds for various levels.

    @Override
    public void drawMe(Graphics g) {
        g.drawImage(getSelfImage(), getX(), getY(), null);

        for (GameObject o: sceneObjects) {
            o.drawMe(g);
        }


        if (Screen.getLevelHandler().getDisplayedLevel() > 8 && !transitioned) {
            setSelf(ResourceManager.getImage("background2.png",Screen.WINDOW_WIDTH, Screen.WINDOW_HEIGHT ));
            transitioned = true;
        }
    }

    @Override
    public void updateSelfImage() {
        super.updateSelfImage();
        for (SceneObject so: sceneObjects) {
            so.updateSelfImage();
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
