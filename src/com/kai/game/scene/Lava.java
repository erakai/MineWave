package com.kai.game.scene;

import com.kai.game.entities.Entity;
import com.kai.game.util.Parameters;
import com.kai.game.util.ResourceManager;
import com.sun.javafx.tools.packager.Param;

import java.awt.*;

public class Lava extends SceneObject {
    private int damage;
    private double aps;
    private int damageTick, maxDamageTick;

    public Lava(int x, int y, int duration, int damage, int aps) {
        super("Lava", ResourceManager.getImage("lava.png"), x, y, 100, 100, duration);
        this.damage = damage;
        this.aps = aps;

        maxDamageTick = Parameters.FRAMES_PER_SECOND * aps;
        damageTick = maxDamageTick;
    }

    @Override
    public void onTouch(Entity e) {
        damageTick++;
        if (damageTick >= maxDamageTick) {
            e.takeDamage(damage);
            damageTick = 0;
        }
    }


}
