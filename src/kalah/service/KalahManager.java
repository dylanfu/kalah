package kalah.service;

import kalah.model.*;
import kalah.enums.KalahMoveType;

import java.util.ArrayList;
import java.util.List;

import static kalah.enums.KalahMoveType.*;

/**
 * Service layer between the model and Kalah controller, the purpose of this layer is to maintain state.
 * The main Kalah controller sends user input moves to this service layer.
 * This game manager handles the sending of user requests to the model and retrieving data from the model.
 */
public class KalahManager {

    private final KalahBoard board;
    private int currentPlayer;

    /**
     * Constructor for KalahManager class
     * @param board
     */
    public KalahManager(KalahBoard board) {
        this.board = board;
        currentPlayer = 1; // Initialise to KalahScore 1
    }

    private KalahMoveType isValidMove(int houseID) {
        if (board.isHouseEmpty(houseID, currentPlayer)) {
            return INVALID;
        } else {
            return VALID;
        }
    }

    private KalahMoveType isFinished() {
        if (board.areAllHousesEmpty(currentPlayer) ) {
            return GAME_OVER;
        } else {
            return VALID;
        }
    }

    public KalahMoveType makeMove(int houseID) {
        KalahMoveType move = isValidMove(houseID);
        if (!move.equals(VALID)) {
            return move;
        }
        KalahPit lastPitSowed = board.sow(houseID, currentPlayer);
        if (!lastPitSowed.canRepeatTurn(currentPlayer)) {
            currentPlayer = board.getNextPlayer(currentPlayer);
        }
        return isFinished();
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getTotalSeeds() {
        return board.getNumOfPlayers() * board.getNumOfInitSeeds() * board.getNumOfHouses();
    }

    public int getNumOfHouses() {
        return board.getNumOfHouses();
    }

    public int getNumOfPlayers() {
        return board.getNumOfPlayers();
    }

    public KalahStore getStore(int playerID) {
        return board.getStore(playerID);
    }

    public List<KalahHouse> getHouses(int playerID) {
        return board.getHouses(playerID);
    }

    public KalahScore getScore(int playerID) {
        return getKalahScore(playerID);
    }

    public List<KalahScore> getWinners() {
        List<KalahScore> winningPlayers = new ArrayList<>();
        winningPlayers.add(new KalahScore(-1, -1));
        for (KalahScore score : getKalahScores()) {
            if (score.isGreaterThanOrEquals(winningPlayers.get(0))) {
                if (score.isGreaterThan(winningPlayers.get(0))) {
                    winningPlayers = new ArrayList<>();
                }
                winningPlayers.add(score);
            }
        }
        return winningPlayers;
    }

    private KalahScore getKalahScore(int playerNumber) {
        int totalSeedCount = 0;
        for (KalahHouse house : board.getHouses(playerNumber)) {
            totalSeedCount += house.seedCount();
        }
        totalSeedCount += board.getStore(playerNumber).seedCount();
        return new KalahScore(playerNumber, totalSeedCount);
    }

    private List<KalahScore> getKalahScores() {
        List<KalahScore> kalahScores = new ArrayList<>();
        for (int playerID = 1; playerID <= board.getNumOfPlayers(); playerID++) {
            KalahScore score = getKalahScore(playerID);
            kalahScores.add(score);
        }
        return kalahScores;
    }

}
