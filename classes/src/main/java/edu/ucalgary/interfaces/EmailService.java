package edu.ucalgary.interfaces;

import edu.ucalgary.user.User;

public interface EmailService {
    public void sendEmail(User user, String subject, String message);
}