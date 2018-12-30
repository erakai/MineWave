package com.kai.game.entities;

import com.kai.game.skills.Skill;

import java.util.ArrayList;
import java.util.List;

public interface UsesSkills {
    void equipSkill(Skill toEquip);
    boolean useSkill(String skillName, int targetX, int targetY);
    List<Skill> getSkills();
}
