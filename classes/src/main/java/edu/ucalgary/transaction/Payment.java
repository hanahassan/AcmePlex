package edu.ucalgary.transaction;

import java.util.Date;

public class Payment {
    private int paymentID;
    private int ticketID;
    private int userID;
    private double amount;
    private Date paymentDate;

    // Constructor
    public Payment(int paymentID, int ticketID, int userID, double amount, Date paymentDate) {
        this.paymentID = paymentID;
        this.ticketID = ticketID;
        this.userID = userID;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    // Getters and Setters

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentID=" + paymentID +
                ", ticketID=" + ticketID +
                ", userID=" + userID +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                '}';
    }
}
