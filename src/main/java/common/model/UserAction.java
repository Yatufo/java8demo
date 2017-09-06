package common.model;

import lombok.NonNull;
import lombok.Value;

@Value
public class UserAction {
    @NonNull
    private String input;
}
