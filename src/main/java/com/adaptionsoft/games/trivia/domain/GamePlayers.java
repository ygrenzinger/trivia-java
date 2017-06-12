package com.adaptionsoft.games.trivia.domain;

import java.util.ArrayList;
import java.util.List;

public class GamePlayers {

    private final List<Player> players = new ArrayList<>();

    private int index = 0;

    public void add(Player player) {
        players.add(player);
    }

    public Player next() {
       Player player = players.get(index);
       index++;
       if (index == players.size()) index = 0;
       return player;
    }

    public int count() {
        return players.size();
    }

    public boolean isEmpty() {
        return players.isEmpty();
    }
    
}
