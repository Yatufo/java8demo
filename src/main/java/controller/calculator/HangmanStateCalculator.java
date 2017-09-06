package controller.calculator;

import common.StateCalculator;
import lombok.NonNull;
import lombok.val;
import common.model.GameState;
import model.HangmanGame;
import model.HangmanParts;

public class HangmanStateCalculator implements StateCalculator<HangmanGame> {

    /***
     * Defines the state of the game by cheching if the inputs of the game have been enough to guess the word.
     * @param game game to calculate the state.
     * @return new state of the game.
     */
    @Override
    public GameState calculate(@NonNull final HangmanGame game) {
        GameState result = GameState.STARTED;
        val letters = game.getSecretLetters();
        val guesses = game.getGuessedInput();
        val maxAttemps = HangmanParts.values().length;

        val isLastAttempt = game.getAttempts() >= maxAttemps;
        val wordGuessedMatches = game.getGuessedInput().contains(game.getSecretWord());
        val hasGuessed = guesses.containsAll(letters) || wordGuessedMatches;

        if (hasGuessed){
            result = GameState.WON;
        } else if(isLastAttempt){
            result = GameState.LOST;
        }

        return result;
    }
}
