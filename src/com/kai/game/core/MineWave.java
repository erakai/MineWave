package com.kai.game.core;

import javax.swing.*;

public class MineWave {

    //TODO: Before launching the main game, have a dialogue asking what size window the user would like.

    public static void main(String[] args) {
        JFrame frame = new JFrame("MineWave");
        Screen screen = new Screen(frame);
        frame.add(screen);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        screen.animate();
    }
}
