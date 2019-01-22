package com.kai.game.items;

import com.kai.game.entities.Player;

public interface ItemBehavior {
    void onEquip(Player owner);
    void onUnEquip(Player owner);
    String getDescription();
}
