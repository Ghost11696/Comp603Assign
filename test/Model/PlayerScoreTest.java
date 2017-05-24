/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author James-dt
 */
public class PlayerScoreTest {

    private Connection conn;
    private String url = "jdbc:derby://localhost:1527/PLAYER;create=true";
    private final String userName = "quan";
    private final String password = "quan";
    private static final String tableName = "PLAYERSCORE";


    public PlayerScoreTest() {
        try {
            conn = DriverManager.getConnection(url, userName, password);
        } catch (SQLException ex) {
            Logger.getLogger(PlayerScoreTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    /**
     * Test of addHighScoreValues method, of class HighScoreDB.
     */
    @Test
    public void testAddHighScoreValues() {
        int currentRowCount = rowCount();
        try {

            Statement statement = conn.createStatement();
            //testing generic name and score
            statement.executeUpdate("INSERT INTO " + tableName + " VALUES ('fff', 22232)");
            int newRowCount = rowCount();
            assertEquals(currentRowCount + 1, newRowCount);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

        /**
     * Count how many facts in the database
     * 
     * @return number of facts
     */
    public int rowCount() {

        ResultSet rs;
        int rowCount = 0;
        
        try {
            Statement statement = conn.createStatement();
            String sqlQuery = "SELECT COUNT(*) FROM " + tableName;

            rs = statement.executeQuery(sqlQuery);

            rs.next();
            rowCount = rs.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(PlayerScoreTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rowCount;
    }
    
}
