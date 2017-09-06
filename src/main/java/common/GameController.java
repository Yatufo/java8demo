package common;

import common.model.UserAction;
import model.HangmanGame;

/**
 * Defines all the behaviours of the a game.
 */
public interface GameController<G extends Game> {

    /***
     *
     * Starts the game to the initial state.
     *
     * @return the initial state of the game.
     */
    G start();


    /**
     * Recieves an action from the playes that might change the state of the game.
     *
     * @param game the state of the game to be applied.
     * @param action action to be taken.
     * @return the new state of the game.
     */
    G takeAction(HangmanGame game, UserAction action);

    G takeBack();

    G restart();
}
