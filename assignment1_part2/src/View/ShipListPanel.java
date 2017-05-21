/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.PlayerDisplay;
import Model.Game;
import Model.Player;
import Model.Ship;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author James-dt
 */
public class ShipListPanel extends JPanel {

    private Player player;
    private JPanel contentPanel;
    private JLabel Status;
    private JButton[][] allButtons;
    private JLabel[] allShipLabels;
    private PlayerDisplay playerDisplay;

    public ShipListPanel(Player player, PlayerDisplay playerDisplay) {
        this.player = player;
        this.playerDisplay = playerDisplay;
        constructGUI();
    }

    private void constructGUI() {
        JPanel shipListPanel = new JPanel();

        shipListPanel.setLayout(new FlowLayout());

        Ship[] ships = player.getShips();
        allShipLabels = new JLabel[ships.length];
        for (int i = 0; i < allShipLabels.length; ++i) {
            JLabel shipLabel = allShipLabels[i] = new JLabel(ships[i].getName());
            shipLabel.setBorder(new EmptyBorder(UIBattleApp.BORDER_WIDTH, UIBattleApp.BORDER_WIDTH, UIBattleApp.BORDER_WIDTH, UIBattleApp.BORDER_WIDTH));
            shipListPanel.add(shipLabel);
        }

        JPanel gridPanel = new JPanel();

        this.allButtons = new JButton[Game.GRID_SIZE][Game.GRID_SIZE];
        gridPanel.setLayout(new GridLayout(Game.GRID_SIZE, Game.GRID_SIZE));

        for (int y = 0; y < Game.GRID_SIZE; ++y) {
            for (int x = 0; x < Game.GRID_SIZE; ++x) {
                JButton button = playerDisplay.constructButton(x, y);
                this.allButtons[x][y] = button;
                gridPanel.add(button);
            }
        }

        contentPanel = new JPanel();

        Status = new JLabel("\n");

        Status.setBorder(new EmptyBorder(UIBattleApp.BORDER_WIDTH, UIBattleApp.BORDER_WIDTH, UIBattleApp.BORDER_WIDTH, UIBattleApp.BORDER_WIDTH));
        Status.setHorizontalAlignment(SwingConstants.CENTER);

        contentPanel.setBorder(new EmptyBorder(UIBattleApp.BORDER_WIDTH, UIBattleApp.BORDER_WIDTH, UIBattleApp.BORDER_WIDTH, UIBattleApp.BORDER_WIDTH));
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(shipListPanel, BorderLayout.NORTH);
        contentPanel.add(gridPanel, BorderLayout.CENTER);
        contentPanel.add(this.Status, BorderLayout.SOUTH);

    }
    
    public JLabel[] getAllShipLabels(){
        return allShipLabels;
    }
}
