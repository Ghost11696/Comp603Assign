package Game;

/**
 * This class manages the Command user interface (CUI) for the game. 
 *  * @author Bon Berry/James Dillon
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class BattleApp {

	private static Game game;
	public static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {

		System.out.println("Welcome to Battleships");

		System.out.print("Please Enter your name: ");
		String player1 = scan.nextLine();
		
		
		//player 1 cannot be called Computer
		if(player1.equals("Computer")){
			do{
				System.out.println("Please use another name");
				player1 = scan.nextLine();
			}while(player1.equals("Computer"));
		}

		// creates a new game with the two player names
		game = new Game(player1, "Computer");
		game.autoDeployCurrentShip();

		// controls the game
		do {
			game.nextTurn();

			if (game.whoseTurn().equals(game.getPlayer1())) {
				drawMap();

				System.out
						.println("\nEnter co-ordinates to attack! (eg X co ord)");
				int attackX = CreateCoordinate();

				System.out
						.println("\nEnter co-ordinates to attack! (eg Y co ord)");
				int attackY = CreateCoordinate();

				game.getPlayer2().getGrid().getCell(attackY, attackX).isHit();
				if (game.getPlayer2().getGrid().getCell(attackY, attackX)
						.getAttackCounter() > 1) {
					System.out.println("\n"
							+ game.getPlayer1().getName()
							+ " has attacked this cell "
							+ game.getPlayer2().getGrid()
									.getCell(attackY, attackX)
									.getAttackCounter() + " times already");

				} else {
					if (game.getPlayer2().getGrid().getCell(attackY, attackX)
							.shipExist()) {
						game.getPlayer1().setScore(
								game.getPlayer1().getScore() + 50);
						System.out.println("\n"
								+ game.getPlayer1().getName()
								+ " attacked and "
								+ game.getPlayer2().getName()
								+ game.getPlayer2().getGrid()
										.getCell(attackY, attackX)
										.AttackToString());
					} else {

						System.out.println("\n"
								+ game.getPlayer1().getName()
								+ " attacked and"
								+ game.getPlayer2().getGrid()
										.getCell(attackY, attackX)
										.AttackToString());
					}
				}

			} else {
				Cell cord;
				cord = game.getPlayer1().getGrid().autoTargetMe();
				game.getPlayer1().getGrid().getCell(cord.getX(), cord.getY())
						.isHit();

				if (game.getPlayer1().getGrid()
						.getCell(cord.getX(), cord.getY()).shipExist()) {
					game.getPlayer1().setScore(
							game.getPlayer1().getScore() - 50);
					System.out.println("\n"
							+ game.getPlayer2().getName()
							+ " attacked and "
							+ game.getPlayer1().getName()
							+ game.getPlayer1().getGrid()
									.getCell(cord.getX(), cord.getY())
									.AttackToString());
				} else {
					System.out.println("\n"
							+ game.getPlayer2().getName()
							+ " attacked and"
							+ game.getPlayer1().getGrid()
									.getCell(cord.getX(), cord.getY())
									.AttackToString());
				}
			}

		} while (game.whoWon() == null);

		System.out.println(game.whoWon().toString());

		// creates a highscore class
		HighScore playerHighScore = new HighScore(game.whoWon());
		playerHighScore.compareScore();
		playerHighScore.printHighScoreTitle();
		playerHighScore.writeToFile();

	}

	/**
	 * This method Checks the users input is and int and between the grid axis
	 * 
	 * @return an int to attack
	 */
	public static int CreateCoordinate() {

		int attack = -1;

		do {
			try {
				System.out.println("please use a integer 0-9");
				attack = scan.nextInt();
			} catch (InputMismatchException e) {
				scan.nextLine();
			}
		} while (!(attack >= 0 && attack <= 9));
		return attack;
	}

	/**
	 * This method builds and prints the map so the player can see their own
	 * board and the enemy's
	 */
	public static void drawMap() {

		System.out.println("\n		 Your Board");
		for (int z = 0; z < Game.GRID_SIZE; z++) {
			System.out.print("   "
					+ game.getPlayer1().getGrid().getCell(0, z).getY());
		}

		for (int x = 0; x < Game.GRID_SIZE; x++) {
			System.out.print("\n"
					+ game.getPlayer1().getGrid().getCell(x, 0).getX() + " ");
			for (int y = 0; y < Game.GRID_SIZE; y++) {
				System.out.print(game.getPlayer1().getGrid().getCell(x, y)
						.toString());
			}
		}

		System.out.println("\n\n		 Enemy Board");
		for (int z = 0; z < Game.GRID_SIZE; z++) {
			System.out.print("   "
					+ game.getPlayer2().getGrid().getCell(0, z).getY());
		}

		for (int x = 0; x < Game.GRID_SIZE; x++) {
			System.out.print("\n"
					+ game.getPlayer2().getGrid().getCell(x, 0).getX() + " ");
			for (int y = 0; y < Game.GRID_SIZE; y++) {
				System.out.print(game.getPlayer2().getGrid().getCell(x, y)
						.toString());
			}
		}
	}
}