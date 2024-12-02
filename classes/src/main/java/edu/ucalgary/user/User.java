package edu.ucalgary.user;

public class User {
    private int userID;
    private String name;
    private String email;
    private String password;
    private String phone;
    private boolean isRegistered;
    private String cardNumber;
    private String cardExpiry;
    private String joinDate;
    private boolean accountFeePaid;
    private double credits;

    // Constructor
    public User(int userID, String name, String email, String password, String phone, boolean isRegistered, 
                String cardNumber, String cardExpiry, String joinDate, boolean accountFeePaid, double credits) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.isRegistered = isRegistered;
        this.cardNumber = cardNumber;
        this.cardExpiry = cardExpiry;
        this.joinDate = joinDate;
        this.accountFeePaid = accountFeePaid;
        this.credits = credits;
    }

        // New constructor with only the basic parameters
    public User(String name, String email, String password, boolean isRegistered) {
        // Setting default values for other fields
        this.userID = 0; // Placeholder for userID
        this.phone = ""; // Default empty phone number
        this.isRegistered = isRegistered;
        this.cardNumber = ""; // Default empty card number
        this.cardExpiry = ""; // Default empty card expiry
        this.joinDate = ""; // Default empty join date
        this.accountFeePaid = false; // Default account fee status
        this.credits = 0.0; // Default credits
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public boolean isRegistered() { return isRegistered; }
    public void setRegistered(boolean registered) { isRegistered = registered; }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getCardExpiry() { return cardExpiry; }
    public void setCardExpiry(String cardExpiry) { this.cardExpiry = cardExpiry; }

    public String getJoinDate() { return joinDate; }
    public void setJoinDate(String joinDate) { this.joinDate = joinDate; }

    public boolean isAccountFeePaid() { return accountFeePaid; }
    public void setAccountFeePaid(boolean accountFeePaid) { this.accountFeePaid = accountFeePaid; }

    public double getCredits() { return credits; }
    public void setCredits(double credits) { this.credits = credits; }
}
