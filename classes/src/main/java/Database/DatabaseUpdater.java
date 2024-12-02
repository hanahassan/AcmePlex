package Database;

import java.sql.*;

public class DatabaseUpdater {

    private DatabaseUpdater() {
        // Prevent instantiation
    }

    public static void addMovie(String title) {
        String sql = "INSERT INTO Movies (Title) VALUES (?)";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, title);
            ps.executeUpdate();
    
            // Get the generated MovieID if the movie was inserted successfully
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int movieId = rs.getInt(1);
                    System.out.println("Movie added: " + title + " with ID: " + movieId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void addShowtime(String movieName, String theatreName, String showDate, String showTime, int availableSeats) {
        // Check if movie already exists before adding a new one
        int movieId = getMovieIdByTitle(movieName);  // Fetch movie ID if it exists
        if (movieId == -1) {
            addMovie(movieName);  // Add movie if not present
            movieId = getMovieIdByTitle(movieName);  // Get the newly inserted MovieID
        }
        
        // Add Showtime once movie is confirmed to exist
        String sql = "INSERT INTO Showtimes (TheatreID, MovieID, ShowDate, ShowTime, AvailableSeats, RUReservedSeats) " +
                     "SELECT t.TheatreID, ?, ?, ?, ?, 0 " +
                     "FROM Theatres t " +
                     "WHERE t.Name = ?";
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, movieId);  // Use the movieId for the insert
            ps.setString(2, showDate);
            ps.setString(3, showTime);
            ps.setInt(4, availableSeats);
            ps.setString(5, theatreName);
            ps.executeUpdate();
            System.out.println("Showtime added for movie: " + movieName + " at " + theatreName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static int getMovieIdByTitle(String title) {
        String sql = "SELECT MovieID FROM Movies WHERE Title = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, title);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("MovieID");  // Return existing MovieID if found
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;  // Return -1 if movie not found
    }    
    

    public static void editMovie(int movieId, String newTitle) {
        String sql = "UPDATE Movies SET Title = ? WHERE MovieID = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newTitle);
            ps.setInt(2, movieId);
            ps.executeUpdate();
            System.out.println("Movie updated: ID = " + movieId + ", New Title = " + newTitle);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    

    public static void editShowtime(int showtimeID, String newShowDate, String newShowTime, int newAvailableSeats) {
        String sql = "UPDATE Showtimes SET ShowDate = ?, ShowTime = ?, AvailableSeats = ? WHERE ShowtimeID = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newShowDate);
            ps.setString(2, newShowTime);
            ps.setInt(3, newAvailableSeats);
            ps.setInt(4, showtimeID);
            ps.executeUpdate();
            System.out.println("Showtime updated for ShowtimeID: " + showtimeID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    public static void removeMovie(int movieId) {
        String sql = "DELETE FROM Movies WHERE MovieID = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, movieId);
            ps.executeUpdate();
            System.out.println("Movie removed: ID = " + movieId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addUser(String name, String email, String phone, boolean isRegistered, String cardNumber, Date cardExpiry, Date joinDate, boolean feePaid, double credits, String password) {
        String sql = "INSERT INTO Users (Name, Email, Phone, IsRegistered, CardNumber, CardExpiry, RUJoinDate, AccountFeePaid, Credits, Password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setBoolean(4, isRegistered);
            ps.setString(5, cardNumber);
            ps.setDate(6, cardExpiry);
            ps.setDate(7, joinDate);
            ps.setBoolean(8, feePaid);
            ps.setDouble(9, credits);
            ps.setString(10, password);
            ps.executeUpdate();
            System.out.println("User added: " + name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addTicket(int showtimeId, int userId, String seatNumber, Timestamp purchaseDate, double price, boolean isCancelled) {
        // SQL to insert a new ticket
        String sqlTicket = "INSERT INTO Tickets (ShowtimeID, UserID, SeatNumber, PurchaseDate, Price, IsCancelled) VALUES (?, ?, ?, ?, ?, ?)";
        
        // SQL to update the AvailableSeats after a ticket is purchased
        String sqlUpdateSeats = "UPDATE Showtimes SET AvailableSeats = AvailableSeats - 1 WHERE ShowtimeID = ?";
    
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sqlTicket);
             PreparedStatement psUpdateSeats = conn.prepareStatement(sqlUpdateSeats)) {
            
            // Start a transaction to ensure atomicity
            conn.setAutoCommit(false);
    
            // Set parameters for inserting the ticket
            ps.setInt(1, showtimeId);
            ps.setInt(2, userId);
            ps.setString(3, seatNumber);
            ps.setTimestamp(4, purchaseDate);
            ps.setDouble(5, price);
            ps.setBoolean(6, isCancelled);
            ps.executeUpdate();  // Insert the ticket
    
            // Set parameters for updating the available seats
            psUpdateSeats.setInt(1, showtimeId);
            psUpdateSeats.executeUpdate();  // Decrease AvailableSeats by 1
    
            // Commit the transaction
            conn.commit();
            
            System.out.println("Ticket added for UserID: " + userId + " and AvailableSeats updated for ShowtimeID: " + showtimeId);
            
        } catch (SQLException e) {
            try {
                // If an error occurs, roll back the transaction
                Connection conn = DatabaseConnection.connect();
                conn.rollback();
                System.out.println("Transaction rolled back.");
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        }
    }
    

    public static void cancelTicketAndAddCredit(int ticketID) {
        String sqlTicketUpdate = "UPDATE Tickets SET IsCancelled = TRUE, CancelledDate = NOW() WHERE TicketID = ?";
        String sqlCreditUpdate = "INSERT INTO Credit (UserID, Amount, CreditDate) " +
                                 "SELECT UserID, Price, NOW() FROM Tickets WHERE TicketID = ?";
        String sqlGetUserCredit = "SELECT Credits FROM Users WHERE UserID = (SELECT UserID FROM Tickets WHERE TicketID = ?)";
    
        try (Connection conn = DatabaseConnection.connect()) {
            // Start transaction
            conn.setAutoCommit(false);
            
            // Update the ticket to cancelled
            try (PreparedStatement psTicket = conn.prepareStatement(sqlTicketUpdate)) {
                psTicket.setInt(1, ticketID);
                psTicket.executeUpdate();
            }
    
            // Add credit for the cancelled ticket
            try (PreparedStatement psCredit = conn.prepareStatement(sqlCreditUpdate)) {
                psCredit.setInt(1, ticketID);
                psCredit.executeUpdate();
            }
    
            // Get user's current credits to update
            try (PreparedStatement psGetCredit = conn.prepareStatement(sqlGetUserCredit)) {
                psGetCredit.setInt(1, ticketID);
                ResultSet rs = psGetCredit.executeQuery();
                if (rs.next()) {
                    double currentCredits = rs.getDouble("Credits");
                    double creditAmount = DatabaseRetriever.getTicketAmount(ticketID);  // You need to implement getTicketPrice method
    
                    // Update the user's credit total
                    String sqlUpdateCredits = "UPDATE Users SET Credits = ? WHERE UserID = (SELECT UserID FROM Tickets WHERE TicketID = ?)";
                    try (PreparedStatement psUpdateCredits = conn.prepareStatement(sqlUpdateCredits)) {
                        psUpdateCredits.setDouble(1, currentCredits + creditAmount);
                        psUpdateCredits.setInt(2, ticketID);
                        psUpdateCredits.executeUpdate();
                    }
                }
            }
    
            // Commit transaction
            conn.commit();
            System.out.println("Ticket cancelled and credit added for TicketID: " + ticketID);
        } catch (SQLException e) {
            try {
                // If an error occurs, roll back the transaction
                DatabaseConnection.connect().rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        }
    }
    
}
