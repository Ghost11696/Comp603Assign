/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.BorderLayout;
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

    private JFrame startFrame;
    private JTextField tbName;
    private UIBattleApp uibattleApp;

    public StartWindow(UIBattleApp uibattleApp) {
        
        this.uibattleApp = uibattleApp;

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

        mainPanel.setBorder(new EmptyBorder(UIBattleApp.BORDER_WIDTH, UIBattleApp.BORDER_WIDTH, UIBattleApp.BORDER_WIDTH, UIBattleApp.BORDER_WIDTH));
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

    @Override
    public void actionPerformed(ActionEvent ae) {
        uibattleApp.playerName = tbName.getText();
        GameWindow gw = new GameWindow(uibattleApp);
        startFrame.dispose();
    }

}
