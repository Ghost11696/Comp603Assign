package DataBase;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * @author HumbleB
 */
public class PlayerScore {

    private Connection conn;
    //private String url = "jdbc:derby:PLAYER;create=true";
    private String url = "jdbc:derby://localhost:1527/PLAYER;create=true";
    private final String userName = "quan";
    private final String password = "quan";
    private Statement statement;
    private static final String tableName = "PLAYERSCORE";

    /**
     * connects to the db and creates a table 
     * and also adds a player name and score to the table
     * @param player1Name
     * @param player1Score 
     */
    public void addHighScoreValues(String player1Name, int player1Score) {
        try {

            conn = DriverManager.getConnection(url, userName, password);
            statement = conn.createStatement();

            checkTableExisting(tableName);
            //Create table
            statement.executeUpdate("CREATE TABLE " + tableName + " (NAME VARCHAR(50), SCORE INT)");

            //Adds values to the table
            statement.executeUpdate("INSERT INTO " + tableName + " VALUES ('" + player1Name + "'," + player1Score + ")");

        } catch (Throwable ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
        }
    }

    /**
     * checks if existing table exists
     * @param newTableName 
     */
    private void checkTableExisting(String newTableName) {
        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rsDBMeta = dbmd.getTables(null, null, null, null);//types);
            Statement dropStatement = null;

            while (rsDBMeta.next()) {
                String tableName = rsDBMeta.getString("TABLE_NAME");
                if (tableName.compareToIgnoreCase(newTableName) == 0) {
                    String sqlDropTable = "DROP TABLE " + newTableName;
                    dropStatement = conn.createStatement();
                    dropStatement.executeUpdate(sqlDropTable);
                }
            }
            if (rsDBMeta != null) {
                rsDBMeta.close();
            }
            if (dropStatement != null) {
                dropStatement.close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * closes the database
     */
    public void closeConn() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
