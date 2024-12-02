package edu.ucalgary.user;
import java.util.List;

import edu.ucalgary.movie.Seat;
import edu.ucalgary.movie.Showtime;

public class RUManager {

    // Method for Registered Users to reserve 10% of the seats before the public
    public void reserveSeatsForRU(User user, Showtime showtime, List<Seat> seats) {
        if (user instanceof RegisteredUser) {
            int reservedSeatsCount = (int) (seats.size() * 0.1); // 10% of total seats
            for (int i = 0; i < reservedSeatsCount; i++) {
                Seat seat = seats.get(i);
                seat.bookSeat(); // Mark as booked for Registered Users
                showtime.setReservedSeats(showtime.getReservedSeats() + 1); // Increment reserved seats
            }
            System.out.println("10% of seats reserved for Registered User.");
        } else {
            System.out.println("User is not registered. Cannot reserve seats.");
        }
    }

    // Method to handle the annual fee payment for Registered Users
    public void handleAnnualFee(User user) {
        if (user instanceof RegisteredUser) {
            RegisteredUser registeredUser = (RegisteredUser) user;
            if (!registeredUser.isAccountFeePaid()) {
                System.out.println("Annual fee not paid. Processing payment...");
                // Process the annual fee payment logic here
                registeredUser.payAnnualFee();
                System.out.println("Annual fee paid.");
            } else {
                System.out.println("Annual fee already paid.");
            }
        } else {
            System.out.println("Only Registered Users are required to pay the annual fee.");
        }
    }
}