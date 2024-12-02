package Database;

import java.sql.*;

public class DatabaseConnection {

    // Database connection parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/AcmePlex";
    private static final String USER = "root";
    private static final String PASSWORD = "password1";

    private static Connection conn = null;

    // Method to establish a connection
    public static Connection connect() {
        try {
            if (conn == null || conn.isClosed()) {
                // Establish the connection
                conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                System.out.println("Database connected successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to the database.");
        }
        return conn;
    }

    // Method to close the connection
    public static void disconnect() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Database disconnected successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to disconnect from the database.");
        }
    }

    // Utility method to check if the connection is valid
    public static boolean isConnected() {
        try {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
