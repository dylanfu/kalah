package kalah.view;

/**
 * Interface for the display implementation.
 */
public interface KalahDisplay {

    /**
     * Displays that the next player needs to make a move.
     */
    String promptNextMove();

    /**
     * Displays that the game has quit.
     */
    void quitGame();

    /**
     * Displays that the game is over.
     */
    void gameOver();

    /**
     * Displays that their move was an invalid move (empty house chosen).
     */
    void invalidHouse();

}
