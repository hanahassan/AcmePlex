package edu.ucalgary.user;

import java.time.LocalDate;

public class RegisteredUser extends User {
    private String password;
    private boolean accountFeePaid;
    private double credits;

    // Constants
    public static final double ANNUAL_ACCOUNT_FEE = 20.00; // Annual fee for RUs
    public static final double RESERVED_SEATS_PERCENTAGE = 0.10; // 10% of seats reserved for RUs

    // Constructor
    public RegisteredUser(int userID, String name, String email, String phone, String password,
                      String cardNumber, LocalDate cardExpiry, LocalDate joinDate,
                      boolean accountFeePaid, double credits) {
        super(userID, name, email, password, phone, true, 
            cardNumber, cardExpiry.toString(), joinDate.toString(), accountFeePaid, credits); // Call parent constructor
        this.password = password;
        this.accountFeePaid = accountFeePaid;
        this.credits = credits;
    }   

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAccountFeePaid() {
        return accountFeePaid;
    }

    public void setAccountFeePaid(boolean accountFeePaid) {
        this.accountFeePaid = accountFeePaid;
    }

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }

    // Functional Methods
    public void payAnnualFee() {
        if (!accountFeePaid) {
            accountFeePaid = true;
            credits -= ANNUAL_ACCOUNT_FEE; // Deduct from credits if applicable
            System.out.println("Annual fee paid successfully. Remaining credits: $" + credits);
        } else {
            System.out.println("Annual fee is already paid.");
        }
    }

    public boolean isEligibleForReservedSeat(int totalSeats, int reservedSeatsTaken) {
        int maxReservedSeats = (int) Math.floor(totalSeats * RESERVED_SEATS_PERCENTAGE);
        return reservedSeatsTaken < maxReservedSeats;
    }

    public void cancelTicketWithoutAdminFee() {
        System.out.println("Ticket cancelled without admin fee.");
    }

    public void receiveMovieNews(String news) {
        System.out.println("Exclusive News for Registered User: " + news);
    }

    // Overridden toString Method
    @Override
    public String toString() {
        return super.toString() +
                "\nPassword: (hidden for security)" +
                "\nAccount Fee Paid: " + accountFeePaid +
                "\nCredits: $" + credits;
    }
}
