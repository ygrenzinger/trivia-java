package com.adaptionsoft.games.trivia.domain;

class Player {
    private final String name;

    private int purse = 0;
    private int place = 0;
    private boolean inPenaltyBox = false;

    private Player(String name) {
        this.name = name;
    }

    static Player ofName(String name) {
        return new Player(name);
    }

    void answerCorrectly() {
        purse++;
    }

    void goInPenaltyBox() {
        inPenaltyBox = true;
    }

    int move(int roll) {
        place = place + roll;
        if (place > Game.MAX_ALLOWED_POSITION) {
            place = place - (Game.MAX_ALLOWED_POSITION + 1);
        }
        return place;
    }

    boolean isWinner() {
        return purse == 6;
    }

    boolean isInPenaltyBox() {
        return inPenaltyBox;
    }

    int getPlace() {
        return place;
    }

    String getName() {
        return name;
    }

    int getPurse() {
        return purse;
    }
    
}
