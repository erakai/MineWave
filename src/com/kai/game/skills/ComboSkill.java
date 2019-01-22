package com.kai.game.skills;

import com.kai.game.entities.Player;
import com.kai.game.entities.Projectile;
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

        if (p.SHOOT) {
            Projectile proj = new Projectile(p, ResourceManager.getImage("Mine.png", p.MINE_SIZE.getWidth(), p.MINE_SIZE.getHeight()),
                    p.getX() + p.getWidth() / 2 - 12, p.getY() + p.getHeight() / 2 - 12, p.MINE_SIZE.getHardWidth(), p.MINE_SIZE.getHardHeight(), 8, tX, tY, 1200, p.getPlayerDamage() * p.getMaxMines());
            p.createProjectile(proj);
        } else {
            for (int i = 0; i < p.getMaxMines(); i++) {
                p.createProjectile(tX + (int) (Math.random() * placementVariance), tY + (int) (Math.random() * placementVariance), true);
            }
        }
    }

}
