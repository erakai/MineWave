package com.kai.game.scene;

import com.kai.game.GameObject;
import com.kai.game.entities.Entity;

import java.awt.*;

public abstract class SceneObject extends GameObject {

    public SceneObject(Image self, int x, int y, int width, int height) {
        super(self, x, y, width, height);
    }

    //When an entity comes into contact with this sceneObject.
    public abstract void onTouch(Entity e);
}
