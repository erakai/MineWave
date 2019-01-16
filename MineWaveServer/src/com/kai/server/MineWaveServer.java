package com.kai.server;

public class MineWaveServer {
    public static final int SERVER_PORT = 8701;

    //TODO: I think MineWaveServer ( the module ) might be getting bundled with the client in the client jar.
    //^ Investigate? Need to fix the artifact in project structure.


    /*=========

        TO ANYBODY NOT KAI LOOKING AT THIS

        This server will only ever have one instance in existence, which I will be running on a vps.
        Please do not run this or delete the leaderboard or anything.

    =========*/

    public static void main(String[] args) {
        ServerThread server = new ServerThread(SERVER_PORT);
        server.start();
    }
}
