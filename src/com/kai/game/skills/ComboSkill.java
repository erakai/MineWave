package com.kai.game.skills;

import com.kai.game.entities.Player;
import com.kai.game.util.ResourceManager;

public class ComboSkill extends Skill {

    private final int placementVariance = 8;

    public ComboSkill(Player owner) {
        super("ComboSkill", owner, ResourceManager.getImage("ComboImage.png", Skill.SKILL_SIZE.getWidth(), Skill.SKILL_SIZE.getHeight()), 8,
                new String[] {
                        "Combo: 8 second cooldown.",
                        "Places all mines at the selected location."
                });
    }

    @Override
    public void _use(int tX, int tY) {
        Player p = (Player)getOwner();

        p.removeAllMines();

        for (int i = 0 ; i < p.getMaxMines(); i++) {
            p.createProjectile(tX + (int)(Math.random() * placementVariance), tY + (int)(Math.random() * placementVariance), true);
        }

    }

}
