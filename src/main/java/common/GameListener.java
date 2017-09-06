package common;

/**
 * Listens for changes in a game
 */
public interface GameListener<G extends Game> {

    void notify(G game);
}
