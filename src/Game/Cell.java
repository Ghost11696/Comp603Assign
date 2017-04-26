package Game;

/**
 * Cell class. A Cell is one element in a Grid. A Cell knows which Ship is
 * occupying it. Cell is responsible for handling attacks. As well as this, Cell
 * can calculate what the UI should display.
 */
public class Cell {

	/**
	 * @param owningPlayer
	 *            The player who this cell belongs to.
	 * @param x
	 *            The x-coordinate for this cell. Will always be in the range 0
	 *            to Game.GRID_SIZE-1, inclusive.
	 * @param y
	 *            The y-coordinate for this cell. Will always be in the range 0
	 *            to Game.GRID_SIZE-1, inclusive.
	 */
	private Player owningPlayer;
	private int x, y;
	private boolean occupied, attacked, hit;
	private Ship occupyingShip;
	private int attackCounter = 0;

	public Cell(Player owningPlayer, int x, int y) {
		this.owningPlayer = owningPlayer;

		if (x >= 0 && x <= Game.GRID_SIZE - 1) {
			this.x = x; // Sets the value of the variable x as entered in the x
						// parameter, as long as it is between 0 and
						// Game.GRID_SIZE-1, inclusive.
		}

		if (x >= 0 && x <= Game.GRID_SIZE - 1) {
			this.y = y; // Sets the value of the variable y as entered in the y
						// parameter, as long as it is between 0 and
						// Game.GRID_SIZE-1, inclusive.
		}

		occupied = false;
		attacked = false;
		hit = false;
	}

	/** @return The player who this cell belongs to. */
	public Player getPlayer() {
		return owningPlayer;
	}

	/** @return The ship that occupies this cell. */
	public Ship getOccupyingShip() {
		return occupyingShip;
	}

	/** @return The x-coordinate of this cell. */
	public int getX() {
		return x;
	}

	/** @return The y-coordinate of this cell. */
	public int getY() {
		return y;
	}

	/** @return the true if ship is occupied */
	public boolean shipExist() {
		boolean ship;
		if (occupied == true) {
			ship = true;
		} else
			ship = false;

		return ship;
	}

	/**
	 * Has this cell been hit? This cell can only be hit if it meets both these
	 * conditions: 1. It is occupied. 2. It has been attacked.
	 * 
	 * @return Returns true if this cell was hit, false otherwise.
	 */
	public boolean isHit() {
		hit = false;
		attacked = true;
		attackCounter++;

		if (occupied && attacked) {
			occupyingShip.hit();
			hit = true;
		}

		return hit;
	}

	/**
	 * Marks this cell as occupied by the specified ship.
	 * 
	 * @param ship
	 *            The ship to occupy this cell with.
	 */
	public void occupyWith(Ship ship) {
		occupyingShip = ship;
		occupied = true;
	}

	/**
	 * Has this cell been attacked before?
	 * 
	 * @return Returns true if this cell has been attacked before, false
	 *         otherwise.
	 */
	public boolean wasAttacked() {
		return attacked;
	}

	/**
	 * Counts how many times the cell has been attacked
	 * 
	 * @return Returns an int with the amount of times the cell has been
	 *         attacked
	 */
	public int getAttackCounter() {
		return attackCounter;
	}

	/**
	 * Prints the representation of the cell
	 * 
	 * @return Returns a toString method of the cell
	 */
	public String toString() {
		String data = "";

		data += '[';

		if (attacked && hit) {
			data += 'X';
		} else if (attacked && !hit) {
			data += '#';
		} else if (occupied && owningPlayer.getName() != "Computer") {
			data += 'O';
		} else {
			data += ' ';
		}

		data += "] ";

		return data;
	}
	/**
	 * Prints an attack to String
	 * @return a String with an attack to String
	 */
	public String AttackToString() {
		String data = "";

		if (hit && occupyingShip.isSunk()) {
			data += "'s ship sunk!";
		} else if (hit && !occupyingShip.isSunk()) {
			data += "'s ship was hit!";
		} else {
			data += " Missed!";
		}
		return data;
	}
}
