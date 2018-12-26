package com.kai.game.master;

import com.kai.game.GameObject;
import com.kai.game.Updatable;
import com.kai.game.entities.Entity;
import com.kai.game.scene.Environment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class Screen extends JPanel implements KeyListener, MouseListener {
    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 500;
    public static final int FRAMES_PER_SECOND = 100;

    public static GameState state;

    private static GameObject environment;
    private static List<Updatable> entities;

    public Screen() {
        entities = new ArrayList<>();

        transitionToScene(GameState.MENU);

        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
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
            environment.paintComponent(g);
        }

        for (Updatable u: entities) {
            if (u instanceof GameObject) {
                ((GameObject) u).paintComponent(g);
            }
        }

    }

    private void update() {
        for (Updatable u: entities) {
            u.update();
        }
    }

    public void animate() {
        while(true) {

            try {
                Thread.sleep(1000/FRAMES_PER_SECOND);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            update();
            repaint();

        }
    }

    public static void transitionToScene(GameState newState) {
        state = newState;
        entities.clear();
        environment = null;

        switch(newState.getName()) {
            case "Menu":

                break;
            case "Selection Screen":

                break;
            case "Running":
                environment = new Environment();
                break;
            case "Death Screen":

                break;
        }
    }

    public static GameObject getEnvironment() {
        return environment;
    }


    public static void addUpdatable(Updatable e) {
        entities.add(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {Input.keyPressed(e.getKeyCode());}

    @Override
    public void keyReleased(KeyEvent e) {Input.keyReleased(e.getKeyCode());}

    @Override
    public void mouseClicked(MouseEvent e) {Input.mouseClicked(e.getX(), e.getY());}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
