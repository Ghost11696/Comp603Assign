package Model;
/**
 * GameTest class. Performs only basic tests 
 */
public class GameTest extends junit.framework.TestCase
{
    public GameTest() { }

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
		new Game("", "");
	}

	public void testGetPlayer1Callable() { 
		Player player = (new Game("", "")).getPlayer1();
	}

	public void testGetPlayer1IsConsistent() {
		Game game = new Game("1", "2");
		assertSame(
			"Two successive calls to Game.getPlayer1() returned two different Players." +
			"There should only be one player 1. ",
			game.getPlayer1(), game.getPlayer1()
		);
	}

	public void testGetPlayer2Callable() {
		Player player = (new Game("", "")).getPlayer2();
	}

	public void testGetPlayer2IsConsistent() {
		Game game = new Game("1", "2");
		assertSame(
			"Two successive calls to Game.getPlayer2() returned two different Players." +
			"There should only be one player 2. ",
			game.getPlayer2(), game.getPlayer2()
		);
	}

	public void testNextTurnCallable() {
		new Game("", "").nextTurn();
	}

	public void testPlayersAreDifferent() {
		Game game = new Game("1", "2");
		assertNotSame(
			"Both Game.getPlayer1 and Game.getPlayer2 return the same Player. " +
			"They should return different Players.",
			game.getPlayer1(), game.getPlayer2()
		);
	}

	public void testPlayersExist() {
		Game game = new Game("1", "2");
		assertNotNull(game.getPlayer1());
		assertNotNull(game.getPlayer2());
	}

	public void testWhoWonCallable() {
		Player result = (new Game("", "")).whoWon();
	}

	public void testWhoseTurnCallable() {
		Player result = (new Game("", "")).whoseTurn();
	}

	public void testWhoseTurnDoesNotChangeTurn() {
		Game game = new Game("1", "2");
		assertSame(
			"Game.whoseTurn was called twice, and it returned a different player the second time, " + 
			"even though nextTurn had not been called. The player whose turn it is must only change " +
			"if nextTurn is called.",
			game.whoseTurn(), game.whoseTurn()
		);
	}
}
