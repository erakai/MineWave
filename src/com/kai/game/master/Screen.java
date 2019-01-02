package com.kai.game.master;

import com.kai.game.GameObject;
import com.kai.game.Updatable;
import com.kai.game.entities.Player;
import com.kai.game.hud.InGameDisplay;
import com.kai.game.hud.MainMenu;
import com.kai.game.hud.SelectionScreen;
import com.kai.game.scene.Environment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Screen extends JPanel implements KeyListener, MouseListener, MouseMotionListener {
    //Either 1000/500 or 1200/600 is recommended, imo.
    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = WINDOW_WIDTH/2;
    public static final int FRAMES_PER_SECOND = 60;

    public static GameState state;

    //Handles background and scene objects.
    private static GameObject environment;
    //Handles the player.
    private static Player player;
    //Handles level management and enemies.
    private static LevelHandler levelHandler;
    //Handles misc objects that need to be drawn or updated.
    private static List<Updatable> toUpdate;
    //Handles all user interface.
    private static List<GameObject> userInterface;

    public Screen() {
        toUpdate = new ArrayList<>();
        userInterface = new ArrayList<>();

        transitionToScene(GameState.MENU);

        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Environment manages all scene objects.
        if (environment != null) {
            environment.drawMe(g);
        }

        for (Updatable u: toUpdate) {
            if (u instanceof GameObject) {
                ((GameObject) u).drawMe(g);
            }
        }

        //LevelHandler manages all enemies.
        if (levelHandler != null) {
            levelHandler.drawAllEnemies(g);
        }

        for (GameObject UI: userInterface) {
            UI.drawMe(g);
        }

    }

    private void update() {

        //Respond to all player input.
        Input.updateChanges();

        if (levelHandler != null) {
            levelHandler.updateEnemies();
        }

        for (Updatable u: toUpdate) {
            u.update();
        }

        if (state == GameState.RUNNING && player != null) {
            if (checkForGameOver()) {
                transitionToScene(GameState.DEATH);
            }
        }

    }

    public void animate() {
        while(true) {

            repaint();

            try {
                Thread.sleep(1000/FRAMES_PER_SECOND);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            update();

        }
    }

    private boolean checkForGameOver() {
        return (getPlayer().getHealth() < 1);
    }

    public static void transitionToScene(GameState newState) {
        state = newState;
        toUpdate.clear();
        userInterface.clear();
        environment = null;
        player = null;
        levelHandler = null;

        switch(newState.getName()) {
            case "Menu":
                userInterface.add(new MainMenu());
                break;
            case "Selection Screen":
                userInterface.add(new SelectionScreen());
                break;
            case "Running":
                environment = new Environment();

                player = new Player(WINDOW_WIDTH/2,WINDOW_HEIGHT/2, (int)(22.0/1200.0 * WINDOW_WIDTH), (int)(60.0/600.0 * WINDOW_HEIGHT));
                addUpdatable(player);

                levelHandler = new LevelHandler(1);
                addUpdatable(levelHandler);

                addUpdatable(new InGameDisplay((int)(310.0/1200.0 * Screen.WINDOW_WIDTH), (int)(500.0/600.0 * Screen.WINDOW_HEIGHT)));

                break;
            case "Death Screen":

                break;
        }
    }

    public static GameObject getEnvironment() {
        return environment;
    }

    public static Player getPlayer() {
        return player;
    }

    public static LevelHandler getLevelHandler() { return levelHandler; }

    public static void addUpdatable(Updatable e) {
        toUpdate.add(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {Input.keyPressed(e.getKeyCode());}

    @Override
    public void keyReleased(KeyEvent e) {Input.keyReleased(e.getKeyCode());}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {Input.mouseClicked(e.getX(), e.getY());}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) { }

    @Override
    public void mouseMoved(MouseEvent e) {Input.mouseMoved(e.getX(), e.getY());}
}
