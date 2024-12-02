package edu.ucalgary.movie;


public class Movie {
    private String movieID;
    private String title;

    // Constructor to initialize both movieID and title
    public Movie(String movieID, String title) {
        this.movieID = movieID;
        this.title = title;
    }

    // Constructor to initialize only title, with movieID set to a default value
    public Movie(String title) {
        this.movieID = "0"; // Default value or can be assigned later
        this.title = title;
    }

    // Getters and setters
    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Optional: ToString method for better representation
    @Override
    public String toString() {
        return "Movie{" +
                "movieID=" + movieID +
                ", title='" + title + '\'' +
                '}';
    }
}