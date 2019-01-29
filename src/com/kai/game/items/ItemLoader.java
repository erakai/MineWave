package com.kai.game.items;

import com.kai.game.core.Screen;
import com.kai.game.entities.Player;
import com.kai.game.skills.Skill;
import com.kai.game.util.Parameters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//TODO: Use an actual xml instead of being stupid and just reading a text file.
//I'm too tired atm to google how to do xmls (although I have basic knowledge of xmls and json).

public class ItemLoader {
    public static HashMap<String, Item> items;
    private static final String ITEMS_FILE_NAME = "items.txt";

    public ItemLoader() {
        items = new HashMap<>();

        loadItems();
    }

    /*
    Rarity tiers:
        1 - Common
        2 - Uncommon
        3 - Rare
        4 - Mystic

    Stats (view ALL stats in game/entities/StatManager.java):
        * max mines
            - The max amount of mines you can have placed at once
        * range
            - Radius of range circle = range * 25
        * speed
            - How many pixels the player can move per frame
        * damage
            - How much damage one mine does to an enemy
        * defense
            - Damage taken = enemy damage * ((100 - player defense) / 100.0)
        * max health
            - Whenever max life is gained/lost, gain/lose that much current life

    Format of an item (have any number of stats/skills/special keywords):
    You can find the skill names in game/skills

        name: [item name]
        rarity: [rarity number]
        image: [x position of item in items.png] [y position of item in items.png]
        * [amount] [stat name]
        * [skill name]
        ! [special keyword]
        = [description]
        ----

    Guidelines:
        Common items should be pretty terrible, uncommon items should feel a little impactful.
        Rare items should be powerful and give a noticeable benefit.
        Any item that grants a skill should be at least rare (maybe uncommon if it gives nothing else)
        Anything that gives a unique effect or passive should be mystic.
        Mystic items will usually be the only ones to have a special case in getBehaviorForKeyword().
        Twisted items are intentionally overpowered and should not be considered when designing anything.

     Ring Ideas:
        - A ring that makes you immune to lava or bats or something
        - A ring that increases your drop chances
     */

    private static void loadItems() {
        String s = getItemsString();

        for (String item: s.split("----")) {
            String itemName = "null";
            int itemRarity = -1;
            String itemDescription = "null";
            int[] imageCoordinates = new int[2];
            HashMap<String, Integer> itemStats = new HashMap<>();
            List<ItemBehavior> possibleBehaviors = new ArrayList<>();
            for (String currentLine: item.split("LINEBREAK")) {
                String[] parts = currentLine.split(" ");
                if (parts[0].equals("name:")) {
                    itemName = currentLine.substring(6);
                } else if (parts[0].equals("rarity:")) {
                    itemRarity = Integer.valueOf(parts[1]);
                } else if (parts[0].equals("image:")) {
                    imageCoordinates[0] = Integer.valueOf(parts[1]);
                    imageCoordinates[1] = Integer.valueOf(parts[2]);
                } else if (parts[0].equals("*")) {
                    if (parts[1].matches("-?\\d+(\\.\\d+)?")) {
                        itemStats.put(currentLine.split(parts[1])[1].substring(1), Integer.valueOf(parts[1]));
                    } else {
                        possibleBehaviors.add(new ItemBehavior() {
                            public void onEquip(Player owner) { owner.equipSkill(Skill.getFreshSkill(currentLine.substring(2),owner)); }
                            public void onUnEquip(Player owner) {
                                owner.unEquipSkill(currentLine.substring(2));
                            }
                            public String getDescription() { return ("Grants " + currentLine.substring(2).replaceAll("Skill", "")); }
                        });
                    }
                } else if (parts[0].equals("=")) {
                    itemDescription = currentLine.substring(2);
                } else {
                    possibleBehaviors.add(getBehaviorForKeyword(currentLine));
                }
            }
            if (itemName != "null" && itemRarity != -1 && itemDescription != "null") {
                Item newItem = new Item(itemName, itemRarity, itemDescription, itemStats, possibleBehaviors, imageCoordinates);
                System.out.println(newItem);
                items.put(itemName, newItem);
                //System.out.println(newItem);
            }
        }
    }

    private static ItemBehavior getBehaviorForKeyword(String keyword) {
        if (keyword.equals("! SHOOT")) {
            return new ItemBehavior() {
                public void onEquip(Player owner) {
                    double SECONDS_PER_SHOT = 0.25;
                    owner.maxShotTick = (int)(SECONDS_PER_SHOT * Parameters.FRAMES_PER_SECOND);
                    owner.currentShotTick = owner.maxShotTick;
                    owner.SHOOT = true;
                }
                public void onUnEquip(Player owner) {
                    if (!(owner.getRings()[0].getName().equals("Speckled Chain") && owner.getRings()[1].getName().equals("Speckled Chain"))) {
                        owner.SHOOT = false;
                    }
                }
                public String getDescription() {
                    return "Mines are now bullets";
                }
            };
        }
        if (keyword.equals("! SACRIFICE")) {
            return new ItemBehavior() {
                public void onEquip(Player owner) { }
                public void onUnEquip(Player owner) { }
                public String getDescription() { return "Permanently wound yourself upon healing"; }
            };
        }
        return null;
    }

    private static String getItemsString() {
        StringBuilder stringBuilder = new StringBuilder(250);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(ItemLoader.class.getResourceAsStream(ITEMS_FILE_NAME)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "LINEBREAK");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return stringBuilder.toString();
    }

    //TODO: Make this not case sensitive (very easy):
    public static Item getItem(String itemName) {
        return new Item(items.get(itemName));
    }

    public static HashMap<String, Item> getAllItems() {
        return items;
    }
}
