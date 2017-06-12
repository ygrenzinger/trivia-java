package com.adaptionsoft.games.trivia.infra;

import com.adaptionsoft.games.trivia.MessageChannel;

import java.util.ArrayList;
import java.util.List;

public class MockMessageChannel implements MessageChannel {

    private List<String> printedMessages = new ArrayList<>();

    @Override
    public void writeLine(String line) {
        printedMessages.add(line);
    }

    public List<String> getPrintedMessages() {
        return printedMessages;
    }
}
