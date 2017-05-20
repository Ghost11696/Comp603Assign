/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Game;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author James-dt
 */
public class GameWindow extends JFrame{
    
    private Game game;
    private JPanel playerDisplay;
    private JFrame jframe;
    private String playerName;

    public GameWindow() {
        new StartGUI();
        game = new Game(playerName, "computer");
        jframe = new JFrame("BattleApp");
        playerDisplay = new PlayerDisplay(jframe, game);
        
        
                    
     final GameWindow gameWindow = this;

            JPanel displayGridsPanel = new JPanel();
            {
                PlayerDisplay humanDisplay, computerDisplay;
                this.allPlayerDisplays = new PlayerDisplay[2];

                this.allPlayerDisplays[GameWindow.HUMAN_INDEX] = humanDisplay = new PlayerDisplay(this, this.getHumanPlayer());
                this.allPlayerDisplays[GameWindow.COMPUTER_INDEX] = computerDisplay = new PlayerDisplay(this, this.getComputerPlayer());

                displayGridsPanel.setLayout(new GridLayout(1, 2));
                displayGridsPanel.add(humanDisplay.getComponent());
                displayGridsPanel.add(computerDisplay.getComponent());

                humanDisplay.setListener(this.myDeployer);
                computerDisplay.setListener(this.myAttackPhaseHandler);
            }

            JPanel buttonsPanel = new JPanel();
            {
                JButton bnAutoDeploy, bnAutoTarget, bnQuit, bnRotateShip;

                buttonsPanel.setLayout(new FlowLayout());
                buttonsPanel.add(bnAutoDeploy = gameWindow.myDeployer.bnAutoDeploy = new JButton("Auto-deploy"));
                buttonsPanel.add(bnAutoTarget = gameWindow.myAttackPhaseHandler.bnAutoTarget = new JButton("Auto-target"));
                buttonsPanel.add(bnQuit = new JButton("Quit"));

                bnAutoDeploy.setEnabled(false);
                bnAutoTarget.setEnabled(false);

                bnAutoDeploy.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) { gameWindow.myDeployer.onAutoDeploy(); }
                });

                bnAutoTarget.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) { gameWindow.myAttackPhaseHandler.onAutoTarget(); }
                });

                bnQuit.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) { gameWindow.onQuit(); }
                });

            }

            JFrame form = this.form = new JFrame(UI.WINDOW_TITLE);
            {
                form.setLayout(new BorderLayout());
                form.add(displayGridsPanel, BorderLayout.CENTER);
                form.add(buttonsPanel, BorderLayout.SOUTH);
            }

            form.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            form.pack();
            form.setResizable(false);
			form.setLocationRelativeTo(null);
            form.setVisible(true);
        }

    private class StartGUI implements ActionListener{

        private JFrame form;
        private JTextField tbName;

        public StartGUI() {

            JPanel namePanel = new JPanel();
            form = new JFrame("BattleApp");

            namePanel.setLayout(
                    new BorderLayout());
            namePanel.add(
                    new JLabel("Please enter your name: "), BorderLayout.WEST);
            namePanel.add(
                    this.tbName = new JTextField(30), BorderLayout.CENTER);

            JPanel bottomPanel = new JPanel();

            JButton bnOK;

            bottomPanel.setLayout(
                    new BorderLayout());
            bottomPanel.add(bnOK
                    = new JButton("OK"), BorderLayout.EAST);

            bnOK.addActionListener(
                    this);

            JPanel mainPanel = new JPanel();

            mainPanel.setBorder(
                    new EmptyBorder(UI.BORDER_WIDTH, UI.BORDER_WIDTH, UI.BORDER_WIDTH, UI.BORDER_WIDTH));
            mainPanel.setLayout(
                    new BorderLayout());
            mainPanel.add(namePanel, BorderLayout.CENTER);

            mainPanel.add(bottomPanel, BorderLayout.SOUTH);

            form.add(mainPanel);

            form.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

            form.pack();

            form.setResizable(
                    false);
            form.setLocationRelativeTo(
                    null);
            form.setVisible(
                    true);
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            playerName = tbName.getText();
            form.dispose();
        }

    }      
}
