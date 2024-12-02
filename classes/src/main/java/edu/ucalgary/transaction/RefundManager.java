package edu.ucalgary.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ucalgary.movie.Ticket;

/**
 * RefundManager handles cancellations and issuing credits for Registered Users.
 * This class processes ticket cancellations and issues refunds by updating 
 * the user's credits and logging the refund in the Payments table.
 */
public class RefundManager {

    private Connection conn;

    public RefundManager(Connection conn) {
        this.conn = conn;
    }

    public RefundManager() {
        //TODO Auto-generated constructor stub
    }

    /**
     * Issues a refund for a cancelled ticket.
     * @param ticket The cancelled ticket to process a refund for.
     */
    public void issueRefund(Ticket ticket) {
        try {
            if (ticket.isCancelled()) {
                // Refund logic: Retrieve the ticket price and user details
                String selectTicketQuery = "SELECT Price, UserID FROM Tickets WHERE TicketID = ?";
                double refundAmount = 0;
                int userID = 0;

                // Get ticket price and user details
                try (PreparedStatement pstmt = conn.prepareStatement(selectTicketQuery)) {
                    pstmt.setInt(1, ticket.getTicketID());
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        refundAmount = rs.getDouble("Price");
                        userID = rs.getInt("UserID");
                    }
                }

                // Update the user's credit balance to include the refund
                String updateCreditsQuery = "UPDATE Users SET Credits = Credits + ? WHERE UserID = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(updateCreditsQuery)) {
                    pstmt.setDouble(1, refundAmount);
                    pstmt.setInt(2, userID);
                    pstmt.executeUpdate();
                }

                // Log the refund in the Payments table
                String insertRefundQuery = "INSERT INTO Payments (TicketID, UserID, Amount, PaymentDate) VALUES (?, ?, ?, NOW())";
                try (PreparedStatement pstmt = conn.prepareStatement(insertRefundQuery)) {
                    pstmt.setInt(1, ticket.getTicketID());
                    pstmt.setInt(2, userID);
                    pstmt.setDouble(3, -refundAmount); // Negative value for refund
                    pstmt.executeUpdate();
                }

                System.out.println("Refund issued for ticket " + ticket.getTicketID());
            } else {
                System.out.println("Ticket is not cancelled, cannot issue a refund.");
            }
        } catch (SQLException e) {
            System.out.println("Error processing refund: " + e.getMessage());
        }
    }

    /**
     * Handles cancellations of tickets by updating the ticket status and initiating refund.
     * @param ticket The ticket to be cancelled.
     */
    public void handleCancellation(Ticket ticket) {
        try {
            if (!ticket.isCancelled()) {
                // Mark the ticket as cancelled
                String cancelTicketQuery = "UPDATE Tickets SET Status = 'Cancelled' WHERE TicketID = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(cancelTicketQuery)) {
                    pstmt.setInt(1, ticket.getTicketID());
                    pstmt.executeUpdate();
                }

                // Issue refund for the cancelled ticket
                issueRefund(ticket);

                System.out.println("Ticket " + ticket.getTicketID() + " has been cancelled.");
            } else {
                System.out.println("Ticket " + ticket.getTicketID() + " is already cancelled.");
            }
        } catch (SQLException e) {
            System.out.println("Error processing cancellation: " + e.getMessage());
        }
    }
}
