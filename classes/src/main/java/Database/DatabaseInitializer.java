package Database;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
        public static void main(String[] args) {
                try (Connection conn = DatabaseConnection.connect();
                      Statement stmt = conn.createStatement()) {
                        initializeDatabase(conn, stmt);
                    System.out.println("Database initialized successfully!");
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    // Disconnect from the database
                    DatabaseConnection.disconnect();
                }
            }

        private static void initializeDatabase(Connection conn, Statement stmt) throws SQLException {

            stmt.executeUpdate("DROP DATABASE IF EXISTS `AcmePlex`");
            stmt.executeUpdate("CREATE DATABASE `AcmePlex`");
            stmt.executeUpdate("USE `AcmePlex`");

            // Drop tables if they exist (to reset the database)
            stmt.executeUpdate("DROP TABLE IF EXISTS AdminSettings, Payments, Tickets, Users, Showtimes, Movies, Theatres");

            // Create Theatres table
            stmt.executeUpdate("CREATE TABLE Theatres (" +
                    "TheatreID INT AUTO_INCREMENT PRIMARY KEY," +
                    "Name VARCHAR(255) NOT NULL," +
                    "Location VARCHAR(255) NOT NULL)");

            // Create Movies table
            stmt.executeUpdate("CREATE TABLE Movies (" +
                    "MovieID INT AUTO_INCREMENT PRIMARY KEY," +
                    "Title VARCHAR(255) NOT NULL)");

            // Create Showtimes table
            stmt.executeUpdate("CREATE TABLE Showtimes (" +
                    "ShowtimeID INT AUTO_INCREMENT PRIMARY KEY," +
                    "TheatreID INT NOT NULL," +
                    "MovieID INT NOT NULL," +
                    "ShowDate DATE NOT NULL," +
                    "ShowTime TIME NOT NULL," +
                    "AvailableSeats INT NOT NULL," +
                    "RUReservedSeats INT NOT NULL," +
                    "FOREIGN KEY (TheatreID) REFERENCES Theatres(TheatreID)," +
                    "FOREIGN KEY (MovieID) REFERENCES Movies(MovieID))");

            // Create Users table
            stmt.executeUpdate("CREATE TABLE Users (" +
                    "UserID INT AUTO_INCREMENT PRIMARY KEY," +
                    "Name VARCHAR(255) NOT NULL," +
                    "Email VARCHAR(255) UNIQUE NOT NULL," +
                    "Password VARCHAR(255) NOT NULL," +
                    "Phone VARCHAR(50)," +
                    "IsRegistered BOOLEAN NOT NULL," +
                    "CardNumber VARCHAR(50) NULL," +
                    "CardExpiry DATE NULL," +
                    "RUJoinDate DATE NULL," +
                    "AccountFeePaid BOOLEAN DEFAULT FALSE," +
                    "Credits DECIMAL(10, 2) DEFAULT 0.00)");

            // Create Tickets table
            stmt.executeUpdate("CREATE TABLE Tickets (" +
                    "TicketID INT AUTO_INCREMENT PRIMARY KEY," +
                    "ShowtimeID INT NOT NULL," +
                    "UserID INT NOT NULL," +
                    "SeatNumber VARCHAR(10) NOT NULL," +
                    "PurchaseDate DATETIME NOT NULL," +
                    "Price DECIMAL(10, 2) NOT NULL," +
                    "IsCancelled BOOLEAN DEFAULT FALSE," +
                    "CancelledDate DATETIME NULL," +
                    "FOREIGN KEY (ShowtimeID) REFERENCES Showtimes(ShowtimeID)," +
                    "FOREIGN KEY (UserID) REFERENCES Users(UserID))");

            // Create Payments table
            stmt.executeUpdate("CREATE TABLE Payments (" +
                    "PaymentID INT AUTO_INCREMENT PRIMARY KEY," +
                    "TicketID INT NOT NULL," +
                    "UserID INT NOT NULL," +
                    "Amount DECIMAL(10, 2) NOT NULL," +
                    "PaymentDate DATETIME NOT NULL," +
                    "FOREIGN KEY (TicketID) REFERENCES Tickets(TicketID)," +
                    "FOREIGN KEY (UserID) REFERENCES Users(UserID))");

            // Create AdminSettings table
            stmt.executeUpdate("CREATE TABLE AdminSettings (" +
                    "SettingID INT AUTO_INCREMENT PRIMARY KEY," +
                    "Name VARCHAR(255) NOT NULL," +
                    "Value DECIMAL(10, 2) NOT NULL," +
                    "LastUpdated DATETIME NOT NULL)");

                    // Create Credit table
            stmt.executeUpdate("CREATE TABLE Credit (" +
                    "CreditID INT AUTO_INCREMENT PRIMARY KEY," +
                    "UserID INT NOT NULL," +
                    "Amount DECIMAL(10, 2) NOT NULL," +
                    "CreditDate DATETIME NOT NULL," +
                    "FOREIGN KEY (UserID) REFERENCES Users(UserID))");

            // Populate initial data
            stmt.executeUpdate("INSERT INTO Theatres (Name, Location) VALUES" +
                    "('AcmePlex Downtown', 'Stephen Avenue')," +
                    "('AcmePlex NW', 'University Ave')");

            stmt.executeUpdate("INSERT INTO Movies (Title) VALUES" +
                    "('The Great Adventure')," +
                    "('Space Odyssey')," +
                    "('Mystery Island')");

            // Insert shared employee user into Users table
            stmt.executeUpdate("INSERT INTO Users (Name, Email, Phone, IsRegistered, CardNumber, CardExpiry, Password) VALUES" +
            "('AcmePlex Admin', 'admin@acmeplex.com', '555-0000', FALSE, '0000000000000000', '2025-12-31', 'adminpass123')");

        }
}
