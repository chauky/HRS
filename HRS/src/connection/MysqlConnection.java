package connection;

import com.mysql.jdbc.Connection;
import java.sql.*;
/**
 * @desc A singleton database access class for MySQL
 * @author Ramindu
 * @ Refer to http://rdeshapriya.com/a-singleton-java-class-for-mysql-db-connection/
 */
public final class MysqlConnection {
    public Connection conn;
    private Statement statement;
    public static MysqlConnection db;
    private MysqlConnection() {
        String url= "jdbc:mysql://localhost:3306/";
        String dbName = "hoteldb";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "";
        try {
            Class.forName(driver).newInstance();
            this.conn = (Connection)DriverManager.getConnection(url+dbName,userName,password);
        }
        catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }
    /**
     *
     * @return MysqlConnect Database connection object
     */
    public static synchronized MysqlConnection getDbCon() {
        if ( db == null ) {
            db = new MysqlConnection();
        }
        return db;
 
    }
    /**
     *
     * @param query String The query to be executed
     * @return a ResultSet object containing the results or null if not available
     * @throws SQLException
     */
    public ResultSet query(String query) throws SQLException{
        statement = db.conn.createStatement();
        ResultSet res = statement.executeQuery(query);
        return res;
    }
    /**
     * @desc Method to insert data to a table
     * @param insertQuery String The Update/Insert/Delete query
     * @return int
     * @throws SQLException
     */
    public int executeQuery(String Query) throws SQLException {
        statement = db.conn.createStatement();
        int result = statement.executeUpdate(Query);
        return result;
 
    }
}

