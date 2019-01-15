package com.kai.game.hud;

import com.kai.game.core.GameObject;
import com.kai.game.util.ResourceManager;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.awt.*;

public class DeathScreen extends GameObject {

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

    public DeathScreen() {
        super(ResourceManager.getImage("DeathScreen.png"), 0, 0, 1200, 600);
/*
        if (givenLeaderboard == null) {
            connected = false;
        } else {
            this.leaderboard = givenLeaderboard;
            Collections.sort(leaderboard);
        }*/
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

    @Override
    public void drawMe(Graphics g) {
        g.drawImage(getSelfImage(), getX(), getY(), null);
    }

    public static Death createDeath(String name, String killedBy, String ability, int level) {
        return new Death(name, killedBy, ability, level);
    }

    public void setOnLeaderboards(boolean onLeaderboards) {
        this.onLeaderboards = onLeaderboards;
    }

    public static class Death implements Comparable<Death>, Serializable {
        private String name, killedBy, ability;
        private int level;

        Death(String name, String killedBy, String ability, int level) {
            this.name = name;
            this.killedBy = killedBy;
            this.ability = ability;
            this.level = level;
        }

        @Override
        public int compareTo(Death o) {
            if (level < o.getLevel()) { return -1;
            } else if (level > o.getLevel()) { return 1; }
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
