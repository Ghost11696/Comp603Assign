package Model;

/**
 * Cell class. A Cell is one element in a Grid. A Cell knows which Ship
 * is occupying it. Cell is responsible for handling attacks.
 * As well as this, Cell can calculate what the UI should display.
 */
public class Cell {

    public static final int ATTACK_ALREADY = 1001;
    public static final int ATTACK_HIT = 1002;
    public static final int ATTACK_MISS = 1003;
    public static final int ATTACK_SUNK = 1004;

    
    public static final int DISPLAY_BLANK = 2001;
    public static final int DISPLAY_HIT = 2002;
    public static final int DISPLAY_MISS = 2003;
    public static final int DISPLAY_OCCUPIED = 2004;

    /**
     * Cell constructor. Puts the cell in the initial state of unoccupied,
     * unattacked and not hit. Also sets its x and y coordinates, and who 
     * the cell belongs to.
     * @param owningPlayer The player who this cell belongs to.
     * @param x The x-coordinate for this cell. Will always be in the range 0 to Game.GRID_SIZE-1, inclusive.
     * @param y The y-coordinate for this cell. Will always be in the range 0 to Game.GRID_SIZE-1, inclusive.
     */
    private Player owningPlayer;
    private int x, y;
    private boolean occupied, attacked, hit;
    private Ship occupyingShip;
    
    public Cell(Player owningPlayer, int x, int y) {
        this.owningPlayer = owningPlayer;
        
        if(x >= 0 && x <= Game.GRID_SIZE-1) {
            this.x = x;     
        }
        
        if(x >= 0 && x <= Game.GRID_SIZE-1) {
            this.y = y;    
        }
        
        occupied = false;
        attacked = false;
        hit = false;
    }
    
    /** 
     * Calculates what the user interface should display for this particular cell.
     *
     * @param viewingPlayer The player that is viewing this grid. 
     * @return Returns the display state - one of Cell.DISPLAY_BLANK, Cell.DISPLAY_HIT,
     */
    public int displayState(Player viewingPlayer) {
        int state = Cell.DISPLAY_BLANK;
        
        if(attacked && hit) {
            state = Cell.DISPLAY_HIT;
        }
        
        if(attacked && !hit) {
            state = Cell.DISPLAY_MISS;
        }
        
        return state;
    }

    /**
     * @return The player who this cell belongs to.
     */
    public Player getPlayer() {
        return owningPlayer;
    }

    /**
     * @return The ship that occupies this cell.
     */
    public Ship getOccupyingShip() {
        return occupyingShip;
    }

    /**
     * @return The x-coordinate of this cell.
     */
    public int getX() {
        return x;
    }

    /**
     * @return The y-coordinate of this cell.
     */
    public int getY() {
        return y;
    }

    /** 
     * Has this cell been hit? This cell can only be hit if it meets
     * both these conditions:
     *  1. It is occupied.
     *  2. It has been attacked.
     * @return Returns true if this cell was hit, false otherwise. 
     */
    public boolean isHit() {
        hit = false;
        
        if(occupied && attacked) {
            hit = true;
        }
        
        return hit;
    }

    /** 
     * Marks this cell as occupied by the specified ship. 
     * @param ship The ship to occupy this cell with.
     */
    public void occupyWith(Ship ship) {
        occupyingShip = ship;
        occupied = true;
    }

    /**
     * Attempts an attack on this cell.
     */
    public int tryAttack() {
        int cellStatus = Cell.ATTACK_ALREADY;
        
        if(!attacked) {
            if(occupied) {
                occupyingShip.hit();                    
                hit = true;                             
                
                if(occupyingShip.isSunk()) {
                    cellStatus = Cell.ATTACK_SUNK;
                }
                if (!occupyingShip.isSunk()) {
                    cellStatus = Cell.ATTACK_HIT;
                }
            }
            if(!occupied) {
                cellStatus = ATTACK_MISS;
            }
            attacked = true;                             
        }
        return cellStatus;
    }
    
    /**
     * Has this cell been attacked before?
     * @return Returns true if this cell has been attacked before, false otherwise.
     */
    public boolean wasAttacked() {
        return attacked;
    }
    
    /**
     * is this cell occupied
     * @return Returns true if this cell is occupied, false otherwise.
     */
    public boolean isOccupied(){
        return occupied;
    }
}
