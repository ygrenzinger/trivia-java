package com.adaptionsoft.games.trivia.legacy;

import com.adaptionsoft.games.trivia.MessageChannel;

import java.util.ArrayList;
import java.util.LinkedList;

public class LegacyGame {
    ArrayList players = new ArrayList();
    int[] places = new int[6];
    int[] purses = new int[6];
    boolean[] inPenaltyBox = new boolean[6];

    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    private final MessageChannel messageChannel;

    public LegacyGame(MessageChannel messageChannel) {
        this.messageChannel = messageChannel;
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast(("Science Question " + i));
            sportsQuestions.addLast(("Sports Question " + i));
            rockQuestions.addLast(createRockQuestion(i));
        }
    }

    public String createRockQuestion(int index) {
        return "Rock Question " + index;
    }

    public boolean isPlayable() {
        return (howManyPlayers() >= 2);
    }

    public boolean add(String playerName) {


        players.add(playerName);
        places[howManyPlayers()] = 0;
        purses[howManyPlayers()] = 0;
        inPenaltyBox[howManyPlayers()] = false;

        messageChannel.writeLine(playerName + " was added");
        messageChannel.writeLine("They are player number " + players.size());
        return true;
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        messageChannel.writeLine(players.get(currentPlayer) + " is the current player");
        messageChannel.writeLine("They have rolled a " + roll);

        if (inPenaltyBox[currentPlayer]) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                messageChannel.writeLine(players.get(currentPlayer) + " is getting out of the penalty box");
                places[currentPlayer] = places[currentPlayer] + roll;
                if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

                messageChannel.writeLine(players.get(currentPlayer)
                        + "'s new location is "
                        + places[currentPlayer]);
                messageChannel.writeLine("The category is " + currentCategory());
                askQuestion();
            } else {
                messageChannel.writeLine(players.get(currentPlayer) + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {

            places[currentPlayer] = places[currentPlayer] + roll;
            if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

            messageChannel.writeLine(players.get(currentPlayer)
                    + "'s new location is "
                    + places[currentPlayer]);
            messageChannel.writeLine("The category is " + currentCategory());
            askQuestion();
        }

    }

    private void askQuestion() {
        if (currentCategory() == "Pop")
            messageChannel.writeLine(popQuestions.removeFirst().toString());
        if (currentCategory() == "Science")
            messageChannel.writeLine(scienceQuestions.removeFirst().toString());
        if (currentCategory() == "Sports")
            messageChannel.writeLine(sportsQuestions.removeFirst().toString());
        if (currentCategory() == "Rock")
            messageChannel.writeLine(rockQuestions.removeFirst().toString());
    }


    private String currentCategory() {
        if (places[currentPlayer] == 0) return "Pop";
        if (places[currentPlayer] == 4) return "Pop";
        if (places[currentPlayer] == 8) return "Pop";
        if (places[currentPlayer] == 1) return "Science";
        if (places[currentPlayer] == 5) return "Science";
        if (places[currentPlayer] == 9) return "Science";
        if (places[currentPlayer] == 2) return "Sports";
        if (places[currentPlayer] == 6) return "Sports";
        if (places[currentPlayer] == 10) return "Sports";
        return "Rock";
    }

    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox[currentPlayer]) {
            if (isGettingOutOfPenaltyBox) {
                messageChannel.writeLine("Answer was correct!!!!");
                purses[currentPlayer]++;
                messageChannel.writeLine(players.get(currentPlayer)
                        + " now has "
                        + purses[currentPlayer]
                        + " Gold Coins.");

                boolean winner = didPlayerWin();
                currentPlayer++;
                goNextTurn();

                return winner;
            } else {
                currentPlayer++;
                goNextTurn();
                return true;
            }


        } else {

            messageChannel.writeLine("Answer was correct!!!!");
            purses[currentPlayer]++;
            messageChannel.writeLine(players.get(currentPlayer)
                    + " now has "
                    + purses[currentPlayer]
                    + " Gold Coins.");

            boolean winner = didPlayerWin();
            currentPlayer++;
            goNextTurn();

            return winner;
        }
    }

    private void goNextTurn() {
        if (currentPlayer == players.size()) currentPlayer = 0;
    }

    public boolean wrongAnswer() {
        messageChannel.writeLine("Question was incorrectly answered");
        messageChannel.writeLine(players.get(currentPlayer) + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;

        currentPlayer++;
        goNextTurn();
        return true;
    }


    private boolean didPlayerWin() {
        return !(purses[currentPlayer] == 6);
    }
}
