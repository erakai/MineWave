package com.kai.game.hud;

import com.kai.game.core.GameObject;
import com.kai.game.skills.Skill;
import com.kai.game.util.MFont;
import com.kai.game.util.MPoint;
import com.kai.game.util.MRectangle;
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

    //Signifies if the mouse is over the replay butotn
    private boolean replayHover = false;

    //The Skill the player was using (to draw)
    private Skill recentPlayerSkill;

    public DeathScreen() {
        super(ResourceManager.getImage("AlternateDeathScreen.png"), 0, 0, 1200, 600);
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


    private Font small = new MFont(1.4);
    private Font medium = new MFont(2.3);
    private Font large = new MFont(4);

    private MPoint firstRecentInfo = new MPoint(35, 52);
    private MPoint secondRecentInfo = new MPoint(35, 82);
    private MPoint thirdRecentInfo = new MPoint(35, 112);
    private MPoint recentImageInfo = new MPoint(250, 40);

    private MPoint leaderboardText = new MPoint(785, 60);
    private MPoint name = new MPoint(696, 90);
    private MPoint killedBy = new MPoint(800, 90);
    private MPoint ability = new MPoint(955, 90);
    private MPoint score = new MPoint(1100, 90);

    private MPoint ldbText = new MPoint(0, 125);
    private MPoint ldbDecrement = new MPoint(0, 50);

    private MPoint error = new MPoint(750, 300);
    private MPoint replay = new MPoint(100, 395);

    private MRectangle hoverRectangle = new MRectangle(new MPoint(25, 358), new MPoint(327, 410));

    //TODO: Somewhere on DeathScreen, have some text that says if the user made it onto the leaderboard.
    public void drawMe(Graphics g) {
        g.drawImage(getSelfImage(), getX(), getY(), null);

        //Draw info of recent player death
        g.setFont(small);
        g.setColor(Color.WHITE);
        g.drawString("You died!", firstRecentInfo.getX(), firstRecentInfo.getY());
        g.drawString("Score: " + recentPlayerDeath.getLevel(), secondRecentInfo.getX(), secondRecentInfo.getY());
        g.drawString("Killed By: " + recentPlayerDeath.getKilledBy(), thirdRecentInfo.getX(), thirdRecentInfo.getY());
        g.drawImage(recentPlayerSkill.getSelfImage(), recentImageInfo.getX(), recentImageInfo.getY(), null);


        //Drawing leaderboard
        g.setFont(large);
        g.setColor(Color.ORANGE);
        g.drawString("Leaderboard", leaderboardText.getX(), leaderboardText.getY());

        g.setFont(small);
        g.drawString("Name", name.getX(), name.getY());
        g.drawString("Killed By", killedBy.getX(), killedBy.getY());
        g.drawString("Ability", ability.getX(), ability.getY());
        g.drawString("Score", score.getX(), score.getY());

        g.setColor(new Color(42, 112, 224));
        if (connected) {
            for (int i = 0; i<leaderboard.size(); i++) {
                Death death = leaderboard.get(i);
                if (onLeaderboards && death.getName().equals(recentPlayerDeath.getName()) && death.getLevel() == recentPlayerDeath.getLevel() && death.getKilledBy().equals(recentPlayerDeath.getKilledBy()) && death.getAbility().equals(recentPlayerDeath.getAbility())) {
                    g.setColor(new Color(211, 211, 211));
                    g.fillRect(name.getX()-12, ldbText.getY() + (i * ldbDecrement.getY()- (ldbDecrement.getY()/2)), 490, ldbDecrement.getY()/2 + (ldbDecrement.getY()/3) - 5 );
                    g.setColor(new Color(42, 112, 224));
                }
                g.drawString(death.getName(), name.getX(), ldbText.getY() + (i * ldbDecrement.getY()));
                g.drawString(death.getKilledBy(), killedBy.getX(), ldbText.getY() + (i * ldbDecrement.getY()));
                g.drawString(death.getAbility(), ability.getX(), ldbText.getY() + (i * ldbDecrement.getY()));
                g.drawString(String.valueOf(death.getLevel()), score.getX(), ldbText.getY() + (i * ldbDecrement.getY()));
            }
        } else {
            g.setColor(new Color(250, 25, 67));
            g.setFont(small);
            g.drawString("Could not connect to server :(", error.getX(), error.getY());
        }

        //Drawing replay
        g.setFont(medium);
        if (replayHover) {
            g.setColor(new Color(43, 64, 121));
            g.fillRect(hoverRectangle.getTopLeft().getX(), hoverRectangle.getTopLeft().getY(), hoverRectangle.getWidth()+1, hoverRectangle.getHeight()+1);
        }
        g.setColor(new Color(192, 209, 216));
        g.drawString("Play again?", replay.getX(), replay.getY());
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


    public boolean testHover(int mouseX, int mouseY) {
        replayHover = (mouseX > hoverRectangle.getTopLeft().getX() && mouseX < hoverRectangle.getBottomRight().getX() &&
            mouseY > hoverRectangle.getTopLeft().getY() && mouseY < hoverRectangle.getBottomRight().getY());
        return replayHover;
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
