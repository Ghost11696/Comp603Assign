package Model;

/**
 * Ship class. A Ship has a name and a length. It 
 * also counts how many times it has been hit so that it can determine whether
 * it has been sunk or not.
 */
public abstract class Ship {
    /* Constants to represent the two possible orientations - DO NOT CHANGE. */
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    /**
     * Ship constructor. Initialises the ship. Sets the name and length as 
     * specified by the respective parameters, and also initialises the ship 
     * so that it has not received any hits.
     */
    String shipName;
    int shipLength, hits;
    
    public Ship(String name, int length) {
        shipName = name;
        shipLength = length;
        hits = 0;
    }
    
    /**
     * @return The name of this ship.
     */
    public String getName() {
        return shipName;
    }

    /** 
     * @return Returns the length of the ship - the number of consecutive cells 
     * this ship will occupy on the grid. 
     */
    public int getLength() {
       return shipLength;
    }

    /** 
     * Increments the number of hits received by this ship one.
     * This method will never be called if this ship has already been sunk.
     */
    public void hit() {
        if(hits != shipLength) {
            hits++;
        }
    }
    
    /** 
     * Returns whether this ship has been sunk. A ship is considered 
     * sunk when the number of times it has been hit is equal to its length.
     * @return Returns true if this ship has been sunk, false otherwise. 
     */
    public boolean isSunk() {
        boolean sunk = false;
        
        if(hits == shipLength) {
            sunk = true;
        }
        return sunk;
    }
}
