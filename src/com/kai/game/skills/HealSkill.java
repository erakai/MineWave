package com.kai.game.skills;

import com.kai.game.entities.Entity;
import com.kai.game.entities.Player;
import com.kai.game.util.ResourceManager;

public class HealSkill extends Skill {

    private final static int HEAL_AMOUNT = 1;

    public HealSkill(Entity owner) {
        super("HealSkill", owner, ResourceManager.getImage("HealImage.png", Skill.SKILL_SIZE.getWidth(), Skill.SKILL_SIZE.getHeight()), 20,
                new String[] {
                        "Heal: 20 second cooldown.",
                        "Heals the user!"
                });
    }


    @Override
    public void _use(int tX, int tY) {
        if (getOwner().getHealth() != getOwner().getMaxHealth()) {
            getOwner().heal(HEAL_AMOUNT);
        } else {
            setLastUsed(-10000);
        }
    }
}
