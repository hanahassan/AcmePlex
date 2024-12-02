package edu.ucalgary.interfaces;
import edu.ucalgary.movie.Movie;
import edu.ucalgary.movie.Showtime;

public interface Searchable {

   
    public Movie searchMovie(String title);
    public Showtime searchShowtime(String movieTitle, String location);
}
