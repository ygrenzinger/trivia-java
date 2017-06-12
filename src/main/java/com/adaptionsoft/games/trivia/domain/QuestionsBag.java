package com.adaptionsoft.games.trivia.domain;

import java.util.Deque;
import java.util.LinkedList;

public class QuestionsBag {
    private Deque<String> popQuestions = new LinkedList<>();
    private Deque<String> scienceQuestions = new LinkedList<>();
    private Deque<String> sportsQuestions = new LinkedList<>();
    private Deque<String> rockQuestions = new LinkedList<>();


    public static QuestionsBag build() {
        QuestionsBag questionsBag = new QuestionsBag();
        for (int i = 0; i < 50; i++) {
            questionsBag.popQuestions.addLast("Pop Question " + i);
            questionsBag.scienceQuestions.addLast(("Science Question " + i));
            questionsBag.sportsQuestions.addLast(("Sports Question " + i));
            questionsBag.rockQuestions.addLast("Rock Question " + i);
        }
        return questionsBag;
    }


    public String takeQuestion(QuestionType questionType) {
        switch (questionType) {
            case POP:
                return popQuestions.removeFirst();
            case ROCK:
                return rockQuestions.removeFirst();
            case SPORTS:
                return sportsQuestions.removeFirst();
            case SCIENCE:
                return scienceQuestions.removeFirst();
            default:
                throw new RuntimeException("This type of question is not managed");
        }
    }

}

