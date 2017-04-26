package Game;

/**
 * Player class. Each Player has a name, five Ships and a Grid. A Player can
 * determine whether he or she has been defeated or not.
 */
public class Player {
	/**
	 * Player constructor. Initialises the following three things:
	 *
	 * 1. Sets the player's name as specified by the name parameter.
	 *
	 * 2. Initialises the player with their five ships: an AircraftCarrier, a
	 * Battleship, a Submarine, a Destroyer and a PatrolBoat.
	 *
	 * 3. Creates the Grid for this player.
	 *
	 * @param name
	 *            The name to be used for this player.
	 */
	private String playerName;
	private Ship[] ships;
	private Grid grid;
	private int score;

	public Player(String name) {
		playerName = name;
		ships = new Ship[] { new AircraftCarrier(), new Battleship(),
				new Submarine(), new Destroyer(), new PatrolBoat() };
		grid = new Grid(this);
		this.score = 0;
	}

	/** @return This player's grid. */
	public Grid getGrid() {
		return grid;
	}

	/** @return This player's score. */
	public int getScore() {
		return score;
	}

	/** Sets this player's score */
	public void setScore(int score) {
		this.score = score;
	}

	/** @return This player's name. */
	public String getName() {
		return playerName;
	}

	/**
	 * @return The array of this player's ships. The array must always contain
	 *         the ships in this order: AircraftCarrier, Battleship, Submarine,
	 *         Destroyer and PatrolBoat.
	 */
	public Ship[] getShips() {
		return ships;
	}

	/**
	 * Returns true if this player has been defeated. A player is defeated when
	 * all their ships have been sunk.
	 * 
	 * @return Returns true if this player has been defeated, false otherwise.
	 */
	public boolean isDefeated() {
		boolean defeated = true;

		for (int i = 0; i <= ships.length - 1; i++) {
			if (!ships[i].isSunk()) {
				defeated = false;
			}
		}

		return defeated;
	}

	/**
	 * Returns a string depending if player won or lost
	 * 
	 * @return Returns a "win" string player has not been defeated, lose string
	 *         if otherwise
	 */
	public String toString() {
		String data;

		if (!isDefeated()) {
			data = ("You WIN " + playerName + "! CONGRATULATIONS! thanks for playing battleships");
		} else
			data = ("You LOSE " + playerName + "! better luck next time! thanks for playing battleships");

		return data;
	}
}
