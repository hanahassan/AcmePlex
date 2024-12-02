package edu.ucalgary.interfaces;

import edu.ucalgary.movie.Ticket;
import edu.ucalgary.user.User;

public interface Payable {
    public void processPayment();
    public void issueRefund();
    public void processPayment(User user, Ticket ticket);
    public void issueRefund(Ticket ticket);
}