package edu.ucalgary.movie;

import java.sql.Date;

import edu.ucalgary.user.User;



public class MovieBookingSystem {

    public MovieBookingSystem(String systemData) {
        System.out.println("System initialized with data: " + systemData);
    }

    public void bookTicket(User user, Showtime showtime, Seat seat) {
        if (seat.isAvailable()) {
            // Generate or get the ticketID (could be auto-generated or from a database)
            int ticketID = generateTicketID();  // Implement this to generate a unique ticket ID

            // Get the current date as the purchase date
            Date purchaseDate = new Date(System.currentTimeMillis());

            // Fetch the ticket price (assuming ticket price is fixed or passed in some way)
            double price = calculateTicketPrice(showtime);  // Implement this to calculate the price

            // Create the Ticket object with all necessary parameters
            Ticket ticket = new Ticket(ticketID, showtime, user, seat, purchaseDate, price);

            // Book the seat for the user
            seat.bookSeat();  // Assuming the `Seat` class has a `book()` method to mark the seat as unavailable

            // Add payment processing or any other logic you need here
            System.out.println("Ticket booked successfully for user: " + user.getName());
        } else {
            System.out.println("The seat is unavailable for this showtime.");
        }
    }

    public void cancelBooking(Ticket ticket) {
        ticket.cancelTicket();  // Call cancelTicket() from the Ticket class

        // Handle refund logic here if needed
        System.out.println("Booking for Ticket ID " + ticket.getTicketID() + " has been cancelled.");
    }

    // Method to generate a unique ticket ID (implement this as needed)
    private int generateTicketID() {
        // For now, just return a random ID or implement logic for generating a unique ticket ID
        return (int) (Math.random() * 10000);  // Example of generating a random ticket ID
    }

    // Method to calculate the ticket price (implement based on your business logic)
    private double calculateTicketPrice(Showtime showtime) {
        // Implement your price calculation logic here. If price is fixed, return it directly.
        return 15.00;  // Example: Return a fixed price for all tickets. You can customize this.
    }
}
