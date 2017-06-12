package com.adaptionsoft.games.trivia;


import com.adaptionsoft.games.trivia.infra.MockMessageChannel;
import com.adaptionsoft.games.trivia.legacy.LegacyGameRunner;
import org.junit.Test;

import java.util.OptionalLong;

import static org.assertj.core.api.Assertions.assertThat;

public class GoldenMasterTest {

    @Test
    public void should_game_not_be_random() throws Exception {
        run();
    }

    private void run() {

        for (int i = 0; i < 100; i++) {

            MockMessageChannel mockMessageChannel = new MockMessageChannel();
            GameRunner.run(mockMessageChannel, OptionalLong.of(i));

            MockMessageChannel refactoredMessageChannel = new MockMessageChannel();
            LegacyGameRunner.run(refactoredMessageChannel, OptionalLong.of(i));

            assertThat(refactoredMessageChannel.getPrintedMessages()).containsAll(
                    mockMessageChannel.getPrintedMessages()
            );
        }
    }

}
