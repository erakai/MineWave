package com.kai.game.items;

import com.kai.game.core.Screen;
import com.kai.game.entities.Player;
import com.kai.game.skills.Skill;

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

    Format of an item (have any number of stats/skills):

        name: [item name]
        rarity: [rarity number]
        image: [x position of item in items.png] [y position of item in items.png]
        * [amount] [stat name]
        * [skill name]
        = [description]
        ----

    Guidelines:
        Common items should be pretty terrible, uncommon items should feel a little impactful.
        Rare items should be powerful and give a noticeable benefit.
        Any item that grants a skill should be at least rare (maybe uncommon if it gives nothing else)
        Anything that gives a unique effect or passive should be mystic.
        Mystic rings will typically have a special case in getBehaviorForKeyword().
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
                        itemStats.put(parts[2], Integer.valueOf(parts[1]));
                    } else {
                        possibleBehaviors.add(new ItemBehavior() {
                            public void onEquip(Player owner) {
                                owner.equipSkill(Skill.getFreshSkill(currentLine.replaceAll("\\*", ""), Screen.getPlayer()));
                            }
                            public void onUnEquip(Player owner) {
                                owner.unEquipSkill(currentLine);
                            }
                            public String getDescription() {
                                return "Grants TeleportSkill";
                            }
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
                items.put(itemName, newItem);
                System.out.println(newItem);
            }
        }
    }

    private static ItemBehavior getBehaviorForKeyword(String keyword) {
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

    public static Item getItem(String itemName) {
        return new Item(items.get(itemName));
    }

}
