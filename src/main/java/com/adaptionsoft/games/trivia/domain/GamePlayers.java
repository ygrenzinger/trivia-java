package com.adaptionsoft.games.trivia.domain;

import java.util.ArrayList;
import java.util.List;

class GamePlayers {

    private final List<Player> players = new ArrayList<>();

    private int index = 0;

    void add(Player player) {
        players.add(player);
    }

    Player next() {
        Player player = players.get(index);
        index++;
        if (index == players.size()) index = 0;
        return player;
    }

    int count() {
        return players.size();
    }

    boolean isEmpty() {
        return players.isEmpty();
    }

}
