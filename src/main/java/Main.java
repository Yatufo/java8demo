import controller.calculator.HangmanStateCalculator;
import controller.validator.HangmanActionValidator;
import controller.HangmanGameController;
import lombok.val;
import common.model.GameState;
import common.model.UserAction;
import model.HangmanGame;
import org.aeonbits.owner.ConfigFactory;
import util.DefaultConfig;
import view.HangmanUIGameListener;

import java.util.Scanner;

import static java.lang.System.out;

public class Main {
    public static void main(final String[] args){

        val scanner = new Scanner(System.in);
        val cfg = ConfigFactory.create(DefaultConfig.class);
        val controller = new HangmanGameController(cfg,
                new HangmanActionValidator(cfg),
                new HangmanUIGameListener(),
                new HangmanStateCalculator());


        out.println("####### Hangman #######");
        HangmanGame game = controller.start();
        while (GameState.STARTED.equals(game.getGameState())){

            out.print("Pick a letter or guess a word ");
            val input = scanner.next();
            game = controller.takeAction(game, new UserAction(input));
        }

        out.println("####### ####### #######");
        out.println(String.format("####### YOU %s #######", game.getGameState()));
        out.println("####### ####### #######");

    }
}
