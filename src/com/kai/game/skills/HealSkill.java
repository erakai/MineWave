package com.kai.game.skills;

import com.kai.game.core.Screen;
import com.kai.game.entities.Entity;
import com.kai.game.entities.Player;
import com.kai.game.util.ResourceManager;

public class HealSkill extends Skill {

    private final static int HEAL_AMOUNT = 1;

    //TODO: You can spam equip/unequip to heal to full right now... Fix?

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
            if (getOwner() instanceof Player) {
                Player p = (Player)getOwner();
                p.decreaseStat("max health", 1);
                p.setMaxHealth(p.getStats().get("max health"));
                if (p.getMaxHealth() < 1) {
                    Screen.playerDied("Suicide");
                }
            }
            getOwner().heal(HEAL_AMOUNT);
        } else {
            setLastUsed(-10000);
        }
    }
}
