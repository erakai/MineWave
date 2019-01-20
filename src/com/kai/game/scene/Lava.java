package com.kai.game.scene;

import com.kai.game.entities.Entity;
import com.kai.game.entities.Player;
import com.kai.game.util.Parameters;
import com.kai.game.util.ResourceManager;

import java.awt.*;

public class Lava extends SceneObject {
    private int damage;
    private double aps;
    private boolean playerOnly;
    private int damageTick, maxDamageTick;

    public Lava(int x, int y, int duration, int damage, double aps, boolean playerOnly) {
        super("Lava", ResourceManager.getImage("lava.png", 75, 62), x, y, 76, 62, duration);
        this.damage = damage;
        this.aps = aps;
        this.playerOnly = playerOnly;

        maxDamageTick = (int)( Parameters.FRAMES_PER_SECOND * this.aps );
        damageTick = maxDamageTick;
    }

    @Override
    public void onTouch(Entity e) {
            if (damageTick < maxDamageTick) {
                damageTick++;
            }
            if (damageTick >= maxDamageTick) {
                if ( !playerOnly || e instanceof Player) {
                    damage(e, damage);
                }
                damageTick = 0;
            }
    }


}
