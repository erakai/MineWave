package com.kai.game.skills;

import com.kai.game.entities.Entity;
import com.kai.game.master.ResourceManager;

public class TeleportSkill extends Skill {

    public TeleportSkill(Entity owner) {
        super("TeleportSkill", owner, ResourceManager.getImage("TeleportImage.png", Skill.SKILL_WIDTH, Skill.SKILL_HEIGHT), 10,
                new String[] {
                        "Teleport: 10 second cooldown.",
                        "Teleports the player to the mouse, leaving a trail of mines behind them."
                });
    }

    @Override
    public void _use(int targetedX, int targetedY) {
        getOwner().setX(targetedX- (getOwner().width/2));
        getOwner().setY(targetedY- (getOwner().height/2));
    }
}
