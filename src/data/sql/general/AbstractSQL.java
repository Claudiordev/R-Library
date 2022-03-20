package data.sql.general;

import java.sql.*;

public class AbstractSQL {
    public static String url; //url to database
    public static String username;
    public static String password;
    public static String database;
    public static Connection conn; //Connection to database

    /**
     * Set the Driver class for JDBC SQL e.g "com.mysql.jdbc.Driver" *
     * */
    public void classCall(String className) {
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return Connection with data provided on Object instanciation
     * @throws SQLException
     */
    public java.sql.Connection connect() throws SQLException {

        if (username != null) {
            //MySQL
            classCall("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url,username,password);
        } else {
            //SQLLite
            classCall("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
        }

        return conn;
    }

    /**
     * @param query Insert/Update/Delete query
     * @return true if done with success, false if not;
     * @throws SQLException
     */
    public boolean query(String query) throws SQLException {
        try (Connection conn = connect()) {
            Statement statement = conn.createStatement();
            if (statement.execute(query)) return true;
        }
        return false;
    }

    /**
     * Retrieve data from Database
     * @param query of data to be retrieve
     * @return ResultSet Object containing data
     * @throws SQLException if an error occurs connecting to the Database
     */
    public ResultSet retrieve(String query) throws SQLException{
        Statement statement = connect().createStatement();
        return statement.executeQuery(query);
    }
}
