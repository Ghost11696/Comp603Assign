package Controller;

import Model.Cell;
import Model.Grid;
import Model.Player;
import Model.Ship;
import View.GameWindow;
import View.UI;
import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class DeploymentHandler implements PlayerDisplayListener {
    // Public fields

    public JButton bnAutoDeploy;
    public JButton bnRotateShip;

    // Private fields
    private Cell[] currentCells = new Cell[0];
    private int currentOrientation = Ship.HORIZONTAL;
    private int currentX = -1;
    private int currentY = -1;
    private boolean isCurrentPositionValid = false;
    private SingleTask myDisplayStatusTask = null;
    private GameWindow myGameWindow;
    private int nextShipIndex = 0;

    // Accessors
    public Ship getCurrentShip() {
        Ship[] ships = this.getHumanShips();
        if (this.nextShipIndex >= ships.length) {
            return null;
        } else {
            return ships[this.nextShipIndex];
        }
    }

    public Ship[] getHumanShips() {
        return this.myGameWindow.getHumanPlayer().getShips();
    }

    public boolean isComplete() {
        return this.nextShipIndex >= this.getHumanShips().length;
    }

    // Constructors
    public DeploymentHandler(GameWindow gameWindow) {
        this.myGameWindow = gameWindow;
    }

    // Public methods
    public void drawOverlay() {
        Color drawColour = this.isCurrentPositionValid ? UI.COLOUR_OCCUPIED : UI.COLOUR_INVALID;
        PlayerDisplay playerDisplay = this.myGameWindow.getHumanDisplay();
        for (Cell coveredCell : this.currentCells) {
            if (coveredCell == null) {
                continue;
            }
            playerDisplay.drawCell(coveredCell.getX(), coveredCell.getY(), drawColour);
        }
    }

    public void onAutoDeploy() {
        while (!this.isComplete()) {
            this.autoDeployCurrentShip();
        }
        this.complete();
    }

    public void onGridClicked(PlayerDisplay playerDisplay, MouseEvent e, int x, int y) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            this.rotateShip();
        } else {
            this.setDeploymentPosition(x, y);
            if (this.isCurrentPositionValid) {
                this.deployCurrentShip();
                this.redraw();

                if (this.isComplete()) {
                    this.complete();
                }
            } else {
                final DeploymentHandler deploymentHandler = this;
                myDisplayStatusTask = new SingleTask(1000) {
                    public void runSingleTask() {
                        deploymentHandler.displayCurrentStatus();
                    }
                };
                PlayerDisplay humanDisplay = this.myGameWindow.getHumanDisplay();
                humanDisplay.status(humanDisplay.getPlayer().getName() + ", you cannot deploy your " + this.getCurrentShip().getName() + " there.");
            }
        }
    }

    public void onGridEntered(PlayerDisplay playerDisplay, MouseEvent e, int x, int y) {
        this.setDeploymentPosition(x, y);
        this.redraw();
    }

    public void onGridExited(PlayerDisplay playerDisplay, MouseEvent e, int x, int y) {
        this.clearDeploymentPosition();
        this.redraw();
    }

    public void onSwitchOrientation() {
        this.rotateShip();
    }

    public void redraw() {
        this.myGameWindow.getHumanDisplay().redraw();
        this.drawOverlay();
    }

    public void startGUI() {
        this.deployComputerShips();
        this.displayCurrentStatus();

        this.bnAutoDeploy.setEnabled(true);
       // this.bnRotateShip.setEnabled(true);
        this.myGameWindow.getHumanDisplay().setEnabled(true);
        this.myGameWindow.redraw();
    }

    // Private methods
    private void autoDeployCurrentShip() {
        this.myGameWindow.getHumanPlayer().getGrid().autoDeploy(this.getCurrentShip());
        ++this.nextShipIndex;
    }

    private void clearDeploymentPosition() {
        this.setDeploymentPosition(-1, -1);
    }

    private void complete() {
        this.bnAutoDeploy.setEnabled(false);
        this.bnRotateShip.setEnabled(false);
        this.myGameWindow.getHumanDisplay().setEnabled(false);

        this.nextShipIndex = this.getHumanShips().length;
        this.clearDeploymentPosition();
        this.redraw();
        this.displayCurrentStatus();

        this.myGameWindow.onDeployComplete(this);
    }

    private void deployComputerShips() {
        Player computerPlayer = this.myGameWindow.getComputerPlayer();
        for (Ship ship : computerPlayer.getShips()) {
            computerPlayer.getGrid().autoDeploy(ship);
        }
        this.myGameWindow.getComputerDisplay().status("The computer has deployed its ships.");
    }

    private void deployCurrentShip() {
        this.myGameWindow.getHumanPlayer().getGrid().deploy(this.getCurrentShip(), this.currentX, this.currentY, this.currentOrientation);
        ++this.nextShipIndex;
        this.revalidateCurrentPosition();
        this.displayCurrentStatus();
    }

    private void displayCurrentStatus() {
        if (this.myDisplayStatusTask != null) {
            this.myDisplayStatusTask.cancel();
            this.myDisplayStatusTask = null;
        }
        Ship currentShip = this.getCurrentShip();
        if (currentShip == null) {
            this.myGameWindow.getHumanDisplay().status("Deployment complete.");
        } else {
            this.myGameWindow.getHumanDisplay().status("Deploy your " + currentShip.getName() + ".");
        }
    }

    private void revalidateCurrentPosition() {
        if (this.currentX == -1 || this.currentY == -1 || this.isComplete()) {
            this.currentCells = new Cell[0];
            this.isCurrentPositionValid = false;
        } else {
            Grid humanGrid = this.myGameWindow.getHumanPlayer().getGrid();
            this.currentCells = humanGrid.coveredCells(
                    this.getCurrentShip(), this.currentX, this.currentY, this.currentOrientation
            );
            this.isCurrentPositionValid = humanGrid.isValidDeployment(
                    this.getCurrentShip(), this.currentX, this.currentY, this.currentOrientation
            );
        }
    }

    private void rotateShip() {
        this.currentOrientation = (this.currentOrientation == Ship.HORIZONTAL) ? Ship.VERTICAL : Ship.HORIZONTAL;
        this.revalidateCurrentPosition();
        this.redraw();
    }

    private void setDeploymentPosition(int x, int y) {
        if (this.currentX == x && this.currentY == y) {
            return;
        }
        this.currentX = x;
        this.currentY = y;
        this.revalidateCurrentPosition();
    }
}
