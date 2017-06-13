package com.adaptionsoft.games.trivia.domain;

import java.util.Deque;
import java.util.LinkedList;

import static com.adaptionsoft.games.trivia.domain.QuestionType.*;

class QuestionsBag {
    private Deque<String> popQuestions = new LinkedList<>();
    private Deque<String> scienceQuestions = new LinkedList<>();
    private Deque<String> sportsQuestions = new LinkedList<>();
    private Deque<String> rockQuestions = new LinkedList<>();


    static QuestionsBag build() {
        QuestionsBag questionsBag = new QuestionsBag();
        for (int i = 0; i < 50; i++) {
            questionsBag.popQuestions.addLast("Pop Question " + i);
            questionsBag.scienceQuestions.addLast(("Science Question " + i));
            questionsBag.sportsQuestions.addLast(("Sports Question " + i));
            questionsBag.rockQuestions.addLast("Rock Question " + i);
        }
        return questionsBag;
    }


    Question takeQuestion(int place) {
        QuestionType questionType = category(place);
        String question = retrieveQuestion(questionType);
        return new Question(question, questionType);
    }

    private QuestionType category(int place) {
        switch (place % 4) {
            case 0:
                return POP;
            case 1:
                return SCIENCE;
            case 2:
                return SPORTS;
            default:
                return ROCK;
        }
    }

    private String retrieveQuestion(QuestionType questionType) {
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

