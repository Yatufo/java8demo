package controller.validator;

import lombok.Getter;
import lombok.NonNull;

public enum HangmanValidationMessages {
    INPUT_NOT_ALLOWED("The input character is not on the list of allowed chars"),
    INPUT_REPEATED_WARNING("If that same letter is picked again later in the game, this counts as an incorrect pick.");

    @Getter
    private String message;

    HangmanValidationMessages(@NonNull final String message){
        this.message = message;
    }
}
