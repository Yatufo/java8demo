package view;

import common.GameListener;
import lombok.NonNull;
import lombok.val;
import common.model.GameState;
import model.HangmanGame;

import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.lang.System.out;
import static java.util.Arrays.asList;

public class HangmanUIGameListener implements GameListener<HangmanGame> {
    @Override
    public void notify(@NonNull final HangmanGame game) {
        val secretLetterList = asList(game.getSecretWord().split(""));
        secretLetterList.replaceAll(letter -> (game.getGuessedInput().contains(letter) ? letter : "_" ));

        val wordWithHiddenLetters = String.join("", secretLetterList);
        val messages = getMessages(game);
        //TODO: paint the hangman.

        val secret = (GameState.STARTED.equals(game.getGameState()) ? wordWithHiddenLetters : game.getSecretWord());
        out.println(format("\n\n\n"));
        out.println(format("Secret: %s", secret));
        out.println(format("Guesses: %s", String.join("-", game.getGuessedInput())));
        out.println(format("Messages: %s", messages));
    }

    private String getMessages(@NonNull final HangmanGame game) {
        val list = game.getValidation().getMessages().stream()
                .map(m -> m.getMessage()).collect(Collectors.toList());
        return String.join(",", list);
    }
}
