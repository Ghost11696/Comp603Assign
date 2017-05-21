/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.*;
import View.GameWindow;
import View.UIBattleApp;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.TimerTask;
import javax.swing.JButton;

/**
 *
 * @author James-dt
 */
public class AttackPhaseHandler implements PlayerDisplayListener {
    // Public fields

    public JButton bnAutoTarget;

    // Private fields
    private TimerTask myHumanTurnDisplayTask = null;
    private GameWindow myGameWindow;

    // Constructors
    public AttackPhaseHandler(GameWindow gameWindow) {
        this.myGameWindow = gameWindow;
    }

    // Public methods
    public void computerTakeTurn() {
        Player computerPlayer = this.myGameWindow.getComputerPlayer();
        Cell computerTarget = this.myGameWindow.otherPlayer(computerPlayer).getGrid().autoTargetMe();
        this.tryTakeTurn(this.myGameWindow.getComputerPlayer(), computerTarget.getX(), computerTarget.getY());
    }

    public void clearHumanTurnDisplayTask() {
        if (this.myHumanTurnDisplayTask == null) {
            return;
        }
        this.myHumanTurnDisplayTask.cancel();
        this.myHumanTurnDisplayTask = null;
    }

    public void onAutoTarget() {
        Cell humanTarget = this.myGameWindow.getComputerPlayer().getGrid().autoTargetMe();
        this.humanTakeTurn(humanTarget.getX(), humanTarget.getY());
    }

    public void onGridClicked(PlayerDisplay playerDisplay, MouseEvent e, int x, int y) {
        this.humanTakeTurn(x, y);
    }

    public void onGridEntered(PlayerDisplay playerDisplay, MouseEvent e, int x, int y) {
    }

    public void onGridExited(PlayerDisplay playerDisplay, MouseEvent e, int x, int y) {
    }

    public void startGUI() {
        this.bnAutoTarget.setEnabled(true);
        this.myGameWindow.getHumanDisplay().setEnabled(false);
        this.myGameWindow.getComputerDisplay().setEnabled(true);
        this.myGameWindow.redraw();
        this.startCurrentTurn();
    }

    // Private methods
    private Color colourAttackResult(int attackResult) {
        switch (attackResult) {
            case Cell.ATTACK_HIT:
                return UIBattleApp.COLOUR_HIT;
            case Cell.ATTACK_MISS:
                return UIBattleApp.COLOUR_MISS;
            case Cell.ATTACK_SUNK:
                return UIBattleApp.COLOUR_SUNK;
            default:
                return SystemColor.windowText;
        }
    }

    private void displayTurnResult(Player attackingPlayer, int attackResult) {
        PlayerDisplay playerDisplay = this.myGameWindow.getEnemyDisplayFor(attackingPlayer);
        if (attackResult == Cell.ATTACK_ALREADY) {
            playerDisplay.status(attackingPlayer.getName() + ", you have already attacked there.");
        } else {
            playerDisplay.status(attackingPlayer.getName() + " attacked - " + this.labelAttackResult(attackResult), this.colourAttackResult(attackResult));
            playerDisplay.redraw();
        }
    }

    private void humanTakeTurn(int targetX, int targetY) {
        Player humanPlayer = this.myGameWindow.getHumanPlayer();
        boolean wasSuccessful = this.tryTakeTurn(humanPlayer, targetX, targetY);
        if (wasSuccessful) {
            this.clearHumanTurnDisplayTask();
        } else {
            this.myGameWindow.getEnemyDisplayFor(humanPlayer).status(humanPlayer.getName() + ", it is not your turn.");
        }
    }

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

    private void onWon(Player winner) {
        this.myGameWindow.onWon(winner);
    }

    private void setHumanTurnDisplayTask() {
        this.clearHumanTurnDisplayTask();

        final GameWindow gameWindow = this.myGameWindow;
        final String humanName = this.myGameWindow.getHumanPlayer().getName();
        this.myHumanTurnDisplayTask = new SingleTask(1000) {
            public void runSingleTask() {
                if (gameWindow.isComplete()) {
                    return;
                }
                gameWindow.getComputerDisplay().status(humanName + ", choose a cell to attack.");
            }
        };
    }

    private void startComputerTurn() {
        Player computerPlayer = this.myGameWindow.getComputerPlayer();
        this.myGameWindow.getEnemyDisplayFor(computerPlayer).status(computerPlayer.getName() + " is thinking...");

        final AttackPhaseHandler attackPhaseHandler = this;
        new SingleTask(UIBattleApp.COMPUTER_THINK_TIME) {
            public void runSingleTask() {
                if (attackPhaseHandler.myGameWindow.isComplete()) {
                    return;
                }
                attackPhaseHandler.computerTakeTurn();
            }
        };
    }

    private void startCurrentTurn() {
        Player turnPlayer = this.myGameWindow.getGame().whoseTurn();
        if (turnPlayer == this.myGameWindow.getHumanPlayer()) {
            this.startHumanTurn();
        } else {
            this.startComputerTurn();
        }
    }

    private void startHumanTurn() {
        this.setHumanTurnDisplayTask();
    }

    private void startNextTurn() {
        Player winner = this.myGameWindow.getGame().whoWon();
        if (winner != null) {
            this.onWon(winner);
        } else {
            this.myGameWindow.getGame().nextTurn();
            this.startCurrentTurn();
        }
    }

    private boolean tryTakeTurn(Player attackingPlayer, int x, int y) {
        if (this.myGameWindow.getGame().whoseTurn() != attackingPlayer) {
            return false;
        }
        Player attackedPlayer = this.myGameWindow.otherPlayer(attackingPlayer);
        int result = attackedPlayer.getGrid().getCell(x, y).tryAttack();
        this.displayTurnResult(attackingPlayer, result);
        if (result != Cell.ATTACK_ALREADY) {
            this.startNextTurn();
        }
        return true;
    }
}
