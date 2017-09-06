package controller.validator;

import common.model.UserAction;
import model.HangmanGame;

public interface ActionValidator {

    /**
     * Validates the user input for a specific state of the game.
     *
     * @param game representation of the game.
     * @param action user action.
     * @return the resulting validation.
     */
    HangmanValidation validate(HangmanGame game, UserAction action);
}
