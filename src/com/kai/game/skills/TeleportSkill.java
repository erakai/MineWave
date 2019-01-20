package com.kai.game.skills;

import com.kai.game.entities.Entity;
import com.kai.game.entities.Player;
import com.kai.game.util.ResourceManager;

public class TeleportSkill extends Skill {

    public TeleportSkill(Entity owner) {
        super("TeleportSkill", owner, ResourceManager.getImage("TeleportImage.png", Skill.SKILL_SIZE.getWidth(), Skill.SKILL_SIZE.getHeight()), 10,
                new String[] {
                        "Teleport: 10 second cooldown.",
                        "Teleports the player to the selected location.",
                        "Leaves a mine behind in the previous area."
                });
    }

    @Override
    public void _use(int targetedX, int targetedY) {

        if (getOwner() instanceof Player) {
            Player p = (Player)getOwner();
            if (p.getCurrentMines() >= p.getMaxMines()) {
                p.addToRemoveQueue(p.getProjectiles().get(0));
            }
            p.createProjectile(p.getX(), p.getY(), true);
        }

        getOwner().setX(targetedX- (getOwner().getWidth()/2));
        getOwner().setY(targetedY- (getOwner().getHeight()/2));
    }
}
