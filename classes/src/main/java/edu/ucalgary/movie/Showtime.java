package edu.ucalgary.movie;

import java.sql.Date;
import java.sql.Time;

public class Showtime {
    private int showtimeID;
    private Movie movie;
    private Theatre theatre;
    private Date showDate;
    private Time showTime;
    private int availableSeats;
    private int reservedSeats;

    // Constructor
    public Showtime(int showtimeID, Movie movie, Theatre theatre, java.util.Date showDate2, Time showTime, int availableSeats) {
        this.showtimeID = showtimeID;
        this.movie = movie;
        this.theatre = theatre;
        this.showDate = showDate;
        this.showTime = showTime;
        this.availableSeats = availableSeats;
        this.reservedSeats = 0; // Initially no seats are reserved
    }

    // Getters and Setters
    public int getShowtimeID() {
        return showtimeID;
    }

    public void setShowtimeID(int showtimeID) {
        this.showtimeID = showtimeID;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public void setTheatre(Theatre theatre) {
        this.theatre = theatre;
    }

    public Date getShowDate() {
        return showDate;
    }

    public void setShowDate(Date showDate) {
        this.showDate = showDate;
    }

    public Time getShowTime() {
        return showTime;
    }

    public void setShowTime(Time showTime) {
        this.showTime = showTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public int getReservedSeats() {
        return reservedSeats;
    }

    public void setReservedSeats(int reservedSeats) {
        this.reservedSeats = reservedSeats;
    }

    // Method to reserve seats for the showtime
    public boolean reserveSeats(int numberOfSeats) {
        if (numberOfSeats <= availableSeats - reservedSeats) {
            reservedSeats += numberOfSeats;
            System.out.println(numberOfSeats + " seats reserved for showtime " + showtimeID);
            return true;
        } else {
            System.out.println("Not enough available seats for reservation.");
            return false;
        }
    }

    // Method to cancel reserved seats
    public boolean cancelReservation(int numberOfSeats) {
        if (numberOfSeats <= reservedSeats) {
            reservedSeats -= numberOfSeats;
            System.out.println(numberOfSeats + " reserved seats cancelled for showtime " + showtimeID);
            return true;
        } else {
            System.out.println("Cannot cancel more seats than were reserved.");
            return false;
        }
    }

    @Override
    public String toString() {
        return "Showtime{" +
                "showtimeID=" + showtimeID +
                ", movie=" + movie.getTitle() +
                ", theatre=" + theatre.getName() +
                ", showDate=" + showDate +
                ", showTime=" + showTime +
                ", availableSeats=" + availableSeats +
                ", reservedSeats=" + reservedSeats +
                '}';
    }
}
