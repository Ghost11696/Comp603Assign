package Controller;

import View.PlayerDisplay;
import Model.*;
import View.*;
import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

/**
 * class controls the deployment phase of battleships for this iteration 
 * the deployment is automatic, however methods can be included so that the 
 * player can add his own ships on mouse click
 * @author James-dt
 */
public class DeploymentHandler implements PlayerDisplayListener {


    public JButton bnAutoDeploy;


    private Cell[] currentCells = new Cell[0];
    private int currentOrientation = Ship.HORIZONTAL;
    private int currentX = -1;
    private int currentY = -1;
    private boolean isCurrentPositionValid = false;
    private GameWindow myGameWindow;
    private int nextShipIndex = 0;


    public DeploymentHandler(GameWindow gameWindow) {
        this.myGameWindow = gameWindow;
    }

    /**
     * gets an array of the players ships and gets the current ship using an
     * index
     *
     * @returns the current ship
     */
    public Ship getCurrentShip() {
        Ship[] ships = getHumanShips();
        if (nextShipIndex >= ships.length) {
            return null;
        } else {
            return ships[nextShipIndex];
        }
    }

    /**
     * returns the human players ships
     *
     * @return human players ship
     */
    public Ship[] getHumanShips() {
        return myGameWindow.getHumanPlayer().getShips();
    }

    /**
     * if the index is greater than the array size it will return true
     *
     * @return 
     */
    public boolean isComplete() {
        return nextShipIndex >= getHumanShips().length;
    }

    /**
     * displays the players panel
     */
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

    /**
     * implements the autodeploy method
     */
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

    /**
     * redraws the display
     */
    public void redraw() {
        myGameWindow.getHumanDisplay().redraw();
        drawOverlay();
    }

    /**
     * creates the gui for deployment (for this iteration its automatic)
     */
    public void startGUI() {
        deployComputerShips();
        displayCurrentStatus();

        bnAutoDeploy.setEnabled(true);
        myGameWindow.getHumanDisplay().setEnabled(true);
        myGameWindow.redraw();
    }

    /**
     * auto deploys ship
     */
    private void autoDeployCurrentShip() {
        myGameWindow.getHumanPlayer().getGrid().autoDeploy(this.getCurrentShip());
        ++nextShipIndex;
    }

    /**
     * clears the position
     */
    private void clearDeploymentPosition() {
        setDeploymentPosition(-1, -1);
    }

    /**
     * resets the display gui 
     */
    private void complete() {
        bnAutoDeploy.setEnabled(false);
        myGameWindow.getHumanDisplay().setEnabled(false);

        nextShipIndex = getHumanShips().length;
        clearDeploymentPosition();
        redraw();
        displayCurrentStatus();

        myGameWindow.onDeployComplete(this);
    }

    /**
     * deploys the computer ships
     */
    private void deployComputerShips() {
        Player computerPlayer = myGameWindow.getComputerPlayer();
        for (Ship ship : computerPlayer.getShips()) {
            computerPlayer.getGrid().autoDeploy(ship);
        }
        myGameWindow.getComputerDisplay().status("The computer has deployed its ships.");
    }

    /**
     * for iteration 2 - player can manually deploy ship
     */
    private void displayCurrentStatus() {

        Ship currentShip = getCurrentShip();
        if (currentShip == null) {
            myGameWindow.getHumanDisplay().status("Deployment complete.");
        } else {
            myGameWindow.getHumanDisplay().status("Deploy your " + currentShip.getName() + ".");
        }
    }

    /**
     * checks that position is valid
     */
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


    /**
     * sets the ship at the deployment position
     * @param x
     * @param y 
     */
    private void setDeploymentPosition(int x, int y) {
        if (currentX == x && currentY == y) {
            return;
        }
        currentX = x;
        currentY = y;
        revalidateCurrentPosition();
    }
}
