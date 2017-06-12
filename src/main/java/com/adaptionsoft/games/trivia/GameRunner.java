
package com.adaptionsoft.games.trivia;
import java.util.OptionalLong;
import java.util.Random;

import com.adaptionsoft.games.trivia.domain.Game;
import com.adaptionsoft.games.trivia.infra.ConsoleMessageChannel;


public class GameRunner {

	public static void run(MessageChannel messageChannel, OptionalLong seed) {

		Random random = new Random();
		seed.ifPresent(random::setSeed);

		Game game = new Game(messageChannel, random);

		game.add("Chet");
		game.add("Pat");
		game.add("Sue");

		game.run();
	}

	public static void main(String[] args) {
		run(new ConsoleMessageChannel(), OptionalLong.empty());
	}
}
