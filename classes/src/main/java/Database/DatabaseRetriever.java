package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseRetriever {

    private DatabaseRetriever() {
        // Prevent instantiation
    }

    public static List<String[]> getMoviesByTheaterID(int theaterID) {
        String sql = "SELECT M.MovieID, M.Title FROM Movies M " +
                     "JOIN Showtimes S ON M.MovieID = S.MovieID " +
                     "WHERE S.TheaterID = ?";
        List<String[]> movies = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Set the theaterID parameter in the query
            pstmt.setInt(1, theaterID);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                // Collect movie IDs and titles into the list
                while (rs.next()) {
                    String movieID = String.valueOf(rs.getInt("MovieID"));
                    String title = rs.getString("Title");
                    movies.add(new String[]{movieID, title});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return movies;
    }

    public static List<String[]> getAllMovies() {
        String sql = "SELECT MovieID, Title FROM Movies";
        List<String[]> movies = new ArrayList<>();
    
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
    
            // Collect movie IDs and titles into the list
            while (rs.next()) {
                String movieID = String.valueOf(rs.getInt("MovieID"));
                String title = rs.getString("Title");
                movies.add(new String[]{movieID, title});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return movies;
    }    

    public static List<String[]> getAllTheatres() throws SQLException {
        String query = "SELECT TheatreID, Name, Location FROM Theatres";
        List<String[]> theatres = new ArrayList<>();
        try ( Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                theatres.add(new String[]{
                    String.valueOf(rs.getInt("TheatreID")), // Get TheatreID as String
                    rs.getString("Name"),                  // Get Name
                    rs.getString("Location")               // Get Location
                });
            }
        }
        return theatres;
    }     

    public static List<String[]> getTheatresByMovie(String movieTitle) {
        String sql = "SELECT t.Name, t.Location FROM Theatres t " +
                     "JOIN Showtimes s ON t.TheatreID = s.TheatreID " +
                     "JOIN Movies m ON s.MovieID = m.MovieID WHERE m.Title = ?";
        List<String[]> theatres = new ArrayList<>();

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, movieTitle);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    theatres.add(new String[]{rs.getString("Name"), rs.getString("Location")});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return theatres;
    }

    public static List<String[]> getAllShowtimes() throws SQLException {
        String query = "SELECT ShowtimeID, TheatreID, MovieID, ShowDate, ShowTime, AvailableSeats, RUReservedSeats FROM Showtimes";
        List<String[]> showtimes = new ArrayList<>();
        try (Connection conn = DatabaseConnection.connect();
            Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                showtimes.add(new String[]{
                    String.valueOf(rs.getInt("ShowtimeID")),   // Get ShowtimeID as String
                    String.valueOf(rs.getInt("TheatreID")),    // Get TheatreID as String
                    String.valueOf(rs.getInt("MovieID")),      // Get MovieID as String
                    rs.getDate("ShowDate").toString(),         // Get ShowDate as String
                    rs.getTime("ShowTime").toString(),         // Get ShowTime as String
                    String.valueOf(rs.getInt("AvailableSeats")), // Get AvailableSeats as String
                    String.valueOf(rs.getInt("RUReservedSeats")) // Get RUReservedSeats as String
                });
            }
        }
        return showtimes;
    }    

    public static List<String[]> getShowtimes(String movieTitle, String theatreName) {
        String sql = "SELECT s.ShowDate, s.ShowTime FROM Showtimes s " +
                     "JOIN Theatres t ON s.TheatreID = t.TheatreID " +
                     "JOIN Movies m ON s.MovieID = m.MovieID " +
                     "WHERE m.Title = ? AND t.Name = ?";
        List<String[]> showtimes = new ArrayList<>();

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, movieTitle);
            ps.setString(2, theatreName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    showtimes.add(new String[]{rs.getDate("ShowDate").toString(), rs.getTime("ShowTime").toString()});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return showtimes;
    }

    public static List<String> getSeats(int showtimeId) {
        String sql = "SELECT SeatNumber FROM Tickets WHERE ShowtimeID = ? AND IsCancelled = FALSE";
        List<String> seats = new ArrayList<>();

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, showtimeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    seats.add(rs.getString("SeatNumber"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seats;
    }

    public static double getUserCredits(int userId) {
        String sql = "SELECT Credits FROM Users WHERE UserID = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("Credits");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0; // Return null if no result is found or an error occurs
    }

    public static String getUserEmail(int userId) {
        String sql = "SELECT Email FROM Users WHERE UserID = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Email");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ""; // Return null if no result is found or an error occurs
    }

    public static double getTicketAmount(int ticketId) {
        String sql = "SELECT Price FROM Tickets WHERE TicketID = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ticketId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("Price");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Return null if no result is found or an error occurs
    }

    public static Boolean getAccountFeeStatus(int userId) {
        String sql = "SELECT AccountFeePaid FROM Users WHERE UserID = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("AccountFeePaid");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no result is found or an error occurs
    }

    public static int authenticateUser(String email, String password) throws SQLException {
        String sql = "SELECT UserID FROM Users WHERE Email = ? AND Password = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("UserID");
                }
            }
        }
        return -1; // Return -1 if authentication fails
    }

    public static List<String[]> getTicketsByUser(int userId) {
        String sql = "SELECT TicketID, ShowtimeID, UserID, SeatNumber, PurchaseDate, Price, IsCancelled, CancelledDate " +
                       "FROM Tickets WHERE UserID = ?";
        List<String[]> tickets = new ArrayList<>();
    
        try (Connection conn = DatabaseConnection.connect();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tickets.add(new String[]{
                        String.valueOf(rs.getInt("TicketID")),
                        String.valueOf(rs.getInt("ShowtimeID")),
                        String.valueOf(rs.getInt("UserID")),
                        rs.getString("SeatNumber"),
                        rs.getTimestamp("PurchaseDate").toString(),
                        String.valueOf(rs.getDouble("Price")),
                        String.valueOf(rs.getBoolean("IsCancelled")),
                        rs.getTimestamp("CancelledDate") != null ? rs.getTimestamp("CancelledDate").toString() : "N/A"
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return tickets;
    }
    
    public static List<String[]> getPercentageSeatsTakenByRU(String showtime) {
        String sql = "SELECT (COUNT(CASE WHEN user_type = 'RU' THEN 1 END) * 100.0 / COUNT(*)) AS percentage FROM seats WHERE showtime_id = ?";
        List<String[]> percentageList = new ArrayList<>();
    
        try (Connection conn = DatabaseConnection.connect();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, showtime);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    percentageList.add(new String[]{
                        String.valueOf(rs.getDouble("percentage"))
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return percentageList;
    }

    public static List<String[]> getAllUsers() throws SQLException {
        String query = "SELECT UserID, Name, Email, Password, Phone, IsRegistered, CardNumber, CardExpiry, RUJoinDate, AccountFeePaid, Credits FROM Users";
        List<String[]> users = new ArrayList<>();
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                users.add(new String[]{
                    String.valueOf(rs.getInt("UserID")),             // Get UserID as String
                    rs.getString("Name"),                           // Get Name
                    rs.getString("Email"),                          // Get Email
                    rs.getString("Password"),                       // Get Password
                    rs.getString("Phone"),                          // Get Phone
                    String.valueOf(rs.getBoolean("IsRegistered")),  // Get IsRegistered as String
                    rs.getString("CardNumber"),                     // Get CardNumber
                    rs.getDate("CardExpiry") != null ? rs.getDate("CardExpiry").toString() : null, // Get CardExpiry or null
                    rs.getDate("RUJoinDate") != null ? rs.getDate("RUJoinDate").toString() : null, // Get RUJoinDate or null
                    rs.getObject("AccountFeePaid") != null ? String.valueOf(rs.getBoolean("AccountFeePaid")) : null, // Get AccountFeePaid or null
                    rs.getBigDecimal("Credits") != null ? rs.getBigDecimal("Credits").toString() : null // Get Credits or null
                });
            }
        }
        return users;
    }

    public static List<String[]> getAllTickets() throws SQLException {
        String query = "SELECT TicketID, ShowtimeID, UserID, SeatNumber, PurchaseDate, Price, IsCancelled, CancelledDate FROM Tickets";
        List<String[]> tickets = new ArrayList<>();
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                tickets.add(new String[]{
                    String.valueOf(rs.getInt("TicketID")),             // Get TicketID as String
                    String.valueOf(rs.getInt("ShowtimeID")),          // Get ShowtimeID as String
                    String.valueOf(rs.getInt("UserID")),              // Get UserID as String
                    rs.getString("SeatNumber"),                      // Get SeatNumber
                    rs.getTimestamp("PurchaseDate").toString(),      // Get PurchaseDate as String
                    rs.getBigDecimal("Price").toString(),            // Get Price as String
                    String.valueOf(rs.getBoolean("IsCancelled")),    // Get IsCancelled as String
                    rs.getTimestamp("CancelledDate") != null ? rs.getTimestamp("CancelledDate").toString() : null // Get CancelledDate or null
                });
            }
        }
        return tickets;
    }

    public static List<String[]> getAllPayments() throws SQLException {
        String query = "SELECT PaymentID, TicketID, UserID, Amount, PaymentDate FROM Payments";
        List<String[]> payments = new ArrayList<>();
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                payments.add(new String[]{
                    String.valueOf(rs.getInt("PaymentID")),      // Get PaymentID as String
                    String.valueOf(rs.getInt("TicketID")),       // Get TicketID as String
                    String.valueOf(rs.getInt("UserID")),         // Get UserID as String
                    rs.getBigDecimal("Amount").toString(),       // Get Amount as String
                    rs.getTimestamp("PaymentDate").toString()   // Get PaymentDate as String
                });
            }
        }
        return payments;
    }
    
    public static List<String[]> getAllCredits() throws SQLException {
        String query = "SELECT CreditID, UserID, Amount, CreditDate, LastUpdated FROM Credit";
        List<String[]> credits = new ArrayList<>();
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                credits.add(new String[]{
                    String.valueOf(rs.getInt("CreditID")),      // Get CreditID as String
                    String.valueOf(rs.getInt("UserID")),        // Get UserID as String
                    rs.getBigDecimal("Amount").toString(),      // Get Amount as String
                    rs.getTimestamp("CreditDate").toString(),  // Get CreditDate as String
                    rs.getTimestamp("LastUpdated").toString()  // Get LastUpdated as String
                });
            }
        }
        return credits;
    }
    
    
}
    