package com.kai.game.entities.enemies;

import com.kai.game.entities.Projectile;
import com.kai.game.entities.UsesSkills;
import com.kai.game.skills.Skill;
import com.kai.game.skills.TeleportSkill;
import com.kai.game.util.Parameters;
import com.kai.game.util.ResourceManager;

import java.awt.*;
import java.util.ArrayList;a
import java.util.List;

public class MagicBall extends ProjectileEnemy implements UsesSkills {
    List<Skill> skills;

    private static final double SECONDS_PER_TELEPORT = 3;

    public MagicBall(int x, int y) {
        super(ResourceManager.getImage("MagicBall.png"), x, y, 60, 60,
                1, 16, "Magic Ball", 1, 2, 60);

        skills = new ArrayList<>();
        TeleportSkill tp = new TeleportSkill(this);
        tp.setLastUsed(System.currentTimeMillis());
        tp.setCooldown((int)SECONDS_PER_TELEPORT);
        equipSkill(tp);

    }

    @Override
    public void createProjectile(int tX, int tY) {
        Image self = ResourceManager.getImage("MagicBallProj.png");
        int nx = getHardX()+getWidth()/2 - 8;
        int ny = getHardY()+getHeight()/2 - 8;

        projectiles.add(new Projectile(this, self
                , nx, ny, 16, 16, 3, getX()-500, getY()-500, getRange(), getDamage()));

        projectiles.add(new Projectile(this, self
                , nx, ny, 16, 16, 3, getX()-500,         getY(), getRange(), getDamage()));

        projectiles.add(new Projectile(this, self
                , nx, ny, 16, 16, 3, getX()-500, getY()+500, getRange(), getDamage()));

        projectiles.add(new Projectile(this, self
                ,nx, ny, 16, 16, 3, getX()+500, getY()+500, getRange(), getDamage()));

        projectiles.add(new Projectile(this, self
                ,nx, ny, 16, 16, 3, getX()+500,         getY(), getRange(), getDamage()));

        projectiles.add(new Projectile(this, self
                , nx, ny, 16, 16, 3, getX()+500, getY()-500, getRange(), getDamage()));

        projectiles.add(new Projectile(this, self
                , nx, ny, 16, 16, 3, getX(), getY()-500, getRange(), getDamage()));

        projectiles.add(new Projectile(this, self
                , nx, ny, 16, 16, 3,         getX(),     getY()+500, getRange(), getDamage()));

    }

    @Override
    public void equipSkill(Skill toEquip) {
        skills.add(toEquip);
    }

    @Override
    public boolean useSkill(String skillName, int targetX, int targetY) {
        //Unsure how to do this without copying and pasting code.
        for (Skill s: getSkills()) {
            if (s.getName().equals(skillName)) {
                return s.use(targetX, targetY);
            }
        }
        return false;
    }

    @Override
    public List<Skill> getSkills() {
        return skills;
    }

    @Override
    public void chase(int targetX, int targetY) {
        manageProjectileShooting(targetX, targetY);
        manageTeleporting(targetX, targetY);
    }

    private void manageTeleporting(int tX, int tY) {
        if (useSkill("TeleportSkill", tX, tY)) {
            setDamageTick(0);
        }

    }
}
