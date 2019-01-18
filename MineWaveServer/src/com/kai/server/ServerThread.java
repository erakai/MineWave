package com.kai.server;

import com.kai.game.hud.DeathScreen;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServerThread extends Thread {
    private final int SERVER_PORT;

    //TODO: Stop eoff exceptions that occur when a client disconnects.

    //private final String FILE_NAME = "MineWaveServer/src/com/kai/leaderboard/leaderboard.txt";
    private final String FILE_NAME = "../leaderboard.txt";

    private ServerDisplay window;

    List<ClientHandler> clients;

    List<DeathScreen.Death> currentLeaderboard;

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
        } catch (FileNotFoundException ex ) {
            File file = new File(FILE_NAME);
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loadLeaderboardFromFile() {
        try (FileInputStream f = new FileInputStream(new File(FILE_NAME))) {
            if (f.available() > 0) {
                ObjectInputStream oi = new ObjectInputStream(f);
                List<DeathScreen.Death> test = (List<DeathScreen.Death>) oi.readObject();
                if (test == null) {
                    currentLeaderboard = new ArrayList<>();
                } else if (test.isEmpty()) {
                    currentLeaderboard = new ArrayList<>();
                } else {
                    currentLeaderboard = test;
                }
            }
        } catch (FileNotFoundException e) { saveLeaderboardToFile(); }
        catch (Exception ex ) { ex.printStackTrace(); }
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

    //Check if the received death is on the leaderboard
    private boolean checkIfEligible(DeathScreen.Death newDeath) {
        if (currentLeaderboard == null) { return true; }
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

        DeathScreen.Death newDeath;
        Boolean onLeaderboard;
        private void beganReceiving() throws IOException, ClassNotFoundException {
            while ((newDeath = (DeathScreen.Death)in.readObject()) != null) {
                window.log("Death received from " + socket.getInetAddress() +": \n" + newDeath + "\n --- ");
                onLeaderboard = false;
                if (checkIfEligible(newDeath)) {
                    if (currentLeaderboard == null) {
                        currentLeaderboard = new ArrayList<>();
                    }
                    onLeaderboard = true;
                    if (currentLeaderboard.size() > 9) {
                        currentLeaderboard.remove(9);
                    }
                    currentLeaderboard.add(newDeath);
                }

                out.writeObject(onLeaderboard);
                out.writeObject(currentLeaderboard);

                saveLeaderboardToFile();
            }
            socket.close();
        }
    }

}
