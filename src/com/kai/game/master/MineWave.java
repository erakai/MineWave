package com.kai.game.master;

import javax.swing.*;

public class MineWave {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Mine Wave");
        Screen screen = new Screen();
        frame.add(screen);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        screen.animate();
    }
}
