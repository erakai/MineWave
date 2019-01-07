package com.kai.game.hud;

import com.kai.game.GameObject;
import com.kai.game.entities.Player;
import com.kai.game.util.MFont;
import com.kai.game.util.Parameters;
import com.kai.game.util.ResourceManager;
import com.kai.game.core.Screen;
import com.kai.game.skills.*;

import java.util.ArrayList;
import java.util.List;
import com.kai.game.util.MPoint;

import java.awt.*;

public class SelectionScreen extends GameObject {
    private static List<Skill> selectableAbilities;
    public static Skill currentlySelected;

    // == This class/gamestate needs a decent amount of work. ==

    //TODO: Use a method to replace location variables.
    private static final MPoint startDraw = new MPoint(20, 275);

    private final MPoint textDrawX = new MPoint(30, 0);
    private final MPoint textDrawX2 = new MPoint(900, 0);

    private final MPoint textDrawY = new MPoint(0, 65);
    private final MPoint textDrawY2 = new MPoint(0, 95);
    private final MPoint textDrawY3 = new MPoint(0, 125);
    private final MPoint textDrawY4 = new MPoint(0, 442);

    private final int abilityDescIncrement = (int)(30.0/600.0 * Screen.WINDOW_HEIGHT);

    public SelectionScreen() {
        super(ResourceManager.getImage("selection.png", Screen.WINDOW_WIDTH, Screen.WINDOW_HEIGHT), 0, 0, Screen.WINDOW_WIDTH, Screen.WINDOW_HEIGHT);

        selectableAbilities = new ArrayList<>();
        addPlayerAbilities();
    }

    private void addPlayerAbilities() {
        selectableAbilities.add(new TeleportSkill(null));
        selectableAbilities.add(new ShieldSkill(null));
        selectableAbilities.add(new ComboSkill(null));
        selectableAbilities.add(new GreatMineSkill(null));
    }

    @Override
    public void updateSelfImage() {
        super.updateSelfImage();
        for (Skill s: selectableAbilities) {
            s.updateSelfImage();
        }
    }

    public static Skill getCurrentlySelected(Player p) {
        switch (currentlySelected.getName()) {
            case "TeleportSkill":
                return new TeleportSkill(p);
            case "ShieldSkill":
                return new ShieldSkill(p);
            case "ComboSkill":
                return new ComboSkill(p);
            case "GreatMineSkill":
                return new GreatMineSkill(p);
            default:
                return null;
        }
    }

    //TODO: Make abilitySelectionAttempt() not static and instead have SelectionScreen be retrieved from Screen
    public static void abilitySelectionAttempt(int mouseX, int mouseY) {
        //TODO: Clean up the abilitySelectionAttempt() code.
        for (int i = 0; i < selectableAbilities.size(); i++) {
            int abilityX = startDraw.getX() + (i*10) + (i * Skill.SKILL_SIZE.getWidth());
            int abilityY = startDraw.getY();
            if (mouseX > abilityX && mouseX < abilityX + Skill.SKILL_SIZE.getWidth()
                && mouseY > abilityY && mouseY < abilityY + Skill.SKILL_SIZE.getWidth()) {
                currentlySelected = selectableAbilities.get(i);
                break;
            }
        }
    }

    @Override
    public void drawMe(Graphics g) {
        //TODO: On the selection screen, instead of pressing enter, add a "play" button.
        g.drawImage(getSelfImage(), getX(), getY(), null);

        for (int i = 0; i < selectableAbilities.size(); i++) {
            g.setColor(new Color(247, 181, 26));
            if (currentlySelected == selectableAbilities.get(i)) {
                g.fillRect((startDraw.getX() + (i*10) + (i * Skill.SKILL_SIZE.getWidth()))-5, startDraw.getY()-5, Skill.SKILL_SIZE.getWidth()+10, Skill.SKILL_SIZE.getHeight()+10);
            }
            selectableAbilities.get(i).drawMe(g, startDraw.getX() + (i*10) + (i * Skill.SKILL_SIZE.getWidth()), startDraw.getY());
        }

        g.setColor(new Color(255, 255, 255));
        g.setFont(new MFont(1.2));
        g.drawString("Click on an skill to read what it does.", textDrawX.getX(), textDrawY.getY());
        g.drawString("This skill is activated by pressing E.", textDrawX.getX(), textDrawY2.getY());
        g.drawString("Press enter to equip the currently selected skill.", textDrawX.getX(), textDrawY3.getY());

        g.drawString("W/A/S/D to move.", textDrawX2.getX(), textDrawY.getY());
        g.drawString("Left click to place a mine.", textDrawX2.getX(), textDrawY2.getY());
        g.drawString("Max of 7 mines at once.", textDrawX2.getX(), textDrawY3.getY());

        if (currentlySelected != null) {
            for (int i = 0; i<currentlySelected.description.length; i++) {
                g.drawString(currentlySelected.description[i], textDrawX.getX(), textDrawY4.getY() + (i * abilityDescIncrement));
            }
        }

    }


}
