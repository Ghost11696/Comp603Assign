/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Cell;
import Model.Game;
import Model.Grid;
import Model.Player;
import Controller.PlayerDisplayListener;
import Model.Ship;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 *
 * @author James-dt
 */
public class PlayerDisplay extends JPanel implements PlayerDisplayListener {

    private JButton[][] allButtons;
    private JPanel contentPanel;
    private boolean isEnabled = false;
    private JLabel laStatus;
    private PlayerDisplayListener myListener = null;
    private Player myPlayer;
    private Game game;
    private ShipListPanel slp;
    private final Game Game;

    public PlayerDisplay(JFrame gameWindow, Game game) {
        this.Game = game;
        this.myPlayer = game.getPlayer1();
        slp = new ShipListPanel(myPlayer, this);
    }

    // Accessors
    public JPanel getComponent() {
        return contentPanel;
    }

    public boolean getEnabled() {
        return isEnabled;
    }

    public Grid getGrid() {
        return myPlayer.getGrid();
    }

    public PlayerDisplayListener getListener() {
        return myListener;
    }

    public Player getPlayer() {
        return myPlayer;
    }

    public Ship[] getShips() {
        return myPlayer.getShips();
    }

    public void setEnabled(boolean newEnabled) {
        isEnabled = newEnabled;
        for (int y = 0; y < Game.GRID_SIZE; ++y) {
            for (int x = 0; x < Game.GRID_SIZE; ++x) {
                allButtons[x][y].setEnabled(isEnabled);
            }
        }
    }

    public void setListener(PlayerDisplayListener newListener) {
        myListener = newListener;
    }


    // Public methods
    public void drawCell(int x, int y, Color drawColour) {
        allButtons[x][y].setBackground(drawColour);
    }

    public void redraw() {
        redrawCells();
        redrawShipLabels();
    }

    public void status(String message) {
        status(message, SystemColor.windowText);
    }

    public void status(String message, Color backgroundColour) {
        laStatus.setText(message);
        laStatus.setForeground(backgroundColour);
    }

    // Private methods
    private Color colourForState(int displayState) {
        switch (displayState) {
            case Cell.DISPLAY_HIT:
                return UI.COLOUR_HIT;
            case Cell.DISPLAY_MISS:
                return UI.COLOUR_MISS;
            case Cell.DISPLAY_OCCUPIED:
                return UI.COLOUR_OCCUPIED;
            default:
                return this.isEnabled ? UI.COLOUR_BLANK_ENABLED : UI.COLOUR_BLANK_DISABLED;
        }
    }

    public JButton constructButton(final int x, final int y) {
        final PlayerDisplay playerDisplay = this;

        JButton button = new JButton("\n");
        button.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                playerDisplay.onGridClicked(playerDisplay, e, x, y);
            }

            public void mouseEntered(MouseEvent e) {
                playerDisplay.onGridEntered(playerDisplay, e, x, y);
            }

            public void mouseExited(MouseEvent e) {
                playerDisplay.onGridExited(playerDisplay, e, x, y);
            }
        });

        return button;
    }

    private void redrawCell(int x, int y) {
        int displayState = this.myPlayer.getGrid().getCell(x, y).displayState(myPlayer);
        Color drawColour = this.colourForState(displayState);
        drawCell(x, y, drawColour);
    }

    private void redrawCells() {
        for (int y = 0; y < Game.GRID_SIZE; ++y) {
            for (int x = 0; x < Game.GRID_SIZE; ++x) {
                redrawCell(x, y);
            }
        }
    }

    private void redrawShipLabels() {
        Ship[] ships = this.getShips();
        for (int i = 0; i < slp.getAllShipLabels().length; ++i) {
            Color drawColour = ships[i].isSunk() ? UI.COLOUR_SHIP_SUNK : UI.COLOUR_SHIP_NOT_SUNK;
            slp.getAllShipLabels()[i].setForeground(drawColour);
        }
    }

    @Override
    public void onGridClicked(PlayerDisplay playerDisplay, MouseEvent e, int x, int y) {
        if (this.myListener == null || !this.isEnabled) {
            return;
        } else {
            this.myListener.onGridClicked(playerDisplay, e, x, y);
        }
    }

    @Override
    public void onGridEntered(PlayerDisplay playerDisplay, MouseEvent e, int x, int y) {
        if (this.myListener == null || !this.isEnabled) {
            return;
        } else {
            this.myListener.onGridEntered(playerDisplay, e, x, y);
        }
    }

    @Override
    public void onGridExited(PlayerDisplay playerDisplay, MouseEvent e, int x, int y) {
        if (this.myListener == null || !this.isEnabled) {
            return;
        } else {
            this.myListener.onGridExited(playerDisplay, e, x, y);
        }
    }
}
