package Model;
/**
 * ShipTest class. Performs only basic tests 
 */
public class ShipTest extends junit.framework.TestCase
{
    public ShipTest() { }

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

	public void testGetNameCallable() {
		for(Ship ship : this.createShips()) {
			String result = ship.getName();
		}
	}

	public void testGetNameNotNull() {
		for(Ship ship : this.createShips()) {
			assertNotNull(ship.getName());
		}
	}

	public void testGetLengthCallable() {
		for(Ship ship : this.createShips()) {
			int result = ship.getLength();
		}
	}

	public void testGetLengthIsPositive() {
		for(Ship ship : this.createShips()) {
			assertTrue(ship.getLength() > 0);
		}
	}

	public void testHitCallable() {
		for(Ship ship : this.createShips()) {
			ship.hit();
		}
	}

	public void testShipIsNotSunkAtStart() {
		for(Ship ship : this.createShips()) {
			assertFalse(ship.isSunk());
		}
	}

	public void testIsSunkCallable() {
		for(Ship ship : this.createShips()) {
			boolean result = ship.isSunk();
		}
	}

	public void testPatrolBoatSinksEventually() {
		PatrolBoat ship = new PatrolBoat();
		for(int hitAttempts = 0; hitAttempts < 100; ++hitAttempts) {
			if(ship.isSunk()) { break; }
			else{ ship.hit(); }
		}
		assertTrue("The PatrolBoat did not sink. Instead, Ship.isSunk returned false.", ship.isSunk());
	}

	public void testSubclassConstructorsCallable() {
		this.createShips();
	}

	private Ship[] createShips() {
		return new Ship[] {
			new AircraftCarrier(),
			new Battleship(),
			new Destroyer(),
			new PatrolBoat(),
			new Submarine()
		};
	}
}
