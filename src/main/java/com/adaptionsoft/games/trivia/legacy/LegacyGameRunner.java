
package com.adaptionsoft.games.trivia.legacy;

import com.adaptionsoft.games.trivia.MessageChannel;
import com.adaptionsoft.games.trivia.infra.ConsoleMessageChannel;

import java.util.OptionalLong;
import java.util.Random;


public class LegacyGameRunner {

	public static void run(MessageChannel messageChannel, OptionalLong seed) {
		LegacyGame aLegacyGame = new LegacyGame(messageChannel);

		aLegacyGame.add("Chet");
		aLegacyGame.add("Pat");
		aLegacyGame.add("Sue");

		Random rand = new Random();
		seed.ifPresent(rand::setSeed);

		boolean notAWinner;
		do {

			aLegacyGame.roll(rand.nextInt(5) + 1);

			if (rand.nextInt(9) == 7) {
				notAWinner = aLegacyGame.wrongAnswer();
			} else {
				notAWinner = aLegacyGame.wasCorrectlyAnswered();
			}



		} while (notAWinner);
	}

	public static void main(String[] args) {
		run(new ConsoleMessageChannel(), OptionalLong.empty());

	}
}
