package edu.ucalgary;

import java.util.ArrayList;
import java.util.List;

import edu.ucalgary.interfaces.EmailService;
import edu.ucalgary.interfaces.Payable;
import edu.ucalgary.interfaces.Searchable;
import edu.ucalgary.movie.Movie;
import edu.ucalgary.movie.MovieBookingSystem;
import edu.ucalgary.movie.Seat;
import edu.ucalgary.movie.SeatAvailabilityTracker;
import edu.ucalgary.movie.Showtime;
import edu.ucalgary.movie.Theatre;
import edu.ucalgary.movie.Ticket;
import edu.ucalgary.notification.EmailingSystem;
import edu.ucalgary.transaction.BillingSystem;
import edu.ucalgary.transaction.Payment;
import edu.ucalgary.transaction.RefundManager;
import edu.ucalgary.user.AuthenticationManager;
import edu.ucalgary.user.Employee;
import edu.ucalgary.user.RegisteredUser;
import edu.ucalgary.user.User;

public class Acmeplex {
    // Lists to store the various objects
    private List<Movie> movies;
    private List<MovieBookingSystem> movieBookingSystems;
    private List<Seat> seats;
    private List<SeatAvailabilityTracker> seatAvailabilityTrackers;
    private List<Showtime> showtimes;
    private List<Theatre> theatres;
    private List<Ticket> tickets;
    private List<EmailService> emailServices;
    private List<Payable> payables;
    private List<Searchable> searchables;
    private List<EmailingSystem> emailingSystems;
    private List<BillingSystem> billingSystems;
    private List<Payment> payments;
    private List<RefundManager> refundManagers;
    private List<User> users;
    private List<RegisteredUser> registeredUsers;
    private List<Employee> employees;
    private List<AuthenticationManager> authenticationManagers;

    // Constructor to initialize lists
    public Acmeplex() {
        this.movies = new ArrayList<>();
        this.movieBookingSystems = new ArrayList<>();
        this.seats = new ArrayList<>();
        this.seatAvailabilityTrackers = new ArrayList<>();
        this.showtimes = new ArrayList<>();
        this.theatres = new ArrayList<>();
        this.tickets = new ArrayList<>();
        this.emailServices = new ArrayList<>();
        this.payables = new ArrayList<>();
        this.searchables = new ArrayList<>();
        this.emailingSystems = new ArrayList<>();
        this.billingSystems = new ArrayList<>();
        this.payments = new ArrayList<>();
        this.refundManagers = new ArrayList<>();
        this.users = new ArrayList<>();
        this.registeredUsers = new ArrayList<>();
        this.employees = new ArrayList<>();
        this.authenticationManagers = new ArrayList<>();
    }

    // Getters
    public List<Movie> getMovies() {
        return movies;
    }

    public List<MovieBookingSystem> getMovieBookingSystems() {
        return movieBookingSystems;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public List<SeatAvailabilityTracker> getSeatAvailabilityTrackers() {
        return seatAvailabilityTrackers;
    }

    public List<Showtime> getShowtimes() {
        return showtimes;
    }

    public List<Theatre> getTheatres() {
        return theatres;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public List<EmailService> getEmailServices() {
        return emailServices;
    }

    public List<Payable> getPayables() {
        return payables;
    }

    public List<Searchable> getSearchables() {
        return searchables;
    }

    public List<EmailingSystem> getEmailingSystems() {
        return emailingSystems;
    }

    public List<BillingSystem> getBillingSystems() {
        return billingSystems;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public List<RefundManager> getRefundManagers() {
        return refundManagers;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<RegisteredUser> getRegisteredUsers() {
        return registeredUsers;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<AuthenticationManager> getAuthenticationManagers() {
        return authenticationManagers;
    }

    // Setters that append to lists
    public void setMovie(Movie movie) {
        this.movies.add(movie);
    }

    public void setMovieBookingSystem(MovieBookingSystem movieBookingSystem) {
        this.movieBookingSystems.add(movieBookingSystem);
    }

    public void setSeat(Seat seat) {
        this.seats.add(seat);
    }

    public void setSeatAvailabilityTracker(SeatAvailabilityTracker tracker) {
        this.seatAvailabilityTrackers.add(tracker);
    }

    public void setShowtime(Showtime showtime) {
        this.showtimes.add(showtime);
    }

    public void setTheatre(Theatre theatre) {
        this.theatres.add(theatre);
    }

    public void setTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }

    public void setEmailService(EmailService emailService) {
        this.emailServices.add(emailService);
    }

    public void setPayable(Payable payable) {
        this.payables.add(payable);
    }

    public void setSearchable(Searchable searchable) {
        this.searchables.add(searchable);
    }

    public void setEmailingSystem(EmailingSystem emailingSystem) {
        this.emailingSystems.add(emailingSystem);
    }

    public void setBillingSystem(BillingSystem billingSystem) {
        this.billingSystems.add(billingSystem);
    }

    public void setPayment(Payment payment) {
        this.payments.add(payment);
    }

    public void setRefundManager(RefundManager refundManager) {
        this.refundManagers.add(refundManager);
    }

    public void setUser(User user) {
        this.users.add(user);
    }

    public void setRegisteredUser(RegisteredUser registeredUser) {
        this.registeredUsers.add(registeredUser);
    }

    public void setEmployee(Employee employee) {
        this.employees.add(employee);
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManagers.add(authenticationManager);
    }
}
