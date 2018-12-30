package com.kai.game.skills;

import com.kai.game.entities.Player;
import com.kai.game.master.ResourceManager;

public class ShieldSkill extends Skill {

    public ShieldSkill(Player owner) {
        super("ShieldSkill", owner, ResourceManager.getImage("ShieldImage.png", Skill.SKILL_WIDTH, Skill.SKILL_HEIGHT), 6,
                new String[] {
                        "Shield: 6 second cooldown.",
                        "Creates a protective barrier of mines around the player.",
                        "Not effective against enemies ontop of the player."
                });
    }

    @Override
    public void _use(int targetedX, int targetedY) {
        Player p = (Player)getOwner();
        int x = p.getX();
        int y = p.getY();

        p.removeAllMines();

        p.createProjectile(x+p.getScaledWidth(30), y+p.getScaledHeight(-10));
        p.createProjectile(x+p.getScaledWidth(-10), y+p.getScaledHeight(-10));
        p.createProjectile(x+p.getScaledWidth(30), y+p.getScaledHeight(30));
        p.createProjectile(x+p.getScaledWidth(-10), y+p.getScaledHeight(30));
        p.createProjectile(x+p.getScaledWidth(-10), y+p.getScaledHeight(70));
        p.createProjectile(x+p.getScaledWidth(30), y+p.getScaledHeight(70));

    }

}
