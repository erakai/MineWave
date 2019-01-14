package com.kai.game.skills;

import com.kai.game.entities.Entity;
import com.kai.game.entities.Player;
import com.kai.game.util.MTimer;
import com.kai.game.util.Parameters;
import com.kai.game.util.ResourceManager;
import org.omg.Dynamic.Parameter;

import java.awt.*;

public class GunSkill extends Skill {

    public int currentShotTick, maxShotTick;
    private static final double SECONDS_PER_SHOT = 0.25;

    public GunSkill(Player owner) {
        super("GunSkill", owner, ResourceManager.getImage("ShootingAbilityImage.png", Skill.SKILL_SIZE.getWidth(), Skill.SKILL_SIZE.getHeight()), true,
                new String[] {
                        "Gun: Passive Ability",
                        "Instead of placing mines, you instead fire them as projectiles.",
                        "Your damage is set to 2/3rds of what it once was."
                });
        if (owner != null) {
            owner.SHOOT = true;
            owner.setPlayerDamage((int)(owner.getPlayerDamage()/3.0 * 2));
        }

        maxShotTick = (int)(SECONDS_PER_SHOT * Parameters.FRAMES_PER_SECOND);
        currentShotTick = maxShotTick;
    }

    @Override
    public void _use(int tX, int tY) {
        //Nothing, it's a passive.
    }
}
