package edu.ucalgary.movie;

public class Seat {
    private String seatNumber;
    private boolean isAvailable;

    // Constructor
    public Seat(String seat) {
        this.seatNumber = seat;
        this.isAvailable = true; // Initially, all seats are available
    }

    public Seat(int seatID) {
        //TODO Auto-generated constructor stub
    }

    // Getters and Setters
    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    // Methods to book and cancel seats
    public void bookSeat() {
        this.isAvailable = false; // When the seat is booked
    }

    public void cancelSeat() {
        this.isAvailable = true; // When the seat is cancelled
    }

    // toString method to display seat details
    @Override
    public String toString() {
        return "Seat{" +
                "seatNumber='" + seatNumber + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
