package edu.ucalgary.user;

import java.time.LocalDate;

public class Employee extends User {
    private String jobTitle;
    private LocalDate hireDate;
    private double salary;

    // Constructor
        public Employee(int userID, String name, String email, String phone, boolean isRegistered,
                    String cardNumber, LocalDate cardExpiry, LocalDate joinDate, boolean accountFeePaid,
                    double credits, String jobTitle, LocalDate hireDate, double salary) {
        // Convert LocalDate to String for the parent constructor
        super(userID, name, email, "", phone, isRegistered, 
            cardNumber, cardExpiry.toString(), joinDate.toString(), accountFeePaid, credits);
        this.jobTitle = jobTitle;
        this.hireDate = hireDate;
        this.salary = salary;
    }

    // Getters and Setters
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
