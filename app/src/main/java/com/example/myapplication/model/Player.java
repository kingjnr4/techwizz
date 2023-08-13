package com.example.myapplication.model;

public class Player {
    String playerName;
    String playerPosition;

    public Player(String playerName, String playerPosition) {
        this.playerName = playerName;
        this.playerPosition = playerPosition;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(String playerPosition) {
        this.playerPosition = playerPosition;
    }
}
