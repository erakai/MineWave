package com.kai;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerThread extends Thread {
    private final int SERVER_PORT;

    private final String FILE_NAME = "leaderboard.txt";

    private ServerDisplay window;

    List<ClientHandler> clients;

    List<Death> currentLeaderboard;

    public ServerThread(int port) {
        this.SERVER_PORT = port;
        clients = new ArrayList<>();
        ServerDisplay.init(this);
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
            ObjectInputStream oi = new ObjectInputStream(f);
            List<Death> test = (List<Death>)oi.readObject();
            if (test == null) {
                currentLeaderboard = new ArrayList<>();
            } else if (test.isEmpty()) {
                currentLeaderboard = new ArrayList<>();
            } else {
                currentLeaderboard = test;
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
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
            } catch (IOException ex) { window.log("Error with client " + socket.getInetAddress()); };
        }

        @Override
        public void run() {

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
            return "Name: " + name + "\nKilled By: " + killedBy + "\nAbility: " + ability + "\nLevel: " + level;
        }
    }
}
