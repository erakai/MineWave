package com.kai.game.entities;

public interface DoesCombat {
    void takeDamage(double amount);
    void heal(double amount);
    void attack(Entity target);
    void attack(Entity target, int ovrDamage);

}
