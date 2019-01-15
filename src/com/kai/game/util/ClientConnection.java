package com.kai.game.util;

import com.kai.game.hud.DeathScreen;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClientConnection extends Thread {
    private Socket socket;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    DeathScreen.Death clientDeath;
    private boolean madeOnLeaderboards;
    private List<DeathScreen.Death> givenLeaderboard;


    public static boolean CONNECTED = false;

    public ClientConnection() {
        try {
            socket = new Socket(Parameters.SERVER_NAME, Parameters.SERVER_PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            CONNECTED = true;
        } catch (Exception ex) { CONNECTED = false; }

    }

    public void setClientDeath(DeathScreen.Death d) {
        this.clientDeath = d;
    }


    @Override
    public void run() {
        try {
            getLeaderboard(clientDeath);
        } catch (IOException | ClassNotFoundException e) { e.printStackTrace(); }
    }

    public void getLeaderboard(DeathScreen.Death d) throws IOException, ClassNotFoundException {
        if (CONNECTED) {
            out.writeObject(d);
            Object received;
            while ((received = in.readObject()) != null) {
                if (received instanceof Boolean) {
                    madeOnLeaderboards = (Boolean)received;
                }
                if (received instanceof List) {
                    givenLeaderboard = (List<DeathScreen.Death>)received;
                }
            }
        }
    }

    public boolean isMadeOnLeaderboards() {
        if (!CONNECTED) { return false; }
        return madeOnLeaderboards;
    }

    public List<DeathScreen.Death> getGivenLeaderboard() {
        if (!CONNECTED) { return null; }
        return givenLeaderboard;
    }

    public Socket getSocket() {
        return socket;
    }

    public static boolean isCONNECTED() {
        return CONNECTED;
    }
}
