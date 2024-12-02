package edu.ucalgary.notification;

import edu.ucalgary.interfaces.EmailService;
import edu.ucalgary.user.User;

public class EmailingSystem implements EmailService {
    @Override
    public void sendEmail(User user, String subject, String message) {
        // Simulate sending email
        System.out.println("Sending email to " + user.getEmail() + " with subject: " + subject);
        System.out.println("Message: " + message);
    }

}