package com.adaptionsoft.games.trivia.domain;

import com.adaptionsoft.games.trivia.MessageChannel;

import java.util.Optional;
import java.util.Random;

import static com.adaptionsoft.games.trivia.domain.QuestionType.*;

public class Game {

    private final MessageChannel messageChannel;
    private final Random random;
    private final QuestionsBag questionsBag;
    private final GamePlayers players = new GamePlayers();

    private Optional<Player> winner = Optional.empty();
    private Player currentPlayer;

    private boolean gettingOutOfPenaltyBox = false;

    public Game(MessageChannel messageChannel, Random random) {
        this.messageChannel = messageChannel;
        this.random = random;
        questionsBag = QuestionsBag.build();
    }

    public void add(String playerName) {
        players.add(Player.ofName(playerName));
        messageChannel.writeLine(playerName + " was added");
        messageChannel.writeLine("They are player number " + players.count());
    }

    public void run() {
        if (players.isEmpty()) return;
        
        while (!winner.isPresent()) {
            playTurn();
        }
    }

    private void playTurn() {
        currentPlayer = players.next();
        roll();
        if (random.nextInt(9) == 7) {
            handleWrongAnswer();
        } else {
            handleCorrectAnswer();
            checkIfWinner();
        }
    }

    private void roll() {
        int roll = random.nextInt(5) + 1;
        String playerName = currentPlayer.getName();
        messageChannel.writeLine(playerName + " is the current player");
        messageChannel.writeLine("They have rolled a " + roll);

        if (currentPlayer.isInPenaltyBox()) {
            if (roll % 2 == 0) {
                messageChannel.writeLine(playerName + " is not getting out of the penalty box");
                gettingOutOfPenaltyBox = false;
            } else {
                gettingOutOfPenaltyBox = true;
                messageChannel.writeLine(playerName + " is getting out of the penalty box");
                movePlayer(roll);
            }
        } else {
            movePlayer(roll);
        }

    }

    private void movePlayer(int roll) {
        int nextPlace = currentPlayer.getPlace() + roll;
        if (nextPlace > 11) {
            currentPlayer.setPlace(nextPlace - 12);
        } else {
            currentPlayer.setPlace(nextPlace);
        }

        messageChannel.writeLine(currentPlayer.getName() + "'s new location is " + currentPlayer.getPlace());
        askQuestion();
    }

    private void askQuestion() {
        QuestionType questionType = currentCategory();
        messageChannel.writeLine("The category is " + questionType);
        String question = questionsBag.takeQuestion(questionType);
        messageChannel.writeLine(question);
    }


    private QuestionType currentCategory() {
        int mod = currentPlayer.getPlace() % 4;
        switch (mod) {
            case 0: return POP;
            case 1: return SCIENCE;
            case 2: return SPORTS;
            default: return ROCK;
        }
    }

    private void handleCorrectAnswer() {
        if (!(currentPlayer.isInPenaltyBox() && !gettingOutOfPenaltyBox)) {
            messageChannel.writeLine("Answer was correct!!!!");
            currentPlayer.incPurse();
            messageChannel.writeLine(currentPlayer.getName() + " now has " + currentPlayer.getPurse() + " Gold Coins.");
        }
    }

    private void handleWrongAnswer() {
        messageChannel.writeLine("Question was incorrectly answered");
        messageChannel.writeLine(currentPlayer.getName() + " was sent to the penalty box");
        currentPlayer.goInPenaltyBox();
    }


    private void checkIfWinner() {
        if (currentPlayer.isWinner()) {
            winner = Optional.of(currentPlayer);
        }
    }
}
