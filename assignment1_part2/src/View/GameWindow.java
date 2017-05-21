/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.*;
import Model.Game;
import Model.Player;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author James-dt
 */
public class GameWindow extends JFrame {

    // Public static constants
    public static final int COMPUTER_INDEX = 1;
    public static final int HUMAN_INDEX = 0;

    // Private fields
    private PlayerDisplay[] allPlayerDisplays;
    private JFrame form;
    private AttackPhaseHandler myAttackPhaseHandler;
    private DeploymentHandler myDeployer;
    private Game myGame;
    private boolean isComplete = false;
    private Game game;
    private UIBattleApp uibattleApp;

    public GameWindow(UIBattleApp uibattleApp) {
        this.uibattleApp = uibattleApp;
        game = new Game(uibattleApp.playerName, "computer");
        constructGUI();
    }

    private void constructGUI() {

        final GameWindow gameWindow = this;
        myAttackPhaseHandler = new AttackPhaseHandler(gameWindow);
        myDeployer = new DeploymentHandler(gameWindow);
        JPanel displayGridsPanel = new JPanel();
        PlayerDisplay humanDisplay, computerDisplay;
        allPlayerDisplays = new PlayerDisplay[2];
        allPlayerDisplays[HUMAN_INDEX] = humanDisplay = new PlayerDisplay(this, game.getPlayer1());
        allPlayerDisplays[COMPUTER_INDEX] = computerDisplay = new PlayerDisplay(this, game.getPlayer2());

        displayGridsPanel.setLayout(new GridLayout(1, 2));
        displayGridsPanel.add(humanDisplay.getComponent());
        displayGridsPanel.add(computerDisplay.getComponent());

        JPanel buttonsPanel = new JPanel();
        JButton bnAutoDeploy, bnAutoTarget, bnQuit;

        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.add(bnAutoDeploy = myDeployer.bnAutoDeploy = new JButton("Auto-deploy"));
        buttonsPanel.add(bnAutoTarget = myAttackPhaseHandler.bnAutoTarget = new JButton("Auto-target"));
        buttonsPanel.add(bnQuit = new JButton("Quit"));

        bnAutoDeploy.setEnabled(false);
        bnAutoTarget.setEnabled(false);

        bnAutoDeploy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameWindow.myDeployer.onAutoDeploy();
            }
        });

        bnAutoTarget.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameWindow.myAttackPhaseHandler.onAutoTarget();
            }
        });

        bnQuit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameWindow.onQuit();
            }
        });

        form = new JFrame("BattleApp");

        form.setLayout(new BorderLayout());
        form.add(displayGridsPanel, BorderLayout.CENTER);
        form.add(buttonsPanel, BorderLayout.SOUTH);

        form.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        form.pack();
        form.setResizable(false);
        form.setLocationRelativeTo(null);
        form.setVisible(true);

    }

    public void onDeployComplete(DeploymentHandler handler) {
        myAttackPhaseHandler.startGUI();
    }

    public void onQuit() {
        form.dispose();
    }

    public void onWon(Player winningPlayer) {
        isComplete = true;
        // open database and save score
        form.dispose();

    }

    public Player otherPlayer(Player player) {
        Player otherPlayerTest;
        if ((otherPlayerTest = game.getPlayer1()) != player) {
            return otherPlayerTest;
        }
        return game.getPlayer2();
    }

    public void startGUI() {
        for (PlayerDisplay playerDisplay : allPlayerDisplays) {
            playerDisplay.status(playerDisplay.getPlayer().getName());
        }

        myDeployer.startGUI();
    }

    public void redraw() {
        for (PlayerDisplay playerDisplay : allPlayerDisplays) {
            playerDisplay.redraw();
        }
        myDeployer.drawOverlay();
    }

    public PlayerDisplay getComputerDisplay() {
        return this.allPlayerDisplays[GameWindow.COMPUTER_INDEX];
    }

    public Player getComputerPlayer() {
        return this.myGame.getPlayer2();
    }

    public Color getDefaultBackgroundColour() {
        return this.form.getBackground();
    }

    public PlayerDisplay getDisplayFor(Player player) {
        return (this.allPlayerDisplays[0].getPlayer() == player) ? this.allPlayerDisplays[0] : this.allPlayerDisplays[1];
    }

    public PlayerDisplay getEnemyDisplayFor(Player player) {
        return (this.allPlayerDisplays[0].getPlayer() == player) ? this.allPlayerDisplays[1] : this.allPlayerDisplays[0];
    }

    public PlayerDisplay getHumanDisplay() {
        return this.allPlayerDisplays[GameWindow.HUMAN_INDEX];
    }

    public Player getHumanPlayer() {
        return game.getPlayer1();
    }

    public Game getGame() {
        return game;
    }

    public UIBattleApp getUI() {
        return uibattleApp;
    }

    public boolean isComplete() {
        return isComplete;
    }
}
