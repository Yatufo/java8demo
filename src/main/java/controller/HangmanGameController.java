package controller;

import com.google.common.collect.ImmutableList;
import common.GameController;
import common.GameListener;
import common.StateCalculator;
import common.model.GameState;
import common.model.UserAction;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.val;
import model.*;
import util.DefaultConfig;
import controller.validator.ActionValidator;
import controller.validator.HangmanValidation;

import java.util.List;
import java.util.Random;

import static controller.validator.HangmanValidationMessages.INPUT_NOT_ALLOWED;
import static java.util.Collections.emptyList;

@AllArgsConstructor
public class HangmanGameController implements GameController<HangmanGame> {

    @NonNull
    private DefaultConfig cfg;
    @NonNull
    private ActionValidator validator;
    @NonNull
    private GameListener<HangmanGame> view;
    @NonNull
    private StateCalculator<HangmanGame> calculator;

    @Override
    public HangmanGame start() {

        val game = HangmanGame.builder()
                .secretWord(getRandomWord())
                .gameState(GameState.STARTED)
                .attempts(0)
                .actions(emptyList())
                .validation(HangmanValidation.EMPTY).build();

        view.notify(game);
        return game;
    }

    private String getRandomWord() {
        val words = cfg.secretWords().split(cfg.separator());
        val random = new Random().nextInt(words.length);
        return words[random];
    }

    @Override
    public HangmanGame takeAction(@NonNull final HangmanGame game, @NonNull final UserAction action) {
        val builder = HangmanGame.builder();
        game.copyTo(builder);

        val updatedValidation = validator.validate(game, action);
        builder.validation(updatedValidation);

        if (!updatedValidation.getMessages().contains(INPUT_NOT_ALLOWED)){
            builder.actions(getUpdatedActions(game, action));
            builder.attempts(game.getAttempts() + 1);
        }

        val updatedState = calculator.calculate(builder.build());
        val updatedGame = builder.gameState(updatedState).build();

        view.notify(updatedGame);
        return updatedGame;
    }



    private List<UserAction> getUpdatedActions(@NonNull final HangmanGame game, @NonNull final UserAction action) {
        return new ImmutableList.Builder<UserAction>().addAll(game.getActions()).add(action).build();
    }


    @Override
    public HangmanGame takeBack() {
        throw new UnsupportedOperationException();
    }

    @Override
    public HangmanGame restart() {
        throw new UnsupportedOperationException();
    }
}
