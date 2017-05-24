package Model;

/**
 * Represents a grid. A Grid consists of Cells. Every Grid belongs to a Player.
 * A Grid manages the deployment of Ships onto it. It is also capable of
 * automatically choosing targets on behalf of a Player.
 */
public class Grid {
    /**
     * Grid constructor. Creates the Cells that form this Grid.
     * The width and height of this grid should be equal to the constant Game.GRID_SIZE.
     * Also sets which Player owns this Grid.
     */
    Player owningPlayer;
    Cell[][] grid;
    
    public Grid(Player player) {
        owningPlayer = player;
        grid = new Cell[Game.GRID_SIZE][Game.GRID_SIZE];                    
        
        for(int row = 0; row <= Game.GRID_SIZE-1; row++) {                  
            for(int column = 0; column <= Game.GRID_SIZE-1; column++) {     
                grid[row][column] = new Cell(owningPlayer, row, column);    
            }
        }
    }
      
    /**
     * Works exactly like deploy, except the position and orientation
     * of the ship are chosen randomly.
     */
    public void autoDeploy(Ship ship) {
        int topLeftX = (int)(Math.random()*10);                                 
        int topLeftY = (int)(Math.random()*10);                                 
        int orientation = (int)((Math.random()*10)/5);                          
        
        while(!isValidDeployment(ship, topLeftX, topLeftY, orientation)) {
                topLeftX = (int)(Math.random()*10);                             
                topLeftY = (int)(Math.random()*10);                             
            }
        
        deploy(ship, topLeftX, topLeftY, orientation);                          
    }
    

    /**
     * @return A valid cell for this grid's player's opponent to target.
     */

    public Cell autoTargetMe() {
        Cell validCell = null;
        Cell tempCell = getCell((int)(Math.random()*10), (int)(Math.random()*10));
        
        while(!owningPlayer.isDefeated()) {
            if(!tempCell.wasAttacked()) {
                validCell = tempCell; break;
            }
            else {
                tempCell = getCell((int)(Math.random()*10), (int)(Math.random()*10));
            }
        }
        return validCell;
    }
    
    /**
     *
     * This method should not check whether the particular positioning of the ship overlaps another
     * ship. 
     *
     * @param ship The ship to be placed. Will never be null.
     * @param topLeftX The x-coordinate for the top-left end of the ship. 
     * @param topLeftY The y-coordinate for the top-left end of the ship. 
     * @param orientation Will always be only one of two values - Ship.HORIZONTAL or Ship.VERTICAL.
     * @return Returns an array of cells, as described above.
     */    
    public Cell[] coveredCells(Ship ship, int topLeftX, int topLeftY, int orientation) {
        Cell[] occupiedCells = new Cell[ship.shipLength];                                   
        
        if(orientation == Ship.HORIZONTAL && topLeftY <= Game.GRID_SIZE-1) {        
            for(int i = 0; i <= ship.shipLength-1; i++) {
                if(topLeftX <= Game.GRID_SIZE-1) {
                    occupiedCells[i] = new Cell(owningPlayer, topLeftX, topLeftY);          
                }
                else {
                    occupiedCells[i] = null;                                                
                }
                topLeftX++;
            }
        }
        
        if(orientation == Ship.VERTICAL && topLeftX <= Game.GRID_SIZE-1) {
            for(int i = 0; i <= ship.shipLength-1; i++) {
                if(topLeftY <= Game.GRID_SIZE-1) {
                    occupiedCells[i] = new Cell(owningPlayer, topLeftX, topLeftY);          
                }
                else {
                    occupiedCells[i] = null;                                                
                }
                topLeftY++;
            }
        }
            
        return occupiedCells;
    }


    /**
     * Deploys a ship onto this grid at the specified coordinates and orientation. That means that this 
     * method sets the relevant cells of this grid to be marked as occupied by the specified ship. This 
     * method will only be called if the parameters are valid according to the isValidDeployment method.
     * @param ship The ship to be placed. Will never be null.
     * @param topLeftX The x-coordinate for the top-left end of the ship. 
     * @param topLeftY The y-coordinate for the top-left end of the ship. 
     * @param orientation Will always be only one of two values - Ship.HORIZONTAL or Ship.VERTICAL.
     * @return Returns true if the ship was successfully deployed, otherwise false.
     */
    public boolean deploy(Ship ship, int topLeftX, int topLeftY, int orientation) {
        boolean validDeploy = true;
        int x = topLeftX;
        int y = topLeftY;
        
        if(!isValidDeployment(ship, topLeftX, topLeftY, orientation)) {
            validDeploy = false;                                                        
        }
        
        for(int i = 0; i <= ship.shipLength-1; i++) {
            getCell(x, y).occupyWith(ship);                                             
            
            if(orientation == Ship.HORIZONTAL) {
                x++;                                                                   
            }
            if(orientation == Ship.VERTICAL) {
                y++;                                                                   
            }
        }
        return validDeploy;
    }

    /**
     * Returns the cell in this grid that is at the specified coordinates. 
     * @param x The x-coordinate of the cell. 
     * @param y The y-coordinate of the cell. 
     * @return The cell at the specified coordinates.
     */
    public Cell getCell(int x, int y) {
        return grid[x][y];
    }

    /** @return Returns the player that owns this grid.  
     */
    public Player getPlayer() {
        return owningPlayer;
    }

    /**
     * Checks whether it is valid to deploy a ship at a particular position and orientation. 
     * It would not be valid if either of the following two criteria are met:
     * @return Returns true if valid, false otherwise.
     */
    public boolean isValidDeployment(Ship ship, int topLeftX, int topLeftY, int orientation) {
        boolean valid = true;
        Cell occupiedCell;
        int x = topLeftX;
        int y = topLeftY;
        
        for(int i = 0; i <= ship.shipLength-1; i++) {
            if(x >= Game.GRID_SIZE || y >= Game.GRID_SIZE) {
                valid = false; break;                               
            }
            
            if(getCell(x,y).isOccupied()) {
                valid = false;                                      
            }
            
            if(orientation == Ship.HORIZONTAL) {
                x++;                                                
            }
            if(orientation == Ship.VERTICAL) {
                y++;                                                
            }
        }
        
        if(orientation == Ship.HORIZONTAL) {
            if(topLeftX + ship.shipLength > Game.GRID_SIZE) {
                valid = false;                                      
            }
        }
        
        if(orientation == Ship.VERTICAL) {
            if(topLeftY + ship.shipLength > Game.GRID_SIZE) {
                valid = false;                                      
            }
        }
 
        return valid;
    }
}
