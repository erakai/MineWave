package com.kai.game.hud;

import com.kai.game.core.GameObject;
import com.kai.game.skills.Skill;
import com.kai.game.util.MFont;
import com.kai.game.util.MPoint;
import com.kai.game.util.ResourceManager;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.awt.*;

public class DeathScreen extends GameObject {
    //Most recent death that spawned this DeathScreen
    private Death recentPlayerDeath;

    /*
    On program start, socket created in Screen or wherever, creates a boolean saying whether connection successful or not.
    Boolean passed to this class.
    When transitioning to death state:
        1. if boolean = true, create dialogue asking user for name, send new death to server
            Server sends back full leaderBoard, passed to deathScreen.
        2. if boolean = false, create dialogue saying they didn't make it because they are garbage
    DeathScreen displays either received leaderBoard or error.
    Have a replay button.
     */

    private List<Death> leaderboard;

    //Signifies whether or not this client is connected to the server.
    private boolean connected = true;

    //Signifies whether or not this player made it on the leaderboards;
    private boolean onLeaderboards;

    //The Skill the player was using (to draw)
    private Skill recentPlayerSkill;

    public DeathScreen() {
        super(ResourceManager.getImage("DeathScreen.png"), 0, 0, 1200, 600);
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public void setLeaderboard(List<Death> leaderboard) {
        this.leaderboard = leaderboard;
        if (leaderboard != null) {
            Collections.sort(leaderboard);
        }
    }


    Font medium = new MFont(1.4);
    Font small = new MFont(1.1);
    Font large = new MFont(4);

    MPoint firstRecentInfo = new MPoint(35, 52);
    MPoint secondRecentInfo = new MPoint(35, 82);
    MPoint thirdRecentInfo = new MPoint(35, 112);
    MPoint recentImageInfo = new MPoint(250, 40);

    MPoint leaderboardText = new MPoint(790, 60);
    MPoint name = new MPoint(696, 90);
    MPoint killedBy = new MPoint(800, 90);
    MPoint ability = new MPoint(950, 90);
    MPoint score = new MPoint(1100, 90);

    MPoint ldbText = new MPoint(0, 125);
    MPoint ldbDecrement = new MPoint(0, 50);

    MPoint error = new MPoint(750, 300);

    public void drawMe(Graphics g) {
        g.drawImage(getSelfImage(), getX(), getY(), null);

        //Draw info of recent player death
        g.setFont(medium);
        g.setColor(Color.WHITE);
        g.drawString("You died!", firstRecentInfo.getX(), firstRecentInfo.getY());
        g.drawString("Score: " + recentPlayerDeath.getLevel(), secondRecentInfo.getX(), secondRecentInfo.getY());
        g.drawString("Killed By: " + recentPlayerDeath.getKilledBy(), thirdRecentInfo.getX(), thirdRecentInfo.getY());
        g.drawImage(recentPlayerSkill.getSelfImage(), recentImageInfo.getX(), recentImageInfo.getY(), null);


        //Drawing leaderboard
        g.setFont(large);
        g.setColor(Color.ORANGE);
        g.drawString("Leaderboard", leaderboardText.getX(), leaderboardText.getY());

        g.setFont(medium);
        g.drawString("Name", name.getX(), name.getY());
        g.drawString("Killed By", killedBy.getX(), killedBy.getY());
        g.drawString("Ability", ability.getX(), ability.getY());
        g.drawString("Score", score.getX(), score.getY());

        g.setColor(new Color(42, 112, 224));
        if (connected) {
            for (int i = 0; i<leaderboard.size(); i++) {
                Death death = leaderboard.get(i);
                g.drawString(death.getName(), name.getX(), ldbText.getY() + (i * ldbDecrement.getY()));
                g.drawString(death.getKilledBy(), killedBy.getX(), ldbText.getY() + (i * ldbDecrement.getY()));
                g.drawString(death.getAbility(), ability.getX(), ldbText.getY() + (i * ldbDecrement.getY()));
                g.drawString(String.valueOf(death.getLevel()), score.getX(), ldbText.getY() + (i * ldbDecrement.getY()));
            }
        } else {
            g.setColor(new Color(250, 25, 67));
            g.setFont(medium);
            g.drawString("Could not connect to server :(", error.getX(), error.getY());
        }

        //TODO: Draw Replay Button and have it work
    }

    public void setRecentPlayerSkill(Skill recentPlayerSkill) {
        this.recentPlayerSkill = recentPlayerSkill;
    }

    public void setRecentPlayerDeath(Death recentPlayerDeath) {
        this.recentPlayerDeath = recentPlayerDeath;
    }

    public void setOnLeaderboards(boolean onLeaderboards) {
        this.onLeaderboards = onLeaderboards;
    }

    public static class Death implements Comparable<Death>, Serializable {
        private String name, killedBy, ability;
        private int level;

        public Death(String name, String killedBy, String ability, int level) {
            this.name = name;
            this.killedBy = killedBy;
            this.ability = ability;
            this.level = level;
        }

        @Override
        public int compareTo(Death o) {
            if (level < o.getLevel()) { return 1;
            } else if (level > o.getLevel()) { return -1; }
            return 0;
        }

        public String getName() {
            return name;
        }

        public String getKilledBy() {
            return killedBy;
        }

        public String getAbility() {
            return ability;
        }

        public int getLevel() {
            return level;
        }



        @Override
        public String toString() {
            return "Name: " + name + "\nKilled By: " + killedBy + "\nAbility: " + ability + "\nLevel: " + level;
        }
    }


}
