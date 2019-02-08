package com.kai.game.skills;

import com.kai.game.entities.Player;
import com.kai.game.entities.Projectile;
import com.kai.game.util.ResourceManager;


public class GreatMineSkill extends Skill {

    private final int sizeMultiplier = 10;
    private final int damageMultiplier = 2;

    public GreatMineSkill(Player owner) {
        super("GreatMineSkill", owner, ResourceManager.getImage("GreatMineSkill.png", Skill.SKILL_SIZE.getWidth(), Skill.SKILL_SIZE.getHeight()), 15,
                new String[] {
                        "Great Mine: 15 second cooldown.",
                        "Creates a large mine at the selected location.",
                        "The mine does 2x damage to enemies."
                });
    }

    @Override
    public void _use(int tX, int tY) {
        Player p = (Player)getOwner();

        if (p.getCurrentMines() >= p.getMaxMines()) {
            p.addToRemoveQueue(p.getProjectiles().get(0));
        }

        int bigProjSpeed = 0;
        int bigProjRange = 0;
        int bigProjX = (int)(tX-(p.MINE_SIZE.getWidth() * sizeMultiplier /2.0));
        int bigProjY =  (tY-(p.MINE_SIZE.getHeight() * sizeMultiplier /2));
        if (p.isShoot()) {
            bigProjX = p.getCenterX() - (p.MINE_SIZE.getWidth() * sizeMultiplier / 2);
            bigProjY = p.getCenterY() - (p.MINE_SIZE.getHeight() * sizeMultiplier / 2);
            bigProjRange = 1200;
            bigProjSpeed = 8;
        }


        p.createProjectile(new Projectile(p, ResourceManager.getImage("Mine.png", p.MINE_SIZE.getWidth() * sizeMultiplier, p.MINE_SIZE.getHeight() * sizeMultiplier),
               bigProjX, bigProjY ,
                p.MINE_SIZE.getWidth() * sizeMultiplier, p.MINE_SIZE.getHeight() * sizeMultiplier, bigProjSpeed, tX, tY,bigProjRange, p.getPlayerDamage() * damageMultiplier));
    }
}
