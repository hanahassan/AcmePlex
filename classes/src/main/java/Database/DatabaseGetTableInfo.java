package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseGetTableInfo {

    private DatabaseGetTableInfo() {
        // Prevent instantiation
    }

    public static List<String[]> getTableInfoById(int tableId, String tableName) {
        String sql = "";
        
        // Build the SQL query based on the table name
        switch (tableName) {
            case "Theatres":
                sql = "SELECT TheatreID, Name, Location FROM Theatres WHERE TheatreID = ?";
                break;
            case "Movies":
                sql = "SELECT MovieID, Title FROM Movies WHERE MovieID = ?";
                break;
            case "Showtimes":
                sql = "SELECT ShowtimeID, TheatreID, MovieID, ShowDate, ShowTime, AvailableSeats, RUReservedSeats FROM Showtimes WHERE ShowtimeID = ?";
                break;
            case "Users":
                sql = "SELECT UserID, Name, Email, Password, Phone, IsRegistered, CardNumber, CardExpiry, RUJoinDate, AccountFeePaid, Credits FROM Users WHERE UserID = ?";
                break;
            case "Tickets":
                sql = "SELECT TicketID, ShowtimeID, UserID, SeatNumber, PurchaseDate, Price, IsCancelled, CancelledDate FROM Tickets WHERE TicketID = ?";
                break;
            case "Payments":
                sql = "SELECT PaymentID, TicketID, UserID, Amount, PaymentDate FROM Payments WHERE PaymentID = ?";
                break;
            case "AdminSettings":
                sql = "SELECT SettingID, Name, Value, LastUpdated FROM AdminSettings WHERE SettingID = ?";
                break;
            case "Credit":
                sql = "SELECT CreditID, UserID, Amount, CreditDate FROM Credit WHERE CreditID = ?";
                break;
            default:
                throw new IllegalArgumentException("Invalid table name.");
        }

        List<String[]> tableData = new ArrayList<>();

        try (Connection conn = DatabaseConnection.connect();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tableId); // Set the provided ID

            try (ResultSet rs = ps.executeQuery()) {
                // Loop through the result set to gather the data
                while (rs.next()) {
                    // Collecting data in String[] based on the table structure
                    switch (tableName) {
                        case "Theatres":
                            tableData.add(new String[]{
                                String.valueOf(rs.getInt("TheatreID")),
                                rs.getString("Name"),
                                rs.getString("Location")
                            });
                            break;
                        case "Movies":
                            tableData.add(new String[]{
                                String.valueOf(rs.getInt("MovieID")),
                                rs.getString("Title")
                            });
                            break;
                        case "Showtimes":
                            tableData.add(new String[]{
                                String.valueOf(rs.getInt("ShowtimeID")),
                                String.valueOf(rs.getInt("TheatreID")),
                                String.valueOf(rs.getInt("MovieID")),
                                rs.getDate("ShowDate").toString(),
                                rs.getTime("ShowTime").toString(),
                                String.valueOf(rs.getInt("AvailableSeats")),
                                String.valueOf(rs.getInt("RUReservedSeats"))
                            });
                            break;
                        case "Users":
                            tableData.add(new String[]{
                                String.valueOf(rs.getInt("UserID")),
                                rs.getString("Name"),
                                rs.getString("Email"),
                                rs.getString("Password"),
                                rs.getString("Phone"),
                                String.valueOf(rs.getBoolean("IsRegistered")),
                                rs.getString("CardNumber"),
                                rs.getDate("CardExpiry").toString(),
                                rs.getDate("RUJoinDate").toString(),
                                String.valueOf(rs.getBoolean("AccountFeePaid")),
                                String.valueOf(rs.getDouble("Credits"))
                            });
                            break;
                        case "Tickets":
                            tableData.add(new String[]{
                                String.valueOf(rs.getInt("TicketID")),
                                String.valueOf(rs.getInt("ShowtimeID")),
                                String.valueOf(rs.getInt("UserID")),
                                rs.getString("SeatNumber"),
                                rs.getTimestamp("PurchaseDate").toString(),
                                String.valueOf(rs.getDouble("Price")),
                                String.valueOf(rs.getBoolean("IsCancelled")),
                                rs.getTimestamp("CancelledDate") != null ? rs.getTimestamp("CancelledDate").toString() : "N/A"
                            });
                            break;
                        case "Payments":
                            tableData.add(new String[]{
                                String.valueOf(rs.getInt("PaymentID")),
                                String.valueOf(rs.getInt("TicketID")),
                                String.valueOf(rs.getInt("UserID")),
                                String.valueOf(rs.getDouble("Amount")),
                                rs.getTimestamp("PaymentDate").toString()
                            });
                            break;
                        case "AdminSettings":
                            tableData.add(new String[]{
                                String.valueOf(rs.getInt("SettingID")),
                                rs.getString("Name"),
                                String.valueOf(rs.getDouble("Value")),
                                rs.getTimestamp("LastUpdated").toString()
                            });
                            break;
                        case "Credit":
                            tableData.add(new String[]{
                                String.valueOf(rs.getInt("CreditID")),
                                String.valueOf(rs.getInt("UserID")),
                                String.valueOf(rs.getDouble("Amount")),
                                rs.getTimestamp("CreditDate").toString()
                            });
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid table name.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tableData;
    }
}
