package controller;

import common.GameListener;
import controller.calculator.HangmanStateCalculator;
import common.StateCalculator;
import controller.validator.ActionValidator;
import controller.validator.HangmanValidation;
import controller.validator.HangmanValidationMessages;
import lombok.val;
import common.model.GameState;
import common.model.UserAction;
import model.HangmanGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.HangmanUIGameListener;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class HangmanGameControllerTest extends CommonTest {

    private static final HangmanValidation ACTION_ALLOWED = HangmanValidation.EMPTY;
    private static final HangmanValidation ACTION_NOT_ALLOWED = new HangmanValidation(asList(HangmanValidationMessages.INPUT_NOT_ALLOWED));
    private ActionValidator validator;
    private StateCalculator<HangmanGame> calculator;
    private GameListener<HangmanGame> view;
    private HangmanGameController controller;

    @BeforeEach
    void initAll() {
        validator = mock(ActionValidator.class);
        view = mock(HangmanUIGameListener.class);
        calculator = mock(HangmanStateCalculator.class);
        controller = new HangmanGameController(cfg, validator, view, calculator);

        when(validator.validate(any(), any())).thenReturn(ACTION_ALLOWED);
        when(calculator.calculate(any())).thenReturn(GameState.STARTED);
    }


    @Test
    @DisplayName("Start game")
    void shouldStartGame() {
        // Given

        // When
        val gameStarted = controller.start();

        // Then
        assertAll("Started Game",
                () -> assertEquals("secret", gameStarted.getSecretWord()),
                () -> assertEquals(GameState.STARTED, gameStarted.getGameState()),
                () -> assertTrue(gameStarted.getActions().isEmpty()),
                () -> assertEquals(gameStarted.getAttempts().intValue(), 0));
        verify(view).notify(gameStarted);

    }

    @Test
    @DisplayName("Execute and valid action in a game")
    void shouldExecuteAction() {
        // Given
        val game = NEW_GAME;
        val firstAction = new UserAction("a");

        // When
        val updatedGame = controller.takeAction(game, firstAction);

        // Then
        assertThat(updatedGame.getActions(), contains(firstAction));
        assertThat(updatedGame.getValidation(), is(equalTo(ACTION_ALLOWED)));
        assertThat(updatedGame.getAttempts(), is(equalTo(game.getAttempts() + 1)));
        verify(view).notify(updatedGame);
        verify(calculator).calculate(updatedGame);
    }


    @Test
    @DisplayName("An invalid action should be keep in the game state")
    void invalidAction() {
        // Given
        val game = NEW_GAME;
        val firstAction = new UserAction("a");
        when(validator.validate(any(), any())).thenReturn(ACTION_NOT_ALLOWED);

        // When
        val updatedGame = controller.takeAction(game, firstAction);

        // Then
        assertThat(updatedGame.getValidation(), is(equalTo(ACTION_NOT_ALLOWED)));
        assertThat(updatedGame.getAttempts(), is(equalTo(game.getAttempts())));
        verify(calculator).calculate(updatedGame);
        verify(view).notify(updatedGame);
    }

    @Test
    @DisplayName("An invalid action should be keep in the game state")
    void repeatedActionWarning() {
        // Given
        val game = NEW_GAME;

        val repeatedWarningValidation = new HangmanValidation(asList(HangmanValidationMessages.INPUT_REPEATED_WARNING));
        val repeatedAction = new UserAction("a");
        when(validator.validate(any(), any())).thenReturn(repeatedWarningValidation);

        // When
        val updatedGame = controller.takeAction(game, repeatedAction);

        // Then
        assertThat(updatedGame.getValidation(), is(equalTo(repeatedWarningValidation)));
        assertThat(updatedGame.getActions(),
                contains(repeatedAction));
        verify(calculator).calculate(updatedGame);
        verify(view).notify(updatedGame);
    }

}