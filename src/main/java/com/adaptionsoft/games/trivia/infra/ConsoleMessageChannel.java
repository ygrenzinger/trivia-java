package com.adaptionsoft.games.trivia.infra;

import com.adaptionsoft.games.trivia.MessageChannel;

public class ConsoleMessageChannel implements MessageChannel {
    @Override
    public void writeLine(String line) {
        System.out.println(line);
    }
}
