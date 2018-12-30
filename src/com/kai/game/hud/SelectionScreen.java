package com.kai.game.hud;

import com.kai.game.GameObject;
import com.kai.game.entities.Player;
import com.kai.game.master.ResourceManager;
import com.kai.game.master.Screen;
import com.kai.game.skills.ComboSkill;
import com.kai.game.skills.ShieldSkill;
import com.kai.game.skills.Skill;
import com.kai.game.skills.TeleportSkill;

import java.util.ArrayList;
import java.util.List;

import java.awt.*;

public class SelectionScreen extends GameObject {
    private static List<Skill> selectableAbilities;
    public static Skill currentlySelected;

    // == This class/gamestate needs a decent amount of work. ==

    //TODO: Use a method to replace location variables.
    private static final int startDrawX = (int)(20/1200.0 * Screen.WINDOW_WIDTH);
    private static final int startDrawY = (int)(275/600.0 * Screen.WINDOW_HEIGHT);

    private final int textDrawX = (int)(30.0/1200.0 * Screen.WINDOW_WIDTH);
    private final int textDrawX2 = (int) (900.0/1200.0 * Screen.WINDOW_WIDTH);

    private final int textDrawY = (int)(65.0/600.0 * Screen.WINDOW_HEIGHT);
    private final int textDrawY2 = (int)(95.0/600.0 * Screen.WINDOW_HEIGHT);
    private final int textDrawY3 = (int)(125.0/600.0 * Screen.WINDOW_HEIGHT);

    private final int textDrawY4 = (int)(442.0/600.0 * Screen.WINDOW_HEIGHT);
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
    }

    public static Skill getCurrentlySelected(Player p) {
        switch (currentlySelected.getName()) {
            case "TeleportSkill":
                return new TeleportSkill(p);
            case "ShieldSkill":
                return new ShieldSkill(p);
            case "ComboSkill":
                return new ComboSkill(p);
            default:
                return null;
        }
    }

    //TODO: Make abilitySelectionAttempt() not static and instead have SelectionScreen be retrieved from Screen
    public static void abilitySelectionAttempt(int mouseX, int mouseY) {
        //TODO: Clean up the abilitySelectionAttempt() code.
        for (int i = 0; i < selectableAbilities.size(); i++) {
            int abilityX = startDrawX + (i*10) + (i * Skill.SKILL_WIDTH);
            int abilityY = startDrawY;
            if (mouseX > abilityX && mouseX < abilityX + Skill.SKILL_WIDTH
                && mouseY > abilityY && mouseY < abilityY + Skill.SKILL_HEIGHT) {
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
            selectableAbilities.get(i).drawMe(g, startDrawX + (i*10) + (i * Skill.SKILL_WIDTH), startDrawY);
        }

        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font(MainMenu.ogfont.getName(), MainMenu.ogfont.getStyle(), (int)(MainMenu.ogfont.getSize()*(Screen.WINDOW_WIDTH/1000.0))));
        g.drawString("Click on an skill to read what it does.", textDrawX, textDrawY);
        g.drawString("This skill is activated by pressing E.", textDrawX, textDrawY2);
        g.drawString("Press enter to equip the currently selected skill.", textDrawX, textDrawY3);

        g.drawString("W/A/S/D to move.", textDrawX2, textDrawY);
        g.drawString("Left click to place a mine.", textDrawX2, textDrawY2);
        g.drawString("Max of 7 mines at once.", textDrawX2, textDrawY3);

        if (currentlySelected != null) {
            for (int i = 0; i<currentlySelected.description.length; i++) {
                g.drawString(currentlySelected.description[i], textDrawX, textDrawY4 + (i * abilityDescIncrement));
            }
        }

    }


}
