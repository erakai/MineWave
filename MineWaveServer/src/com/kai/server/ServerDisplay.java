package com.kai.server;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ServerDisplay extends JPanel {


    public static JFrame frame;
    public static ServerDisplay mainPanel;
    public static ServerThread myServer;

    private JTextArea loggingDisplay;

    private PrintWriter pw;
    private File logFile;


    public ServerDisplay(LayoutManager layout) {
        super(layout);

        logFile = new File("../log.txt");
        initFileLog();
    }

    private void initFileLog() {
        try {
            pw = new PrintWriter(new FileWriter(logFile, true));
        } catch (FileNotFoundException ex) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addComponents() {
        GridBagConstraints c = new GridBagConstraints();

        loggingDisplay = new JTextArea(30,60);
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
        String date = "[" + sdf.format(cal.getTime()) + "]";

        loggingDisplay.append(date + " " + toLog + "\n");
        System.out.println(date + " " + toLog);
        fileLog(date + " " + toLog);
    }

    private void fileLog(String toLog) {
        pw.write(toLog + "\n");
        pw.flush();
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