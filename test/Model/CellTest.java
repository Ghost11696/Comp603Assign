package Model;
import java.util.*;
/**
 * CellTest class. Performs only basic tests 
 */
public class CellTest extends junit.framework.TestCase
{
    public CellTest() { }

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

	public void testCellRetrievable() {
		Cell cell = new Player("").getGrid().getCell(0, 0);
	}

	public void testDisplayStateCallable() { 
		int result = new Player("").getGrid().getCell(0, 0).displayState(new Player(""));
	}

	public void testDisplayStateDifferentForEnemy() {
		Player owningPlayer = new Player("");
		Player enemyPlayer = new Player("");
		Cell cell = owningPlayer.getGrid().getCell(0, 0);
		cell.occupyWith(new PatrolBoat());

		assertTrue(
			"Cell.displayState returned the same value for the owning player as it did for the enemy player, even though the cell is occupied. " +
			"Only the owning player should be able to see the position of their ships.",
			cell.displayState(owningPlayer) != cell.displayState(enemyPlayer)
		);
	}

	public void testDisplayStateReturnValueRange() {
		Random rand = new Random();
		for(int i = 0; i < 100; ++i) {
			Player owningPlayer = new Player("");
			
			Cell cell = owningPlayer.getGrid().getCell(0, 0);
			if(rand.nextDouble() < 0.5) { cell.occupyWith(new PatrolBoat()); }
			if(rand.nextDouble() < 0.5) { cell.tryAttack(); }

			Player viewingPlayer = (rand.nextDouble() < 0.5) ? new Player("") : owningPlayer;
			int result = cell.displayState(viewingPlayer);

			if(	result != Cell.DISPLAY_HIT && 
				result != Cell.DISPLAY_MISS &&
				result != Cell.DISPLAY_OCCUPIED && 
				result != Cell.DISPLAY_BLANK ) {
				
				assertTrue(
					"The Cell.displayState method returned a value other than one of the four allowable values: " +
					"Cell.DISPLAY_HIT, Cell.DISPLAY_MISS, Cell.DISPLAY_OCCUPIED or Cell.DISPLAY_BLANK. ",
					false
				);
				return;
			}
		}
	}

	public void testGetPlayerCallable() {
		Player player = new Player("").getGrid().getCell(0, 0).getPlayer();
	}

	public void testGetOccupyingShipCallable() {
		Ship ship = new Player("").getGrid().getCell(0, 0).getOccupyingShip();
	}

	public void testGetOccupyingShipConsistent() {
		Cell cell = new Player("").getGrid().getCell(0, 0);
		cell.occupyWith(new PatrolBoat());
		assertSame(
			"Two consecutive to Cell.getOccupyingShip() on the same unchanged cell returned two different objects. ",
			cell.getOccupyingShip(), cell.getOccupyingShip()
		);
	}

	public void testGetOccupyingShipIsNullAtStart() {
		assertNull(new Player("").getGrid().getCell(0, 0).getOccupyingShip());
	}

	public void testGetXCallable() {
		int x = new Player("").getGrid().getCell(0, 0).getX();
	}

	public void testGetYCallable() {
		int y = new Player("").getGrid().getCell(0, 0).getY();
	}

	public void testIsHitCallable() {
		boolean isHit = new Player("").getGrid().getCell(0, 0).isHit();
	}

	public void testIsNotHitAtStart() {
		assertFalse(new Player("").getGrid().getCell(0, 0).isHit());
	}

	public void testOccupyWithCallable() {
		new Player("").getGrid().getCell(0, 0).occupyWith(new AircraftCarrier());
	}

	public void testTryAttackCallable() {
		int result = new Player("").getGrid().getCell(0, 0).tryAttack();
	}

	public void testTryAttackReturnValueRange() {
		Random rand = new Random();
		for(int i = 0; i < 100; ++i) {
			Cell cell = new Player("").getGrid().getCell(0, 0);

			if(rand.nextDouble() < 0.5) {
				Ship ship = new AircraftCarrier();
				int hits = rand.nextInt(ship.getLength()+1);
				for(int repeatHit = 0; repeatHit < hits; ++repeatHit) {
					ship.hit();
				}
				cell.occupyWith(ship);
			}
			
			for(int repeatAttack = 0; repeatAttack < 2; ++repeatAttack) {
				int result = cell.tryAttack();
				if(	result != Cell.ATTACK_HIT &&
					result != Cell.ATTACK_MISS &&
					result != Cell.ATTACK_SUNK &&
					result != Cell.ATTACK_ALREADY ) {
					
					assertTrue(
						"The Cell.tryAttack() method returned a value other than one of the four allowable values: " +
						"Cell.ATTACK_HIT, Cell.ATTACK_MISS, Cell.ATTACK_SUNK or Cell.ATTACK_ALREADY. ",
						false
					);
					return;
				}
			}
		}
	}

	public void testWasAttackedCallable() {
		boolean result = new Player("").getGrid().getCell(0, 0).wasAttacked();
	}

	public void testWasAttackedIsFalseAtStart() {
		assertFalse(new Player("").getGrid().getCell(0, 0).wasAttacked());
	}
}
