package com.kai.game.util;

import java.awt.*;

public class Parameters {

    /* ==================== Holds constants. ==================== */

    //The number of times the screen is updated per second.
    public static final int FRAMES_PER_SECOND = 60;

    //This font is set in /game/hud/MainMenu. Holds the user's system's default font.
    public static Font ORIGINAL_FONT;

    //The information for connecting to Server for leaderboard data
    //54.193.51.83
    public static final String SERVER_NAME = "54.193.51.83";
    public static final int SERVER_PORT = 8701;

    //The time (in millis) that the Client will wait to connect to the server.
    public static final int TIMEOUT_LENGTH = 1000;

    //The drop chance of items without any boosts
    public static final double COMMON_CHANCE = 0.05;
    public static final double UNCOMMON_CHANCE = 0.02;
    public static final double RARE_CHANCE = 0.01;
    public static final double MYSTIC_CHANCE = 0.0025;


}
