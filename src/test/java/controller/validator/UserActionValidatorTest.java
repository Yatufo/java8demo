package controller.validator;

import controller.CommonTest;
import lombok.val;
import common.model.GameState;
import common.model.UserAction;
import model.HangmanGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertTrue;


class UserActionValidatorTest extends CommonTest {
    private HangmanActionValidator validator = new HangmanActionValidator(cfg);
    private HangmanGame.HangmanGameBuilder defaultBuilder;


    @BeforeEach
    void initAll() {
        defaultBuilder = HangmanGame.builder()
                .secretWord("anyword")
                .attempts(0)
                .gameState(GameState.STARTED)
                .validation(HangmanValidation.EMPTY);
    }




    @ParameterizedTest
    @MethodSource("validInputProvider")
    @DisplayName("Validate all the valid english letters")
    void validInputInNewGame(final String input) {

        // Given
        val action = new UserAction(input);
        val game = NEW_GAME;

        // When
        val validationResult = validator.validate(game, action);


        // Then
        assertTrue(validationResult.getMessages().isEmpty());

    }


    @Test
    @DisplayName("validate a single valid character being input twice")
    void sameInputTwice() {
        // Given

        val firstAction = new UserAction("a");
        val secondAction = new UserAction("a");

        val game = defaultBuilder.actions(asList(firstAction)).build();

        // When
        val secongResult = validator.validate(game, secondAction);


        // Then
        assertThat(secongResult.getMessages(),
                contains(HangmanValidationMessages.INPUT_REPEATED_WARNING));

    }

    @Test
    @DisplayName("validate a single valid character being input three times is valid")
    void sameInputThrice() {
        // Given
        val repeatedAction = new UserAction("a");
        val game = defaultBuilder.actions(asList(repeatedAction, repeatedAction)).build();

        // When
        val validationResult = validator.validate(game, repeatedAction);


        // Then
        assertTrue(validationResult.getMessages().isEmpty());
    }

}