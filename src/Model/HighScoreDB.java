/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author James-dt
 */
public class HighScoreDB {

    private String url = "jdbc:derby:HighScoreDB;create=true";
    private String dbUser = "player";
    private String dbPass = "player";
    public Connection connection;

    /**
     * 
     */
    public HighScoreDB() {
        connectDB();
    }

    /**
     * connect to the database
     */
    public void connectDB() {
        
        try {

            connection = DriverManager.getConnection(url, dbUser, dbPass);
        } catch (SQLException ex) {
            Logger.getLogger(HighScoreDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * closes the db
     */
    public void closeConn() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(HighScoreDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * adds a player name and score to the database
     */
    public void addHighScoreValues(String name, int score) {
        String addNameDB = name;
        int addScoreDB = score;

        try {
            Statement statement = connection.createStatement();
            //testing generic name and score
            String sqlQuery = "INSERT INTO PLAYER.HIGHSCORE VALUES ('fff', 23234)";//('"+ addNameDB +"', "+ addScoreDB +");";
            statement.executeUpdate(sqlQuery);
        } catch (SQLException ex) {
            Logger.getLogger(HighScoreDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
