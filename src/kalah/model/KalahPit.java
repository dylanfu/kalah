package kalah.model;

/**
 * Represents a KalahPit class.
 */
public abstract class KalahPit {

    final int playerID;
    int seedCount;
    KalahPit oppositePit;
    private KalahPit nextPit;

    /**
     * Constructor for KalahPit class.
     * @param playerID
     * @param seedCount
     */
    KalahPit(int playerID, int seedCount) {
        this.playerID = playerID;
        this.seedCount = seedCount;
    }

    final void setNextPit(KalahPit pit) {
        if (this.nextPit == null) {
            this.nextPit = pit;
        }
    }

    final void setOppositePit(KalahPit pit) {
        if (this.oppositePit == null) {
            this.oppositePit = pit;
        }
    }

    final KalahPit next() {
        return nextPit;
    }

    boolean isEmpty() {
        if (seedCount == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sows an amount of seeds into the pit.
     * @param amount
     * @param playerNumber
     * @return Amount of seeds sown into this pit
     */
    int sowSeeds(int amount, int playerNumber) {
        if (canSow(playerNumber)) {
            seedCount += amount;
            return amount;
        } else {
            return 0;
        }
    }

    /**
     * Sows one seed into the pit.
     * @param playerNumber
     * @return Amount of seeds sown into this pit
     */
    int sowSeed(int playerNumber) {
        // Amount of seeds being sown is 1
        return sowSeeds(1, playerNumber);
    }

    /**
     * Remove seeds from this pit, if a player can capture it.
     * @param playerID
     * @return Amount of seeds player can capture
     */
    int captureIfPlayerCan(int playerID) {
        if (canCapture(playerID)) {
            return removeSeeds() + oppositePit.removeSeeds();
        } else {
            return 0;
        }
    }

    int removeSeeds() {
        int amountRemoved = seedCount;
        seedCount = 0;
        return amountRemoved;
    }

    abstract boolean canSow(int playerID);

    abstract boolean canCapture(int playerID);

    public abstract boolean canRepeatTurn(int playerID);

    public int seedCount() {
        return seedCount;
    }

}
