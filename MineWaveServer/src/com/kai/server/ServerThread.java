package com.kai.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServerThread extends Thread {
    private final int SERVER_PORT;

    private final String FILE_NAME = "MineWaveServer/src/com/kai/leaderboard/leaderboard.txt";

    private ServerDisplay window;

    List<ClientHandler> clients;

    List<Death> currentLeaderboard;

    public ServerThread(int port) {
        this.SERVER_PORT = port;
        clients = new ArrayList<>();
        window = ServerDisplay.init(this);
        loadLeaderboardFromFile();
    }



    private void saveLeaderboardToFile() {
        try (FileOutputStream f = new FileOutputStream(new File(FILE_NAME))) {
                ObjectOutputStream o = new ObjectOutputStream(f);
                o.writeObject(currentLeaderboard);
                o.close();
        } catch (Exception ex ) { ex.printStackTrace(); }
    }

    private void loadLeaderboardFromFile() {
        try (FileInputStream f = new FileInputStream(new File(FILE_NAME))) {
            if (f.available() > 0) {
                ObjectInputStream oi = new ObjectInputStream(f);
                List<Death> test = (List<Death>) oi.readObject();
                if (test == null) {
                    currentLeaderboard = new ArrayList<>();
                } else if (test.isEmpty()) {
                    currentLeaderboard = new ArrayList<>();
                } else {
                    currentLeaderboard = test;
                }
            }
        } catch (Exception ex ) { ex.printStackTrace(); }
    }


    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            window.log("Server started on port " + SERVER_PORT);
            while(true) {
                Socket connectedSocket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(connectedSocket);
                clients.add(handler);
                new Thread(handler).start();

                saveLeaderboardToFile();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //Check if the received death is on the leaderboard
    private boolean checkIfEligible(Death newDeath) {
        if (currentLeaderboard.size() < 10) { return true; }
        Collections.sort(currentLeaderboard);
        return (newDeath.getLevel() > currentLeaderboard.get(9).getLevel());
    }

    private class ClientHandler implements Runnable {
        private Socket socket;

        private ObjectOutputStream out;
        private ObjectInputStream in;


        public ClientHandler(Socket socket) {
            this.socket = socket;
            window.log("ClientHandler created for client " + socket.getInetAddress());
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
            } catch (IOException ex) { window.log("Error with client " + socket.getInetAddress()); }
        }

        @Override
        public void run() {
            try {
                beganReceiving();
            } catch (Exception ex) { ex.printStackTrace(); }
        }

        Death newDeath;
        Boolean onLeaderboard;
        private void beganReceiving() throws IOException, ClassNotFoundException {
            while ((newDeath = (Death)in.readObject()) != null) {
                window.log("Death received from " + socket.getInetAddress() +": \n" + newDeath + "\n --- ");
                onLeaderboard = false;
                if (checkIfEligible(newDeath)) {
                    onLeaderboard = true;
                    if (currentLeaderboard.size() > 9) {
                        currentLeaderboard.remove(9);
                    }
                    currentLeaderboard.add(newDeath);
                }
                out.writeObject(onLeaderboard);
                out.writeObject(currentLeaderboard);
            }
        }
    }

    private static class Death implements Comparable<Death>, Serializable {
        private String name, killedBy, ability;
        private int level;

        Death(String name, String killedBy, String ability, int level) {
            this.name = name;
            this.killedBy = killedBy;
            this.ability = ability;
            this.level = level;
        }

        @Override
        public int compareTo(Death o) {
            if (level < o.getLevel()) { return -1;
            } else if (level > o.getLevel()) { return 1; }
            return 0;
        }

        public String getName() {
            return name;
        }

        public String getKilledBy() {
            return killedBy;
        }

        public String getAbility() {
            return ability;
        }

        public int getLevel() {
            return level;
        }

        @Override
        public String toString() {
            return "Name: " + name + "\nKilled By: " + killedBy + "\nAbility: " + ability + "\nLevel: " + level + "\n-";
        }
    }
}
