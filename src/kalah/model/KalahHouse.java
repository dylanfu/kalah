package kalah.model;

/**
 * Represents a KalahHouse class.
 */
public class KalahHouse extends KalahPit {

    private final int houseID;

    /**
     * Constructor for KalahHouse class
     * @param playerID
     * @param houseID
     * @param seedCount
     */
    KalahHouse(int playerID, int houseID, int seedCount) {
        super(playerID, seedCount);
        this.houseID = houseID;
    }

    /**
     * KalahHouse can capture seeds from the opposite store
     * @param playerID
     * @return true or false
     */
    @Override
    boolean canCapture(int playerID) {
        return playerID == this.playerID && seedCount == 1 && !oppositePit.isEmpty();
    }

    /**
     * Can't repeat turn if last seed sown is a house
     * @param playerID
     * @return false
     */
    @Override
    public boolean canRepeatTurn(int playerID) {
        return false;
    }

    /**
     * A house can always be sown into
     * @param playerID
     * @return true
     */
    @Override
    boolean canSow(int playerID) {
        return true;
    }

    public int getHouseID() {
        return houseID;
    }

}
