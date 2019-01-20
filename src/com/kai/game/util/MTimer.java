package com.kai.game.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import java.util.ArrayList;

public class MTimer {

    //TODO: Implement MTimer in Skill and enemy and other classes (BossIncomingSign) who don't use a timer right now.

    private long startTime;
    private HashMap<String, Long> markedTimes;

    public MTimer() {
        startTime = System.nanoTime();
        markedTimes = new HashMap<>();
        markedTimes.put("start", startTime);
    }

    public int getSecondsSinceStart() {
        return (int)((System.nanoTime() - startTime)/1000000000.0);
    }

    public int getSecondsSinceMarkedTime(String key) {
        return (int)((System.nanoTime() - markedTimes.get(key))/1000000000.0);
    }

    public void markTime(String identifier) {
        markedTimes.put(identifier, System.nanoTime());
    }
}
