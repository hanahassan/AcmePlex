package edu.ucalgary.movie;

import java.util.List;

public class SeatAvailabilityTracker {

    public SeatAvailabilityTracker(String data) {
        // Initialize the tracker with the provided data
        System.out.println("Seat Availability Tracker initialized with: " + data);
    }

    public void checkAvailability(List<Seat> seats) {
        // Loop through and display available seats
        for (Seat seat : seats) {
            if (seat.isAvailable()) {
                System.out.println("Seat " + seat.getSeatNumber() + " is available.");
            }
        }
    }
}