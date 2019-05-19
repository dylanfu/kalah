package kalah.model;

/**
 * KalahScore class, representing a player ID and seedCount.
 * Mainly acts as a data transmission object.
 */
public class KalahScore implements Comparable<KalahScore> {

    private final int playerID;
    private int seedCount;

    /**
     * Constructor for KalahScore class.
     * @param playerID
     * @param seedCount
     */
    public KalahScore(int playerID, int seedCount) {
        this.playerID = playerID;
        this.seedCount = seedCount;
    }

    public int getPlayerID() {
        return playerID;
    }

    public int getSeedCount() {
        return seedCount;
    }

    public boolean isGreaterThanOrEquals(KalahScore playerScore) {
        return compareTo(playerScore) >= 0;
    }

    public boolean isGreaterThan(KalahScore playerScore) {
        return compareTo(playerScore) > 0;
    }

    @Override
    public int compareTo(KalahScore playerScore) {
        return this.seedCount - playerScore.seedCount;
    }

}
