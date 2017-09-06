package model;

import com.google.common.collect.ImmutableSet;
import common.Game;
import common.model.GameState;
import common.model.UserAction;
import controller.validator.HangmanValidation;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Value
@Builder
public class HangmanGame implements Game {
    @NonNull
    private String secretWord;
    @NonNull
    private GameState gameState;
    @NonNull
    private Integer attempts;
    @NonNull
    private List<UserAction> actions;
    @NonNull
    private HangmanValidation validation;

    @Getter(lazy = true)
    private final Set<String> secretLetters = createSecretLetters();
    @Getter(lazy = true)
    private final List<String> guessedInput = createGuessedInput();

    private Set<String> createSecretLetters() {
        return Arrays.stream(this.getSecretWord().split("")).collect(ImmutableSet.toImmutableSet());
    }

    private List<String> createGuessedInput() {
        return this.getActions().stream().map(UserAction::getInput).collect(Collectors.toList());
    }


    //TODO: Generate this util.
    public void copyTo(@NonNull final HangmanGame.HangmanGameBuilder builder) {
        builder.actions(this.getActions());
        builder.secretWord(this.getSecretWord());
        builder.gameState(this.getGameState());
        builder.attempts(this.getAttempts());
        builder.gameState(this.getGameState());
    }
}
