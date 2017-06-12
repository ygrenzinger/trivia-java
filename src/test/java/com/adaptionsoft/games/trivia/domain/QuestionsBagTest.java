package com.adaptionsoft.games.trivia.domain;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionsBagTest {
    private QuestionsBag questionsBag;

    @Before
    public void before() {
        questionsBag = QuestionsBag.build();
    }

    @Test
    public void should_build_a_bag_full_of_questions() {
         assertThat(questionsBag);
    }

}