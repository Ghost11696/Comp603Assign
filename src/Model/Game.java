package Model;

import DataBase.PlayerScore;

/**
 * Game class. Represents one game of battleships. Each Game has
 * two Players. The Game keeps track of whose turn it is, and is
 * responsible for determining which Player has won.
 */
public class Game {
    public static final int GRID_SIZE = 10; // The height and width of the grid - DO NOT CHANGE
    
    /**
     * Game Constructor. Creates this game's players from the specified names.
     * Also initialises the game so that player 1 starts first.
     * @param player1Name The name of player 1.
     * @param player2Name The name of player 2.
     */   
    private Player player1, player2;
    private int turn;
    private PlayerScore highscore;
    
    public Game(String player1Name, String player2Name) {
        player1 = new Player(player1Name);
        player2 = new Player(player2Name);
        turn = 1;
    }

    /** @return Returns player 1. */
    public Player getPlayer1() {
        return player1;
    }

    /** @return Returns player 2. */
    public Player getPlayer2() {
        return player2;
    }

    /** 
     * Advances the game to the next turn - that is, makes it
     * the next player's turn. 
     */
    public void nextTurn() {
       turn++;
    }

    /**
     * Calculates and returns who has won the game, or returns null
     * if no one has won the game yet. A player is considered to have 
     * won the game when they have sunk all their opponent's ships.
     * @return The player who won, or null if no one has won yet.
     */
    public Player whoWon() {
        Player whoWon = null;
        
        if(player1.isDefeated())
            whoWon = player2;   //If Player One has been defeated, then Player Two won.
            
        if(player2.isDefeated())
            whoWon = player1;   //If Player Two has been defeated, then Player One won.
            
        return whoWon;       
    }

    /**
     * Returns the player whose turn it is currently. This method will
     * never be called if the whoWon method returns a winner
     * @return The player whose turn it is currently.
     */
    public Player whoseTurn() {
        
        Player whoseTurn = null; 
        
        if(whoWon() == null) {
            if(turn%2 == 1)
                whoseTurn = player1; //If turn is odd, then it is Player One's turn.
            if(turn%2 == 0) 
                whoseTurn = player2; //If turn is even, then it is Player Two's turn.
        }
        
        return whoseTurn;
    }

    /**
     * opens the PLAYERSCORE database
     * adds the players name and score to the database
     * then closes the database
     */
    public void startConnection() {
        highscore = new PlayerScore();
        int playerOneScore = (player1.getScore() - player2.getScore());
        String playerOneName = player1.getName();
        highscore.addHighScoreValues(playerOneName, playerOneScore);
        highscore.closeConn();
    }
}
