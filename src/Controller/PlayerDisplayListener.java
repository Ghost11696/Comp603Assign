package Controller;

import View.PlayerDisplay;
import java.awt.event.MouseEvent;

/**
 * * Interface for classes that want to be informed about changes in the game
 * state.
 *
 * @author James-dt
 */
public interface PlayerDisplayListener {

    public void onGridClicked(PlayerDisplay playerDisplay, MouseEvent e, int x, int y);

    public void onGridEntered(PlayerDisplay playerDisplay, MouseEvent e, int x, int y);

    public void onGridExited(PlayerDisplay playerDisplay, MouseEvent e, int x, int y);


}
