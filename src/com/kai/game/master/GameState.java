package com.kai.game.master;

public enum GameState {
    MENU("Menu", 0), SELECT("Selection Screen", 1), RUNNING("Running", 2), DEATH("Death Screen", 3);

    private String name;
    private int number;

    GameState(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
       return name;
    }

    public int getNumber() {
        return number;
    }
}
