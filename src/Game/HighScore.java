package Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Highscore class. Contains functions that open a file and close
 * it can also compare highscores and add them to the file
 * it also prints a highscore title
 */
public class HighScore {

	private ArrayList<String> nameArray = new ArrayList<String>();
	private ArrayList<Integer> scoreArray = new ArrayList<Integer>();
	private Player playerScore;
	private String fileName = "src/Score.txt";
	Scanner fileScan;
	PrintWriter pw;

	public HighScore(Player playerScore) {
		setPlayerScore(playerScore);
		readTextFile();
	}

	/**
	 * sets the Player as playerScore
	 * 
	 * @param playerScore
	 */
	private void setPlayerScore(Player playerScore) {
		this.playerScore = playerScore;
	}

	/**
	 * this method reads the txt document and splits the names and scores of the
	 * players
	 */
	public void readTextFile() {

		try {
			fileScan = new Scanner(new File(fileName));

			while (fileScan.hasNext()) {
				String[] highScoreVariables = fileScan.nextLine().split(" ");
				nameArray.add(highScoreVariables[1]);
				scoreArray.add(Integer.parseInt(highScoreVariables[2]));
			}
			fileScan.close();
		}

		catch (FileNotFoundException e) {
			System.err.println("Unable to find data file '" + fileName + "'");
		} catch (IOException e) {
			System.err.println("Problem encountered processing file.");
		}
	}

	/**
	 * this method compares player1 with the current high scoring player and
	 * inserts the players name depending if it is high enough
	 */
	public void compareScore() {
		for (int i = 0; i < scoreArray.size(); i++) {
			if (playerScore.getScore() > scoreArray.get(i).intValue()) {
				scoreArray.add(i, playerScore.getScore());
				nameArray.add(i, playerScore.getName());
				break;
			}
		}
	}

	/**
	 * Writes new/existing high score list to score.txt using a try and catch
	 * exception this method writes from the file specified
	 */
	public void writeToFile() {
		try {
			pw = new PrintWriter(fileName);

			for (int i = 0; i < scoreArray.size(); i++) {
				System.out.println(i + 1 + " " + nameArray.get(i) + " "
						+ scoreArray.get(i));
				pw.write(i + 1 + " " + nameArray.get(i) + " "
						+ scoreArray.get(i));
				pw.println();
			}
			pw.close();
		} catch (FileNotFoundException e) {
			System.err.println("Unable to find data file '" + fileName + "'");
		} catch (IOException ex) {
			System.err.println("Problem encountered processing file.");
		}

	}
	
	/**
	 * Prints a fancy title with a border
	 */
	public void printHighScoreTitle() {
		String data = "";

		data += "+-----------------------------------------------+\n";
		data += "|                  HIGHSCORES                   |\n";
		data += "+-----------------------------------------------+\n";

		System.out.println(data);
	}
}
