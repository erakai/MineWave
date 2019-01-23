package com.kai.game.core;

import com.kai.game.entities.enemies.*;
import com.kai.game.items.LootInstance;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class RoomHandler implements Updatable {
    private static int currentLevel;
    private static Random rand;
    private static RoomInstance[][] rooms;
    private static int currentRoomX, currentRoomY;


    RoomHandler(int startingLevel) {
        RoomHandler.currentLevel = startingLevel;
        rand = new Random();
        rooms = new RoomInstance[50][50];
        currentLevel = 0;
        enterRoom(0, 0);
    }

    public void updateSelfImage() {
        for (LootInstance l: getCurrentRoom().getRoomLoot()) {
            l.updateSelfImage();
            l.updateImages();
        }
    }

    public static RoomInstance getCurrentRoom() {
        return rooms[currentRoomY][currentRoomX];
    }

    public static RoomInstance[][] getRoomArray() {
        return rooms;
    }

    public static int getCurrentRoomX() {
        return currentRoomX;
    }

    public static int getCurrentRoomY() {
        return currentRoomY;
    }

    private void enterRoom(int nX, int nY) {
        if (rooms[nY][nX] == null) {
            currentLevel++;
            rooms[nY][nX] = new RoomInstance(currentLevel);
        }
        currentRoomX = nX;
        currentRoomY = nY;
        getCurrentRoom().entered();
    }

    void drawAllRoomContents(Graphics g) {
        getCurrentRoom().drawRoom(g);
    }

    void updateEnemies() {
        getCurrentRoom().updateEnemies();
    }

    public void update() {
        if (getCurrentRoom().isEnemiesEmpty()) {
            checkIfNextLevel();
        }
        getCurrentRoom().update();
    }

    public void updateLoot() {
        getCurrentRoom().updateLoot();
    }

    public void newLootInstance(LootInstance lI) {
        getCurrentRoom().getRoomLoot().add(lI);
    }

    public static void addEnemy(Enemy e) {
        getCurrentRoom().addEnemy(e);
    }

    private void checkIfNextLevel() {
        if (Screen.getPlayer().getX() > Screen.WINDOW_WIDTH) {
            if (currentRoomX != rooms.length-1) {
                Screen.getPlayer().setX(10);
                enterRoom(currentRoomX+1, currentRoomY);
            }
        }  else if (Screen.getPlayer().getX() < 0) {
            if (currentRoomX != 0) {
                Screen.getPlayer().setX(1200 - (Screen.getPlayer().getWidth() + 10));
                enterRoom(currentRoomX-1, currentRoomY);
            }
        } else if (Screen.getPlayer().getY() < 0) {
            if (currentRoomY != 0) {
                Screen.getPlayer().setY(600 - (Screen.getPlayer().getHeight() + 10));
                enterRoom( currentRoomX, currentRoomY-1);
            }
        } else if (Screen.getPlayer().getY() > Screen.WINDOW_HEIGHT) {
            if (currentRoomY != rooms.length-1) {
                Screen.getPlayer().setY(10);
                enterRoom( currentRoomX, currentRoomY+1);
            }
        }
    }

    public boolean isEnemiesEmpty() {
        return getCurrentRoom().getEnemies().isEmpty();
    }

    public int getCurrentLevel() {
        return currentLevel;
    }
    public List<LootInstance> getRoomLoot() {
        return getCurrentRoom().getRoomLoot();
    }


    private static final int MIN_DISTANCE_FROM_PLAYER = 100;
    public static int getXAwayFromPlayer(int enemyWidth) {
        int rx;
        do {
            rx = rand.nextInt(Screen.WINDOW_WIDTH);
        } while (((Math.abs(Screen.getPlayer().getX() - rx) < MIN_DISTANCE_FROM_PLAYER)
                || !(rx > 0))
                || !(rx+enemyWidth < Screen.WINDOW_WIDTH));
        return rx;
    }

    public static int getYAwayFromPlayer(int enemyHeight) {
        int ry;
        do {
            ry = rand.nextInt(Screen.WINDOW_HEIGHT);
        } while	(((Math.abs(Screen.getPlayer().getY() - ry) < MIN_DISTANCE_FROM_PLAYER)
                || !(ry > 0))
                || !(ry+enemyHeight < Screen.WINDOW_HEIGHT));
        return ry;
    }





}
