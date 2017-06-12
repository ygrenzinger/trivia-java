package com.adaptionsoft.games.trivia.domain;

public enum QuestionType {

    POP("Pop"), SCIENCE("Science"), SPORTS("Sports"), ROCK("Rock");

    private final String label;

    QuestionType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
