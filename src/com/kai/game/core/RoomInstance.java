package com.kai.game.core;

import com.kai.game.entities.SpecialDeath;
import com.kai.game.entities.UsesProjectiles;
import com.kai.game.entities.bosses.Boss;
import com.kai.game.entities.bosses.LavaGiant;
import com.kai.game.entities.bosses.LightningWorm;
import com.kai.game.entities.bosses.Vampire;
import com.kai.game.entities.enemies.*;
import com.kai.game.items.LootInstance;
import com.kai.game.util.MPoint;
import com.kai.game.util.MRectangle;
import com.kai.game.util.Parameters;
import com.kai.game.util.ResourceManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.kai.game.core.RoomHandler.getXAwayFromPlayer;
import static com.kai.game.core.RoomHandler.getYAwayFromPlayer;

public class RoomInstance implements Updatable {
    private List<Enemy> enemies;
    private List<LootInstance> roomLoot;
    private List<Enemy> toAddQueue;

    private Random rand;
    private boolean canMoveUp, canMoveDown, canMoveRight, canMoveLeft;
    private Image upArrow, leftArrow, downArrow, rightArrow;

    public RoomInstance(int roomLevel) {
        //TODO: Link each room instance to it's own environment.
        enemies = new ArrayList<>();
        roomLoot = new ArrayList<>();
        toAddQueue = new ArrayList<>();
        rand = new Random();

        upArrow = ResourceManager.getImage("arrow.png");
        leftArrow = ResourceManager.rotate(upArrow, 270);
        downArrow = ResourceManager.rotate(upArrow, 180);
        rightArrow = ResourceManager.rotate(upArrow, 90);

        generateLevel(roomLevel);
    }

    public void entered() {
        updateAvailableDirections();
        Screen.getEnvironment().clearAllSceneObjects();
        Screen.getPlayer().removeAllMines();
    }

    public void drawRoom(Graphics g) {
        for (Enemy e: enemies) {
            e.drawMe(g);
        }
        for (LootInstance l: roomLoot) {
            l.drawMe(g);
        }
        if (isEnemiesEmpty()) {
            drawArrows(g);
        }
    }

    public void updateAvailableDirections() {
        RoomInstance[][] rooms = RoomHandler.getRoomArray();
        int currentX = RoomHandler.getCurrentRoomX();
        int currentY = RoomHandler.getCurrentRoomY();
        canMoveUp = true;
        canMoveDown = true;
        canMoveRight = true;
        canMoveLeft = true;

        //TODO: what the fuck am i doing make it stop please rewrite this
        try {
            if (!(rooms[currentY][currentX+1] == null)) {
                canMoveRight = false;
            }
        } catch (ArrayIndexOutOfBoundsException ex) { canMoveRight = false; }
        try {
            if (!(rooms[currentY][currentX-1] == null)) {
                canMoveLeft = false;
            }
        } catch (ArrayIndexOutOfBoundsException ex) { canMoveLeft = false; }
        try {
            if (!(rooms[currentY+1][currentX] == null)) {
                canMoveDown = false;
            }
        } catch (ArrayIndexOutOfBoundsException ex) { canMoveDown = false; }
        try {
            if (!(rooms[currentY-1][currentX] == null)) {
                canMoveUp = false;
            }
        } catch (ArrayIndexOutOfBoundsException ex) { canMoveUp = false; }
    }

    private MPoint upArrowPoint = new MPoint(575, 10);
    private MPoint leftArrowPoint = new MPoint(10, 275);
    private MPoint downArrowPoint = new MPoint(575, 540);
    private MPoint rightArrowPoint = new MPoint(1140, 275);

    private void drawArrows(Graphics g) {
        if (canMoveUp) {
            g.drawImage(upArrow, upArrowPoint.getX(), upArrowPoint.getY(), null);
        }
        if (canMoveDown) {
            g.drawImage(downArrow, downArrowPoint.getX(), downArrowPoint.getY(), null);
        }
        if (canMoveLeft) {
            g.drawImage(leftArrow, leftArrowPoint.getX(), leftArrowPoint.getY(), null);
        }
        if (canMoveRight) {
            g.drawImage(rightArrow, rightArrowPoint.getX(), rightArrowPoint.getY(), null);
        }
    }

    public void updateEnemies() {
        List<Enemy> toRemove = new ArrayList<>();
        for (Enemy e: enemies) {
            e.update();
            if ((int)e.getHealth() < 1) {
                ((SpecialDeath) e).onDeath(1);
                toRemove.add(e);
            }
            Screen.getEnvironment().sceneCollisions(e);
        }
        enemies.removeAll(toRemove);

        if (!toAddQueue.isEmpty()) {
            injectQueue();
        }

        chasePlayer();

        Screen.getEnvironment().sceneCollisions(Screen.getPlayer());
    }

    private void checkEnemyCollisions() {
        List<GameObject> checkWith = new ArrayList<>();
        checkWith.add(Screen.getPlayer());

        for (Enemy e: enemies) {
            if (e instanceof UsesProjectiles) {
                ((UsesProjectiles) e).callAllProjectileCollisions(checkWith);
                if (e instanceof Boss && e.checkCollision(Screen.getPlayer())) {
                    ((Boss) e).meleeAttack(Screen.getPlayer());
                }
            } else if (e.checkCollision(Screen.getPlayer())) {
                e.attack(Screen.getPlayer());
            }
            if (checkForGameOver()) {
                Screen.playerDied(e);
                break;
            }
        }

        Screen.getPlayer().callAllProjectileCollisions(new ArrayList<>(enemies));
    }

    public void updateLoot() {
        List<LootInstance> removeQ = new ArrayList<>();
        for (LootInstance lI: roomLoot) {
            lI.checkIfStandingOn(Screen.getPlayer());

            if (lI.getContainedItems().size() == 0) {
                removeQ.add(lI);
            }
        }
        roomLoot.removeAll(removeQ);
    }

    private void chasePlayer() {
        for (Enemy e: enemies) {
            e.chase(Screen.getPlayer().getX(), Screen.getPlayer().getY());
        }
    }

    public void update() {
        checkEnemyCollisions();
    }

    private void injectQueue() {
        enemies.addAll(toAddQueue);
        toAddQueue.clear();
    }

    public boolean checkForGameOver() {
        return (Screen.getPlayer().getHealth() < 1);
    }

    public void addEnemy(Enemy e) {
        toAddQueue.add(e);
    }

    public boolean isEnemiesEmpty() {
        return enemies.isEmpty();
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<LootInstance> getRoomLoot() {
        return roomLoot;
    }

    public boolean isCanMoveUp() {
        return canMoveUp;
    }

    public boolean isCanMoveDown() {
        return canMoveDown;
    }

    public boolean isCanMoveRight() {
        return canMoveRight;
    }

    public boolean isCanMoveLeft() {
        return canMoveLeft;
    }

    private static final int DEFAULT_MIN_DISTANCE = 200;
    public void createNewEnemy(Class c, int amount) {
        for (int i = 0; i < amount; i++) {
            if (c == Insect.class) {
                addEnemy(new Insect(getXAwayFromPlayer(DEFAULT_MIN_DISTANCE), getYAwayFromPlayer(DEFAULT_MIN_DISTANCE)));
            } else if (c == Beetle.class) {
                addEnemy(new Beetle(getXAwayFromPlayer(DEFAULT_MIN_DISTANCE), getYAwayFromPlayer(DEFAULT_MIN_DISTANCE)));
            } else if (c == InsectNest.class) {
                addEnemy(new InsectNest(getXAwayFromPlayer(DEFAULT_MIN_DISTANCE), getYAwayFromPlayer(DEFAULT_MIN_DISTANCE)));
            } else if (c == ArmoredInsect.class) {
                addEnemy(new ArmoredInsect(getXAwayFromPlayer(DEFAULT_MIN_DISTANCE), getYAwayFromPlayer(DEFAULT_MIN_DISTANCE)));
            } else if (c == Turret.class) {
                addEnemy(new Turret(getXAwayFromPlayer(DEFAULT_MIN_DISTANCE), getYAwayFromPlayer(DEFAULT_MIN_DISTANCE)));
            } else if (c == Worm.class) {
                addEnemy(new Worm(getXAwayFromPlayer(DEFAULT_MIN_DISTANCE), getYAwayFromPlayer(DEFAULT_MIN_DISTANCE)));
            } else if (c == Bat.class) {
                addEnemy(new Bat(getXAwayFromPlayer(DEFAULT_MIN_DISTANCE), getYAwayFromPlayer(DEFAULT_MIN_DISTANCE)));
            } else if (c == Vampire.class) {
                addEnemy(new Vampire(getXAwayFromPlayer(DEFAULT_MIN_DISTANCE), getYAwayFromPlayer(DEFAULT_MIN_DISTANCE)));
            } else if (c == MagicBall.class) {
                addEnemy(new MagicBall(getXAwayFromPlayer(DEFAULT_MIN_DISTANCE), getYAwayFromPlayer(DEFAULT_MIN_DISTANCE)));
            } else if (c == Lavakut.class) {
                addEnemy(new Lavakut(getXAwayFromPlayer(DEFAULT_MIN_DISTANCE), getYAwayFromPlayer(DEFAULT_MIN_DISTANCE)));
            } else if (c == LavaGiant.class) {
                addEnemy(new LavaGiant(getXAwayFromPlayer(DEFAULT_MIN_DISTANCE), getYAwayFromPlayer(DEFAULT_MIN_DISTANCE)));
            } else if (c == LightningWorm.class) {
                addEnemy(new LightningWorm(getXAwayFromPlayer(DEFAULT_MIN_DISTANCE), getYAwayFromPlayer(DEFAULT_MIN_DISTANCE)));
            }
        }
    }

    //TODO: Replace presets with semi-randomly generated levels (assign each enemy a threat level).
    private void generateLevel(int level) {
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

        if (level >= 9 && level <= 15) {
            difficultyFive();
        }

        if (level == 16) {
            vampireLevel16();
        }

        if (level >= 17 && level <= 23) {
            difficultySix();
        }

        if (level == 24) {
            lavaLevel24();
        }

        if (level >= 25 && level < 100) {
            difficultySeven();
        }

        if (level == 100) {
            bossRush();
        }

        if (level > 100) {
            difficultySeven();
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
    private void difficultySix() {
        switch(rand.nextInt(5)) {
            case 0:
                createNewEnemy(Bat.class, 10);
                createNewEnemy(Worm.class, 1);
                break;
            case 1:
                createNewEnemy(MagicBall.class, 2);
                createNewEnemy(Beetle.class, 6);
                break;
            case 2:
                createNewEnemy(InsectNest.class, 1);
                createNewEnemy(MagicBall.class, 1);
                createNewEnemy(ArmoredInsect.class, 3);
                createNewEnemy(Turret.class, 1);
                break;
            case 3:
                createNewEnemy(Turret.class, 3);
                createNewEnemy(Insect.class, 4);
                break;
            case 4:
                createNewEnemy(Beetle.class, 1);
                createNewEnemy(Turret.class, 1);
                createNewEnemy(ArmoredInsect.class, 1);
                createNewEnemy(Insect.class, 1);
                createNewEnemy(MagicBall.class, 1);
                createNewEnemy(InsectNest.class, 1);
                break;
        }
    }
    private void difficultySeven() {
        switch(rand.nextInt(5)) {
            case 0:
                createNewEnemy(Turret.class, 1);
                createNewEnemy(Lavakut.class, 2);
                createNewEnemy(Worm.class, 1);
                break;
            case 1:
                createNewEnemy(InsectNest.class, 1);
                createNewEnemy(ArmoredInsect.class, 4);
                createNewEnemy(Lavakut.class, 2);
                break;
            case 2:
                createNewEnemy(Beetle.class, 3);
                createNewEnemy(Lavakut.class, 3);
                createNewEnemy(ArmoredInsect.class, 3);
                break;
            case 3:
                createNewEnemy(Insect.class, 4);
                createNewEnemy(MagicBall.class, 2);
                createNewEnemy(Turret.class, 1);
                break;
            case 4:
                createNewEnemy(InsectNest.class, 2);
                createNewEnemy(ArmoredInsect.class, 2);
                createNewEnemy(MagicBall.class, 4);
                break;
        }
    }
    private void wormLevel8() {
        Screen.getEnvironment().setSelf(ResourceManager.getImage("background2.png"));
        addEnemy(new BossIncomingSign(500, 150, 5, LightningWorm.class));
    }
    private void vampireLevel16() {
        Screen.getEnvironment().setSelf(ResourceManager.getImage("background3.png"));
        addEnemy(new BossIncomingSign(500, 150, 5, Vampire.class));
    }
    private void lavaLevel24() {
        Screen.getEnvironment().setSelf(ResourceManager.getImage("background4.png"));
        addEnemy(new BossIncomingSign(500, 150, 5, LavaGiant.class));
    }
    public static void centerPlayer() {
        Screen.getPlayer().setX(Screen.WINDOW_WIDTH/2 + (Screen.getPlayer().getWidth()/2));
        Screen.getPlayer().setY(Screen.WINDOW_HEIGHT/2 + (Screen.getPlayer().getHeight()/2));
    }

    private void bossRush() {
        createNewEnemy(LightningWorm.class, 1);
        createNewEnemy(LavaGiant.class, 1);
        createNewEnemy(Vampire.class, 1);
    }



    private static final MRectangle BOSS_INC_SIZE = new MRectangle(200, 50);
    private class BossIncomingSign extends Enemy {

        private int duration;
        private long start, current;
        private Class bossToSpawn;

        public BossIncomingSign(int x, int y, int duration, Class bossToSpawn) {
            super(ResourceManager.getImage("bossinc.png", BOSS_INC_SIZE.getWidth(), BOSS_INC_SIZE.getHeight()), x, y,
                    BOSS_INC_SIZE.getWidth(), BOSS_INC_SIZE.getHeight(), 0, 1000000, "Boss Incoming Sign", 0, 1);
            this.duration = duration;
            this.start = System.currentTimeMillis();
            this.bossToSpawn = bossToSpawn;

            centerPlayer();
            Screen.getPlayer().removeAllMines();

        }

        @Override
        public void chase(int targetX, int targetY) {
            current = System.currentTimeMillis();
        }

        @Override
        public void drawMe(Graphics g) {
            g.drawImage(getSelfImage(), getX(), getY(), null);
            g.setColor(Color.red);
            g.setFont(Parameters.ORIGINAL_FONT);
            g.setFont(new Font(g.getFont().getFontName(), g.getFont().getStyle(), (int)(g.getFont().getSize() * (Screen.WINDOW_WIDTH/(1200.0/1.75)))));
            g.drawString("BOSS INCOMING", (int)(519.0/1200.0 * Screen.WINDOW_WIDTH), (int)(182.0/600.0 * Screen.WINDOW_HEIGHT));
        }

        @Override
        public void update() {
            if (current - start > (duration * 1000)) {
                createNewEnemy(bossToSpawn, 1);
                if (bossToSpawn == Worm.class) {
                    MPoint scaledDistance = new MPoint(50, 50);

                    addEnemy(new Turret(scaledDistance.getX(), scaledDistance.getY())); // top left
                    addEnemy(new Turret(Screen.WINDOW_WIDTH - scaledDistance.getX(), scaledDistance.getY())); // top right
                    addEnemy(new Turret(scaledDistance.getX(), Screen.WINDOW_HEIGHT - scaledDistance.getY() - Turret.TURRET_HEIGHT)); // bottom left
                    addEnemy(new Turret(Screen.WINDOW_WIDTH - scaledDistance.getX(), Screen.WINDOW_HEIGHT - scaledDistance.getY() - Turret.TURRET_HEIGHT)); // bottom right
                }
                setHealth(0);
            }
        }

        @Override
        public void onDeath(double multiplier) {

        }
    }


}
