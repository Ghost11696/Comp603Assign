package View;

import java.awt.BorderLayout;
import java.awt.Color;
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
public class StartWindow implements ActionListener {
    
    /**
     *  static variables to be used for colour, boarder width and indexes
     */
    public static final int COMPUTER_INDEX = 1;
    public static final int HUMAN_INDEX = 0;
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
    
    public String playerName = "";
    private JFrame startFrame;
    private JTextField tbName;


    /**
     * creates a small frame to collect the players name
     * before the game is created
     */
    public StartWindow() {
        

        JPanel namePanel = new JPanel();
        startFrame = new JFrame("BattleApp");

        namePanel.setLayout(new BorderLayout());
        namePanel.add(new JLabel("Please enter your name: "), BorderLayout.WEST);
        namePanel.add(this.tbName = new JTextField(30), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();

        JButton bnOK;

        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(bnOK = new JButton("OK"), BorderLayout.EAST);

        bnOK.addActionListener(this);

        JPanel mainPanel = new JPanel();

        mainPanel.setBorder(new EmptyBorder(BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH));
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(namePanel, BorderLayout.CENTER);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        startFrame.add(mainPanel);
        startFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        startFrame.pack();
        startFrame.setResizable(false);
        startFrame.setLocationRelativeTo(null);
        startFrame.setVisible(true);
    }

    /**
     * a new game is started after player clicks button
     * and closes the current frame
     * @param ae 
     */
    @Override
    public void actionPerformed(ActionEvent ae) {

        playerName = tbName.getText();
        GameWindow gw = new GameWindow(this);
        startFrame.dispose();
    }

}
