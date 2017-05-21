/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.Color;

/**
 *
 * @author James-dt
 */
public class UIBattleApp {
    
    public static final int BORDER_WIDTH = 1;

    public static final Color COLOUR_BLANK_DISABLED = Color.LIGHT_GRAY;
    public static final Color COLOUR_BLANK_ENABLED = Color.WHITE;
    public static final Color COLOUR_HIT = Color.RED;
    public static final Color COLOUR_INVALID = Color.RED;
    public static final Color COLOUR_MISS = Color.BLUE;
    public static final Color COLOUR_OCCUPIED = Color.GREEN;
    public static final Color COLOUR_SHIP_NOT_SUNK = Color.BLACK;
    public static final Color COLOUR_SHIP_SUNK = Color.LIGHT_GRAY;
    public static final Color COLOUR_SUNK = Color.MAGENTA;

    public static final long COMPUTER_THINK_TIME = 250; // time the computer spends "thinking", in milliseconds
    public String playerName = "";
    public static final String WINDOW_TITLE = "BattleApp";
    
    
    public UIBattleApp(){
     StartWindow sw = new StartWindow(this);
    }
}
