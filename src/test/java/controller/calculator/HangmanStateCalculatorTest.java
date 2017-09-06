package controller.calculator;

import controller.CommonTest;
import controller.validator.HangmanValidation;
import lombok.val;
import common.model.GameState;
import common.model.UserAction;
import model.HangmanGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class HangmanStateCalculatorTest extends CommonTest {

    private HangmanStateCalculator calculator = new HangmanStateCalculator();
    private HangmanGame.HangmanGameBuilder defaultBuilder;
    private String wrongGuess = "ABCDEFGH";


    @BeforeEach
    void initAll() {
        defaultBuilder = HangmanGame.builder()
                .secretWord("anyword")
                .attempts(0)
                .gameState(GameState.STARTED)
                .validation(HangmanValidation.EMPTY);

    }

    @DisplayName("Win the game if has guessed all the letters before the last attempt")
    @ParameterizedTest
    @MethodSource("validSecretProvider")
    void winningGame(final String secretWord) {
        // Given
        val allRightActions = toListOfActions(secretWord);

        val game = defaultBuilder.secretWord(secretWord).actions(allRightActions).build();

        // When
        val result = calculator.calculate(game);

        // Then
        assertThat(result, is(equalTo(GameState.WON)));
    }

    List<UserAction> toListOfActions(final String secretWord) {
        return Arrays.stream(secretWord.split("")).map(UserAction::new).collect(Collectors.toList());
    }


    @Test
    @DisplayName("Win if the number of attempts is equal to the max and has guessed")
    void winIfMaxAttempsGuessed() {
        // Given
        val secretWord = "abcdefghij";
        val actions = toListOfActions(secretWord);

        val game = defaultBuilder.secretWord(secretWord).attempts(MAX_GUESS_ATTEMPS).actions(actions).build();


        // When
        val result = calculator.calculate(game);

        // Then
        assertThat(result, is(equalTo(GameState.WON)));
    }

    @Test
    @DisplayName("Lose if the number of attempts is more than the max the has not guessed")
    void loseIfMaxAttempsNotGuessed() {
        // Given
        val actions = toListOfActions(wrongGuess);
        val game = defaultBuilder.attempts(MAX_GUESS_ATTEMPS).actions(actions).build();

        // When
        val result = calculator.calculate(game);

        // Then
        assertThat(result, is(equalTo(GameState.LOST)));
    }


    @DisplayName("win the game if the input matches the secret word")
    @ParameterizedTest
    @MethodSource("validSecretProvider")
    void winGameWithWord(final String secretWord) {

        // Given
        val actionWithRightSecretWord = Collections.singletonList(new UserAction(secretWord));

        val game = defaultBuilder.secretWord(secretWord).actions(actionWithRightSecretWord).build();

        // When
        val result = calculator.calculate(game);


        // Then
        assertThat(result, is(equalTo(GameState.WON)));
    }


}