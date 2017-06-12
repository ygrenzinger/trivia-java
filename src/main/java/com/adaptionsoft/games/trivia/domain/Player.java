package com.adaptionsoft.games.trivia.domain;

public class Player {
    private final String name;
    private int purse = 0;

    private int place = 0;
    private boolean inPenaltyBox = false;

    private Player(String name) {
        this.name = name;
    }

    public static Player ofName(String name) {
        return new Player(name);
    }

    public String getName() {
         return name;
    }

    public void incPurse() {
        purse++;
    }

    public int getPurse() {
        return purse;
    }

    public void goInPenaltyBox() {
        inPenaltyBox = true;
    }

    public boolean isInPenaltyBox() {
        return inPenaltyBox;
    }

    public boolean isWinner() {
        return purse == 6;
    }

    public int move(int roll) {
        place = place + roll;
        if (place > Game.MAX_ALLOWED_POSITION) {
            place = place - (Game.MAX_ALLOWED_POSITION + 1);
        }
        return place;
    }

    public int getPlace() {
        return place;
    }
}
