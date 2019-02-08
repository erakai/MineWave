package com.kai.game.util;

import java.awt.*;

public class Parameters {

    /* ==================== Holds constants. ==================== */

    //The number of times the screen is updated per second.
    public static final int FRAMES_PER_SECOND = 60;

    //This font is set in /game/hud/MainMenu. Holds the user's system's default font.
    public static Font ORIGINAL_FONT;

    //The information for connecting to Server for leaderboard data (should be 54.193.51.83 and 8701)
    public static final String SERVER_NAME = "54.193.51.83";
    public static final int SERVER_PORT = 8701;

    //The time (in millis) that the Client will wait to connect to the server.
    public static final int TIMEOUT_LENGTH = 1000;

    //The drop chance of items without any boosts
    public static final double[] LOOT_CHANCES = {0.07, 0.03, 0.006, 0.0025, 0.0005};
    public static double GLOBAL_LOOT_BOOST = 1.00;
    public static double COMMON_CHANCE = LOOT_CHANCES[0] * GLOBAL_LOOT_BOOST; // 7.0%
    public static double UNCOMMON_CHANCE = LOOT_CHANCES[1] * GLOBAL_LOOT_BOOST; // 3.0%
    public static double RARE_CHANCE = LOOT_CHANCES[2] * GLOBAL_LOOT_BOOST; // 0.6%
    public static double MYSTIC_CHANCE = LOOT_CHANCES[3] * GLOBAL_LOOT_BOOST; // 0.25%
    public static double TWISTED_CHANCE = LOOT_CHANCES[4] * GLOBAL_LOOT_BOOST; //0.05%

}
