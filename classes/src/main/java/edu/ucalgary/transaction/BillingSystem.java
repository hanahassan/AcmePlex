package edu.ucalgary.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import edu.ucalgary.movie.Ticket;
import edu.ucalgary.user.User;

/*
 * The BillingSystem class creates and interacts with Payment objects during payment processing, 
 * but it does not own or manage the lifecycle of the Payment object in a way that would make it a 
 * composition. 
 * The Payment class could exist independently of the BillingSystem class, and a payment might exist 
 * even if no billing system is involved.
 */
public class BillingSystem { 

    private Connection conn;

    public BillingSystem(Connection conn) {
        this.conn = conn;
    }

    public BillingSystem() {};

    // Process payment for the ticket purchase
    public void processPayment(User user, Ticket ticket) {
        try {
            // Assuming the user pays the full ticket price
            double amount = ticket.getPrice();
            Date paymentDate = new Date();

            // Create a new Payment object
            Payment payment = new Payment(0, ticket.getTicketID(), user.getUserID(), amount, paymentDate);

            // Record the payment in the Payments table
            String insertPaymentQuery = "INSERT INTO Payments (TicketID, UserID, Amount, PaymentDate) VALUES (?, ?, ?, NOW())";
            try (PreparedStatement pstmt = conn.prepareStatement(insertPaymentQuery, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, ticket.getTicketID());
                pstmt.setInt(2, user.getUserID());
                pstmt.setDouble(3, amount);
                pstmt.executeUpdate();

                // Retrieve the generated payment ID
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        payment = new Payment(generatedKeys.getInt(1), ticket.getTicketID(), user.getUserID(), amount, paymentDate);
                    }
                }
            }

            // Update the user's credits after successful payment
            String updateCreditsQuery = "UPDATE Users SET Credits = Credits - ? WHERE UserID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateCreditsQuery)) {
                pstmt.setDouble(1, amount);
                pstmt.setInt(2, user.getUserID());
                pstmt.executeUpdate();
            }

            System.out.println("Payment processed successfully: " + payment);
        } catch (SQLException e) {
            System.out.println("Error processing payment: " + e.getMessage());
        }
    }

    // Issue a refund for a cancelled ticket
    public void issueRefund(Ticket ticket) {
        try {
            if (ticket.isCancelled()) {
                // Refund logic: Update the user's credits and log the refund in the database
                String selectTicketQuery = "SELECT Price, UserID FROM Tickets WHERE TicketID = ?";
                double refundAmount = 0;
                int userID = 0;

                // Get the ticket's price and user details
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

                // Log the refund in the Payments table (Optional, for tracking purposes)
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
}
