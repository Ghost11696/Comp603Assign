package Model;

/**
 * Player class. Each Player has a name, five Ships and a Grid. A Player
 * can determine whether he or she has been defeated or not.
 */
public class Player {

    private String playerName;
    private Ship[] ships;
    private Grid grid;
    private int playerScore = 0;
    
    public Player(String name) {
        playerName = name;
        ships = new Ship[]{new AircraftCarrier(), new Battleship(), new Submarine(), new Destroyer(), new PatrolBoat()};
        grid = new Grid(this);
    }

    /**
     * @return This player's grid.
     */
    public Grid getGrid() {
        return grid;
    }

    /**
     * @return This player's name.
     */
    public String getName() {
        return playerName;
    }

    /**
     * @return The array of this player's ships. The array must always contain the ships in this order:
     *  AircraftCarrier, Battleship, Submarine, Destroyer and PatrolBoat.
     */
    public Ship[] getShips() {
        return ships;
    }

    /**
     * Returns true if this player has been defeated. 
     * A player is defeated when all their ships have been sunk.
     * @return Returns true if this player has been defeated, false otherwise.
     */
    public boolean isDefeated() {
        boolean defeated = true;
        
        for (int i = 0; i <= ships.length - 1; i++) {
            if (!ships[i].isSunk()) {
                defeated = false;   //Sets defeated as false if the ship at element "i" of the array is not sunk.
            }
        }

        return defeated;
    }

    /**
     * adds a score to the players
     *
     * @param i can be 10 or 100 depending if ship has been hit or sunk
     */
    public void effectScore(int i) {
        playerScore += i;
    }

    /**
     * gets the players score
     * @return the players score
     */
    public int getScore() {
        return playerScore;
    }
    
    /**
     * @return returns true if player is Ai
     */
    public boolean isComputer(){
        return playerName.equals("Computer");
    }
    
    

}
