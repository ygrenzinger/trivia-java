package com.adaptionsoft.games.trivia.domain;

import com.adaptionsoft.games.trivia.MessageChannel;

import java.util.Optional;
import java.util.Random;

public class Game {
    static final int MAX_ALLOWED_POSITION = 11;

    private final MessageChannel messageChannel;
    private final Random random;
    private final QuestionsBag questionsBag;
    private final GamePlayers players = new GamePlayers();

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

        Optional<Player> winner = playTurn();
        while (!winner.isPresent()) {
            winner = playTurn();
        }
    }

    private Optional<Player> playTurn() {
        currentPlayer = players.next();
        movePlayer();
        if (isCorrectQuestion()) {
            handleWrongAnswer();
        } else {
            handleCorrectAnswer();
        }
        return checkIfWinner();
    }

    private boolean isCorrectQuestion() {
        return random.nextInt(9) == 7;
    }

    private void movePlayer() {
        int roll = roll();
        String playerName = currentPlayer.getName();
        messageChannel.writeLine(playerName + " is the current player");
        messageChannel.writeLine("They have rolled a " + roll);

        if (currentPlayer.isInPenaltyBox()) {
            if (roll % 2 == 0) {
                gettingOutOfPenaltyBox = false;
                messageChannel.writeLine(playerName + " is not getting out of the penalty box");
            } else {
                gettingOutOfPenaltyBox = true;
                messageChannel.writeLine(playerName + " is getting out of the penalty box");
            }
        }

        if (!currentPlayer.isInPenaltyBox() || gettingOutOfPenaltyBox) {
            movePlayer(roll);
        }

    }

    private int roll() {
        return random.nextInt(5) + 1;
    }

    private void movePlayer(int roll) {
        int nextPlace = currentPlayer.move(roll);
        messageChannel.writeLine(currentPlayer.getName() + "'s new location is " + nextPlace);
        askQuestion();
    }

    private void askQuestion() {
        Question question = questionsBag.takeQuestion(currentPlayer.getPlace());
        messageChannel.writeLine("The category is " + question.getQuestionType());
        messageChannel.writeLine(question.getQuestion());
    }

    private void handleCorrectAnswer() {
        if (!currentPlayer.isInPenaltyBox() || gettingOutOfPenaltyBox) {
            messageChannel.writeLine("Answer was correct!!!!");
            currentPlayer.answerCorrectly();
            messageChannel.writeLine(currentPlayer.getName() + " now has " + currentPlayer.getPurse() + " Gold Coins.");
        }
    }

    private void handleWrongAnswer() {
        messageChannel.writeLine("Question was incorrectly answered");
        messageChannel.writeLine(currentPlayer.getName() + " was sent to the penalty box");
        currentPlayer.goInPenaltyBox();
    }

    private Optional<Player> checkIfWinner() {
        if (currentPlayer.isWinner()) {
            return Optional.of(currentPlayer);
        }
        return Optional.empty();
    }
}
