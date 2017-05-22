/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author James-dt
 */
public class HighScoreTest {

    HighScoreDB conn = new HighScoreDB();

    public HighScoreTest() {

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
     * Test of connectDB method, of class HighScoreDB.
     */
    @Test
    public void testConnectDB() {
        conn.connectDB();
        Assert.assertNotNull(conn.connection);
        conn.closeConn();
        try {
            Assert.assertTrue(conn.connection.isClosed());
        } catch (SQLException ex) {
            fail("SQL Exception");
        }
    }

    /**
     * Test of addHighScoreValues method, of class HighScoreDB.
     */
    @Test
    public void testAddHighScoreValues() {
        int currentRowCount = rowCount();
        try {
            Statement statement = conn.connection.createStatement();
            //testing generic name and score
            String sqlQuery = "INSERT INTO PLAYER.HIGHSCORE VALUES ('fff', 23234)";
            statement.executeUpdate(sqlQuery);
        } catch (SQLException ex) {
            Logger.getLogger(HighScoreDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        int newRowCount = rowCount();
        assertEquals(currentRowCount + 1, newRowCount);
    }

        /**
     * Count how many facts in the database
     * 
     * @return number of facts
     */
    public int rowCount() {
        String table = "HIGHSCORE";
        ResultSet rs;
        int rowCount = 0;
        
        try {
            Statement statement = conn.connection.createStatement();
            String sqlQuery = "SELECT COUNT(*) FROM " + table;

            rs = statement.executeQuery(sqlQuery);

            rs.next();
            rowCount = rs.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(HighScoreDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rowCount;
    }
    
}
