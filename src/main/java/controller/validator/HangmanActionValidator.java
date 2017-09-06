package controller.validator;

import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.val;
import common.model.UserAction;
import model.HangmanGame;
import util.DefaultConfig;

@AllArgsConstructor
public class HangmanActionValidator implements ActionValidator{

    private DefaultConfig cfg;

    @Override
    public HangmanValidation validate(@NonNull final HangmanGame game, @NonNull final UserAction action) {
        val builder = new ImmutableList.Builder<HangmanValidationMessages>();

        if (!action.getInput().matches(cfg.inputValidationRegex())){
            builder.add(HangmanValidationMessages.INPUT_NOT_ALLOWED);
        }

        val sameAttemps = game.getActions().stream().filter(action::equals).count();
        if (sameAttemps == 1){
            builder.add(HangmanValidationMessages.INPUT_REPEATED_WARNING);
        }

        return new HangmanValidation(builder.build());
    }
}

