package kalah.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KalahBoard {

    final int numOfHouses;
    final int numOfPlayers;
    private final int numOfInitSeeds;
    private final Map<Integer, Map<Integer, KalahHouse>> housesMap;
    private final Map<Integer, KalahStore> storesMap;

    /**
     * Constructor for KalahBoard class. Creates a new board and initialises the houses and stores with the initial
     * number of seeds into hashmaps for efficient look ups. Then wraps the board together.
     * @param numOfHouses
     * @param numOfInitialSeeds
     * @param numOfPlayers
     */
    // TODO: Instead of using hashmaps, implement using a CircularList
    public KalahBoard(int numOfHouses, int numOfInitialSeeds, int numOfPlayers) {
        if (numOfHouses < 1 || numOfInitialSeeds < 1 || numOfPlayers < 2) {
            throw new RuntimeException(String.format(("Please try a different configuration.")));
        }
        this.numOfInitSeeds = numOfInitialSeeds;
        this.numOfHouses = numOfHouses;
        this.numOfPlayers = numOfPlayers;
        this.housesMap = new HashMap<>();
        this.storesMap = new HashMap<>();
        for (int playerID = 1; playerID <= numOfPlayers; playerID++) {
            Map<Integer, KalahHouse> houses = new HashMap<>();
            for (int houseID = 1; houseID <= numOfHouses; houseID++) {
                houses.put(houseID, new KalahHouse(playerID, houseID, numOfInitialSeeds));
            }
            this.housesMap.put(playerID, houses);
            this.storesMap.put(playerID, new KalahStore(playerID, 0));
        }
        wrapBoard();
    }

    /**
     * Wraps the kalah board together by connecting each pit to the next pit and matching opposite pit.
     */
    private void wrapBoard() {
        // Start index to player and house ID at 1, as there is no KalahScore 0 or KalahHouse 0.
        for (int playerID = 1; playerID <= numOfPlayers; playerID++) {
            for (int houseID = 1; houseID <= numOfHouses; houseID++) {
                // Connect the house pits together
                if (houseID == numOfHouses) {
                    getHouse(playerID, houseID).setNextPit(getStore(playerID));
                } else {
                    getHouse(playerID, houseID).setNextPit(getHouse(playerID, houseID + 1));
                }
                // Set the opposing house pit
                getHouse(playerID, houseID).setOppositePit(getHouse(getNextPlayer(playerID), numOfHouses + 1 - houseID));
            }
            // Connect the store to the house of the other player's house
            getStore(playerID).setNextPit(getHouse(getNextPlayer(playerID), 1));

            // Set the store's opposite pit as the other player's store
            getStore(playerID).setOppositePit(getStore(getNextPlayer(playerID)));
        }
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public int getNumOfHouses() {
        return numOfHouses;
    }

    public int getNumOfInitSeeds() {
        return numOfInitSeeds;
    }

    /**
     * Sowing consists of removing seeds from the starting pit and adding seeds one by one to the following pits in an
     * anti-clockwise direction. Then returns the last pit sowed for repeat turn checking.
     * @param houseID
     * @param playerID
     * @return KalahPit
     */
    public KalahPit sow(int houseID, int playerID) {
        KalahPit pit = getHouse(playerID, houseID);
        int seeds = pit.removeSeeds();
        while (seeds > 0) {
            pit = pit.next();
            seeds = seeds - pit.sowSeed(playerID);
        }
        int seedsCaptured = pit.captureIfPlayerCan(playerID);
        getStore(playerID).sowSeeds(seedsCaptured, playerID);
        return pit;
    }

    public int getNextPlayer(int playerID) {
        return playerID % numOfPlayers + 1;
    }

    public boolean isHouseEmpty(int houseID, int playerID) {
        return getHouse(playerID, houseID).isEmpty();
    }

    public boolean areAllHousesEmpty(int playerID) {
        for (KalahHouse house : getHouses(playerID)) {
            if (!house.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public List<KalahHouse> getHouses(int playerID) {
        List<KalahHouse> houses = new ArrayList<>();
        for (int i = 1; i <= numOfHouses; i++) {
            houses.add(getHouse(playerID, i));
        }
        return houses;
    }

    KalahHouse getHouse(int playerID, int houseID) {
        if (!housesMap.containsKey(playerID) || !housesMap.get(playerID).containsKey(houseID)) {
            throw new RuntimeException(String.format("House %d doesn't exist for player %d.", houseID, playerID));
        }
        return housesMap.get(playerID).get(houseID);
    }

    public KalahStore getStore(int playerID) {
        if (!storesMap.containsKey(playerID)) {
            throw new RuntimeException(String.format("A store doesn't exist for player %d.", playerID));
        }
        return storesMap.get(playerID);
    }

}
