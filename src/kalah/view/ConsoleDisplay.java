package kalah.view;

import com.qualitascorpus.testsupport.IO;
import kalah.model.KalahHouse;
import kalah.model.KalahStore;
import kalah.service.KalahManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.String.valueOf;
import static kalah.util.MathUtil.checkParity;
import static kalah.util.MathUtil.getNumberLength;
import static kalah.util.StringUtil.repeatString;
import static kalah.util.StringUtil.rightAlignIntValue;

/**
 * IO Console implementation of a KalahDisplay.
 */
public class ConsoleDisplay implements KalahDisplay {

    private final KalahManager gameService;
    private final IO io;
    private final int maxSeedCharLength;
    private final int maxHouseCharLength;
    private static final char PLAYER_CHAR = 'P';
    private static final char OPEN_BRACE_CHAR = '[';
    private static final char CLOSE_BRACE_CHAR = ']';
    private static final char VERTICAL_CHAR = '|';
    private static final char DASH_CHAR = '-';
    private static final char PLUS_CHAR = '+';
    private static final char SPACE_CHAR = ' ';
    private static final int HOUSE_SPACING = 4;
    private static final int STORE_SPACING = 2;

    public ConsoleDisplay(KalahManager gameService, IO io) {
        this.gameService = gameService;
        this.io = io;
        this.maxSeedCharLength = getNumberLength(gameService.getTotalSeeds());
        this.maxHouseCharLength = getNumberLength(gameService.getNumOfHouses());
    }

    @Override
    public String promptNextMove() {
        printBoard();
        return printPrompt();
    }

    @Override
    public void invalidHouse() {
        printInvalidEmptyHouse();
    }

    @Override
    public void quitGame() {
        printGameOver();
        printBoard();
    }

    @Override
    public void gameOver() {
        printBoard();
        printGameOver();
        printBoard();
        printScoresAndWinners();
    }

    private void printBoard() {
        printOuterBoard();
        printInnerBoardWithSeparator();
        printOuterBoard();
    }

    private void printOuterBoard() {
        io.println(formatOuterBoard(gameService.getNumOfHouses()));
    }

    private void printInnerBoardWithSeparator() {
        for (int i = gameService.getNumOfPlayers(); i > 1; i--) {
            printPits(i);
            io.println(formatMiddleBoard(gameService.getNumOfHouses()));
        }
        printPits(1);
    }

    private void printPits(int playerNum) {
        List<KalahHouse> houses = gameService.getHouses(playerNum);
        KalahStore store = gameService.getStore((playerNum % gameService.getNumOfPlayers()) + 1);
        io.println(formatPits(playerNum, gameService.getNumOfPlayers(), houses, store));
    }

    private String printPrompt() {
        return io.readFromKeyboard("Player " + PLAYER_CHAR + gameService.getCurrentPlayer() + "'s turn - Specify house number or 'q' to quit: ");
    }

    private void printScoresAndWinners() {
        for (int i = 1; i <= gameService.getNumOfPlayers(); i++) {
            io.println("\tplayer " + gameService.getScore(i).getPlayerID() + ":" + gameService.getScore(i).getSeedCount());
        }
        io.println(gameService.getWinners().size() > 1 ? "A tie!" : "Player " + gameService.getWinners().get(0).getPlayerID() + " wins!");
    }

    private void printGameOver() {
        io.println("Game over");
    }

    private void printInvalidEmptyHouse() {
        io.println("House is empty. Move again.");
    }

    private String formatPits(int playerNum, int numPlayers, List<KalahHouse> houses, KalahStore store) {
        return checkParity(playerNum, numPlayers) ? formatPitOrderBackwards(houses, store, playerNum) :
                formatPitOrder(houses, store, playerNum);
    }

    private String formatPitOrderBackwards(List<KalahHouse> houses, KalahStore store, int playerNumber) {
        List<KalahHouse> temp = new ArrayList<>(houses);
        Collections.reverse(temp);
        return formatPlayer(playerNumber) +
                formatHouses(temp) +
                formatStore(store);
    }

    private String formatPitOrder(List<KalahHouse> houses, KalahStore store, int playerNumber) {
        return formatStore(store) +
                formatHouses(houses) +
                formatPlayer(playerNumber);
    }

    private String formatOuterBoard(int numHouses) {
        return formatOuterCornerBoard() + formatInnerBoard(numHouses) + formatOuterCornerBoard();
    }

    private String formatOuterCornerBoard() {
        return PLUS_CHAR +
                repeatString(valueOf(DASH_CHAR), maxSeedCharLength + STORE_SPACING) +
                PLUS_CHAR;
    }

    private String formatInnerBoard(int numHouses) {
        StringBuilder builder = new StringBuilder();
        String dash = repeatString(valueOf(DASH_CHAR),
                maxSeedCharLength + maxHouseCharLength + HOUSE_SPACING) +
                PLUS_CHAR;
        return builder.append(repeatString(dash, numHouses))
                .deleteCharAt(builder.length() - 1)
                .toString();
    }

    private String formatMiddleBoard(int numHouses) {
        return formatMiddleOuterBoard() + formatInnerBoard(numHouses) + formatMiddleOuterBoard();
    }

    private String formatMiddleOuterBoard() {
        return VERTICAL_CHAR +
                repeatString(valueOf(SPACE_CHAR), maxSeedCharLength + STORE_SPACING) +
                VERTICAL_CHAR;
    }

    private String formatPlayer(int playerNumber) {
        return VERTICAL_CHAR +
                repeatString(valueOf(SPACE_CHAR), maxSeedCharLength - 1) + // -1; PLAYER_CHAR takes a slot
                PLAYER_CHAR +
                playerNumber +
                SPACE_CHAR + VERTICAL_CHAR;
    }

    private String formatHouses(List<KalahHouse> houses) {
        StringBuilder builder = new StringBuilder();
        for (KalahHouse house : houses) {
            builder.append(formatHouse(house));
        }
        return builder.deleteCharAt(builder.length() - 1).toString();
    }

    private String formatHouse(KalahHouse house) {
        return SPACE_CHAR +
                rightAlignIntValue(house.getHouseID(), maxHouseCharLength) +
                OPEN_BRACE_CHAR +
                rightAlignIntValue(house.seedCount(), maxSeedCharLength) +
                CLOSE_BRACE_CHAR + SPACE_CHAR + VERTICAL_CHAR;
    }

    private String formatStore(KalahStore store) {
        return valueOf(VERTICAL_CHAR) + SPACE_CHAR +
                rightAlignIntValue(store.seedCount(), maxSeedCharLength) +
                SPACE_CHAR + VERTICAL_CHAR;
    }

}
