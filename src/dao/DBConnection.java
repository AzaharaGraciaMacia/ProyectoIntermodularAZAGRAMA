package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3308/proyintud5azaharagracia";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1968";

    private static Connection connection;

    private DBConnection() {}

    public static Connection getConnection() {
        if (connection == null) {
            synchronized (DBConnection.class) {
                    try {
                        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

                    } catch ( SQLException e) {
                        e.printStackTrace();
                    }
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}