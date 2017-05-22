/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.PlayerDisplay;
import Model.*;
import View.*;
import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

/**
 *
 * @author James-dt
 */
public class DeploymentHandler implements PlayerDisplayListener {
    // Public fields

    public JButton bnAutoDeploy;

    // Private fields
    private Cell[] currentCells = new Cell[0];
    private int currentOrientation = Ship.HORIZONTAL;
    private int currentX = -1;
    private int currentY = -1;
    private boolean isCurrentPositionValid = false;
    private GameWindow myGameWindow;
    private int nextShipIndex = 0;

    // Constructors
    public DeploymentHandler(GameWindow gameWindow) {
        this.myGameWindow = gameWindow;
    }

    // Accessors
    public Ship getCurrentShip() {
        Ship[] ships = getHumanShips();
        if (nextShipIndex >= ships.length) {
            return null;
        } else {
            return ships[nextShipIndex];
        }
    }

    public Ship[] getHumanShips() {
        return myGameWindow.getHumanPlayer().getShips();
    }

    public boolean isComplete() {
        return nextShipIndex >= getHumanShips().length;
    }

    // Public methods
    public void drawOverlay() {
        Color drawColour = isCurrentPositionValid ? StartWindow.COLOUR_OCCUPIED : StartWindow.COLOUR_INVALID;
        PlayerDisplay playerDisplay = myGameWindow.getHumanDisplay();
        for (Cell coveredCell : currentCells) {
            if (coveredCell == null) {
                continue;
            }
            playerDisplay.drawCell(coveredCell.getX(), coveredCell.getY(), drawColour);
        }
    }

    public void onAutoDeploy() {
        while (!isComplete()) {
            autoDeployCurrentShip();
        }
        complete();
    }

    @Override
    public void onGridClicked(PlayerDisplay playerDisplay, MouseEvent e, int x, int y) {
        onAutoDeploy();
    }

    @Override
    public void onGridEntered(PlayerDisplay playerDisplay, MouseEvent e, int x, int y) {
        onAutoDeploy();
        redraw();
    }

    @Override
    public void onGridExited(PlayerDisplay playerDisplay, MouseEvent e, int x, int y) {
        clearDeploymentPosition();
        redraw();
    }

    public void onSwitchOrientation() {
        rotateShip();
    }

    public void redraw() {
        myGameWindow.getHumanDisplay().redraw();
        drawOverlay();
    }

    public void startGUI() {
        deployComputerShips();
        displayCurrentStatus();

        bnAutoDeploy.setEnabled(true);
        myGameWindow.getHumanDisplay().setEnabled(true);
        myGameWindow.redraw();
    }

    // Private methods
    private void autoDeployCurrentShip() {
        myGameWindow.getHumanPlayer().getGrid().autoDeploy(this.getCurrentShip());
        ++nextShipIndex;
    }

    private void clearDeploymentPosition() {
        setDeploymentPosition(-1, -1);
    }

    private void complete() {
        bnAutoDeploy.setEnabled(false);
        myGameWindow.getHumanDisplay().setEnabled(false);

        nextShipIndex = getHumanShips().length;
        clearDeploymentPosition();
        redraw();
        displayCurrentStatus();

        myGameWindow.onDeployComplete(this);
    }

    private void deployComputerShips() {
        Player computerPlayer = myGameWindow.getComputerPlayer();
        for (Ship ship : computerPlayer.getShips()) {
            computerPlayer.getGrid().autoDeploy(ship);
        }
        myGameWindow.getComputerDisplay().status("The computer has deployed its ships.");
    }

    private void displayCurrentStatus() {

        Ship currentShip = getCurrentShip();
        if (currentShip == null) {
            myGameWindow.getHumanDisplay().status("Deployment complete.");
        } else {
            myGameWindow.getHumanDisplay().status("Deploy your " + currentShip.getName() + ".");
        }
    }

    private void revalidateCurrentPosition() {
        if (currentX == -1 || currentY == -1 || isComplete()) {
            currentCells = new Cell[0];
            isCurrentPositionValid = false;
        } else {
            Grid humanGrid = myGameWindow.getHumanPlayer().getGrid();
            this.currentCells = humanGrid.coveredCells(
                    this.getCurrentShip(), currentX, currentY, currentOrientation
            );
            isCurrentPositionValid = humanGrid.isValidDeployment(
                    getCurrentShip(), currentX, currentY, currentOrientation
            );
        }
    }

    private void rotateShip() {
        currentOrientation = (currentOrientation == Ship.HORIZONTAL) ? Ship.VERTICAL : Ship.HORIZONTAL;
        revalidateCurrentPosition();
        redraw();
    }

    private void setDeploymentPosition(int x, int y) {
        if (currentX == x && currentY == y) {
            return;
        }
        currentX = x;
        currentY = y;
        revalidateCurrentPosition();
    }
}
