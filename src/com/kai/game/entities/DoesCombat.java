package com.kai.game.entities;

public interface DoesCombat {
    void takeDamage(int amount);
    void heal(double amount);
    void attack(Entity target);
    void attack(Entity target, int ovrDamage);

}
