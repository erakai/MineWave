package com.kai.game.entities;

import com.kai.game.GameObject;

import java.util.ArrayList;
import java.util.List;

public interface UsesProjectiles {



    void createProjectile(int tX, int tY);
    void onProjectileCollision(GameObject collidedWith, Projectile p);
    void addToRemoveQueue(Projectile p);
    void callAllProjectileCollisions(List<GameObject> objectsToCheckWith);
    void removeAllInQueue();
}
