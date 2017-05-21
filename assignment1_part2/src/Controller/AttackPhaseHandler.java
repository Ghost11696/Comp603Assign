/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.*;
import View.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

/**
 *
 * @author James-dt
 */
public class AttackPhaseHandler implements PlayerDisplayListener {

    private GameWindow game;
    public JButton bnAutoTarget;

    public AttackPhaseHandler(GameWindow game) {
        this.game = game;
    }

    public void computerTakeTurn() {
        Player computerPlayer = game.getComputerPlayer();
        Cell computerTarget = game.otherPlayer(computerPlayer).getGrid().autoTargetMe();
        tryTakeTurn(game.getComputerPlayer(), computerTarget.getX(), computerTarget.getY());
    }

    public void clearHumanTurnDisplayTask() {      
    }
    
    public void onAutoTarget() {
        Cell humanTarget = game.getComputerPlayer().getGrid().autoTargetMe();
        humanTakeTurn(humanTarget.getX(), humanTarget.getY());
    }
    @Override
    public void onGridClicked(PlayerDisplay playerDisplay, MouseEvent e, int x, int y) {
        this.humanTakeTurn(x, y);
    }
    @Override
    public void onGridEntered(PlayerDisplay playerDisplay, MouseEvent e, int x, int y) {
    }
    @Override
    public void onGridExited(PlayerDisplay playerDisplay, MouseEvent e, int x, int y) {
    }

    public void startGUI() {
        bnAutoTarget.setEnabled(true);
        game.getHumanDisplay().setEnabled(false);
        game.getComputerDisplay().setEnabled(true);
        game.redraw();
        startCurrentTurn();
    }

    // Private methods
    private Color colourAttackResult(int attackResult) {
        switch (attackResult) {
            case Cell.ATTACK_HIT:
                return UIBattleApp.COLOUR_HIT;
            case Cell.ATTACK_MISS:
                return UIBattleApp.COLOUR_MISS;
            case Cell.ATTACK_SUNK:
//                return UIBattleApp.COLOUR_SUNK;
            default:
                return SystemColor.windowText;
        }
    }

    private void displayTurnResult(Player attackingPlayer, int attackResult) {
        PlayerDisplay playerDisplay = game.getEnemyDisplayFor(attackingPlayer);
        if (attackResult == Cell.ATTACK_ALREADY) {
            playerDisplay.status(attackingPlayer.getName() + ", you have already attacked there.");
        } else {
            playerDisplay.status(attackingPlayer.getName() + " attacked - " + this.labelAttackResult(attackResult), this.colourAttackResult(attackResult));
            playerDisplay.redraw();
        }
    }

    private void humanTakeTurn(int targetX, int targetY) {
        Player humanPlayer = game.getHumanPlayer();
        boolean wasSuccessful = this.tryTakeTurn(humanPlayer, targetX, targetY);
        if (wasSuccessful) {
            this.clearHumanTurnDisplayTask();
        } else {
            game.getEnemyDisplayFor(humanPlayer).status(humanPlayer.getName() + ", it is not your turn.");
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
        game.onWon(winner);
    }

    private void startComputerTurn() {

        final AttackPhaseHandler attackPhaseHandler = this;

        attackPhaseHandler.computerTakeTurn();

    }

    private void startCurrentTurn() {
        Player turnPlayer = game.getGame().whoseTurn();
        if (turnPlayer == game.getComputerPlayer()) {
            startComputerTurn();
        } 
    }


    private void startNextTurn() {
        Player winner = game.getGame().whoWon();
        if (winner != null) {
            onWon(winner);
        } else {
            game.getGame().nextTurn();
            startCurrentTurn();
        }
    }

    private boolean tryTakeTurn(Player attackingPlayer, int x, int y) {
        if (game.getGame().whoseTurn() != attackingPlayer) {
            return false;
        }
        Player attackedPlayer = game.otherPlayer(attackingPlayer);
        int result = attackedPlayer.getGrid().getCell(x, y).tryAttack();
        this.displayTurnResult(attackingPlayer, result);
        if (result != Cell.ATTACK_ALREADY) {
            this.startNextTurn();
        }
        return true;
    }
}
