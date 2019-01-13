package com.kai.game.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import java.util.ArrayList;

public class MTimer {

    //TODO: Implement MTimer in Skill and other classes who don't use a timer right now.

    private long startTime;
    private HashMap<String, Long> markedTimes;

    public MTimer() {
        startTime = System.currentTimeMillis();
        markedTimes = new HashMap<String, Long>();
        markedTimes.put("start", startTime);
    }

    public int getSecondsSinceStart() {
        return (int)((System.currentTimeMillis() - startTime)/1000.0);
    }

    public int getSecondsSinceMarkedTime(String key) {
        return (int)((System.currentTimeMillis() - markedTimes.get(key))/1000.0);
    }

    public int markTime(String identifier) {
        markedTimes.put(identifier, System.currentTimeMillis());
        return (markedTimes.size()-1);
    }

    private Object getElementByIndex(LinkedHashMap map,int index){
        return map.get( (map.keySet().toArray())[ index ] );
    }
}
