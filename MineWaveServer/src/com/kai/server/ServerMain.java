package com.kai.server;

public class ServerMain {
    public static final int SERVER_PORT = 8701;

    public static void main(String[] args) {
        ServerThread server = new ServerThread(SERVER_PORT);
        server.start();
    }
}
