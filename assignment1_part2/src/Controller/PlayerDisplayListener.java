/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.awt.event.MouseEvent;

/**
 *
 * @author James-dt
 */
public interface PlayerDisplayListener {

    public void onGridClicked(PlayerDisplay playerDisplay, MouseEvent e, int x, int y);

    public void onGridEntered(PlayerDisplay playerDisplay, MouseEvent e, int x, int y);

    public void onGridExited(PlayerDisplay playerDisplay, MouseEvent e, int x, int y);


}
