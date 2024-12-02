package edu.ucalgary.user;

public class AuthenticationManager {

    public User handleLogin(String email, String password) {
        // Logic to check credentials against the database and return the appropriate User
        return new User("John Doe", email, password, true);  // Just a placeholder for now
    }

    public User handleCreateAccount(String name, String email, String password) {
        // Logic to create a new user directly using the User class
        return new User(name, email, password, true);  // Assuming the user is active by default
    }
}