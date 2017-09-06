package controller;

import controller.validator.HangmanValidation;
import lombok.val;
import common.model.GameState;
import model.HangmanGame;
import model.HangmanParts;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.params.provider.Arguments;
import util.DefaultConfig;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;

public class CommonTest {

    public static final DefaultConfig cfg = ConfigFactory.create(DefaultConfig.class);

    public static final String[] VALID_CHARS = "abcdefghijklmnopqrstuvwxyz".split("");
    public static final int MAX_GUESS_ATTEMPS = HangmanParts.values().length;


    public static final HangmanGame NEW_GAME = HangmanGame.builder()
            .secretWord("anyword")
            .gameState(GameState.STARTED)
            .actions(emptyList())
            .attempts(0)
            .validation(HangmanValidation.EMPTY).build();

    static Stream<Arguments> validInputProvider() {
        return stream(VALID_CHARS).map(Arguments::of);
    }

    private static final int MAX_RANDOM_GENERATION = 20;
    private static final int WORD_MAX_LENGTH = 20;

    static Stream<String> validSecretProvider() {
        val random = new Random();
        val max = random.nextInt(MAX_RANDOM_GENERATION) + 1; //Minimum one sample
        return IntStream.range(1, max).mapToObj(i -> generateValidSecret(random).toString());
    }

    private static StringBuilder generateValidSecret(final Random random) {
        val wordLength = random.nextInt(WORD_MAX_LENGTH) + 1; //Minimum one letter
        val builder =  new StringBuilder();

        IntStream.range(0, wordLength).forEach(i -> {
            val character = VALID_CHARS[random.nextInt(VALID_CHARS.length)];
            builder.append(character);
        });
        return builder;
    }
}


