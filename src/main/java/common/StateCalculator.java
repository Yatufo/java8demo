package common;

import common.model.GameState;

public interface StateCalculator<G extends Game> {
    GameState calculate(G game);
}
