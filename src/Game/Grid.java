package Game;


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
    private Player owningPlayer;
    private Cell[][] grid;
    
    public Grid(Player player) {
        owningPlayer = player;
        grid = new Cell[Game.GRID_SIZE][Game.GRID_SIZE];                    //Creates a 2D array, of area Game.GRID_SIZE*Game.GRID_SIZE.
        
        for(int row = 0; row <= Game.GRID_SIZE-1; row++) {                  //"for" loop controlling the rows in the array.
            for(int column = 0; column <= Game.GRID_SIZE-1; column++) {     //"for" loop controlling the columns in the array.
                grid[row][column] = new Cell(owningPlayer, row, column);    //Creates a new Cell at the x and y locations specified by "row" and "column".
            }
        }
    }
      
    /**
     * Works exactly like deploy, except the position and orientation
     * of the ship are chosen randomly. 
     */
    public void autoDeploy(Ship ship) {
        int topLeftX = (int)(Math.random()*10);                                 //Creates a random integer value for the X coordinate.
        int topLeftY = (int)(Math.random()*10);                                 //Creates a random integer value for the Y coordinate.
        int orientation = (int)((Math.random()*10)/5);                          //Creates a random integer value for the orientation of the ship, either 0 or 1.
        
        while(!isValidDeployment(ship, topLeftX, topLeftY, orientation)) {
                topLeftX = (int)(Math.random()*10);                             //Changes the X coordinate if the current position will not result in a valid deploy.
                topLeftY = (int)(Math.random()*10);                             //Changes the Y coordinate if the current position will not result in a valid deploy.
            }
        
        deploy(ship, topLeftX, topLeftY, orientation);                          //Deploys the ship at the random location specified by "topLeftX" and "topLeftY", at the orientation specified by "orientation"
    }
    
    /**
     * @return A valid cell for this grid's player's opponent to target.
     */

    public Cell autoTargetMe() {
        Cell validCell = null;
        int x = (int)(Math.random()*10);    //Sets a random x coordinate within the grid.
        int y = (int)(Math.random()*10);    //Sets a random y coordinate within the gird.
        
        Cell tempCell;
        
        while(!owningPlayer.isDefeated()) {     //Keeps looking for a validCell while the game has not been won yet.
            tempCell = getCell(x, y);
            
            if(!tempCell.wasAttacked()) {
                validCell = tempCell; break;    //Sets tempCell as the validCell if tempCell hasn't been attacked yet.
            }
            else {
                x = (int)(Math.random()*10);    //Sets a new x coordinate if tempCell has been attacked already.
                y = (int)(Math.random()*10);    //Sets a new y coordinate if tempCell has been attacked already.
            }
        }
        return validCell;
    }

    /**
     * @param ship The ship to be placed. Will never be null.
     * @param topLeftX The x-coordinate for the top-left end of the ship. Will always be in the range 0 to Game.GRID_SIZE-1, inclusive.
     * @param topLeftY The y-coordinate for the top-left end of the ship. Will always be in the range 0 to Game.GRID_SIZE-1, inclusive.
     * @param orientation Will always be only one of two values - Ship.HORIZONTAL or Ship.VERTICAL.
     * @return Returns true if the ship was successfully deployed, otherwise false.
     */
    public boolean deploy(Ship ship, int topLeftX, int topLeftY, int orientation) {
        boolean validDeploy = true;
        int x = topLeftX;
        int y = topLeftY;
        
        if(!isValidDeployment(ship, topLeftX, topLeftY, orientation)) {
            validDeploy = false;                                                        //Sets the variable validDeploy as false if the conditions of the deployment do not meet the criteria of a valid deployment.
        }
        
        for(int i = 0; i <= ship.getLength()-1; i++) {
            getCell(x, y).occupyWith(ship);                                             //Occupies the cell at (x, y) with ship.
            
            if(orientation == Ship.HORIZONTAL) {
                x++;                                                                    //Increments x if the ship orientation is horizontal.
            }
            if(orientation == Ship.VERTICAL) {
                y++;                                                                    //Increments y if the ship orientation is vertical.
            }
        }
        return validDeploy;
    }

    /**
     * Returns the cell in this grid that is at the specified coordinates. 
     * @param x The x-coordinate of the cell. Will always be in the range 0 to Game.GRID_SIZE-1, inclusive.
     * @param y The y-coordinate of the cell. Will always be in the range 0 to Game.GRID_SIZE-1, inclusive.
     * @return The cell at the specified coordinates.
     */
    public Cell getCell(int x, int y) {
        return grid[x][y];
    }

    /** @return Returns the player that owns this grid.  */
    public Player getPlayer() {
        return owningPlayer;
    }

    /**
     * Checks whether it is valid to deploy a ship at a particular position and orientation. 
     * It would not be valid if either of the following two criteria are met:
     *  (a) The ship would overlap another ship on the grid.
     *  (b) Part of the ship would exceed the boundaries of the grid.
     * @return Returns true if valid, false otherwise.
     */
    public boolean isValidDeployment(Ship ship, int topLeftX, int topLeftY, int orientation) {
        boolean valid = true;
        int x = topLeftX;
        int y = topLeftY;
        
        for(int i = 0; i <= ship.getLength()-1; i++) {
            if(x >= Game.GRID_SIZE || y >= Game.GRID_SIZE) {
                valid = false; break;                               
            }
            
            if(getCell(x,y).getOccupyingShip() != null) {
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
            if(topLeftX + ship.getLength() > Game.GRID_SIZE) {
                valid = false;                                      
            }
        }
        
        if(orientation == Ship.VERTICAL) {
            if(topLeftY + ship.getLength() > Game.GRID_SIZE) {
                valid = false;                                      
            }
        }
 
        return valid;
    }
	
}
