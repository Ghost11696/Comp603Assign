package Controller;

import View.PlayerDisplay;
import Model.*;
import View.GameWindow;
import View.StartWindow;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.TimerTask;
import javax.swing.JButton;

/**
 * class controls the attacking phase of battleships
 * @author James-dt
 */
public class AttackPhaseHandler implements PlayerDisplayListener {
    // Public fields

    public JButton bnAutoTarget;

    
    private TimerTask myHumanTurnDisplayTask = null;
    private GameWindow myGameWindow;

    
    public AttackPhaseHandler(GameWindow gameWindow) {
        this.myGameWindow = gameWindow;
    }

   /**
    * simulates the computers turn
    */
    public void computerTakeTurn() {
        Player computerPlayer = myGameWindow.getComputerPlayer();
        Cell computerTarget = myGameWindow.otherPlayer(computerPlayer).getGrid().autoTargetMe();
        tryTakeTurn(myGameWindow.getComputerPlayer(), computerTarget.getX(), computerTarget.getY());
    }

    /**
    * Clears the players display window
    */
    public void clearHumanTurnDisplayTask() {
        if (myHumanTurnDisplayTask == null) {
            return;
        }
        myHumanTurnDisplayTask.cancel();
        myHumanTurnDisplayTask = null;
    }

    /**
     * uses the player autotarget button
     * to target the enemy (computer) 
     */
    public void onAutoTarget() {
        Cell humanTarget = this.myGameWindow.getComputerPlayer().getGrid().autoTargetMe();
        humanTakeTurn(humanTarget.getX(), humanTarget.getY());
    }

    /**
     * Implements if the player clicks on the grid
     */
    public void onGridClicked(PlayerDisplay playerDisplay, MouseEvent e, int x, int y) {
        this.humanTakeTurn(x, y);
    }

    /**
     * Implements if the player enters on the grid
     */
    public void onGridEntered(PlayerDisplay playerDisplay, MouseEvent e, int x, int y) {
    }

    /**
     * Implements if the player exits on the grid
     */
    public void onGridExited(PlayerDisplay playerDisplay, MouseEvent e, int x, int y) {
    }

    /**
     * this will generate the Attack GUI
     */
    public void startGUI() {
        bnAutoTarget.setEnabled(true);
        myGameWindow.getHumanDisplay().setEnabled(false);
        myGameWindow.getComputerDisplay().setEnabled(true);
        myGameWindow.redraw();
        startCurrentTurn();
    }

    /**
     * Colours a button depending on attack result
     *
     * @param attackResult can be hit, miss and sunk
     * @returns a colour depending on attack result
     */
    private Color colourAttackResult(int attackResult) {
        switch (attackResult) {
            case Cell.ATTACK_HIT:
                return StartWindow.COLOUR_HIT;
            case Cell.ATTACK_MISS:
                return StartWindow.COLOUR_MISS;
            case Cell.ATTACK_SUNK:
                return StartWindow.COLOUR_SUNK;
            default:
                return SystemColor.windowText;
        }
    }

    /**
     * displays the result of that turn
     * @param attackingPlayer
     * @param attackResult
     */
    private void displayTurnResult(Player attackingPlayer, int attackResult) {
        PlayerDisplay playerDisplay = myGameWindow.getEnemyDisplayFor(attackingPlayer);
        if (attackResult == Cell.ATTACK_ALREADY) {
            playerDisplay.status(attackingPlayer.getName() + ", you have already attacked there.");
        } else {
            playerDisplay.status(attackingPlayer.getName() + " attacked - " + this.labelAttackResult(attackResult), this.colourAttackResult(attackResult));
            playerDisplay.redraw();
        }
    }

    /**
     * if players turn it will try attack using the parameters else it will
     * print a message to the label
     * @param targetX
     * @param targetY
     */
    private void humanTakeTurn(int targetX, int targetY) {
        Player humanPlayer = myGameWindow.getHumanPlayer();
        boolean wasSuccessful = tryTakeTurn(humanPlayer, targetX, targetY);
        if (wasSuccessful) {
            clearHumanTurnDisplayTask();
        } else {
            myGameWindow.getEnemyDisplayFor(humanPlayer).status(humanPlayer.getName() + ", it is not your turn.");
        }
    }

    /**
     * uses the label to display the attack result
     * @param attackResult
     * @return a string of hit, miss and sunk
     */
    private String labelAttackResult(int attackResult) {
        switch (attackResult) {
            case Cell.ATTACK_HIT:
                return "hit";
            case Cell.ATTACK_MISS:
                return "miss";
            case Cell.ATTACK_SUNK:
                return "sunk";
            default:
                return "";
        }
    }

    /**
     * performs the won method
     *
     * @param winner
     */
    private void onWon(Player winner) {
        myGameWindow.onWon(winner);
    }

    /**
     * sets the human players display task using a thread
     */
    private void setHumanTurnDisplayTask() {
        clearHumanTurnDisplayTask();
    }

    /**
     * starts the computer turn and uses a thread to simulate the computer
     */
    private void startComputerTurn() {
        Player computerPlayer = myGameWindow.getComputerPlayer();
        myGameWindow.getEnemyDisplayFor(computerPlayer).status(computerPlayer.getName() + " is thinking...");

        final AttackPhaseHandler attackPhaseHandler = this;
         attackPhaseHandler.computerTakeTurn();
    }
    
    /**
     * determines whose turn it is
     */
    private void startCurrentTurn() {
        Player turnPlayer = this.myGameWindow.getGame().whoseTurn();
        if (turnPlayer == this.myGameWindow.getHumanPlayer()) {
            this.startHumanTurn();
        } else {
            startComputerTurn();
        }
    }

    /**
     * sets the display task for human
     */
    private void startHumanTurn() {
        setHumanTurnDisplayTask();
    }

    /**
     * determines if a player has won the game performs at the beginning of
     * every turn
     */
    private void startNextTurn() {
        Player winner = myGameWindow.getGame().whoWon();
        if (winner != null) {
            this.onWon(winner);
        } else {
            myGameWindow.getGame().nextTurn();
            startCurrentTurn();
        }
    }

    /**
     * if the player can attack and the player attacks a valid cell that hasnt
     * been attacked already the game will continue to the next turn
     *
     * @param attackingPlayer
     * @param x
     * @param y
     * @return true if the player can attack
     */
    private boolean tryTakeTurn(Player attackingPlayer, int x, int y) {
        if (myGameWindow.getGame().whoseTurn() != attackingPlayer) {
            return false;
        }
        Player attackedPlayer = myGameWindow.otherPlayer(attackingPlayer);
        int result = attackedPlayer.getGrid().getCell(x, y).tryAttack();
        changePlayerScore(attackingPlayer, result);
        displayTurnResult(attackingPlayer, result);
        if (result != Cell.ATTACK_ALREADY) {
            startNextTurn();
        }
        return true;
    }

    /**
     * adds points to the players score depending on if the ship
     * is sunk or has been hit
     * @param attackingPlayer
     * @param result 
     */
    private void changePlayerScore(Player attackingPlayer, int result) {
                
            switch (result){
                case Cell.ATTACK_HIT:
                    attackingPlayer.effectScore(10);
                    break;
                case Cell.ATTACK_SUNK:
                    attackingPlayer.effectScore(100);
                    break;
                default: attackingPlayer.effectScore(0);
            }
    }
}
