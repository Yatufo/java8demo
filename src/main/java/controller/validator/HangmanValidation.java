package controller.validator;

import lombok.NonNull;
import lombok.Value;

import java.util.Collections;
import java.util.List;

@Value
public class HangmanValidation{
    @NonNull private List<HangmanValidationMessages> messages;

    public static HangmanValidation EMPTY = new HangmanValidation(Collections.emptyList());
}
