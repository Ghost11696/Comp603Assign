package Model;
/**
 * PlayerTest class. Performs only basic tests 
 */
public class PlayerTest extends junit.framework.TestCase
{
    public PlayerTest() { }

    /**
     * Sets up the test fixture.
     * Called before every test case method.
     */
    protected void setUp() {
    }

    /**
     * Tears down the test fixture.
     * Called after every test case method.
     */
    protected void tearDown() {
    }

	public void testConstructorCallable() {
		new Player("");
	}

	public void testGetGridCallable() {
		Grid grid = new Player("").getGrid();
	}

	public void testGetGridDifferentForDifferentPlayers() {
		assertNotNull(new Player("").getGrid());
		assertNotSame(new Player("").getGrid(), new Player("").getGrid());
	}

	public void testGetGridNotNull() {
		assertNotNull(new Player("").getGrid());
	}

	public void testGetNameCallable() {
		String name = new Player("").getName();
	}

	public void testGetNameNotNull() {
		assertNotNull(new Player("").getName());
	}
	
	public void testGetShipsCallable() {
		Ship[] ships = new Player("").getShips();
	}

	public void testGetShipsDifferentForDifferentPlayers() {
		assertNotNull(new Player("").getShips());
		assertNotSame(new Player("").getShips(), new Player("").getShips());
	}

	public void testGetShipsHasFiveShips() {
		Ship[] ships = new Player("").getShips();
		assertNotNull(ships);
		assertEquals("The Player does not have five ships.", ships.length, 5);
	}

	public void testGetShipsNotNull() {
		assertNotNull(new Player("").getShips());
	}

	public void testIsDefeatedCallable() {
		boolean result = new Player("").isDefeated();
	}

	public void testIsDefeatedIsFalseAtStart() {
		assertFalse(new Player("").isDefeated());
	}
}
