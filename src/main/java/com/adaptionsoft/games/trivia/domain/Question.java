package com.adaptionsoft.games.trivia.domain;

class Question {
    private final String question;
    private final QuestionType questionType;

    Question(String question, QuestionType questionType) {
        this.question = question;
        this.questionType = questionType;
    }

    String getQuestion() {
        return question;
    }

    QuestionType getQuestionType() {
        return questionType;
    }
}
