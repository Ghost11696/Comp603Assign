package View;

import Controller.PlayerDisplayListener;
import Model.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author James-dt
 */
public class PlayerDisplay implements PlayerDisplayListener {


    private JButton[][] allButtons;
    private JLabel[] allShipLabels;
    private JPanel contentPanel;
    private boolean isEnabled = false;
    private JLabel status;
    private PlayerDisplayListener myListener = null;
    private GameWindow myGameWindow;
    private Player myPlayer;
    
    

    public PlayerDisplay(GameWindow gameWindow, Player player) {
        this.myGameWindow = gameWindow;
        this.myPlayer = player;
        this.constructGUI();
    }

    /**
     * 
     * @return the JPanel contentPanel
     */
    public JPanel getComponent() {
        return contentPanel;
    }

    /**
     * 
     * @return 
     */
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
        this.myListener = newListener;
    }

    // Public methods
    public void drawCell(int x, int y, Color drawColour) {
        allButtons[x][y].setBackground(drawColour);
    }

    @Override
    public void onGridClicked(PlayerDisplay playerDisplay, MouseEvent e, int x, int y) {
        if (myListener == null || !isEnabled) {
            return;
        } else {
            myListener.onGridClicked(playerDisplay, e, x, y);
        }
    }

    @Override
    public void onGridEntered(PlayerDisplay playerDisplay, MouseEvent e, int x, int y) {
        if (myListener == null || !isEnabled) {
            return;
        } else {
            myListener.onGridEntered(playerDisplay, e, x, y);
        }
    }

    @Override
    public void onGridExited(PlayerDisplay playerDisplay, MouseEvent e, int x, int y) {
        if (myListener == null || !isEnabled) {
            return;
        } else {
            myListener.onGridExited(playerDisplay, e, x, y);
        }
    }

    public void redraw() {
        redrawCells();
        redrawShipLabels();
    }

    public void status(String message) {
        status(message, SystemColor.windowText);
    }

    public void status(String message, Color backgroundColour) {
        status.setText(message);
        status.setForeground(backgroundColour);
    }

    // Private methods
    private Color colourForState(int displayState) {
        switch (displayState) {
            case Cell.DISPLAY_HIT:
                return StartWindow.COLOUR_HIT;
            case Cell.DISPLAY_MISS:
                return StartWindow.COLOUR_MISS;
            case Cell.DISPLAY_OCCUPIED:
                return StartWindow.COLOUR_OCCUPIED;
            default:
                return isEnabled ? StartWindow.COLOUR_BLANK_ENABLED : StartWindow.COLOUR_BLANK_DISABLED;
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

    private void constructGUI() {
        JPanel shipListPanel = new JPanel();
        
            shipListPanel.setLayout(new FlowLayout());

            Ship[] ships = this.getShips();
            this.allShipLabels = new JLabel[ships.length];
            for (int i = 0; i < this.allShipLabels.length; ++i) {
                JLabel shipLabel = this.allShipLabels[i] = new JLabel(ships[i].getName());
                shipLabel.setBorder(new EmptyBorder(StartWindow.BORDER_WIDTH, StartWindow.BORDER_WIDTH, StartWindow.BORDER_WIDTH, StartWindow.BORDER_WIDTH));
                shipListPanel.add(shipLabel);
            }
        

        JPanel gridPanel = new JPanel();
        
            this.allButtons = new JButton[Game.GRID_SIZE][Game.GRID_SIZE];
            gridPanel.setLayout(new GridLayout(Game.GRID_SIZE, Game.GRID_SIZE));

        for (int y = 0; y < Game.GRID_SIZE; ++y) {
            for (int x = 0; x < Game.GRID_SIZE; ++x) {
                JButton button = this.constructButton(x, y);
                this.allButtons[x][y] = button;
                gridPanel.add(button);
            }
        }

        contentPanel = new JPanel();

        status = new JLabel("\n");

        status.setBorder(new EmptyBorder(StartWindow.BORDER_WIDTH, StartWindow.BORDER_WIDTH, StartWindow.BORDER_WIDTH, StartWindow.BORDER_WIDTH));
        status.setHorizontalAlignment(SwingConstants.CENTER);

        contentPanel.setBorder(new EmptyBorder(StartWindow.BORDER_WIDTH, StartWindow.BORDER_WIDTH, StartWindow.BORDER_WIDTH, StartWindow.BORDER_WIDTH));
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(shipListPanel, BorderLayout.NORTH);
        contentPanel.add(gridPanel, BorderLayout.CENTER);
        contentPanel.add(status, BorderLayout.SOUTH);

    }

    private void redrawCell(int x, int y) {
        int displayState = myPlayer.getGrid().getCell(x, y).displayState(myGameWindow.getHumanPlayer());
        Color drawColour = colourForState(displayState);
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
        Ship[] ships = getShips();
        for (int i = 0; i < allShipLabels.length; ++i) {
            Color drawColour = ships[i].isSunk() ? StartWindow.COLOUR_SHIP_SUNK : StartWindow.COLOUR_SHIP_NOT_SUNK;
            allShipLabels[i].setForeground(drawColour);
        }
    }
}
