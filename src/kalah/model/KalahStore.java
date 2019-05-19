package kalah.model;

/**
 * Represents a KalahStore class.
 */
public class KalahStore extends KalahPit {

    /**
     * Constructor for KalahStore class.
     * @param playerID
     * @param seedCount
     */
    KalahStore(int playerID, int seedCount) {
        super(playerID, seedCount);
    }

    /**
     * KalahStore can't capture seeds from the opposite store.
     * @param playerID
     * @return false
     */
    @Override
    boolean canCapture(int playerID) {
        return false;
    }

    /**
     * Only repeat turn if stored seed in a player's own store.
     * @param playerID
     * @return true or false
     */
    @Override
    public boolean canRepeatTurn(int playerID) {
        return playerID == this.playerID;
    }

    /**
     * Only sow seeds into a player's own store.
     * @param playerID
     * @return true or false
     */
    @Override
    boolean canSow(int playerID) {
        return playerID == this.playerID;
    }

}
