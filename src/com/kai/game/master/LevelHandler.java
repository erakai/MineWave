package com.kai.game.master;

import com.kai.game.GameObject;
import com.kai.game.Updatable;
import com.kai.game.entities.SpecialDeath;
import com.kai.game.entities.UsesProjectiles;
import com.kai.game.entities.enemies.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LevelHandler implements Updatable {
    private static List<Enemy> enemies;
    private static List<Enemy> toAddQueue;

    private static int currentLevel;
    private static Random rand;

    private static final int MIN_DISTANCE_FROM_PLAYER = 80;

    LevelHandler(int startingLevel) {
        enemies = new ArrayList<>();
        toAddQueue = new ArrayList<>();
        LevelHandler.currentLevel = startingLevel;
        rand = new Random();
        transitionToLevel(currentLevel);
    }

    void drawAllEnemies(Graphics g) {
        for (Enemy e: enemies) {
            e.drawMe(g);
        }
    }

    void updateEnemies() {
        List<Enemy> toRemove = new ArrayList<>();
        for (Enemy e: enemies) {
            e.update();
            if ((int)e.getHealth() < 1) {
                if (e instanceof SpecialDeath) {
                    ((SpecialDeath) e).onDeath();
                }
                toRemove.add(e);
            }
        }
        enemies.removeAll(toRemove);

        chasePlayer();
    }

    public void update() {
        checkForLevelCompletion();
        checkEnemyCollisions();
        if (!toAddQueue.isEmpty()) {
            injectQueue();
        }
    }

    private void checkEnemyCollisions() {
        List<GameObject> checkWith = new ArrayList<>();
        checkWith.add(Screen.getPlayer());

        for (Enemy e: enemies) {
            if (e instanceof UsesProjectiles) {
                ((UsesProjectiles) e).callAllProjectileCollisions(checkWith);
            } else if (e.checkCollision(Screen.getPlayer())) {
                e.attack(Screen.getPlayer());
            }
        }

        Screen.getPlayer().callAllProjectileCollisions(new ArrayList<>(enemies));


    }

    private void chasePlayer() {
        for (Enemy e: enemies) {
            e.chase(Screen.getPlayer().getX(), Screen.getPlayer().getY());
        }
    }

    private static int getXAwayFromPlayer(int enemyWidth) {
        int rx;
        do {
            rx = rand.nextInt(Screen.WINDOW_WIDTH);
        } while (((Math.abs(Screen.getPlayer().getX() - rx) < MIN_DISTANCE_FROM_PLAYER)
                || !(rx > 0))
                || !(rx+enemyWidth < Screen.WINDOW_WIDTH));
        return rx;
    }

    private static int getYAwayFromPlayer(int enemyHeight) {
        int ry;
        do {
            ry = rand.nextInt(Screen.WINDOW_HEIGHT);
        } while	(((Math.abs(Screen.getPlayer().getY() - ry) < MIN_DISTANCE_FROM_PLAYER)
                || !(ry > 0))
                || !(ry+enemyHeight < Screen.WINDOW_HEIGHT));
        return ry;
    }

    public static void addEnemy(Enemy e) {
        toAddQueue.add(e);
    }

    private void injectQueue() {
        enemies.addAll(toAddQueue);
        toAddQueue.clear();
    }

    private void checkForLevelCompletion() {
        if (enemies.isEmpty() && toAddQueue.isEmpty()) {
            currentLevel++;
            transitionToLevel(currentLevel);
        }
    }

    private static void createNewEnemy(Class c, int amount) {
        for (int i = 0; i < amount; i++) {
            if (c == Insect.class) {
                enemies.add(new Insect(getXAwayFromPlayer(Insect.INSECT_WIDTH), getYAwayFromPlayer(Insect.INSECT_HEIGHT)));
            } else if (c == Beetle.class) {
                enemies.add(new Beetle(getXAwayFromPlayer(Beetle.BEETLE_WIDTH), getYAwayFromPlayer(Beetle.BEETLE_HEIGHT)));
            } else if (c == InsectNest.class) {
                enemies.add(new InsectNest(getXAwayFromPlayer(InsectNest.INSECT_NEST_WIDTH), getYAwayFromPlayer(InsectNest.INSECT_NEST_HEIGHT)));
            } else if (c == ArmoredInsect.class) {
                enemies.add(new ArmoredInsect(getXAwayFromPlayer(ArmoredInsect.ARMORED_INSECT_WIDTH), getYAwayFromPlayer(ArmoredInsect.ARMORED_INSECT_HEIGHT)));
            } else if (c == Turret.class) {
                enemies.add(new Turret(getXAwayFromPlayer(Turret.TURRET_WIDTH), getYAwayFromPlayer(Turret.TURRET_HEIGHT)));
            } else if (c == Worm.class) {
                enemies.add(new Worm(getXAwayFromPlayer(Worm.WORM_WIDTH), getYAwayFromPlayer(Worm.WORM_HEIGHT)));
            }
        }
    }

    //TODO: Replace presets with semi-randomly generated levels (assign each enemy a threat level).
    private void transitionToLevel(int level) {
        if (level == 1) {
            difficultyOne();
        }

        if (level == 2) {
            difficultyTwo();
        }

        if (level >= 3 && level <= 4) {
            difficultyThree();
        }

        if (level >= 5 && level <= 7) {
            difficultyFour();
        }

        if (level == 8) {
            wormLevel8();
        }

        if (level >= 9) {
            difficultyFive();
        }


    }

    private void difficultyOne() {
        createNewEnemy(Insect.class, 1);
    }

    private void difficultyTwo() {
        switch (rand.nextInt(4)) {
            case 0:
                createNewEnemy(Beetle.class,  2);
                break;
            case 1:
                createNewEnemy(Insect.class, 2);
                break;
            case 2:
                createNewEnemy(Insect.class, 1);
                createNewEnemy(Beetle.class, 1);
                break;
            case 3:
                createNewEnemy(InsectNest.class, 1);
                break;
        }
    }

    private void difficultyThree() {
        switch (rand.nextInt(4)) {
            case 0:
                createNewEnemy(Insect.class ,2);
                createNewEnemy(ArmoredInsect.class, 2);
                break;
            case 1:
                createNewEnemy(ArmoredInsect.class, 2);
                createNewEnemy(InsectNest.class, 1);
                break;
            case 2:
                createNewEnemy(Beetle.class, 2);
                createNewEnemy(ArmoredInsect.class, 1);
                createNewEnemy(Insect.class, 1);
                break;
            case 3:
                createNewEnemy(Beetle.class, 2);
                createNewEnemy(InsectNest.class, 1);
                createNewEnemy(ArmoredInsect.class, 1);
                break;
        }
    }

    private void difficultyFour() {
        switch (rand.nextInt(5)) {
            case 0:
                createNewEnemy(ArmoredInsect.class, 3);
                createNewEnemy(Beetle.class, 2);
                break;
            case 1:
                createNewEnemy(InsectNest.class, 2);
                createNewEnemy(Insect.class, 1);
                break;
            case 2:
                createNewEnemy(Insect.class, 4);
                createNewEnemy(ArmoredInsect.class, 1);
                break;
            case 3:
                createNewEnemy(Beetle.class, 4);
                break;
            case 4:
                createNewEnemy(InsectNest.class, 1);
                createNewEnemy(Beetle.class, 2);
                createNewEnemy(ArmoredInsect.class, 1);
                break;
        }
    }

    private void difficultyFive() {
        switch (rand.nextInt(6)) {
            case 0:
                createNewEnemy(InsectNest.class, 3);
                break;
            case 1:
                createNewEnemy(Turret.class, 2);
                createNewEnemy(ArmoredInsect.class, 2);
                createNewEnemy(Beetle.class, 1);
                break;
            case 2:
                createNewEnemy(Insect.class, 6);
                break;
            case 3:
                createNewEnemy(ArmoredInsect.class, 3);
                createNewEnemy(Beetle.class, 3);
                break;
            case 4:
                createNewEnemy(Turret.class, 2);
                createNewEnemy(InsectNest.class, 2);
                break;
            case 5:
                createNewEnemy(Turret.class, 1);
                createNewEnemy(Insect.class, 2);
                createNewEnemy(Beetle.class, 2);
                break;
        }
    }

    //TODO: Implement a delay/warning of several seconds before "boss" levels, so the player gets situated.
    
    private void wormLevel8() {
        int scaled50X = (int)(50.0/1200.0 * Screen.WINDOW_WIDTH);
        int scaled50Y = (int)(50.0/600.0 * Screen.WINDOW_HEIGHT);

        centerPlayer();

        createNewEnemy(Worm.class, 1);
        enemies.add(new Turret(scaled50X, scaled50Y)); // top left
        enemies.add(new Turret(Screen.WINDOW_WIDTH - scaled50X, scaled50Y)); // top right
        enemies.add(new Turret(scaled50X, Screen.WINDOW_HEIGHT - scaled50Y - Turret.TURRET_HEIGHT)); // bottom left
        enemies.add(new Turret(Screen.WINDOW_WIDTH - scaled50X, Screen.WINDOW_HEIGHT - scaled50Y - Turret.TURRET_HEIGHT)); // bottom right
    }


    private void centerPlayer() {
        Screen.getPlayer().setX(Screen.WINDOW_WIDTH/2 + (Screen.getPlayer().width/2));
        Screen.getPlayer().setY(Screen.WINDOW_HEIGHT/2 + (Screen.getPlayer().height/2));
    }

    public static int getCurrentLevel() {
        return currentLevel;
    }

}
