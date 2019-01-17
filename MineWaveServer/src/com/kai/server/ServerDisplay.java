package com.kai.server;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ServerDisplay extends JPanel {

    /*
    Pretty much copy pasted from a previous project I did, no reason not to tbh.
     */

    public static JFrame frame;
    public static ServerDisplay mainPanel;
    public static ServerThread myServer;

    private JTextArea loggingDisplay;


    public ServerDisplay(LayoutManager layout) {
        super(layout);
    }

    private void addComponents() {
        GridBagConstraints c = new GridBagConstraints();

        loggingDisplay = new JTextArea(40,80);
        c.insets = new Insets(10, 10, 10, 10);
        loggingDisplay.setLineWrap(false);
        loggingDisplay.setWrapStyleWord(true);
        loggingDisplay.setFont(new Font(loggingDisplay.getFont().getName(), loggingDisplay.getFont().getStyle(), (int)(loggingDisplay.getFont().getSize()*1.2)));
        JScrollPane areaScrollPane = new JScrollPane(loggingDisplay);
        loggingDisplay.setEditable(false);
        areaScrollPane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        c.gridwidth = 2;
        add(areaScrollPane);
    }

    public void log(String toLog) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        loggingDisplay.append("[" + sdf.format(cal.getTime()) + "] " + toLog + "\n");
        System.out.println(toLog);
        //TODO: Write log to a file.
    }

    public static ServerDisplay init(ServerThread myServer) {
        ServerDisplay.myServer = myServer;
        frame = new JFrame("MineWaveServer");
        mainPanel = new ServerDisplay(new GridBagLayout());
        mainPanel.addComponents();
        frame.add(mainPanel);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        return mainPanel;
    }

}