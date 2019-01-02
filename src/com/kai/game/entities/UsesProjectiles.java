package com.kai.game.entities;

import com.kai.game.GameObject;
import java.util.List;

public interface UsesProjectiles {

    //Adds a new projectile to the acting projectile list.
    void createProjectile(int tX, int tY);

    //What should happen when a projectile collides with a GameObject.
    void onProjectileCollision(GameObject collidedWith, Projectile p);

    //Adds a projectile to a remove queue. Effectively reads removeProjectile(p);
    void addToRemoveQueue(Projectile p);

    //Called every update tick.
    //Should call p.allCollisions(objectsToCheckWith) on every projectile.
    void callAllProjectileCollisions(List<GameObject> objectsToCheckWith);

    //Called every update tick.
    //Removes all projectiles in the queue from the acting projectile list.
    void removeAllInQueue();

}
