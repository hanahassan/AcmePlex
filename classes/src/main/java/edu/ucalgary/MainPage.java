package edu.ucalgary;

import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import Database.DatabaseRetriever;
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

public class MainPage {

    public static void main(String[] args) throws SQLException {
        // Create an instance of Acmeplex
        Acmeplex acmeplex = new Acmeplex();

        // Retrieve all movies from the database and add to the Acmeplex instance
        List<String[]> moviesData = DatabaseRetriever.getAllMovies();
        for (String[] movieData : moviesData) {
            Movie movie = new Movie(movieData[0], movieData[1]); // Assuming Movie constructor takes ID and Title
            acmeplex.setMovie(movie); // Add movie to the movie list
        }

        // MovieBookingSystem
        List<String[]> movieBookingSystemsData = DatabaseRetriever.getAllShowtimes(); // Modify as needed for valid data
        for (String[] systemData : movieBookingSystemsData) {
            MovieBookingSystem movieBookingSystem = new MovieBookingSystem(systemData[0]); 
            acmeplex.setMovieBookingSystem(movieBookingSystem); // Add to list
        }

        // Seats
        List<String> seatsData = DatabaseRetriever.getSeats(1); // Assuming 1 is a valid Showtime ID
        for (String seat : seatsData) {
            Seat seatObj = new Seat(seat); // Assuming Seat constructor takes SeatNumber
            acmeplex.setSeat(seatObj); // Add to list
        }

        // SeatAvailabilityTrackers
        // Assuming we don't have data for SeatAvailabilityTracker directly from the DB
        List<String[]> seatAvailabilityData = DatabaseRetriever.getAllShowtimes(); // Modify as needed
        for (var availability : seatAvailabilityData) {
            SeatAvailabilityTracker tracker = new SeatAvailabilityTracker(availability[0]); // Example constructor
            acmeplex.setSeatAvailabilityTracker(tracker); // Add to list
        }

        // Showtimes
        List<String[]> showtimesData = DatabaseRetriever.getAllShowtimes(); // Modify as needed
        for (String[] showtimeData : showtimesData) {
            try {
                // Parsing data
                int showtimeID = Integer.parseInt(showtimeData[0]);
                Movie movie = new Movie(showtimeData[1], showtimeData[2]);  // Constructor expects movieID (String), title
                Theatre theatre = new Theatre(Integer.parseInt(showtimeData[3]), showtimeData[4], showtimeData[5]);  // Constructor expects theatreID (int), name, location
                
                // Parsing the date string to java.sql.Date
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilDate = dateFormat.parse(showtimeData[6]);  // Parsing the date string
                Date showDate = new Date(utilDate.getTime());  // Converting to java.sql.Date
                
                // Parsing the time string to java.sql.Time
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                java.util.Date utilTime = timeFormat.parse(showtimeData[7]);  // Parsing the time string
                Time showTime = new Time(utilTime.getTime());  // Converting to java.sql.Time
                
                int availableSeats = Integer.parseInt(showtimeData[8]);  // Parsing available seats to int
                
                // Create Showtime object and add to acmeplex
                Showtime showtime = new Showtime(showtimeID, movie, theatre, showDate, showTime, availableSeats);
                acmeplex.setShowtime(showtime);  // Add to list
                
            } catch (ParseException e) {
                e.printStackTrace();  // Handle the exception if the format is incorrect
            } catch (NumberFormatException e) {
                e.printStackTrace();  // Handle number format exceptions for parsing integers
            }
        }


        // Theatres
        List<String[]> theatresData = DatabaseRetriever.getAllTheatres(); // Modify as needed for valid connection
        for (String[] showtimeData : showtimesData) {
            try {
                // Parsing the showtime data
                int showtimeID = Integer.parseInt(showtimeData[0]);
                Movie movie = new Movie(showtimeData[1], showtimeData[2]); // Movie constructor expects (String movieID, String title)
                
                // Correctly parse the theatreID as an integer and pass it to the Theatre constructor
                int theatreID = Integer.parseInt(showtimeData[3]); // Assuming index 3 contains theatreID
                String name = showtimeData[4]; // Assuming index 4 contains theatre name
                String location = showtimeData[5]; // Assuming index 5 contains location
                
                // Now you can call the Theatre constructor with the correct arguments
                Theatre theatre = new Theatre(theatreID, name, location);
                
                // Parsing the date string to java.sql.Date
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Assuming the format is 'yyyy-MM-dd'
                java.util.Date utilDate = dateFormat.parse(showtimeData[6]); // Parsing the date string
                Date showDate = new Date(utilDate.getTime()); // Converting to java.sql.Date
                
                // Parsing the time string to java.sql.Time
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss"); // Assuming the format is 'HH:mm:ss'
                java.util.Date utilTime = timeFormat.parse(showtimeData[7]); // Parsing the time string
                Time showTime = new Time(utilTime.getTime()); // Converting to java.sql.Time
                
                // Parsing available seats
                int availableSeats = Integer.parseInt(showtimeData[8]); // Parse available seats
                
                // Create Showtime object and add to acmeplex
                Showtime showtime = new Showtime(showtimeID, movie, theatre, showDate, showTime, availableSeats);
                acmeplex.setShowtime(showtime); // Add to list
                
            } catch (ParseException e) {
                e.printStackTrace();  // Handle parsing errors if date or time format is incorrect
            } catch (NumberFormatException e) {
                e.printStackTrace();  // Handle number format errors if any integer parsing fails
            }
        }

        // Tickets
        List<String[]> ticketsData = DatabaseRetriever.getAllShowtimes(); // Modify as needed for actual data
        for (String[] ticketData : ticketsData) {
            try {
                // Parse the necessary fields
                int ticketID = Integer.parseInt(ticketData[0]); // Assuming ticketData[0] is the ticket ID
                
                // Fetch the Showtime object using a method (you need to implement this)
                Showtime showtime = getShowtimeById(ticketData[1]); // You need to implement this function
                
                // Fetch the User object based on ticket data
                User user = getUserById(Integer.parseInt(ticketData[2])); // Assuming ticketData[2] is user ID
                
                // Fetch the Seat object based on ticket data
                Seat seat = getSeatById(Integer.parseInt(ticketData[3])); // Assuming ticketData[3] is seat ID
                
                // Parsing the date string to java.sql.Date
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Assuming the format is 'yyyy-MM-dd'
                java.util.Date utilDate = dateFormat.parse(ticketData[4]); // Parse the date string
                Date date = new Date(utilDate.getTime()); // Convert to java.sql.Date
                
                // Parse the price
                double price = Double.parseDouble(ticketData[5]); // Assuming ticketData[5] contains the price
                
                // Now create the Ticket with the correct constructor
                Ticket ticket = new Ticket(ticketID, showtime, user, seat, date, price);
                
                // Add the ticket to the list or wherever needed
                acmeplex.setTicket(ticket); // Assuming acmeplex is your container class
            } catch (ParseException e) {
                e.printStackTrace();  // Handle date parsing errors
            } catch (NumberFormatException e) {
                e.printStackTrace();  // Handle number format errors
            }
        }
    
    
        // Payables
        List<String[]> payablesData = DatabaseRetriever.getAllShowtimes(); // Modify as needed
    
        // Iterate through the payablesData list
        for (String[] payableData : payablesData) {
            Payable payable = (Payable) new BillingSystem();  // Instantiate BillingSystem with the correct constructor

            // You need to create or retrieve the correct Ticket and User objects
            Ticket ticket = getTicketById(Integer.parseInt(payableData[0]));  // Fetch Ticket by ID
            User user = getUserById(Integer.parseInt(payableData[1]));  // Fetch User by ID
            
            // Call the methods with the necessary parameters
            payable.processPayment(user, ticket);  // Ensure correct arguments are passed
            payable.issueRefund(ticket);  // Same for issueRefund

            // Assuming acmeplex is an object with a method to set payable
            acmeplex.setPayable(payable); // Add to list
        }

        // Searchables
        List<String[]> searchablesData = DatabaseRetriever.getAllShowtimes(); // Modify as needed
        for (String[] searchableData : searchablesData) {
            Searchable searchable = new Searchable() {
                // Implementing searchMovie method
                @Override
                public Movie searchMovie(String title) {
                    // Your implementation here
                    // For example, finding a movie by title
                    return new Movie("movieID", title);  // Placeholder implementation
                }
            
                // Implementing searchShowtime method
                @Override
                public Showtime searchShowtime(String movieTitle, String location) {
                    // Your implementation here
                    // For example, finding a showtime by movie title and location
                    return new Showtime(1, new Movie("movieID", movieTitle), new Theatre(1, "TheatreName", location), new java.sql.Date(System.currentTimeMillis()), new java.sql.Time(System.currentTimeMillis()), 100); // Placeholder implementation
                }
            };
            acmeplex.setSearchable(searchable); // Add to list
        }

        // EmailingSystems
        List<String[]> emailingSystemsData = DatabaseRetriever.getAllShowtimes(); // Modify as needed
        for (String[] emailSystemData : emailingSystemsData) {
            EmailingSystem emailingSystem = new EmailingSystem(); // Modify constructor if needed
            acmeplex.setEmailingSystem(emailingSystem); // Add to list
        }

        // BillingSystems
        List<String[]> billingSystemsData = DatabaseRetriever.getAllShowtimes(); // Modify as needed
        for (String[] billingData : billingSystemsData) {
            BillingSystem billingSystem = new BillingSystem(); // Example constructor
            acmeplex.setBillingSystem(billingSystem); // Add to list
        }

        // Payments
        List<String[]> paymentsData = DatabaseRetriever.getAllShowtimes(); // Modify as needed
        for (String[] paymentData : paymentsData) {
            try {
                // Parse the necessary fields
                int paymentID = Integer.parseInt(paymentData[0]); // Assuming paymentData[0] is the payment ID
                int ticketID = Integer.parseInt(paymentData[1]); // Assuming paymentData[1] is ticket ID
                int userID = Integer.parseInt(paymentData[2]); // Assuming paymentData[2] is user ID
                double amount = Double.parseDouble(paymentData[3]); // Assuming paymentData[3] is the amount
                
                // Parsing the date string to java.sql.Date
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Assuming the format is 'yyyy-MM-dd'
                java.util.Date utilDate = dateFormat.parse(paymentData[4]); // Parse the date string
                Date paymentDate = new Date(utilDate.getTime()); // Convert to java.sql.Date
                
                // Create the Payment object with the correct constructor
                Payment payment = new Payment(paymentID, ticketID, userID, amount, paymentDate);
                
                // Add the payment to the list or wherever needed
                acmeplex.setPayment(payment); // Assuming acmeplex is your container class
            } catch (ParseException e) {
                e.printStackTrace();  // Handle date parsing errors
            } catch (NumberFormatException e) {
                e.printStackTrace();  // Handle number format errors
            }
        }

        // RefundManagers
        List<String[]> refundManagersData = DatabaseRetriever.getAllShowtimes(); // Modify as needed
        for (String[] refundData : refundManagersData) {
            RefundManager refundManager = new RefundManager(); // Modify constructor if needed
            acmeplex.setRefundManager(refundManager); // Add to list
        }

        // Users
        List<String[]> usersData = DatabaseRetriever.getAllShowtimes(); // Modify as needed
        for (String[] userData : usersData) {
            int userID = Integer.parseInt(userData[0]);
            String name = userData[1];
            String email = userData[2];
            String phone = userData[3];
            boolean isRegistered = Boolean.parseBoolean(userData[4]);
            String cardNumber = userData[5];
            LocalDate cardExpiry = LocalDate.parse(userData[6]);
            LocalDate joinDate = LocalDate.parse(userData[7]);
            boolean accountFeePaid = Boolean.parseBoolean(userData[8]);
            double credits = Double.parseDouble(userData[9]);
            
            User user = new User(userID, name, email, "", phone, isRegistered, cardNumber, cardExpiry.toString(), joinDate.toString(), accountFeePaid, credits);
            acmeplex.setUser(user); // Add to list
        }

        // RegisteredUsers
        List<String[]> registeredUsersData = DatabaseRetriever.getAllShowtimes(); // Modify as needed
        for (String[] registeredUserData : registeredUsersData) {
            int userID = Integer.parseInt(registeredUserData[0]);
            String name = registeredUserData[1];
            String email = registeredUserData[2];
            String phone = registeredUserData[3];
            String password = registeredUserData[4];
            String cardNumber = registeredUserData[5];
            LocalDate cardExpiry = LocalDate.parse(registeredUserData[6]);
            LocalDate joinDate = LocalDate.parse(registeredUserData[7]);
            boolean accountFeePaid = Boolean.parseBoolean(registeredUserData[8]);
            double credits = Double.parseDouble(registeredUserData[9]);

            RegisteredUser registeredUser = new RegisteredUser(userID, name, email, phone, password, cardNumber, cardExpiry, joinDate, accountFeePaid, credits);
            acmeplex.setRegisteredUser(registeredUser); // Add to list
        }

        // Employees
        List<String[]> employeesData = DatabaseRetriever.getAllShowtimes(); // Modify as needed
        for (String[] employeeData : employeesData) {
            int userID = Integer.parseInt(employeeData[0]);
            String name = employeeData[1];
            String email = employeeData[2];
            String phone = employeeData[3];
            boolean isRegistered = Boolean.parseBoolean(employeeData[4]);
            String cardNumber = employeeData[5];
            LocalDate cardExpiry = LocalDate.parse(employeeData[6]);
            LocalDate joinDate = LocalDate.parse(employeeData[7]);
            boolean accountFeePaid = Boolean.parseBoolean(employeeData[8]);
            double credits = Double.parseDouble(employeeData[9]);
            String jobTitle = employeeData[10];
            LocalDate hireDate = LocalDate.parse(employeeData[11]);
            double salary = Double.parseDouble(employeeData[12]);

            Employee employee = new Employee(userID, name, email, phone, isRegistered, cardNumber, cardExpiry, joinDate, accountFeePaid, credits, jobTitle, hireDate, salary);
            acmeplex.setEmployee(employee); // Add to list
        }

        // AuthenticationManagers
        List<String[]> authenticationManagersData = DatabaseRetriever.getAllShowtimes(); // Modify as needed
        for (String[] authManagerData : authenticationManagersData) {
            AuthenticationManager authenticationManager = new AuthenticationManager(); // Modify constructor if needed
            acmeplex.setAuthenticationManager(authenticationManager); // Add to list
        }

        System.out.println("Acmeplex setup complete with all lists populated!");
    }
    
    // Helper methods to fetch required objects from database or other sources
    private static Showtime getShowtimeById(String id) {
        // Implement logic to retrieve Showtime object by ID
        return new Showtime(0, null, null, null, null, 0); // Placeholder
    }
                        
    private static User getUserById(int userID) {
        // Implement logic to retrieve User object by ID
        return new User(userID, "Test Name", "test@example.com", "password", "1234567890", true, "1234", "12/25", "2022-01-01", true, 100); // Placeholder
    }
                        
     private static Seat getSeatById(int seatID) {
        // Implement logic to retrieve Seat object by ID
        return new Seat(seatID); // Placeholder
    }

    private static Ticket getTicketById(int ticketID) {
        // Implement logic to retrieve Ticket by ID
        // For now, using placeholder values, including the date parsing
    
        // Define the date format (assuming it's 'yyyy-MM-dd')
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
        java.util.Date utilDate = null;
        try {
            // Parse the date string to java.util.Date
            utilDate = dateFormat.parse("2024-12-01");  // Example date string
        } catch (ParseException e) {
            e.printStackTrace(); // Handle invalid date format
        }
    
        // Convert java.util.Date to java.sql.Date
        Date paymentDate = (utilDate != null) ? new Date(utilDate.getTime()) : null;
    
        // Return the Ticket object with the parsed date
        return new Ticket(ticketID, 
                          new Showtime(ticketID, null, null, null, null, ticketID), 
                          new User(1, "Test User", "test@example.com", "", "", true, "", "", "", true, 0), 
                          new Seat("1"), 
                          paymentDate, 
                          10.0); // Placeholder values
    }
}
