package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;

import kalah.enums.KalahMoveType;
import kalah.model.KalahBoard;
import kalah.service.KalahManager;
import kalah.view.KalahDisplay;
import kalah.view.ConsoleDisplay;

import static kalah.enums.KalahMoveType.*;
import static java.lang.Integer.parseInt;

/**
 * This is the main Kalah controller class that contains the game loop.
 * Connects the service and view classes together. This main controller will request the display class
 * to update, when special inputs or moves occur.
 */
public class Kalah {

    private KalahManager gameService;
    private KalahDisplay display;

    public static void main(String[] args) {
        new Kalah().play(new MockIO());
    }

    public void play(IO io) {
        init(io);
        run();
    }

    private void init(IO io) {
        gameService = new KalahManager(new KalahBoard(6, 4, 2));
        display = new ConsoleDisplay(gameService, io);
    }

    private void run() {
        // Game Loop
        while (true) {
            String userInput = display.promptNextMove();
            if (userInput.equals("q")) {
                display.quitGame();
                break;
            } else {
                KalahMoveType move = gameService.makeMove(parseInt(userInput));
                if (move.equals(INVALID)) {
                    display.invalidHouse();
                }
                if (move.equals(GAME_OVER)) {
                    display.gameOver();
                    break;
                }
            }
        }
    }

}
