package edu.ucalgary.movie;
import java.sql.Date;

import edu.ucalgary.user.User;

public class Ticket {
    private int ticketID;
    private Showtime showtime;
    private User user;
    private Seat seat;
    private Date purchaseDate;
    private double price;
    private boolean isCancelled;

    // Constructor
    public Ticket(int ticketID, Showtime showtime, User user, Seat seat, java.util.Date date, double price) {
        this.ticketID = ticketID;
        this.showtime = showtime;
        this.user = user;
        this.seat = seat;
        this.purchaseDate = (Date) date;
        this.price = price;
        this.isCancelled = false; // Default value indicating ticket is not cancelled
    }

    // Getters and Setters
    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    // Method to cancel the ticket
    public void cancelTicket() {
        if (!isCancelled) {
            isCancelled = true;
            System.out.println("Ticket " + ticketID + " has been cancelled.");
        } else {
            System.out.println("Ticket " + ticketID + " is already cancelled.");
        }
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketID=" + ticketID +
                ", showtime=" + showtime +
                ", user=" + user +
                ", seat=" + seat +
                ", purchaseDate=" + purchaseDate +
                ", price=" + price +
                ", isCancelled=" + isCancelled +
                '}';
    }
}
