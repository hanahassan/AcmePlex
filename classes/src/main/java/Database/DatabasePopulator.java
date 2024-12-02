package Database;
import java.sql.*;

public class DatabasePopulator {

    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.connect()) {
            populateDatabase(conn);
            System.out.println("Database populated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Disconnect from the database
            DatabaseConnection.disconnect();
        }
    }

    private static void populateDatabase(Connection conn) throws SQLException {
        // Insert data into Movies table
        String insertMovieSQL = "INSERT INTO Movies (Title) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(insertMovieSQL)) {
            ps.setString(1, "Ponyo");
            ps.executeUpdate();

            ps.setString(1, "Spirited Away");
            ps.executeUpdate();
        }

        // Insert data into Showtimes table
        String insertShowtimeSQL = "INSERT INTO Showtimes (TheatreID, MovieID, ShowDate, ShowTime, AvailableSeats, RUReservedSeats) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(insertShowtimeSQL)) {
            ps.setInt(1, 1); // Acme Downtown Theatre
            ps.setInt(2, 4); // Ponyo
            ps.setDate(3, Date.valueOf("2024-12-01"));
            ps.setTime(4, Time.valueOf("18:00:00"));
            ps.setInt(5, 150);
            ps.setInt(6, 15);
            ps.executeUpdate();

            ps.setInt(1, 2); // Acme NW
            ps.setInt(2, 5); // Spirited Away
            ps.setDate(3, Date.valueOf("2024-12-02"));
            ps.setTime(4, Time.valueOf("20:00:00"));
            ps.setInt(5, 200);
            ps.setInt(6, 20);
            ps.executeUpdate();
        }

        // Insert data into Users table
        String insertUserSQL = "INSERT INTO Users (Name, Email, Phone, IsRegistered, CardNumber, CardExpiry, RUJoinDate, AccountFeePaid, Credits, Password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(insertUserSQL)) {
            ps.setString(1, "Hana Hassan");
            ps.setString(2, "hanahassan@example.com");
            ps.setString(3, "1234567890");
            ps.setBoolean(4, true); // Registered User
            ps.setString(5, "1234567812345678");
            ps.setDate(6, Date.valueOf("2026-05-01"));
            ps.setDate(7, Date.valueOf("2024-01-01"));
            ps.setBoolean(8, true); // Account fee paid
            ps.setDouble(9, Double.parseDouble("0.00"));
            ps.setString(10, "password123"); // Password
            ps.executeUpdate();

            ps.setString(1, "Mariam Ibrahim");
            ps.setString(2, "mariamibrahim@example.com");
            ps.setString(3, "0987654321");
            ps.setBoolean(4, false); // Ordinary User
            ps.setString(5, null); // No Card for non-registered user
            ps.setDate(6, null);
            ps.setDate(7, null);
            ps.setBoolean(8, false);
            ps.setDouble(9, Double.parseDouble("0.00"));
            ps.setString(10, "pass1234"); // Password
            ps.executeUpdate();

            ps.setString(1, "Chloe Choi");
            ps.setString(2, "chloechoi@example.com");
            ps.setString(3, "1122334455");
            ps.setBoolean(4, true); // Registered User
            ps.setString(5, "2345678923456789");
            ps.setDate(6, Date.valueOf("2026-08-01"));
            ps.setDate(7, Date.valueOf("2024-02-15"));
            ps.setBoolean(8, true); // Account fee paid
            ps.setDouble(9, Double.parseDouble("0.00"));
            ps.setString(10, "securepass"); // Password
            ps.executeUpdate();

            // Insert Aroush Qureshi
            ps.setString(1, "Aroush Qureshi");
            ps.setString(2, "aroushqureshi@example.com");
            ps.setString(3, "2233445566");
            ps.setBoolean(4, false); // Ordinary User
            ps.setString(5, null); // No Card for non-registered user
            ps.setDate(6, null);
            ps.setDate(7, null);
            ps.setBoolean(8, false); // Account fee not paid
            ps.setDouble(9, Double.parseDouble("0.00"));
            ps.setString(10, "mypassword"); // Password
            ps.executeUpdate();
        }

        // Insert data into Tickets table
        String insertTicketSQL = "INSERT INTO Tickets (ShowtimeID, UserID, SeatNumber, PurchaseDate, Price, IsCancelled) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(insertTicketSQL)) {
            ps.setInt(1, 1); // First showtime
            ps.setInt(2, 2); // Hana Hassan
            ps.setString(3, "A1");
            ps.setTimestamp(4, Timestamp.valueOf("2024-11-23 12:00:00"));
            ps.setDouble(5, Double.parseDouble("15.00"));
            ps.setBoolean(6, false); // Not cancelled
            ps.executeUpdate();

            ps.setInt(1, 2); // Second showtime
            ps.setInt(2, 3); // Mariam Ibrahim
            ps.setString(3, "B5");
            ps.setTimestamp(4, Timestamp.valueOf("2024-11-23 15:30:00"));
            ps.setDouble(5, Double.parseDouble("15.00"));
            ps.setBoolean(6, false);
            ps.executeUpdate();

            ps.setInt(1, 1); // First showtime
            ps.setInt(2, 4); // Chloe Choi
            ps.setString(3, "C3");
            ps.setTimestamp(4, Timestamp.valueOf("2024-11-23 12:00:00"));
            ps.setDouble(5, Double.parseDouble("15.00"));
            ps.setBoolean(6, false); // Not cancelled
            ps.executeUpdate();

            ps.setInt(1, 2); // Second showtime
            ps.setInt(2, 5); // Aroush Qureshi
            ps.setString(3, "D7");
            ps.setTimestamp(4, Timestamp.valueOf("2024-11-23 15:30:00"));
            ps.setDouble(5, Double.parseDouble("15.00"));
            ps.setBoolean(6, false); // Not cancelled
            ps.executeUpdate();
        }

        // Insert data into Payments table
        String insertPaymentSQL = "INSERT INTO Payments (TicketID, UserID, Amount, PaymentDate) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(insertPaymentSQL)) {
            // Payment for ticket 1 (Hana Hassan, TicketID = 1)
            ps.setInt(1, 1);
            ps.setInt(2, 2);
            ps.setDouble(3, Double.parseDouble("15.00"));
            ps.setTimestamp(4, Timestamp.valueOf("2024-11-23 12:05:00"));
            ps.executeUpdate();
        
            // Payment for ticket 2 (Mariam Ibrahim, TicketID = 2)
            ps.setInt(1, 2);
            ps.setInt(2, 3);
            ps.setDouble(3, Double.parseDouble("15.00"));
            ps.setTimestamp(4, Timestamp.valueOf("2024-11-23 15:35:00"));
            ps.executeUpdate();
        
            // Payment for ticket 3 (Chloe Choi, TicketID = 3)
            ps.setInt(1, 3);
            ps.setInt(2, 4);
            ps.setDouble(3, Double.parseDouble("15.00"));
            ps.setTimestamp(4, Timestamp.valueOf("2024-11-23 12:10:00"));
            ps.executeUpdate();
        
            // Payment for ticket 4 (Aroush Qureshi, TicketID = 4)
            ps.setInt(1, 4);
            ps.setInt(2, 5);
            ps.setDouble(3, Double.parseDouble("15.00"));
            ps.setTimestamp(4, Timestamp.valueOf("2024-11-23 15:40:00"));
            ps.executeUpdate();
        }

    }
}
