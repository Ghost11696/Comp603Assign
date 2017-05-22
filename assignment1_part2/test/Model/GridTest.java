package Model;
/**
 * GridTest class. Performs only basic tests
 */
public class GridTest extends junit.framework.TestCase
{
    public GridTest() {
    }

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

	public void testAutoDeployCallable() {
		new Player("").getGrid().autoDeploy(new AircraftCarrier());
	}

	public void testAutoTargetMeCallable() {
		Cell result = new Player("").getGrid().autoTargetMe();
	}

	public void testAutoTargetBeNotNullAtStart() {
		assertNotNull(new Player("").getGrid().autoTargetMe());
	}

	public void testCoveredCellsArrayLength() {
		Ship[] ships = new Ship[] {
			new AircraftCarrier(),
			new Battleship(),
			new Destroyer(),
			new Submarine(),
			new PatrolBoat()
		};
		for(Ship ship : ships) {
			Cell[] result = new Player("").getGrid().coveredCells(ship, 0, 0, Ship.HORIZONTAL);
			assertNotNull("Grid.coveredCells returned null. It should return an array.", result);
			assertEquals(
				"The ship length and the length of the array returned by Grid.coveredCells are different.",
				result.length, ship.getLength()
			);
		}
	}

	public void testCoveredCellsCallable() {
		Cell[] result = new Player("").getGrid().coveredCells(new AircraftCarrier(), 0, 0, Ship.HORIZONTAL);
	}

	public void testDeployCallable() {
		new Player("").getGrid().deploy(new AircraftCarrier(), 0, 0, Ship.HORIZONTAL);
	}

	public void testGetCellCallable() {
		Cell cell = new Player("").getGrid().getCell(0, 0);
	}

	public void testGetCellIsConsistent() {
		Grid grid = new Player("").getGrid();
		assertSame(
			"The Grid.getCell method was called twice with the same coordinates, " + 
			"yet it returned a different Cell object the second time. " + 
			"Perhaps your code is creating a new Cell when it should be reusing the existing one. ",
			grid.getCell(0, 0), grid.getCell(0, 0)
		);
	}

	public void testGetCellDifferentAtDifferentLocations() {
		Grid grid = new Player("").getGrid();
		assertNotNull(grid.getCell(0, 0));
		assertNotNull(grid.getCell(1, 1));
		assertNotSame(grid.getCell(0, 0), grid.getCell(1, 1));
	}

	public void testGetCellNotNullAt_x0y0() {
		assertNotNull(new Player("").getGrid().getCell(0, 0));
	}

	public void testGetCellNotNullAt_gridLimit() {
		assertNotNull(new Player("").getGrid().getCell(Game.GRID_SIZE-1, Game.GRID_SIZE-1));
	}

	public void testGetPlayerCallable() {
		Player player = new Player("").getGrid().getPlayer();
	}
	
	public void testGetPlayerReturnsSpecifiedPlayer() {
		Player player = new Player("");
		assertEquals(
			"Grid.getPlayer() did not return the same Player object that was provided to the Grid constructor.",
			player, player.getGrid().getPlayer()
		);
	}

	public void testIsValidDeploymentCallable() {
		boolean result = new Player("").getGrid().isValidDeployment(new AircraftCarrier(), 0, 0, Ship.HORIZONTAL);
	}
}
